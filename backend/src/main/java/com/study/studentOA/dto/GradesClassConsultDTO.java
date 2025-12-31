package com.study.studentOA.dto;

import java.util.Arrays;

/**
 * 内容为查询DTO 涉及班级 学年 学期 课程显示排序
 */
public class GradesClassConsultDTO {
    private String classId; // 班级序号
    private String schoolYear; // 学年
    private String semester; // 学期
    private String[] courseOrder; // 排序方式

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String[] getCourseOrder() {
        return courseOrder;
    }

    public void setCourseOrder(String[] courseOrder) {
        this.courseOrder = courseOrder;
    }

    @Override
    public String toString() {
        return "GradesClassConsultDTO{" +
                "classId='" + classId + '\'' +
                ", schoolYear='" + schoolYear + '\'' +
                ", semester='" + semester + '\'' +
                ", courseOrder=" + Arrays.toString(courseOrder) +
                '}';
    }
}
