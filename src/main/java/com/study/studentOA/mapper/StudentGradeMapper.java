package com.study.studentOA.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.studentOA.entity.StudentGrade;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentGradeMapper extends BaseMapper<StudentGrade> {
    public Integer countStudentGradeByGradeId(String studentGradeId);
    public void deleteByStudentGradeId(String studentGradeId);
    public List<StudentGrade> selectGradesByBatch(List<String> courseNames, List<String> studentIds);
    public List<StudentGrade> selectGradesByBatchCourses(List<String> courseNames, String studentId);
}
