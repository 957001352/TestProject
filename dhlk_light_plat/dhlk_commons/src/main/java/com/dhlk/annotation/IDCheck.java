package com.dhlk.annotation;

import com.dhlk.annotation.validators.IDCheckValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Content: 普通用户账号校验注解
 * Author:jpdong
 * Date:2020/3/4
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
@Constraint(validatedBy = IDCheckValidator.class)
@Documented
public @interface IDCheck {
    String message() default "请正确填写账号";

    int minLength() default 3;

    int maxLength() default 20;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
