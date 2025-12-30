package com.study.studentOA.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.studentOA.entity.Student;

import java.util.List;

public interface IStudentService extends IService<Student> {
    List<Student> getStudentsByClassId(String classId);

    List<String> getClassesList();
}
