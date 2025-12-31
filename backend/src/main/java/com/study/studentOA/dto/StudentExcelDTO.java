package com.study.studentOA.dto;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * EXCEL文件中的列
 */
public class StudentExcelDTO {
    // 假设 Excel 第一列标题是 "学号"
    @ExcelProperty("学号")
    private String studentId;

    // 假设 Excel 第二列标题是 "姓名"
    @ExcelProperty("姓名")
    private String studentName;

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

    @Override
    public String toString() {
        return "StudentExcelDTO{" +
                "studentId='" + studentId + '\'' +
                ", studentName='" + studentName + '\'' +
                '}';
    }
}
