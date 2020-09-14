package com.dhlk.annotation;

import com.dhlk.annotation.validators.LoginNameCheckValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Content:用户登录名校验注解
 * Author:gchen
 * Date:2020/4/16
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
@Constraint(validatedBy = LoginNameCheckValidator.class)
@Documented
public @interface LoginNameCheck {
    String message() default "请正确填写的登录账号，默认长度3~20";

    int minLength() default 3;

    int maxLength() default 20;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
