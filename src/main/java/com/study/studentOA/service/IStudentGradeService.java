package com.study.studentOA.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.studentOA.dto.GradesClassConsultDTO;
import com.study.studentOA.dto.GradesMultiplyStudentsChangeDTO;
import com.study.studentOA.dto.GradesSingleStudentConsultDTO;
import com.study.studentOA.entity.StudentGrade;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IStudentGradeService extends IService<StudentGrade> {
    Integer addSingleStudentSingleGrade(StudentGrade studentGrade);

    Integer changeSingleStudentSingleGrade(StudentGrade studentGrade);

    Integer deleteSingleStudentSingleGrade(StudentGrade studentGrade);


    List<StudentGrade> getSingleClassGrades(GradesClassConsultDTO consultDTO);

    List<StudentGrade> getSingleStudentGrades(GradesSingleStudentConsultDTO consultDTO);

    Integer saveMultiplyStudentGrades(MultipartFile file, String courseName, String semester, String schoolYear) throws IOException;

    Integer deleteMultiplyStudentGrades(GradesMultiplyStudentsChangeDTO changeDTO);
}
