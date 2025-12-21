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

    @PostMapping("/single")
    public Integer addSingleStudentGrade(@RequestBody StudentGrade studentGrade){
        return iStudentGradeService.addSingleStudentGrade(studentGrade);
    }

    @PutMapping("/single")
    public Integer updateSingleStudentGrade(@RequestBody StudentGrade studentGrade){
        return iStudentGradeService.changeSingleStudentGrade(studentGrade);
    }

}
