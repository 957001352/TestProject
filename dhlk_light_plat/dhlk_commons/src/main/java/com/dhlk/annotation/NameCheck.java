package com.dhlk.annotation;

import com.dhlk.annotation.validators.NameCheckValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Content:用户姓名校验注解
 * Author:gchen
 * Date:2020/4/16
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
@Constraint(validatedBy = NameCheckValidator.class)
@Documented
public @interface NameCheck {
    String message() default "请正确填写姓名";

    int minLength() default 2;

    int maxLength() default 20;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
