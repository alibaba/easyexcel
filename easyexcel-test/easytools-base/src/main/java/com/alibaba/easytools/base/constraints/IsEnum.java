package com.alibaba.easytools.base.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

import com.alibaba.easytools.base.constraints.validator.IsEnumValidator;
import com.alibaba.easytools.base.enums.BaseEnum;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 判断一个对象是否是{@link BaseEnum} 里面具体的值
 *
 * @author 是仪
 */
@Documented
@Constraint(validatedBy = {IsEnumValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
public @interface IsEnum {

    /**
     * 需要校验的枚举类
     *
     * @return 枚举类
     */
    Class<? extends BaseEnum<?>> value();

    String message() default "必须是枚举：{value} 中的值";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Defines several {@code @IsEnum} annotations on the same element.
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        IsEnum[] value();
    }
}