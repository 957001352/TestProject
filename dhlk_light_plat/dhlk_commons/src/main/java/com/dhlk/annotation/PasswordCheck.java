package com.dhlk.annotation;

import com.dhlk.annotation.validators.PasswordCheckValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Content:密码校验注解
 * Author:jpdong
 * Date:2020/3/4
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
@Constraint(validatedBy = PasswordCheckValidator.class)
@Documented
public @interface PasswordCheck {
    String message() default "请正确填写密码,默认长度在6~20";

    int minLength() default 6;

    int maxLength() default 20;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
