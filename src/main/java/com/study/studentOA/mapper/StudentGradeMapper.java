package com.study.studentOA.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.studentOA.entity.StudentGrade;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentGradeMapper extends BaseMapper<StudentGrade> {
    public Integer countStudentGradeByGradeId(String studentGradeId);
    public void deleteByStudentGradeId(String studentGradeId);
}
