package com.dhlk.annotation;

import com.dhlk.annotation.validators.EmailCheckValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Content: Email校验注解
 * Author:jpdong
 * Date:2020/3/4
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
@Constraint(validatedBy = EmailCheckValidator.class)
@Documented
public @interface EmailCheck {
    String message() default "请正确填写Email";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
