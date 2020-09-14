package com.dhlk.annotation;

import com.dhlk.annotation.validators.SetNameCheckValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Content:参数姓名校验注解
 * Author:gchen
 * Date:2020/4/16
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
@Constraint(validatedBy = SetNameCheckValidator.class)
@Documented
public @interface SetNameCheck {
    String message() default "请正确填写名称";

    int minLength() default 2;

    int maxLength() default 50;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
