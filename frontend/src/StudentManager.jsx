import React, { useState, useEffect } from 'react';
import { 
  Table, Select, Space, Card, Typography, message, 
  Button, Modal, Form, Input, InputNumber, Upload, Popconfirm 
} from 'antd';
import { 
  TeamOutlined, PlusOutlined, EditOutlined, 
  DeleteOutlined, UploadOutlined 
} from '@ant-design/icons';
import * as studentApi from './api/student';

const { Title } = Typography;
const { Option } = Select;

const StudentManager = () => {
  const [classList, setClassList] = useState([]);
  const [students, setStudents] = useState([]);
  const [loading, setLoading] = useState(false);
  const [selectedClass, setSelectedClass] = useState(null);
  const [selectedRowKeys, setSelectedRowKeys] = useState([]);

  // 弹窗状态
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [isImportModalOpen, setIsImportModalOpen] = useState(false);
  const [importFileList, setImportFileList] = useState([]);
  const [editForm] = Form.useForm();
  const [importForm] = Form.useForm();
  const [editingId, setEditingId] = useState(null);

  useEffect(() => { fetchClassList(); }, []);

  // 获取并排序班级列表
  const fetchClassList = async () => {
    try {
      const res = await studentApi.getClassesList();
      const sorted = res.data.sort((a, b) => parseInt(a) - parseInt(b));
      setClassList(sorted);
    } catch (e) { message.error('获取班级失败'); }
  };

  // 加载学生数据并强制刷新
  const loadData = async (classId) => {
    if (!classId) return;
    setLoading(true);
    try {
      const res = await studentApi.getStudentsByClass(classId);
      setStudents(res.data);
    } catch (e) { message.error('获取学生失败'); }
    finally { setLoading(false); }
  };

  // 保存/修改处理
  const handleEditSubmit = async () => {
    try {
      const values = await editForm.validateFields();
      if (editingId) await studentApi.updateStudent(values);
      else await studentApi.saveStudent(values);
      
      message.success('操作成功');
      setIsEditModalOpen(false);
      fetchClassList(); // 刷新班级
      loadData(selectedClass || values.studentClass); // 强制刷新表格
    } catch (e) {}
  };

  // 批量删除处理
  const handleBatchDelete = async () => {
    try {
      await studentApi.deleteStudents(selectedRowKeys);
      message.success('批量删除成功');
      setSelectedRowKeys([]);
      loadData(selectedClass);
    } catch (e) { message.error('删除失败'); }
  };

  // 批量导入处理
  const handleImportSubmit = async () => {
    try {
      const { classId } = await importForm.validateFields();
      if (importFileList.length === 0) return message.error('请选择文件');
      
      await studentApi.uploadStudentExcel(importFileList[0], classId);
      message.success('导入成功');
      setIsImportModalOpen(false);
      importForm.resetFields();
      setImportFileList([]);
      fetchClassList();
      loadData(classId); // 跳转到导入的班级查看
    } catch (e) {}
  };

  const columns = [
    { title: '学号', dataIndex: 'studentId', key: 'studentId', sorter: (a,b) => a.studentId.localeCompare(b.studentId) },
    { title: '姓名', dataIndex: 'studentName', key: 'studentName' },
    { title: '班级', dataIndex: 'studentClass', key: 'studentClass' },
    {
      title: '操作',
      render: (_, record) => (
        <Button type="link" icon={<EditOutlined />} onClick={() => {
          setEditingId(record.studentId);
          editForm.setFieldsValue(record);
          setIsEditModalOpen(true);
        }}>编辑</Button>
      ),
    },
  ];

  return (
    <div style={{ padding: '20px' }}>
      <Card>
        <Space direction="vertical" style={{ width: '100%' }} size="large">
          <div style={{ display: 'flex', justifyContent: 'space-between' }}>
            <Title level={3}><TeamOutlined /> 学生管理</Title>
            <Space>
              <Select placeholder="切换班级" style={{ width: 120 }} onChange={(v) => {setSelectedClass(v); loadData(v);}}>
                {classList.map(c => <Option key={c} value={c}>{c}班</Option>)}
              </Select>
              <Button icon={<UploadOutlined />} onClick={() => setIsImportModalOpen(true)}>批量导入</Button>
              <Button type="primary" icon={<PlusOutlined />} onClick={() => {
                setEditingId(null);
                editForm.resetFields();
                setIsEditModalOpen(true);
              }}>新增学生</Button>
              {selectedRowKeys.length > 0 && (
                <Popconfirm title="确定删除选中学生？" onConfirm={handleBatchDelete}>
                  <Button danger icon={<DeleteOutlined />}>批量删除({selectedRowKeys.length})</Button>
                </Popconfirm>
              )}
            </Space>
          </div>

          <Table 
            rowSelection={{ selectedRowKeys, onChange: setSelectedRowKeys }}
            columns={columns} 
            dataSource={students} 
            rowKey="studentId" 
            loading={loading}
          />
        </Space>
      </Card>

      {/* 新增/编辑弹窗 */}
      <Modal title={editingId ? "修改" : "新增"} open={isEditModalOpen} onOk={handleEditSubmit} onCancel={() => setIsEditModalOpen(false)}>
        <Form form={editForm} layout="vertical">
          <Form.Item name="studentId" label="学号" rules={[{required: true}]}><Input disabled={!!editingId}/></Form.Item>
          <Form.Item name="studentName" label="姓名" rules={[{required: true}]}><Input /></Form.Item>
          <Form.Item name="studentClass" label="班级号" rules={[{required: true}]}><InputNumber style={{width:'100%'}}/></Form.Item>
        </Form>
      </Modal>

      {/* 批量导入弹窗 (需手动输入班级号) */}
      <Modal title="批量导入" open={isImportModalOpen} onOk={handleImportSubmit} onCancel={() => setIsImportModalOpen(false)}>
        <Form form={importForm} layout="vertical">
          <Form.Item name="classId" label="导入目标班级号" rules={[{required: true}]}><Input placeholder="请输入班级号"/></Form.Item>
          <Form.Item label="选择Excel文件">
            <Upload beforeUpload={f => { setImportFileList([f]); return false; }} fileList={importFileList} accept=".xlsx,.xls">
              <Button icon={<UploadOutlined />}>选择文件</Button>
            </Upload>
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
};

export default StudentManager;