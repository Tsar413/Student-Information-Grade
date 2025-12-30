package com.study.studentOA.controller;

import com.study.studentOA.entity.Student;
import com.study.studentOA.service.IStudentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    @Resource
    private IStudentService iStudentService;

    @GetMapping("/classes/{classId}")
    public List<Student> getStudentsByClassId(@PathVariable String classId){
        return iStudentService.getStudentsByClassId(classId);
    }

    @GetMapping("/classesList")
    public List<String> getClassesList(){
        return iStudentService.getClassesList();
    }
}
