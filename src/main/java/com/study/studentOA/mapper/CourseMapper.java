package com.study.studentOA.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.studentOA.dto.CourseTypeCreditDTO;
import com.study.studentOA.entity.Course;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CourseMapper extends BaseMapper<Course> {
    public CourseTypeCreditDTO getCourseNameTypeCreditByName(String courseName);
    public void deleteByStudentGradeId(String studentGradeId);
}
