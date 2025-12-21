package com.study.studentOA.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_course")
@TableName(value = "tb_course")
public class Course {
    @Id
    @Column(name = "course_id", length = 50)
    @TableField("course_id")
    private String courseId;

    @Column(name = "course_name")
    @TableField("course_name")
    private String courseName;

    @Column(name = "school_year")
    @TableField("school_year")
    private String schoolYear;

    private String semester;

    private String type; // 必修 选修

    private Double credit; // 学分

    public Course() {
    }

    public Course(String courseId, String courseName, String schoolYear, String semester, String type, Double credit) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.schoolYear = schoolYear;
        this.semester = semester;
        this.type = type;
        this.credit = credit;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId='" + courseId + '\'' +
                ", courseName='" + courseName + '\'' +
                ", schoolYear='" + schoolYear + '\'' +
                ", semester='" + semester + '\'' +
                ", type='" + type + '\'' +
                ", credit=" + credit +
                '}';
    }
}
