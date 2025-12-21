package com.study.studentOA.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.studentOA.entity.Course;
import com.study.studentOA.mapper.CourseMapper;
import com.study.studentOA.service.ICourseService;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {
}
