package com.study.studentOA.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.studentOA.entity.Course;

import java.util.List;

public interface ICourseService extends IService<Course> {
    List<Course> getCoursesBySemesterSchoolYearClassId(Course course);

    Integer addNewCourse(Course course);

    Integer changeCourse(Course course);
}
