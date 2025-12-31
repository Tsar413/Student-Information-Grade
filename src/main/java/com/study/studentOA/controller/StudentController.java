package com.study.studentOA.controller;

import com.study.studentOA.dto.StudentIdsDTO;
import com.study.studentOA.entity.Student;
import com.study.studentOA.service.IStudentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
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

    @PostMapping("/multiplyStudents")
    public Integer saveMultiplyStudents(@RequestPart("file") MultipartFile file,
                                        @RequestParam("classId") String classId) throws IOException {
        return iStudentService.saveMultiplyStudents(file, classId);
    }

    @PostMapping("/singleStudent")
    public Integer saveSingleStudent(@RequestBody Student student) {
        return iStudentService.saveSingleStudent(student);
    }

    @PutMapping("/singleStudent")
    public Integer changeSingleStudent(@RequestBody Student student) {
        return iStudentService.changeSingleStudent(student);
    }

    @DeleteMapping("/multiplyStudents")
    public Integer deleteMultiplyStudents(@RequestBody StudentIdsDTO idsDTO){
        return iStudentService.deleteMultiplyStudents(idsDTO);
    }
}
