package com.dhlk.annotation;

import com.dhlk.annotation.validators.NullOrNumbericCheckValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Content: message='',minValue=0,minValueRequire=false,maxValue=0,maxValueRequire=false
 * Author:jpdong
 * Date:2020/3/6
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
@Constraint(validatedBy = NullOrNumbericCheckValidator.class)
@Documented
public @interface NullOrNumbericCheck {
    String message() default "必须是数字或者NULL";

    int minValue() default 0;

    boolean minValueRequire() default false;

    int maxValue() default 0;

    boolean maxValueRequire() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
