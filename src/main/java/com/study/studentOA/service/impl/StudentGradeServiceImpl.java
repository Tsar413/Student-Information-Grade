package com.study.studentOA.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.studentOA.dto.CourseTypeCreditDTO;
import com.study.studentOA.dto.GradesClassConsultDTO;
import com.study.studentOA.entity.Course;
import com.study.studentOA.entity.Student;
import com.study.studentOA.entity.StudentGrade;
import com.study.studentOA.mapper.CourseMapper;
import com.study.studentOA.mapper.StudentGradeMapper;
import com.study.studentOA.mapper.StudentMapper;
import com.study.studentOA.service.IStudentGradeService;
import com.study.studentOA.util.ChangeGradeParamsUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentGradeServiceImpl extends ServiceImpl<StudentGradeMapper, StudentGrade> implements IStudentGradeService {

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private StudentMapper studentMapper;

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
        // student根据班级序号获取全部的学生学号
        List<Student> students = studentMapper.getStudentsByClassId(consultDTO.getClassId()); // 查询学生
        List<String> studentIds = new ArrayList<String>();
        for (Student student : students) {
            studentIds.add(student.getStudentId());
        }
        // course根据学年，学期获取全部课程信息
        List<Course> courses = courseMapper.getCoursesFromSchoolYearSemester(consultDTO.getSchoolYear(), consultDTO.getSemester()); // 查询课程
        List<String> courseNames = new ArrayList<String>();
        for (Course course : courses) {
            courseNames.add(course.getCourseName());
        }
        // 根据学生学号，课程获取成绩信息
        List<StudentGrade> studentGrades = baseMapper.selectGradesByBatch(courseNames, studentIds);
        // 按照预设数组排序
        String[] orderArray = consultDTO.getCourseOrder();
        Map<String, Integer> orderMap = new HashMap<String, Integer>();
        for (int i = 0; i < orderArray.length; i++) {
            orderMap.put(orderArray[i], i);
        }
        // 核心排序：先按学生ID排，再按课程预设顺序排
        Collections.sort(studentGrades, new Comparator<StudentGrade>() {
            @Override
            public int compare(StudentGrade o1, StudentGrade o2) {
                // 1. 第一优先级：按学号排序 (转为 Long 比较以处理数字逻辑)
                long id1 = Long.parseLong(o1.getStudentId());
                long id2 = Long.parseLong(o2.getStudentId());
                int idCompare = Long.compare(id1, id2);

                // 如果学号不同，直接返回结果
                if (idCompare != 0) {
                    return idCompare;
                }

                // 2. 第二优先级：学号相同时，按课程预设权重排序
                int weight1 = orderMap.getOrDefault(o1.getCourse(), Integer.MAX_VALUE);
                int weight2 = orderMap.getOrDefault(o2.getCourse(), Integer.MAX_VALUE);
                return Integer.compare(weight1, weight2);
            }
        });
        return studentGrades;
    }

    // TODO 根据学生的id进行查询
}
