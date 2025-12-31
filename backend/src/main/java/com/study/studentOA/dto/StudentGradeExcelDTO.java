package com.study.studentOA.dto;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * EXCEL文件中的列
 */
public class StudentGradeExcelDTO {
    // 假设 Excel 第一列标题是 "学号"
    @ExcelProperty("学号")
    private String studentId;

    // 假设 Excel 第二列标题是 "成绩"
    @ExcelProperty("成绩")
    private Double score;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "StudentGradeExcelDTO{" +
                "studentId='" + studentId + '\'' +
                ", score=" + score +
                '}';
    }
}
