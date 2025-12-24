package com.study.studentOA.controller;

import com.study.studentOA.dto.GradesClassConsultDTO;
import com.study.studentOA.dto.GradesSingleStudentConsultDTO;
import com.study.studentOA.entity.StudentGrade;
import com.study.studentOA.service.IStudentGradeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/studentGrade")
public class StudentGradeController {

    @Resource
    private IStudentGradeService iStudentGradeService;

    @PostMapping("/singleGrade")
    public Integer addSingleStudentSingleGrade(@RequestBody StudentGrade studentGrade){
        return iStudentGradeService.addSingleStudentSingleGrade(studentGrade);
    }

    @PutMapping("/singleGrade")
    public Integer updateSingleStudentSingleGrade(@RequestBody StudentGrade studentGrade){
        return iStudentGradeService.changeSingleStudentSingleGrade(studentGrade);
    }

    @DeleteMapping("/singleGrade")
    public Integer deleteSingleStudentSingleGrade(@RequestBody StudentGrade studentGrade){
        return iStudentGradeService.deleteSingleStudentSingleGrade(studentGrade);
    }

    @GetMapping("/singleClass")
    public List<StudentGrade> getSingleClassGrades(@RequestBody GradesClassConsultDTO consultDTO){
        return iStudentGradeService.getSingleClassGrades(consultDTO);
    }

    @GetMapping("/singleStudent")
    public List<StudentGrade> getSingleStudentGrades(@RequestBody GradesSingleStudentConsultDTO consultDTO){
        return iStudentGradeService.getSingleStudentGrades(consultDTO);
    }
}
