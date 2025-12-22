package com.study.studentOA.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.studentOA.dto.GradesClassConsultDTO;
import com.study.studentOA.entity.StudentGrade;

import java.util.List;

public interface IStudentGradeService extends IService<StudentGrade> {
    Integer addSingleStudentSingleGrade(StudentGrade studentGrade);

    Integer changeSingleStudentSingleGrade(StudentGrade studentGrade);

    Integer deleteSingleStudentSingleGrade(StudentGrade studentGrade);


    List<StudentGrade> getSingleClassGrades(GradesClassConsultDTO consultDTO);
}
