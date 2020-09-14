package com.dhlk.annotation;

import com.dhlk.annotation.validators.AdminIDCheckValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Content: 管理员账号校验注解
 * Author:jpdong
 * Date:2020/3/4
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
@Constraint(validatedBy = AdminIDCheckValidator.class)
@Documented
public @interface AdminIDCheck {
    String message() default "请正确填写账号，默认长度3~20";

    int minLength() default 3;

    int maxLength() default 20;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
