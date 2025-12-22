package com.study.studentOA.controller;

import com.study.studentOA.entity.StudentGrade;
import com.study.studentOA.service.IStudentGradeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
}
