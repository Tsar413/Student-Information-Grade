import React, { useState, useEffect } from 'react';
import { 
  Table, Select, Space, Card, Typography, message, 
  Button, Modal, Form, Input, InputNumber, Tag, Popconfirm 
} from 'antd';
import { BookOutlined, PlusOutlined, EditOutlined, DeleteOutlined, SearchOutlined } from '@ant-design/icons';
import * as courseApi from './api/course';
import { getClassesList } from './api/student'; // 复用学生模块的获取班级接口

const { Title } = Typography;
const { Option } = Select;

const CourseManager = () => {
  const [form] = Form.useForm();
  const [editForm] = Form.useForm();
  const [courses, setCourses] = useState([]);
  const [classList, setClassList] = useState([]);
  const [loading, setLoading] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingCourse, setEditingCourse] = useState(null);

  // 初始化获取班级列表
  useEffect(() => {
    const init = async () => {
      try {
        const res = await getClassesList();
        setClassList(res.data.sort((a, b) => parseInt(a) - parseInt(b)));
      } catch (e) { message.error('获取班级列表失败'); }
    };
    init();
  }, []);

  // 执行查询
  const handleSearch = async () => {
    try {
      const values = await form.validateFields();
      setLoading(true);
  
      // 构造参数对象
      const query = {
        schoolYear: values.schoolYear,
        semester: values.semester,
        classes: values.classes
        // GET 请求通常不需要传 courseId: null 这种冗余字段，Spring 会自动处理缺省值
      };
  
      const res = await courseApi.getCourses(query);
      setCourses(res.data);
    } catch (e) {
      message.error('查询课程失败');
    } finally {
      setLoading(false);
    }
  };

  // 保存或更新课程
  const handleSave = async () => {
    try {
      const values = await editForm.validateFields();
      if (editingCourse) {
        await courseApi.updateCourse(values);
        message.success('修改成功');
      } else {
        await courseApi.saveCourse({ ...values, courseId: null });
        message.success('新增成功');
      }
      setIsModalOpen(false);
      handleSearch(); // 强制刷新列表
    } catch (e) {}
  };

  // 删除整门课程
  const handleDeleteCourse = async (record) => {
    try {
      await courseApi.deleteCourse(record);
      message.success('课程已删除');
      handleSearch();
    } catch (e) { message.error('删除失败'); }
  };

  const columns = [
    { title: '课程名称', dataIndex: 'courseName', key: 'courseName' },
    { title: '学年', dataIndex: 'schoolYear', key: 'schoolYear' },
    { title: '学期', dataIndex: 'semester', key: 'semester', render: (text) => <Tag color="blue">{text}</Tag> },
    { title: '类型', dataIndex: 'type', key: 'type' },
    { title: '学分', dataIndex: 'credit', key: 'credit' },
    { 
      title: '授课班级', 
      dataIndex: 'classes', 
      key: 'classes',
      render: (text) => text?.split('|').filter(i => i).map(c => <Tag key={c}>{c}班</Tag>)
    },
    {
      title: '操作',
      key: 'action',
      render: (_, record) => (
        <Space>
          <Button type="link" icon={<EditOutlined />} onClick={() => {
            setEditingCourse(record);
            editForm.setFieldsValue(record);
            setIsModalOpen(true);
          }}>编辑</Button>
          <Popconfirm title="确定删除该课程吗？" onConfirm={() => handleDeleteCourse(record)}>
            <Button type="link" danger icon={<DeleteOutlined />}>删除</Button>
          </Popconfirm>
        </Space>
      ),
    },
  ];

  return (
    <div style={{ padding: '20px' }}>
      <Card>
        <Space direction="vertical" style={{ width: '100%' }} size="large">
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
            <Title level={3}><BookOutlined /> 课程信息管理</Title>
            <Button type="primary" icon={<PlusOutlined />} onClick={() => {
              setEditingCourse(null);
              editForm.resetFields();
              setIsModalOpen(true);
            }}>新增课程</Button>
          </div>

          {/* 筛选表单 */}
          <Form form={form} layout="inline" initialValues={{ schoolYear: '2025-2026', semester: '秋' }}>
            <Form.Item name="schoolYear" label="学年">
              <Select style={{ width: 150 }}>
                <Option value="2025-2026">2025-2026</Option>
              </Select>
            </Form.Item>
            <Form.Item name="semester" label="学期">
              <Select style={{ width: 100 }}>
                <Option value="秋">秋</Option>
                <Option value="春">春</Option>
              </Select>
            </Form.Item>
            <Form.Item name="classes" label="班级" rules={[{ required: true, message: '请选择班级' }]}>
              <Select style={{ width: 120 }} placeholder="选择班级">
                {classList.map(c => <Option key={c} value={c}>{c}班</Option>)}
              </Select>
            </Form.Item>
            <Form.Item>
              <Button type="primary" icon={<SearchOutlined />} onClick={handleSearch}>查询课表</Button>
            </Form.Item>
          </Form>

          <Table 
            columns={columns} 
            dataSource={courses} 
            rowKey="courseId" 
            loading={loading}
            pagination={false}
          />
        </Space>
      </Card>

      {/* 新增/编辑课程弹窗 */}
      <Modal 
        title={editingCourse ? "修改课程信息" : "新增课程"} 
        open={isModalOpen} 
        onOk={handleSave} 
        onCancel={() => setIsModalOpen(false)}
        width={600}
      >
        <Form form={editForm} layout="vertical">
          <Form.Item name="courseName" label="课程名称" rules={[{ required: true }]}><Input /></Form.Item>
          <Space size="large" style={{ display: 'flex' }}>
            <Form.Item name="schoolYear" label="学年" rules={[{ required: true }]} style={{ flex: 1 }}>
              <Input placeholder="2025-2026" />
            </Form.Item>
            <Form.Item name="semester" label="学期" rules={[{ required: true }]} style={{ flex: 1 }}>
              <Select>
                <Option value="秋">秋</Option>
                <Option value="春">春</Option>
              </Select>
            </Form.Item>
          </Space>
          <Space size="large" style={{ display: 'flex' }}>
            <Form.Item name="type" label="课程类型" rules={[{ required: true }]} style={{ flex: 1 }}>
              <Select placeholder="请选择">
                <Option value="必修课">必修课</Option>
                <Option value="选修课">选修课</Option>
              </Select>
            </Form.Item>
            <Form.Item name="credit" label="学分" rules={[{ required: true }]} style={{ flex: 1 }}>
              <InputNumber min={1} style={{ width: '100%' }} />
            </Form.Item>
          </Space>
          <Form.Item name="classes" label="关联班级 (多个班级请用 | 隔开)" rules={[{ required: true }]}>
            <Input placeholder="例如: 2501|2502" />
          </Form.Item>
          {editingCourse && <Form.Item name="courseId" hidden><Input /></Form.Item>}
        </Form>
      </Modal>
    </div>
  );
};

export default CourseManager;