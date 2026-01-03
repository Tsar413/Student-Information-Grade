import axios from 'axios';

const api = axios.create({ baseURL: '', timeout: 10000 });

// 成绩相关
export const getClassGrades = (data) => api.get('/api/studentGrade/singleClass', { params: data, paramsSerializer: { indexes: null } });
export const getSingleStudentGrades = (data) => api.get('/api/studentGrade/singleStudent', { params: data, paramsSerializer: { indexes: null } });
export const saveSingleGrade = (data) => api.post('/api/studentGrade/singleGrade', data);
export const updateSingleGrade = (data) => api.put('/api/studentGrade/singleGrade', data);
export const deleteSingleGrade = (data) => api.delete('/api/studentGrade/singleGrade', { data });

// 课程相关
export const getCourses = (data) => api.get('/api/course/courses', { params: data });

// 学生与班级相关
export const getClassesList = () => api.get('/api/student/classesList'); // 获取班级号
export const getStudentsByClass = (classId) => api.get(`/api/student/classes/${classId}`); // 获取班级学生

// 多人成绩上传 (适配 @RequestPart + @RequestParam)
export const uploadMultiplyGrades = (file, extraData) => {
    const formData = new FormData();
    // 核心：确保这里 append 的是一个 Blob 或 File 对象
    formData.append('file', file); 
  
    return api.post('/api/studentGrade/multiplyGrades', formData, {
      params: extraData, // 自动拼接到 URL: ?courseName=xxx&schoolYear=xxx...
      // headers 不要写 Content-Type，浏览器会自动处理 boundary
    });
  };

// 多人成绩删除接口
// data 通常为 studentGradeId 的数组，例如: [1, 2, 3]
export const deleteMultiplyGrades = (dto) => {
    return api.delete('/api/studentGrade/multiplyGrades', {
      data: dto // 包含 studentGradeIds, course, semester, schoolYear
    });
  };