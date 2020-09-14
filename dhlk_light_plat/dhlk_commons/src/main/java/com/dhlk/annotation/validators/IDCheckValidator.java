package com.dhlk.annotation.validators;

import com.dhlk.annotation.IDCheck;
import com.dhlk.utils.CheckUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

/**
 * Content:普通用户账号校验注解
 * Author:jpdong
 * Date:2020/3/4
 */
public class IDCheckValidator implements ConstraintValidator<IDCheck, Object> {
    private String message;
    private int minLength;
    private int maxLength;

    private Class<?>[] groups;
    private Class<? extends Payload>[] payload;

    /**
     * 初始化
     *
     * @param constraint
     */
    public void initialize(IDCheck constraint) {
        this.message = constraint.message();
        this.minLength = constraint.minLength();
        this.maxLength = constraint.maxLength();
        this.groups = constraint.groups();
        this.payload = constraint.payload();
    }

    /**
     * 验证
     *
     * @param obj     注解对象
     * @param context 上下文
     * @return true/false
     */
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        if (obj == null) {
            return false;
        }
        String objStr = obj.toString();
        if (CheckUtils.isNull(objStr) || !CheckUtils.isAdminID(objStr)) {
            this.message = "请输入账号，长度在" + this.minLength + "~" + this.maxLength + "之间";
            return false;
        }
        return true;
    }
}
