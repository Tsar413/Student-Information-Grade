package com.study.studentOA.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.studentOA.dto.CourseTypeCreditDTO;
import com.study.studentOA.dto.GradesClassConsultDTO;
import com.study.studentOA.entity.StudentGrade;
import com.study.studentOA.mapper.CourseMapper;
import com.study.studentOA.mapper.StudentGradeMapper;
import com.study.studentOA.service.IStudentGradeService;
import com.study.studentOA.util.ChangeGradeParamsUtil;
import com.study.studentOA.util.CreditResitChangeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StudentGradeServiceImpl extends ServiceImpl<StudentGradeMapper, StudentGrade> implements IStudentGradeService {

    @Resource
    private CourseMapper courseMapper;

    /**
     * 保存学生成绩
     *
     * @param studentGrade 参数不包含id 不包含重修分数与学分
     * @return 成功200 失败401
     */
    @Override
    public Integer addSingleStudentSingleGrade(StudentGrade studentGrade) {
        System.out.println(studentGrade.getCourse());
        // 获取course的credit与type
        CourseTypeCreditDTO courseNameTypeCreditByName = courseMapper.getCourseNameTypeCreditByName(studentGrade.getCourse());
        // 修改学分 成绩类型
        ChangeGradeParamsUtil.changeStudentGradeCreditType(studentGrade, courseNameTypeCreditByName.getType(), courseNameTypeCreditByName.getCredit());
        try {
            baseMapper.insert(studentGrade);
        } catch (Exception e) {
            return 401;
        }
        return 200;
    }

    /**
     * 修改学生成绩
     *
     * @param studentGrade 参数不包含id 不包含重修分数与学分
     * @return 成功200 失败401
     */
    @Override
    public Integer changeSingleStudentSingleGrade(StudentGrade studentGrade) {
        // 判断对应id是否存在
        String id = "";
        String id1 = studentGrade.getStudentId() + studentGrade.getCourse() + "A";
        String id2 = studentGrade.getStudentId() + studentGrade.getCourse() + "B";
        if(baseMapper.countStudentGradeByGradeId(id1) != 0){
            id = id1;
        } else {
            id = id2;
        }
        // 获取course的credit与type
        CourseTypeCreditDTO courseNameTypeCreditByName = courseMapper.getCourseNameTypeCreditByName(studentGrade.getCourse());
        // 判断成绩是否可以修改 标准是是否提供补考成绩
        if(studentGrade.getResitGrade() == null){
            // 修改学分 成绩类型
            ChangeGradeParamsUtil.changeStudentGradeCreditType(studentGrade, courseNameTypeCreditByName.getType(), courseNameTypeCreditByName.getCredit());
            // 补考成绩置为null
            studentGrade.setResitGrade(null);
        } else {
            // 只有课程为必修课且补考合格才可以修改
            ChangeGradeParamsUtil.resitStudentCredit(studentGrade, courseNameTypeCreditByName.getType(), studentGrade.getResitGrade());
        }
        // 编写修改条件
        UpdateWrapper<StudentGrade> wrapper = new UpdateWrapper<StudentGrade>();
        wrapper.eq("student_grade_id", id);
        try {
            baseMapper.update(studentGrade, wrapper);
        } catch (Exception e) {
            return 401;
        }
        return 200;
    }

    /**
     * 删除单个成绩
     *
     * @param studentGrade 需要删除的科目
     * @return 成功200 失败401
     */
    @Override
    public Integer deleteSingleStudentSingleGrade(StudentGrade studentGrade) {
        String id1 = studentGrade.getStudentId() + studentGrade.getCourse() + "A";
        String id2 = studentGrade.getStudentId() + studentGrade.getCourse() + "B";
        try {
            baseMapper.deleteByStudentGradeId(id1); // 根据两种id彻底删除
            baseMapper.deleteByStudentGradeId(id2);
        } catch (Exception e) {
            return 401;
        }
        return 200;
    }

    @Override
    public List<StudentGrade> getSingleClassGrades(GradesClassConsultDTO consultDTO) {
        // TODO 去student中根据班级序号获取全部的学生学号
        // TODO 去course中根据学年，学期获取全部课程信息
        // TODO 根据学生学号，课程获取成绩信息
        // TODO 按照预设数组排序
        return null;
    }
}
