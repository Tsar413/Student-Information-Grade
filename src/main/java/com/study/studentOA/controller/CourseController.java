package com.study.studentOA.controller;

import com.study.studentOA.entity.Course;
import com.study.studentOA.service.ICourseService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/course")
public class CourseController {
    @Resource
    private ICourseService iCourseService;

    @GetMapping("/courses")
    public List<Course> getCoursesBySemesterSchoolYearClassId(@RequestBody Course course){
        return iCourseService.getCoursesBySemesterSchoolYearClassId(course);
    }

    @PostMapping("/courses")
    public Integer addNewCourse(@RequestBody Course course){
        return iCourseService.addNewCourse(course);
    }
}
