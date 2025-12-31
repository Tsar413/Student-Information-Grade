package com.study.studentOA.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.studentOA.dto.StudentExcelDTO;
import com.study.studentOA.dto.StudentGradeExcelDTO;
import com.study.studentOA.dto.StudentIdsDTO;
import com.study.studentOA.entity.Student;
import com.study.studentOA.entity.StudentGrade;
import com.study.studentOA.mapper.StudentGradeMapper;
import com.study.studentOA.mapper.StudentMapper;
import com.study.studentOA.service.IStudentGradeService;
import com.study.studentOA.service.IStudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

    @Resource
    private IStudentService iStudentService;

    @Resource
    private StudentGradeMapper studentGradeMapper;

    @Resource
    private IStudentGradeService iStudentGradeService;

    /**
     * 该方法被用来获取具体班级的学生信息
     *
     * @param classId 班级id
     * @return 包含学生信息的list
     */
    @Override
    public List<Student> getStudentsByClassId(String classId) {
        return baseMapper.getStudentsByClassId(classId);
    }

    /**
     * 该方法能获取具体的班级清单
     *
     * @return 班级号清单
     */
    @Override
    public List<String> getClassesList() {
        return baseMapper.getClassesList();
    }

    /**
     * 该方法被用来批量导入学生信息
     *
     * @param file 包含学生学号与姓名的文件
     * @param classId 班级号
     * @return 成功200 失败401
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveMultiplyStudents(MultipartFile file, String classId) throws IOException {
        // 1. 读取 Excel 文件内容到 List
        List<StudentExcelDTO> list = EasyExcel.read(file.getInputStream())
                .head(StudentExcelDTO.class)
                .sheet()
                .doReadSync();
        // 2. 创建Student list
        List<Student> students = new ArrayList<Student>();
        for (StudentExcelDTO dto : list) {
            Student student = new Student();
            student.setStudentId(dto.getStudentId());
            student.setStudentName(dto.getStudentName());
            student.setStudentClass(Integer.valueOf(classId));
            students.add(student);
        }
        // 3. 保存student
        try {
            iStudentService.saveBatch(students);
        } catch (Exception e) {
            return 401;
        }
        return 200;
    }

    /**
     * 保存学生信息
     *
     * @param student 包含完整信息的student
     * @return 成功200 失败401
     */
    @Override
    public Integer saveSingleStudent(Student student) {
        try {
            baseMapper.insert(student);
        } catch (Exception e) {
            return 401;
        }
        return 200;
    }

    /**
     * 修改学生信息
     *
     * @param student 包含完整信息的student
     * @return 成功200 失败401
     */
    @Override
    public Integer changeSingleStudent(Student student) {
        UpdateWrapper<Student> wrapper = new UpdateWrapper<Student>();
        wrapper.eq("student_id", student.getStudentId());
        try {
            baseMapper.update(student, wrapper);
        } catch (Exception e) {
            return 401;
        }
        return 200;
    }

    /**
     * 批量删除学生信息
     *
     * @param idsDTO 需要删除的学生的id
     * @return 成功200 失败401
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteMultiplyStudents(StudentIdsDTO idsDTO) {
        try {
            for (String studentId : idsDTO.getStudentIds()) {
                // 1. 前往成绩表删除需要删除的成绩
                studentGradeMapper.deleteByStudentId(studentId);
                // 2. 在学生表中删除对应的学生
                baseMapper.deleteByStudentId(studentId);
            }
        } catch (Exception e) {
            return 401;
        }
        return 200;
    }
}
