package com.study.studentOA.dto;

public class CourseTypeCreditDTO {
    private String courseName;

    private String type;

    private Double credit;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
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
        return "CourseTypeCreditDTO{" +
                "courseName='" + courseName + '\'' +
                ", type='" + type + '\'' +
                ", credit=" + credit +
                '}';
    }
}
