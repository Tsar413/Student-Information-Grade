import axios from 'axios';

// 创建 axios 实例
const api = axios.create({
  baseURL: '', // 留空，因为我们会通过 vite proxy 转发
  timeout: 5000
});

// 1. 获取班级号 [GET /api/student/classesList]
export const getClassesList = () => {
  return api.get('/api/student/classesList');
};

// 2. 获取指定班级学生信息 [GET /api/student/classes/{classId}]
export const getStudentsByClass = (classId) => {
  return api.get(`/api/student/classes/${classId}`);
};

// 3. 保存单人信息 [POST /api/student/singleStudent]
export const saveStudent = (data) => {
  return api.post('/api/student/singleStudent', data);
};

// 4. 修改单人信息 [PUT /api/student/singleStudent]
export const updateStudent = (data) => {
  return api.put('/api/student/singleStudent', data);
};

// 批量导入学生 (POST /api/student/multiplyStudents) 
// 需要 file 和 classId
export const uploadStudentExcel = (file, classId) => {
  const formData = new FormData();
  formData.append('file', file);
  formData.append('classId', classId); // 文档要求传入班级号
  return api.post('/api/student/multiplyStudents', formData, {
    headers: { 'Content-Type': 'multipart/form-data' }
  });
};

// 批量删除学生 (DELETE /api/student/multiplyStudents)
export const deleteStudents = (studentIds) => {
  return api.delete('/api/student/multiplyStudents', {
    data: { studentIds } // 严格匹配文档 Body 结构
  });
};