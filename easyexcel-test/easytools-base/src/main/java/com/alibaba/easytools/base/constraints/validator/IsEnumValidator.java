package com.alibaba.easytools.base.constraints.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.alibaba.easytools.base.constraints.IsEnum;
import com.alibaba.easytools.base.enums.BaseEnum;

/**
 * 枚举的校验器
 *
 * @author 是仪
 */
public class IsEnumValidator implements ConstraintValidator<IsEnum, String> {

    private Class<? extends BaseEnum<?>> enumType;

    @Override
    public void initialize(IsEnum constraintAnnotation) {
        enumType = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        BaseEnum<?>[] baseEnums = enumType.getEnumConstants();
        if (baseEnums == null) {
            return false;
        }
        for (BaseEnum<?> baseEnum : baseEnums) {
            if (baseEnum.getCode().equals(value)) {
                return true;
            }
        }
        return false;
    }

}