package com.study.studentOA.dto;

import java.util.Arrays;

public class StudentIdsDTO {
    private String[] studentIds;

    public String[] getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(String[] studentIds) {
        this.studentIds = studentIds;
    }

    @Override
    public String toString() {
        return "StudentIdsDTO{" +
                "studentIds=" + Arrays.toString(studentIds) +
                '}';
    }
}
