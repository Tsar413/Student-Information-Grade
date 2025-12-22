package com.study.studentOA.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_student_grade")
@TableName(value = "tb_student_grade")
public class StudentGrade {
    @Id
    @Column(name = "student_grade_id", length = 50)
    @TableField("student_grade_id")
    private String studentGradeId;

    @Column(name = "student_id")
    @TableField("student_id")
    private String studentId;

    private String course;

    private Double grade;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Double credit;

    private String type; // 类型 A B C

    @Column(name = "resit_grade")
    @TableField(value = "resit_grade", updateStrategy = FieldStrategy.IGNORED)
    private Double resitGrade; // 重修成绩

    public StudentGrade() {
    }

    public StudentGrade(String studentGradeId, String studentId, String course, Double grade, Double credit, String type, Double resitGrade, Double resitCredit) {
        this.studentGradeId = studentGradeId;
        this.studentId = studentId;
        this.course = course;
        this.grade = grade;
        this.credit = credit;
        this.type = type;
        this.resitGrade = resitGrade;
    }

    public String getStudentGradeId() {
        return studentGradeId;
    }

    public void setStudentGradeId(String studentGradeId) {
        this.studentGradeId = studentGradeId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getResitGrade() {
        return resitGrade;
    }

    public void setResitGrade(Double resitGrade) {
        this.resitGrade = resitGrade;
    }

    @Override
    public String toString() {
        return "StudentGrade{" +
                "studentGradeId='" + studentGradeId + '\'' +
                ", studentId='" + studentId + '\'' +
                ", course='" + course + '\'' +
                ", grade=" + grade +
                ", credit=" + credit +
                ", type='" + type + '\'' +
                ", resitGrade=" + resitGrade +
                '}';
    }
}
