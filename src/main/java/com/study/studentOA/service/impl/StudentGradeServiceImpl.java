package com.study.studentOA.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.studentOA.dto.*;
import com.study.studentOA.entity.Course;
import com.study.studentOA.entity.Student;
import com.study.studentOA.entity.StudentGrade;
import com.study.studentOA.mapper.CourseMapper;
import com.study.studentOA.mapper.StudentGradeMapper;
import com.study.studentOA.mapper.StudentMapper;
import com.study.studentOA.service.IStudentGradeService;
import com.study.studentOA.util.ChangeGradeParamsUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentGradeServiceImpl extends ServiceImpl<StudentGradeMapper, StudentGrade> implements IStudentGradeService {

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private IStudentGradeService iStudentGradeService;

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
        CourseTypeCreditDTO courseNameTypeCreditByName = courseMapper.getCourseNameTypeCreditByNameSemesterSchoolYear(studentGrade.getCourse(), studentGrade.getSemester(), studentGrade.getSchoolYear());
        // 修改学分 成绩类型
        ChangeGradeParamsUtil.changeStudentGradeCreditType(studentGrade, courseNameTypeCreditByName.getType(), courseNameTypeCreditByName.getCredit());
        System.out.println(studentGrade);
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
        CourseTypeCreditDTO courseNameTypeCreditByName = courseMapper.getCourseNameTypeCreditByNameSemesterSchoolYear(studentGrade.getCourse(), studentGrade.getSemester(), studentGrade.getSchoolYear());
        // 判断成绩是否可以修改 标准是是否提供补考成绩
        if(studentGrade.getResitGrade() == null){
            // 修改学分 成绩类型
            ChangeGradeParamsUtil.changeStudentGradeCreditType(studentGrade, courseNameTypeCreditByName.getType(), courseNameTypeCreditByName.getCredit());
            // 补考成绩置为null
            studentGrade.setResitGrade(null);
        } else {
            // 只有课程为必修课且补考合格才可以修改
            ChangeGradeParamsUtil.resitStudentCredit(studentGrade, courseNameTypeCreditByName.getType(), courseNameTypeCreditByName.getCredit());
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

    /**
     * 根据班级班号 学期 学年查询数据 按照预设排序顺序显示
     *
     * @param consultDTO 班号 学期 学年 预设排序顺序的数组
     * @return 班级成绩的list
     */
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

    /**
     * 根据学生的id进行查询
     *
     * @param consultDTO 学号 学期 学年 预设排序顺序的数组
     * @return 个人成绩的list
     */
    @Override
    public List<StudentGrade> getSingleStudentGrades(GradesSingleStudentConsultDTO consultDTO) {
        // course根据学年，学期获取全部课程信息
        List<Course> courses = courseMapper.getCoursesFromSchoolYearSemester(consultDTO.getSchoolYear(), consultDTO.getSemester()); // 查询课程
        List<String> courseNames = new ArrayList<String>();
        for (Course course : courses) {
            courseNames.add(course.getCourseName());
        }
        // 根据学生学号，课程获取成绩信息
        List<StudentGrade> studentGrades = baseMapper.selectGradesByBatchCourses(courseNames, consultDTO.getStudentId());
        // 按照预设数组排序
        String[] orderArray = consultDTO.getCourseOrder();
        Map<String, Integer> orderMap = new HashMap<String, Integer>();
        for (int i = 0; i < orderArray.length; i++) {
            orderMap.put(orderArray[i], i);
        }
        // 核心排序：按课程预设顺序排
        Collections.sort(studentGrades, new Comparator<StudentGrade>() {
            @Override
            public int compare(StudentGrade o1, StudentGrade o2) {
                // 1. 按课程预设权重排序
                int weight1 = orderMap.getOrDefault(o1.getCourse(), Integer.MAX_VALUE);
                int weight2 = orderMap.getOrDefault(o2.getCourse(), Integer.MAX_VALUE);
                return Integer.compare(weight1, weight2);
            }
        });
        return studentGrades;
    }

    /**
     * 该方法的作用在于批量读取Excel文件中的成绩并保存
     *
     * @param file excel文件
     * @param courseName 课程名
     * @param semester 学期
     * @param schoolYear 学年
     * @return 成功200 失败401
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveMultiplyStudentGrades(MultipartFile file, String courseName, String semester, String schoolYear) throws IOException {
        // 1. 读取 Excel 文件内容到 List
        List<StudentGradeExcelDTO> list = EasyExcel.read(file.getInputStream())
                .head(StudentGradeExcelDTO.class)
                .sheet()
                .doReadSync();
        System.out.println(list);
        System.out.println(courseName + " " + semester + " " + schoolYear);
        // 2. 保存成绩
        // 2.1 根据课程 学期 学年获取课程信息
        Course course = courseMapper.getCoursesByCourseNameSchoolYearSemester(courseName, schoolYear, semester);
        // 2.2 修改list 转为StudentGrade
        List<StudentGrade> studentGrades = new ArrayList<StudentGrade>();
        for (StudentGradeExcelDTO studentGradeExcelDTO : list) {
            StudentGrade studentGrade = new StudentGrade();
            // 设置分数
            studentGrade.setGrade(studentGradeExcelDTO.getScore());
            // 设置学生id
            studentGrade.setStudentId(studentGradeExcelDTO.getStudentId());
            // 设置A B
            studentGrade.setType(studentGradeExcelDTO.getScore() >= 60 ? "A" : "B");
            // 设置课程名
            studentGrade.setCourse(courseName);
            // 设置学期
            studentGrade.setSemester(semester);
            // 设置学年
            studentGrade.setSchoolYear(schoolYear);
            // 设置成绩id
            studentGrade.setStudentGradeId(studentGrade.getStudentId() + studentGrade.getCourse() + studentGrade.getType());
            // 设置学分
            if(course.getType().equals("选修")){
                if(studentGradeExcelDTO.getScore() < 60){
                    studentGrade.setCredit(0.0);
                } else {
                    studentGrade.setCredit(course.getCredit());
                }
            } else {
                if(studentGradeExcelDTO.getScore() >= 60){
                    studentGrade.setCredit(course.getCredit());
                }
            }
            // 添加到list
            studentGrades.add(studentGrade);
        }
        // 2.3 批量保存
        try {
            iStudentGradeService.saveBatch(studentGrades);
        } catch (Exception e) {
            return 401;
        }
        return 200;
    }

    // TODO 批量修改

    // TODO 批量删除
}
