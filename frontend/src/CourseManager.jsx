import React, { useState, useEffect } from 'react';
import { 
  Table, Select, Space, Card, Typography, message, 
  Button, Modal, Form, Input, InputNumber, Tag, Popconfirm 
} from 'antd';
import { 
  BookOutlined, PlusOutlined, EditOutlined, 
  DeleteOutlined, SearchOutlined 
} from '@ant-design/icons';
import * as courseApi from './api/course';
import { getClassesList } from './api/student';

const { Title } = Typography;
const { Option } = Select;

const CourseManager = () => {
  const [form] = Form.useForm();
  const [editForm] = Form.useForm();
  const [courses, setCourses] = useState([]);
  const [classList, setClassList] = useState([]);
  const [loading, setLoading] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  
  // 核心状态区分
  const [isEditMode, setIsEditMode] = useState(false); // 是否是点击了“修改”按钮
  const [isExistingCourse, setIsExistingCourse] = useState(false); // 是否在新增时识别到了已有课程

  useEffect(() => {
    fetchClassList();
  }, []);

  const fetchClassList = async () => {
    try {
      const res = await getClassesList();
      setClassList(res.data.sort((a, b) => parseInt(a) - parseInt(b)));
    } catch (e) { message.error('获取班级列表失败'); }
  };

  // 1. 查询课表 (GET + params)
  const handleSearch = async () => {
    try {
      const values = await form.validateFields();
      setLoading(true);
      const query = { ...values, courseId: null, courseName: null, type: null, credit: null };
      const res = await courseApi.getCourses(query);
      setCourses(res.data);
    } catch (e) { message.error('查询失败'); }
    finally { setLoading(false); }
  };

  // 2. 核心逻辑：输入课程名自动识别
  const handleAutoFill = (e) => {
    if (isEditMode) return; // 修改模式下不触发识别
    const name = e.target.value;
    const match = courses.find(c => c.courseName === name);
    
    if (match) {
      message.info('检测到已有课程，请仅输入要追加的班级号');
      setIsExistingCourse(true);
      editForm.setFieldsValue({
        schoolYear: match.schoolYear,
        semester: match.semester,
        type: match.type,
        credit: match.credit,
        courseId: match.courseId // 必须带上ID以便后端识别
      });
    } else {
      setIsExistingCourse(false);
    }
  };

  // 3. 提交处理
  const handleSave = async () => {
    try {
      const values = await editForm.validateFields();
      let res;
      
      if (isEditMode) {
        // 场景 A: 修改基础信息 (PUT)
        res = await courseApi.updateCourse(values);
      } else {
        // 场景 B: 添加课程或追加班级 (POST -> 对应后端 addNewCourse)
        // 注意：追加班级时 values 里已经通过 autoFill 带上了 courseId
        res = await courseApi.saveCourse(values);
      }

      if (res.data === 200 || res.data === 0) {
        message.success('操作成功');
        setIsModalOpen(false);
        handleSearch();
      } else if (res.data === 402) {
        message.warning('该班级已存在于此课程中');
      } else {
        message.error('操作失败，错误码：' + res.data);
      }
    } catch (e) { message.error('提交失败'); }
  };

  // 4. 从课程移除班级 (DELETE /api/course/classes)
  const handleRemoveClass = async (record, targetClass) => {
    try {
      const res = await courseApi.deleteClassFromCourse({ ...record, classes: targetClass });
      if (res.data === 200) {
        message.success(`班级 ${targetClass} 已移除`);
        handleSearch();
      }
    } catch (e) { message.error('移除失败'); }
  };

  const columns = [
    { title: '课程名称', dataIndex: 'courseName' },
    { title: '学年', dataIndex: 'schoolYear' },
    { title: '学期', dataIndex: 'semester' },
    { title: '类型', dataIndex: 'type' },
    { title: '学分', dataIndex: 'credit' },
    { 
      title: '授课班级', 
      dataIndex: 'classes',
      render: (text, record) => (
        <div style={{ display: 'flex', flexWrap: 'wrap', gap: '4px' }}>
          {text?.split('|').filter(i => i).map(c => (
            <Tag 
              color="blue" 
              key={c} 
              closable 
              onClose={(e) => {
                e.preventDefault();
                Modal.confirm({
                  title: '确认移除',
                  content: `从课程中移除 ${c} 班？`,
                  onOk: () => handleRemoveClass(record, c)
                });
              }}
            >
              {c}班
            </Tag>
          ))}
        </div>
      )
    },
    {
      title: '操作',
      render: (_, record) => (
        <Space>
          <Button type="link" icon={<EditOutlined />} onClick={() => {
            setIsEditMode(true);
            setIsExistingCourse(false); // 修改模式不锁定字段
            editForm.setFieldsValue(record);
            setIsModalOpen(true);
          }}>修改</Button>
          <Popconfirm title="删除整门课程？" onConfirm={() => courseApi.deleteCourse(record).then(handleSearch)}>
            <Button type="link" danger icon={<DeleteOutlined />}>删除</Button>
          </Popconfirm>
        </Space>
      ),
    },
  ];

  return (
    <div style={{ padding: '20px' }}>
      <Card>
        <Space direction="vertical" style={{ width: '100%', gap: '20px' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between' }}>
            <Title level={3}><BookOutlined /> 课程与排班管理</Title>
            <Button type="primary" icon={<PlusOutlined />} onClick={() => {
              setIsEditMode(false);
              setIsExistingCourse(false);
              editForm.resetFields();
              setIsModalOpen(true);
            }}>新增课程/追加班级</Button>
          </div>

          <Form form={form} layout="inline" initialValues={{ schoolYear: '2025-2026', semester: '秋' }}>
            <Form.Item name="schoolYear" label="学年"><Input style={{ width: 120 }} /></Form.Item>
            <Form.Item name="semester" label="学期">
              <Select style={{ width: 80 }}><Option value="秋">秋</Option><Option value="春">春</Option></Select>
            </Form.Item>
            <Form.Item name="classes" label="班级" rules={[{ required: true }]}>
              <Select style={{ width: 120 }} placeholder="选班级">
                {classList.map(c => <Option key={c} value={c}>{c}班</Option>)}
              </Select>
            </Form.Item>
            <Form.Item><Button type="primary" icon={<SearchOutlined />} onClick={handleSearch}>查询</Button></Form.Item>
          </Form>

          <Table columns={columns} dataSource={courses} rowKey="courseId" loading={loading} />
        </Space>
      </Card>

      <Modal 
        title={isEditMode ? "修改课程基础信息" : (isExistingCourse ? "已有课程：追加班级" : "新增完整课程")} 
        open={isModalOpen} 
        onOk={handleSave} 
        onCancel={() => setIsModalOpen(false)}
        destroyOnClose
      >
        <Form form={editForm} layout="vertical">
          <Form.Item name="courseId" hidden><Input /></Form.Item>
          
          <Form.Item name="courseName" label="课程名称" rules={[{ required: true }]}>
            <Input 
              onBlur={handleAutoFill} 
              disabled={isEditMode} // 修改模式下不建议改名（通常改名涉及ID）
              placeholder="输入课程名后点击空白处可识别已有课程" 
            />
          </Form.Item>

          <Form.Item 
            name="classes" 
            label="班级号" 
            rules={[{ required: true }]}
          >
            <Input 
              disabled={isEditMode} // 修改基础信息时锁定班级，班级移除在表格操作
              placeholder={isEditMode ? "修改班级请在表格中移除或使用追加" : "请输入班级号"} 
            />
          </Form.Item>

          <Space size="large">
            <Form.Item name="schoolYear" label="学年" rules={[{ required: true }]}>
              <Input disabled={isExistingCourse} placeholder="2025-2026" />
            </Form.Item>
            <Form.Item name="semester" label="学期" rules={[{ required: true }]}>
              <Select disabled={isExistingCourse}><Option value="秋">秋</Option><Option value="春">春</Option></Select>
            </Form.Item>
          </Space>

          <Space size="large">
            <Form.Item name="type" label="类型" rules={[{ required: true }]}>
              <Select disabled={isExistingCourse}><Option value="必修课">必修课</Option><Option value="选修课">选修课</Option></Select>
            </Form.Item>
            <Form.Item name="credit" label="学分" rules={[{ required: true }]}>
              <InputNumber disabled={isExistingCourse} min={1} />
            </Form.Item>
          </Space>
        </Form>
      </Modal>
    </div>
  );
};

export default CourseManager;