package com.dhlk.annotation;

import com.dhlk.annotation.validators.MobizeCheckValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Content:手机号校验注解
 * Author:jpdong
 * Date:2020/3/4
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
@Constraint(validatedBy = MobizeCheckValidator.class)
@Documented
public @interface MobizeCheck {
    String message() default "请正确填写手机号码";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
