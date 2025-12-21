package com.study.studentOA.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.studentOA.entity.StudentGrade;

public interface IStudentGradeService extends IService<StudentGrade> {
    Integer addSingleStudentGrade(StudentGrade studentGrade);

    Integer changeSingleStudentGrade(StudentGrade studentGrade);
}
