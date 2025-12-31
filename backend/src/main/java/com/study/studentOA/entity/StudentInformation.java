package com.study.studentOA.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.persistence.*;

@Entity
@Table(name = "tb_student_information")
@TableName(value = "tb_student_information")
public class StudentInformation {
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

    @Column(name = "student_id_number")
    @TableField("student_id_number")
    private Integer studentIdNumber;

    @Column(name = "student_major")
    @TableField("student_major")
    private Integer studentMajor;

    @Column(name = "student_phone_number")
    @TableField("student_phone_number")
    private String studentPhoneNumber; // 学生电话号码

    private String nationality; // 民族

    @Column(name = "political_identity")
    @TableField("political_identity")
    private String politicalIdentity; // 政治身份

    @Lob
    @Column(columnDefinition = "LONGTEXT", name = "registered_address")
    @TableField("registered_address")
    private String registeredAddress; // 户籍地址

    @Lob
    @Column(columnDefinition = "LONGTEXT", name = "home_address")
    @TableField("home_address")
    private String homeAddress; // 家庭地址

    @Column(name = "student_father_name")
    @TableField("student_father_name")
    private String studentFatherName; // 父亲姓名

    @Column(name = "student_father_political_identity")
    @TableField("student_father_political_identity")
    private String studentFatherPoliticalIdentity; // 父亲政治身份

    @Column(name = "student_father_id_number")
    @TableField("student_father_id_number")
    private String studentFatherIdNumber; // 父亲身份证号

    @Column(name = "student_father_phone_number")
    @TableField("student_father_phone_number")
    private String studentFatherPhoneNumber; // 父亲电话号码

    @Column(name = "student_mother_name")
    @TableField("student_mother_name")
    private String studentMotherName; // 母亲姓名

    @Column(name = "student_mother_political_identity")
    @TableField("student_mother_political_identity")
    private String studentMotherPoliticalIdentity; // 母亲政治身份

    @Column(name = "student_mother_id_number")
    @TableField("student_mother_id_number")
    private String studentMotherIdNumber; // 母亲身份证号

    @Column(name = "student_mother_phone_number")
    @TableField("student_mother_phone_number")
    private String studentMotherPhoneNumber; // 母亲电话号码

    public StudentInformation() {
    }

    public StudentInformation(String studentId, String studentName, Integer studentClass, Integer studentIdNumber, Integer studentMajor, String studentPhoneNumber, String nationality, String politicalIdentity, String registeredAddress, String homeAddress, String studentFatherName, String studentFatherPoliticalIdentity, String studentFatherIdNumber, String studentFatherPhoneNumber, String studentMotherName, String studentMotherPoliticalIdentity, String studentMotherIdNumber, String studentMotherPhoneNumber) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentClass = studentClass;
        this.studentIdNumber = studentIdNumber;
        this.studentMajor = studentMajor;
        this.studentPhoneNumber = studentPhoneNumber;
        this.nationality = nationality;
        this.politicalIdentity = politicalIdentity;
        this.registeredAddress = registeredAddress;
        this.homeAddress = homeAddress;
        this.studentFatherName = studentFatherName;
        this.studentFatherPoliticalIdentity = studentFatherPoliticalIdentity;
        this.studentFatherIdNumber = studentFatherIdNumber;
        this.studentFatherPhoneNumber = studentFatherPhoneNumber;
        this.studentMotherName = studentMotherName;
        this.studentMotherPoliticalIdentity = studentMotherPoliticalIdentity;
        this.studentMotherIdNumber = studentMotherIdNumber;
        this.studentMotherPhoneNumber = studentMotherPhoneNumber;
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

    public Integer getStudentIdNumber() {
        return studentIdNumber;
    }

    public void setStudentIdNumber(Integer studentIdNumber) {
        this.studentIdNumber = studentIdNumber;
    }

    public Integer getStudentMajor() {
        return studentMajor;
    }

    public void setStudentMajor(Integer studentMajor) {
        this.studentMajor = studentMajor;
    }

    public String getStudentPhoneNumber() {
        return studentPhoneNumber;
    }

    public void setStudentPhoneNumber(String studentPhoneNumber) {
        this.studentPhoneNumber = studentPhoneNumber;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPoliticalIdentity() {
        return politicalIdentity;
    }

    public void setPoliticalIdentity(String politicalIdentity) {
        this.politicalIdentity = politicalIdentity;
    }

    public String getRegisteredAddress() {
        return registeredAddress;
    }

    public void setRegisteredAddress(String registeredAddress) {
        this.registeredAddress = registeredAddress;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getStudentFatherName() {
        return studentFatherName;
    }

    public void setStudentFatherName(String studentFatherName) {
        this.studentFatherName = studentFatherName;
    }

    public String getStudentFatherPoliticalIdentity() {
        return studentFatherPoliticalIdentity;
    }

    public void setStudentFatherPoliticalIdentity(String studentFatherPoliticalIdentity) {
        this.studentFatherPoliticalIdentity = studentFatherPoliticalIdentity;
    }

    public String getStudentFatherIdNumber() {
        return studentFatherIdNumber;
    }

    public void setStudentFatherIdNumber(String studentFatherIdNumber) {
        this.studentFatherIdNumber = studentFatherIdNumber;
    }

    public String getStudentFatherPhoneNumber() {
        return studentFatherPhoneNumber;
    }

    public void setStudentFatherPhoneNumber(String studentFatherPhoneNumber) {
        this.studentFatherPhoneNumber = studentFatherPhoneNumber;
    }

    public String getStudentMotherName() {
        return studentMotherName;
    }

    public void setStudentMotherName(String studentMotherName) {
        this.studentMotherName = studentMotherName;
    }

    public String getStudentMotherPoliticalIdentity() {
        return studentMotherPoliticalIdentity;
    }

    public void setStudentMotherPoliticalIdentity(String studentMotherPoliticalIdentity) {
        this.studentMotherPoliticalIdentity = studentMotherPoliticalIdentity;
    }

    public String getStudentMotherIdNumber() {
        return studentMotherIdNumber;
    }

    public void setStudentMotherIdNumber(String studentMotherIdNumber) {
        this.studentMotherIdNumber = studentMotherIdNumber;
    }

    public String getStudentMotherPhoneNumber() {
        return studentMotherPhoneNumber;
    }

    public void setStudentMotherPhoneNumber(String studentMotherPhoneNumber) {
        this.studentMotherPhoneNumber = studentMotherPhoneNumber;
    }

    @Override
    public String toString() {
        return "StudentInformation{" +
                "studentId='" + studentId + '\'' +
                ", studentName='" + studentName + '\'' +
                ", studentClass=" + studentClass +
                ", studentIdNumber=" + studentIdNumber +
                ", studentMajor=" + studentMajor +
                ", studentPhoneNumber='" + studentPhoneNumber + '\'' +
                ", nationality='" + nationality + '\'' +
                ", politicalIdentity='" + politicalIdentity + '\'' +
                ", registeredAddress='" + registeredAddress + '\'' +
                ", homeAddress='" + homeAddress + '\'' +
                ", studentFatherName='" + studentFatherName + '\'' +
                ", studentFatherPoliticalIdentity='" + studentFatherPoliticalIdentity + '\'' +
                ", studentFatherIdNumber='" + studentFatherIdNumber + '\'' +
                ", studentFatherPhoneNumber='" + studentFatherPhoneNumber + '\'' +
                ", studentMotherName='" + studentMotherName + '\'' +
                ", studentMotherPoliticalIdentity='" + studentMotherPoliticalIdentity + '\'' +
                ", studentMotherIdNumber='" + studentMotherIdNumber + '\'' +
                ", studentMotherPhoneNumber='" + studentMotherPhoneNumber + '\'' +
                '}';
    }
}
