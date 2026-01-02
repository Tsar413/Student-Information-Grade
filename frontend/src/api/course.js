import axios from 'axios';

const api = axios.create({
  baseURL: '', 
  timeout: 10000
});

/**
 * 课程模块接口对齐
 */

// 1. 查询班级课表 (GET) - 注意文档中此接口需要 Body 参数，但在标准 GET 中通常建议使用 params，
// 这里严格按照文档 Body 结构发送数据。
// src/api/course.js

// src/api/course.js
export const getCourses = (queryData) => {
    return api.get('/api/course/courses', {
      // 关键：使用 params 字段，Axios 会处理成 URL 参数
      params: queryData 
    });
  };
// 2. 保存课程内容 (POST)
export const saveCourse = (data) => api.post('/api/course/courses', data);

// 3. 修改课程信息 (PUT)
export const updateCourse = (data) => api.put('/api/course/courses', data);

// 4. 删除课程 (DELETE)
export const deleteCourse = (data) => api.delete('/api/course/courses', { data });

// 5. 删除课程中的班级 (DELETE)
export const deleteClassFromCourse = (data) => api.delete('/api/course/classes', { data });