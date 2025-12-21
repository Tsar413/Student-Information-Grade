package com.study.studentOA.util;

import com.study.studentOA.entity.StudentGrade;

public class ChangeGradeParamsUtil {
    /**
     * 工具类 处理成绩的类型与学分
     *
     * @param studentGrade 要修改的成绩
     * @param courseType 需要的课程类型
     * @param credit 学分
     */
    public static void changeStudentGradeCreditType(StudentGrade studentGrade, String courseType, Double credit){
        // 判断是否为选修课
        if(courseType.equals("选修课")){
            // 如果是选修课，则如果成绩小于60 则学分为0；如果成绩大于等于60 则学分正常赋予
            if(studentGrade.getGrade() < 60){
                studentGrade.setCredit(0.0);
            } else {
                studentGrade.setCredit(credit);
            }
        } else {
            // 如果是必修课，则如果成绩合格 则学分正常发放
            if(studentGrade.getGrade() < 60){
                studentGrade.setCredit(null);
            } else {
                studentGrade.setCredit(credit);
            }
        }
        // 根据成绩是否合格 为类型赋值
        if(studentGrade.getGrade() >= 60){
            studentGrade.setType("A"); // 成绩不合格则类型为A
        } else {
            studentGrade.setType("B"); // 成绩不合格则类型为B
        }
        // studentGradeId 拼接方法 studentId+course+type
        String id = studentGrade.getStudentId() + studentGrade.getCourse() + studentGrade.getType();
        studentGrade.setStudentGradeId(id);
    }
}
