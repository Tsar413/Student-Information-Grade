package com.study.studentOA.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.studentOA.entity.Course;
import com.study.studentOA.mapper.CourseMapper;
import com.study.studentOA.service.ICourseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

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
}
