package com.study.studentOA.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_student")
@TableName(value = "tb_student")
public class Student {
    @Id
    @Column(name = "student_id", length = 20)
    @TableField("student_id")
    private String studentId;

    @Column(name = "student_name")
    @TableField("student_name")
    private String studentName;

    @Column(name = "student_class")
    @TableField("student_class")
    private Integer studentClass;

    public Student() {
    }

    public Student(String studentId, String studentName, Integer studentClass) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentClass = studentClass;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Integer getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(Integer studentClass) {
        this.studentClass = studentClass;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", studentName='" + studentName + '\'' +
                ", studentClass=" + studentClass +
                '}';
    }
}
