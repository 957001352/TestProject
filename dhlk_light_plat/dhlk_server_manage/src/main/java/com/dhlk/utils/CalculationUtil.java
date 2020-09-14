package com.dhlk.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class CalculationUtil {
    public static String calculation(long l1,long l2){
        String result = "0";
        BigDecimal n1= new BigDecimal(l1);
        BigDecimal n2= new BigDecimal(l2);
        DecimalFormat df1 = new DecimalFormat("0.00");
        //高精度实数除法
        MathContext mc = new MathContext(10, RoundingMode.HALF_DOWN); //必须设置精度
        if(!n2.equals(BigDecimal.ZERO)){
            double v = Double.parseDouble(n1.divide(n2, mc) + "")*100d;
            result = df1.format(v);
        }
        return result;
    }
}
