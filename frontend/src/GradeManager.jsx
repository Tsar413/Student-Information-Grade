import React, { useState, useEffect } from 'react';
import { 
  Table, Select, Space, Card, Typography, message, Tag, 
  Button, Modal, Form, Input, InputNumber, Popconfirm, Radio, Upload 
} from 'antd';
import { 
  BarChartOutlined, SearchOutlined, EditOutlined, 
  DeleteOutlined, PlusOutlined, TeamOutlined, UserOutlined,
  FileExcelOutlined, UploadOutlined 
} from '@ant-design/icons';
import * as gradeApi from './api/grade'; 

const { Title } = Typography;
const { Option } = Select;

// --- 批量上传子组件 ---
const BatchUploadModal = ({ classId, currentYear, currentSemester, onRefresh }) => {
  const [visible, setVisible] = useState(false);
  const [form] = Form.useForm();
  const [fileList, setFileList] = useState([]);
  const [loading, setLoading] = useState(false);
  const [courseOptions, setCourseOptions] = useState([]);

  const handleOpen = async () => {
    if (!classId) return message.warning('请先在主页面选择班级');
    setVisible(true);
    form.setFieldsValue({ schoolYear: currentYear, semester: currentSemester });
    try {
      const res = await gradeApi.getCourses({ classes: classId, schoolYear: currentYear, semester: currentSemester });
      setCourseOptions(res.data.map(c => c.courseName));
    } catch (e) { message.error('加载课程失败'); }
  };

  const handleUpload = async () => {
    const values = await form.validateFields();
    if (fileList.length === 0) return message.warning('请选择文件');
    const fileObj = fileList[0].originFileObj || fileList[0];
    setLoading(true);
    try {
      await gradeApi.uploadMultiplyGrades(fileObj, values);
      message.success('上传成功');
      setVisible(false);
      setFileList([]);
      onRefresh();
    } catch (e) { message.error('上传失败'); } finally { setLoading(false); }
  };

  return (
    <>
      <Button icon={<FileExcelOutlined />} onClick={handleOpen}>批量录入</Button>
      <Modal title="批量上传成绩" open={visible} onCancel={() => setVisible(false)} onOk={handleUpload} confirmLoading={loading} destroyOnClose>
        <Form form={form} layout="vertical">
          <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '16px' }}>
            <Form.Item name="schoolYear" label="学年" rules={[{ required: true }]}><Input /></Form.Item>
            <Form.Item name="semester" label="学期" rules={[{ required: true }]}>
              <Select><Option value="秋">秋</Option><Option value="春">春</Option></Select>
            </Form.Item>
          </div>
          <Form.Item name="courseName" label="录入科目" rules={[{ required: true }]}>
            <Select placeholder="选择科目">{courseOptions.map(c => <Option key={c} value={c}>{c}</Option>)}</Select>
          </Form.Item>
          <Form.Item label="选择 Excel 文件" required>
            <Upload accept=".xlsx,.xls" fileList={fileList} beforeUpload={f => {setFileList([f]); return false;}} onRemove={() => setFileList([])}>
              <Button icon={<UploadOutlined />} block>选择文件</Button>
            </Upload>
          </Form.Item>
        </Form>
      </Modal>
    </>
  );
};

// --- 主管理页面 ---
const GradeManager = () => {
  const [searchForm] = Form.useForm();
  const [editForm] = Form.useForm();
  const [classList, setClassList] = useState([]);
  const [studentOptions, setStudentOptions] = useState([]); 
  const [courseOptions, setCourseOptions] = useState([]);  
  const [grades, setGrades] = useState([]);
  const [loading, setLoading] = useState(false);
  const [searchMode, setSearchMode] = useState('class');
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [isAdding, setIsAdding] = useState(false);
  const [selectedRowKeys, setSelectedRowKeys] = useState([]);

  useEffect(() => {
    gradeApi.getClassesList().then(res => setClassList(res.data || []));
  }, []);

  const handleClassChange = async (classId) => {
    const { schoolYear, semester } = searchForm.getFieldsValue();
    try {
      const cRes = await gradeApi.getCourses({ classes: classId, schoolYear, semester });
      setCourseOptions(cRes.data.map(c => c.courseName));
      const sRes = await gradeApi.getStudentsByClass(classId);
      setStudentOptions(sRes.data || []);
    } catch (e) { console.error('基础信息加载失败'); }
  };

  const handleSearch = async () => {
    const values = await searchForm.validateFields();
    setLoading(true);
    try {
      const apiCall = searchMode === 'class' ? gradeApi.getClassGrades : gradeApi.getSingleStudentGrades;
      const res = await apiCall({ ...values, courseOrder: values.courseOrder || [] });
      setGrades(res.data || []);
      setSelectedRowKeys([]); 
    } catch (e) { message.error('查询失败'); } finally { setLoading(false); }
  };

  // 【核心功能修正】适配后端的 DTO 批量删除
  const handleBatchDelete = async () => {
    if (selectedRowKeys.length === 0) return;

    // 1. 获取选中的完整数据对象
    const selectedGrades = grades.filter(item => selectedRowKeys.includes(item.studentGradeId));
    
    // 2. 校验：后端 DTO 结构暗示一次只能删一门课
    const uniqueCourses = [...new Set(selectedGrades.map(g => g.course))];
    if (uniqueCourses.length > 1) {
      return message.warning('批量删除仅支持同一门课程，请重新勾选');
    }

    // 3. 组装后端要求的 GradesMultiplyStudentsChangeDTO
    const searchValues = searchForm.getFieldsValue();
    const deleteDTO = {
      studentGradeIds: selectedRowKeys,
      course: uniqueCourses[0], // 获取选中的课程名
      semester: searchValues.semester,
      schoolYear: searchValues.schoolYear
    };

    try {
      await gradeApi.deleteMultiplyGrades(deleteDTO);
      message.success(`成功删除 ${selectedRowKeys.length} 条成绩`);
      handleSearch();
    } catch (e) {
      message.error(e.response?.data?.message || '批量删除失败');
    }
  };

  const columns = [
    { title: '学号', dataIndex: 'studentId', width: 120, fixed: 'left' },
    { title: '课程', dataIndex: 'course', width: 150 },
    { 
      title: '正考', 
      dataIndex: 'grade', 
      width: 100,
      render: (v) => <b style={{ color: v < 60 ? '#f5222d' : '#52c41a', fontSize: '16px' }}>{v}</b>
    },
    { 
      title: '补考', 
      dataIndex: 'resitGrade', 
      width: 100,
      render: (v) => (v !== null && v !== undefined) ? (
        <Space size={4}>
          <b style={{ color: v < 60 ? '#f5222d' : '#1890ff', fontSize: '16px' }}>{v}</b>
          <Tag color="volcano" style={{ fontSize: '10px', height: '16px', lineHeight: '14px', padding: '0 4px' }}>补</Tag>
        </Space>
      ) : null
    },
    { title: '学分', dataIndex: 'credit', width: 70, align: 'center' },
    { 
      title: '性质', 
      dataIndex: 'type', 
      width: 80, 
      render: t => t && <Tag color={t === 'A' ? 'blue' : 'orange'}>{t}</Tag> 
    },
    {
      title: '操作',
      width: 120,
      fixed: 'right',
      render: (_, record) => (
        <Space>
          <Button type="link" size="small" icon={<EditOutlined />} onClick={() => { setIsAdding(false); editForm.setFieldsValue(record); setIsEditModalOpen(true); }}>修改</Button>
          <Popconfirm title="确定删除？" onConfirm={() => gradeApi.deleteSingleGrade(record).then(handleSearch)}>
            <Button type="link" size="small" danger icon={<DeleteOutlined />}>删除</Button>
          </Popconfirm>
        </Space>
      ),
    },
  ];

  return (
    <Card bordered={false} style={{ margin: '16px' }}>
      <Space direction="vertical" style={{ width: '100%' }} size="middle">
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
          <Title level={4} style={{ margin: 0 }}><BarChartOutlined style={{ color: '#1890ff' }} /> 成绩管理系统</Title>
          <Space>
            {selectedRowKeys.length > 0 && (
              <Popconfirm 
                title={`确定永久删除选中的 ${selectedRowKeys.length} 条记录吗？`} 
                onConfirm={handleBatchDelete}
                okButtonProps={{ danger: true }}
              >
                <Button danger icon={<DeleteOutlined />}>批量删除</Button>
              </Popconfirm>
            )}
            <BatchUploadModal 
              classId={searchForm.getFieldValue('classId')} 
              currentYear={searchForm.getFieldValue('schoolYear')} 
              currentSemester={searchForm.getFieldValue('semester')} 
              onRefresh={handleSearch} 
            />
            <Button type="primary" icon={<PlusOutlined />} onClick={() => { setIsAdding(true); editForm.resetFields(); setIsEditModalOpen(true); }}>单条录入</Button>
          </Space>
        </div>

        <div style={{ background: '#fbfbfb', padding: '20px', borderRadius: '12px', border: '1px solid #f0f0f0' }}>
          <Form form={searchForm} layout="inline">
            <Form.Item name="schoolYear" label="学年" initialValue="2025-2026"><Input style={{ width: 110 }} /></Form.Item>
            <Form.Item name="semester" label="学期" initialValue="秋"><Select style={{ width: 70 }}><Option value="秋">秋</Option><Option value="春">春</Option></Select></Form.Item>
            <Form.Item><Radio.Group value={searchMode} onChange={e => setSearchMode(e.target.value)} buttonStyle="solid"><Radio.Button value="class"><TeamOutlined /> 班级</Radio.Button><Radio.Button value="student"><UserOutlined /> 个人</Radio.Button></Radio.Group></Form.Item>
            <Form.Item name="classId" label="班级" rules={[{ required: true }]}><Select style={{ width: 110 }} placeholder="选择" onChange={handleClassChange}>{classList.map(c => <Option key={c} value={c}>{c}班</Option>)}</Select></Form.Item>
            {searchMode === 'student' && (
              <Form.Item name="studentId" label="学生" rules={[{ required: true }]}><Select showSearch style={{ width: 150 }} placeholder="搜索" optionFilterProp="children">{studentOptions.map(s => <Option key={s.studentId} value={s.studentId}>{s.studentName}</Option>)}</Select></Form.Item>
            )}
            <Form.Item name="courseOrder" label="排序"><Select mode="multiple" style={{ width: 180 }} placeholder="点选科目" maxTagCount={1}>{courseOptions.map(c => <Option key={c} value={c}>{c}</Option>)}</Select></Form.Item>
            <Form.Item><Button type="primary" icon={<SearchOutlined />} onClick={handleSearch} loading={loading}>查询</Button></Form.Item>
          </Form>
        </div>

        <Table 
          rowSelection={{ selectedRowKeys, onChange: setSelectedRowKeys }}
          columns={columns} 
          dataSource={grades} 
          rowKey="studentGradeId" 
          loading={loading} 
          bordered 
          scroll={{ x: 1000, y: 500 }} 
          pagination={{ showSizeChanger: true, defaultPageSize: 20 }}
        />
      </Space>

      <Modal title={isAdding ? "录入" : "修改"} open={isEditModalOpen} onCancel={() => setIsEditModalOpen(false)} onOk={() => editForm.validateFields().then(values => {
        const action = values.studentGradeId ? gradeApi.updateSingleGrade(values) : gradeApi.saveSingleGrade(values);
        action.then(() => { setIsEditModalOpen(false); handleSearch(); message.success('操作成功'); });
      })} width={600} destroyOnClose>
        <Form form={editForm} layout="vertical">
          <Form.Item name="studentGradeId" hidden><Input /></Form.Item>
          <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '20px' }}>
            <Form.Item name="studentId" label="学号"><Input disabled={!isAdding} /></Form.Item>
            <Form.Item name="course" label="课程"><Input disabled={!isAdding} /></Form.Item>
            <Form.Item name="grade" label="正考"><InputNumber min={0} max={100} style={{ width: '100%' }} /></Form.Item>
            <Form.Item name="resitGrade" label="补考"><InputNumber min={0} max={100} style={{ width: '100%' }} /></Form.Item>
            <Form.Item name="credit" label="学分"><InputNumber disabled={!isAdding} style={{ width: '100%' }} /></Form.Item>
            <Form.Item name="type" label="性质"><Input disabled={!isAdding} /></Form.Item>
            <Form.Item name="schoolYear" label="学年"><Input disabled={!isAdding} /></Form.Item>
            <Form.Item name="semester" label="学期"><Input disabled={!isAdding} /></Form.Item>
          </div>
        </Form>
      </Modal>
    </Card>
  );
};

export default GradeManager;