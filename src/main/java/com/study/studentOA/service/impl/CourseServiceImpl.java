package com.study.studentOA.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.studentOA.entity.Course;
import com.study.studentOA.entity.StudentGrade;
import com.study.studentOA.mapper.CourseMapper;
import com.study.studentOA.mapper.StudentGradeMapper;
import com.study.studentOA.service.ICourseService;
import com.study.studentOA.service.IStudentGradeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

    @Resource
    private StudentGradeMapper studentGradeMapper;

    @Resource
    private IStudentGradeService iStudentGradeService;

    /**
     * 根据学期 学年 班级获取课程
     *
     * @param course 仅包含 学期 学年 单一班级
     * @return 某一班级在具体学期学年下的课程信息
     */
    @Override
    public List<Course> getCoursesBySemesterSchoolYearClassId(Course course) {
        List<Course> courses = baseMapper.getCoursesBySemesterSchoolYearClassId(course.getSemester(), course.getSchoolYear(), course.getClasses());
        return courses;
    }

    /**
     * 添加课程与班级
     *
     * @param course 包含课程信息的数据
     * @return 成功返回200 失败返回401 已经重复返回402
     */
    @Override
    public Integer addNewCourse(Course course) {
        // 1. 查询课程是否存在，除了courseId与classes其他必须完全一致
        Course course1 = baseMapper.getCoursesByAllExceptCourseIdClasses(
                course.getCourseName(), course.getSchoolYear(), course.getCredit(),
                course.getSemester(), course.getType());
        // 2. 如果不一致则新增
        if(course1 == null){
            Long maxId = baseMapper.getMaxId();
            if(maxId == null){
                maxId = 1L;
            } else {
                maxId++;
            }
            course.setCourseId(maxId);
            try {
                baseMapper.insert(course);
            } catch (Exception e) {
                return 401;
            }
            return 200;
        }
        // 3. 如果一致则仅修改classes classes格式为 班级|班级|班级
        // 修改班级id
        course.setCourseId(course1.getCourseId());
        // 判断班级是否被重复添加
        QueryWrapper<Course> wrapper1 = new QueryWrapper<Course>();
        wrapper1.eq("course_id", course.getCourseId());
        Course course2 = baseMapper.selectList(wrapper1).get(0);
        if(course2.getClasses().contains(course.getClasses())){
            return 402;
        }
        // 添加班级
        course.setClasses(course1.getClasses() + "|" + course.getClasses());
        UpdateWrapper<Course> wrapper2 = new UpdateWrapper<Course>();
        wrapper2.eq("course_id", course.getCourseId());
        try {
            baseMapper.update(course, wrapper2);
        } catch (Exception e) {
            return 401;
        }
        return 200;
    }

    // TODO 修改删除课程 联动成绩表

    /**
     * 修改课程除去班级与id
     *
     * @param course 需要修改的课程信息
     * @return 200 成功 401 失败 402 已经存在同名课程
     */
    @Override
    @Transactional(rollbackFor = Exception.class) // 开启事务，捕获异常后自动回滚
    public Integer changeCourse(Course course) {
        // 根据course_id查询课程数据
        QueryWrapper<Course> wrapper1 = new QueryWrapper<Course>();
        wrapper1.eq("course_id", course.getCourseId());
        Course oldCourse = baseMapper.selectList(wrapper1).get(0);
        // 判断新的课程名能否修改 判断在指定学期学年下是否有相同名字的课
        Course judgeCourse = baseMapper.getCoursesByCourseNameSchoolYearSemester(course.getCourseName(), course.getSchoolYear(), course.getSemester());
        if(judgeCourse != null){
            return 402;
        }
        // 修改课程名 学期 学年 判断课程名是否修改
        if(!oldCourse.getCourseName().equals(course.getCourseName()) || !oldCourse.getSchoolYear().equals(course.getSchoolYear()) || !oldCourse.getSemester().equals(course.getSemester())){
            // 1. 查询所有旧课程名的成绩
            List<StudentGrade> studentGrades = studentGradeMapper.selectGradesByBatchOldCourseNameSemesterSchoolYear(oldCourse.getCourseName(), oldCourse.getSemester(), oldCourse.getSchoolYear());
            // 2. 修改旧课程名成绩的课程名字 id 学期 学年
            for (StudentGrade studentGrade : studentGrades) {
                studentGrade.setStudentGradeId(studentGrade.getStudentId() + course.getCourseName() + studentGrade.getType());
                studentGrade.setCourse(course.getCourseName());
                studentGrade.setSchoolYear(course.getSchoolYear());
                studentGrade.setSemester(course.getSemester());
            }
            // 3. 删除旧有成绩数据
            QueryWrapper<StudentGrade> deleteWrapper = new QueryWrapper<>();
            deleteWrapper.eq("course", oldCourse.getCourseName())
                    .eq("semester", oldCourse.getSemester())
                    .eq("school_year", oldCourse.getSchoolYear());
            studentGradeMapper.delete(deleteWrapper);
            // 4. 插入新的成绩
            iStudentGradeService.saveBatch(studentGrades);
            // 5. 在课程表中修改课程名 学期 学年
            oldCourse.setCourseName(course.getCourseName());
            oldCourse.setSchoolYear(course.getSchoolYear());
            oldCourse.setSemester(course.getSemester());
            UpdateWrapper<Course> wrapper2 = new UpdateWrapper<Course>();
            wrapper2.eq("course_id", course.getCourseId());
            try {
                baseMapper.update(oldCourse, wrapper2);
            } catch (Exception e) {
                return 401;
            }
        }

        // TODO 修改学分
        // TODO 1. 修改成绩单中的学分
        // TODO 1.1 如果成绩合格 学分直接修改
        // TODO 1.2 如果成绩不合格 则学分按照规则进行修改
        // TODO 2. 在课程表中修改学分

        // TODO 修改类型
        // TODO 1. 修改成绩表的类型
        // TODO 2. 修改课程表中的类型
        return 0;
    }
}
