package com.study.studentOA.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.studentOA.entity.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {
    public List<Student> getStudentsByClassId(String classId);
}
