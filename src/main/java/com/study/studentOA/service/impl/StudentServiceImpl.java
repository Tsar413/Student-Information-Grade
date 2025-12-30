package com.study.studentOA.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.studentOA.entity.Student;
import com.study.studentOA.mapper.StudentMapper;
import com.study.studentOA.service.IStudentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {
    /**
     * 该方法被用来获取具体班级的学生信息
     *
     * @param classId 班级id
     * @return 包含学生信息的list
     */
    @Override
    public List<Student> getStudentsByClassId(String classId) {
        return baseMapper.getStudentsByClassId(classId);
    }

    /**
     * 该方法能获取具体的班级清单
     *
     * @return 班级号清单
     */
    @Override
    public List<String> getClassesList() {
        return baseMapper.getClassesList();
    }
}
