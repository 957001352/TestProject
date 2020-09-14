package com.dhlk.annotation;

import com.dhlk.annotation.validators.UrlCheckValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Content: URL校验注解
 * Author:jpdong
 * Date:2020/3/4
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = UrlCheckValidator.class)
public @interface UrlCheck {
    String message() default "URL非法";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
