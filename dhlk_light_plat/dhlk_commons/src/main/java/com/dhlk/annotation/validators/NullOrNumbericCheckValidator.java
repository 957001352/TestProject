package com.dhlk.annotation.validators;

import com.dhlk.annotation.NullOrNumbericCheck;
import com.dhlk.utils.CheckUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Content:空或者数字检查（最大值，最小值，是否判断极值）
 * Author:jpdong
 * Date:2020/3/6
 */
public class NullOrNumbericCheckValidator implements ConstraintValidator<NullOrNumbericCheck, Object> {
    private String message;
    private int minValue;
    private int maxValue;
    private boolean minValueRequire;
    private boolean maxValueRequire;


    public void initialize(NullOrNumbericCheck constraint) {
        this.message = constraint.message();
        this.minValue = constraint.minValue();
        this.maxValue = constraint.maxValue();
        this.minValueRequire = constraint.minValueRequire();
        this.maxValueRequire = constraint.maxValueRequire();
    }

    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        if (obj == null) {
            return true;
        }
        String reMessage = "";
        boolean bl = false;
        String strObj = obj.toString();
        if (CheckUtils.isNumeric(strObj)) {
            bl = true;
        }
        if (minValueRequire) {
            if (Integer.parseInt(strObj) < minValue) {
                bl = false;
                this.message = this.message + ",最小值是" + minValue;
                return bl;
            }
        }

        if (maxValueRequire) {
            if (Integer.parseInt(strObj) > maxValue) {
                bl = false;
                this.message = this.message + ",最大值是" + maxValue;
                return bl;
            }
        }

        return bl;
    }
}
