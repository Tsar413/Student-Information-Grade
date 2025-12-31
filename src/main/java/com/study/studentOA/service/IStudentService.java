package com.study.studentOA.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.studentOA.dto.StudentIdsDTO;
import com.study.studentOA.entity.Student;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IStudentService extends IService<Student> {
    List<Student> getStudentsByClassId(String classId);

    List<String> getClassesList();

    Integer saveMultiplyStudents(MultipartFile file, String classId) throws IOException;

    Integer saveSingleStudent(Student student);

    Integer changeSingleStudent(Student student);

    Integer deleteMultiplyStudents(StudentIdsDTO idsDTO);
}
