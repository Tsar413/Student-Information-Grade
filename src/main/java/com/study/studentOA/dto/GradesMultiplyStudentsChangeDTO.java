package com.study.studentOA.dto;

import java.util.Arrays;

public class GradesMultiplyStudentsChangeDTO {
    private String[] studentGradeIds;

    private String course;

    private String schoolYear;

    private String semester;

    public String[] getStudentGradeIds() {
        return studentGradeIds;
    }

    public void setStudentGradeIds(String[] studentGradeIds) {
        this.studentGradeIds = studentGradeIds;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
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

    @Override
    public String toString() {
        return "GradesMultiplyStudentsChangeDTO{" +
                "studentGradeIds=" + Arrays.toString(studentGradeIds) +
                ", course='" + course + '\'' +
                ", schoolYear='" + schoolYear + '\'' +
                ", semester='" + semester + '\'' +
                '}';
    }
}
