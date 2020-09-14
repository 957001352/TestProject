package com.dhlk.annotation.validators;

import com.dhlk.annotation.MobizeCheck;
import com.dhlk.utils.CheckUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

/**
 * Content: Mobize校验注解
 * Author:jpdong
 * Date:2020/3/4
 */
public class MobizeCheckValidator implements ConstraintValidator<MobizeCheck, Object> {
    private String message;
    private Class<?>[] groups;
    private Class<? extends Payload>[] payload;

    /**
     * 初始化
     *
     * @param constraint
     */
    public void initialize(MobizeCheck constraint) {
        this.message = constraint.message();
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
        if (CheckUtils.isNull(objStr) || !CheckUtils.isMobize(objStr)) {
            return false;
        }
        return true;
    }
}
