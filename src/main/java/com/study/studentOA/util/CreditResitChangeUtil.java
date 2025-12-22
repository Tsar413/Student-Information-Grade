package com.study.studentOA.util;

import java.math.BigDecimal;

public class CreditResitChangeUtil {
    /**
     * 学分修正
     *
     * @param credit 原始学分
     * @return 修改后的学分
     */
    public static Double creditResitChange(Double credit){
        credit *= 0.8;
        // 整数部分
        long longPart = (long) Math.floor(credit);
        // 原数减去整数部分，为小数部分
        double doublePart = new BigDecimal(String.valueOf(credit)).subtract(new BigDecimal(longPart)).doubleValue();
        if(doublePart < 0.3){
            return (double) longPart;
        }
        if(doublePart > 0.7){
            return (double) (longPart + 1);
        }
        return longPart + 0.5;
    }
}
