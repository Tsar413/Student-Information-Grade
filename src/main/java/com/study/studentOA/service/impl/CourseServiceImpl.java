package com.study.studentOA.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.studentOA.entity.Course;
import com.study.studentOA.mapper.CourseMapper;
import com.study.studentOA.service.ICourseService;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

    // TODO 根据学期 学年 班级获取课程
    // TODO 重构Course 存储班级号码
    // TODO 添加课程与班级
    // TODO 修改删除课程 联动成绩表

}
