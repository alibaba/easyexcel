package com.alibaba.excel.metadata.property;

import com.alibaba.excel.annotation.format.EnumFormat;
import com.alibaba.excel.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xiajiafu
 * @since 2022/7/13
 */
@Getter
@Setter
@EqualsAndHashCode
public class EnumFormatProperty {

    private Class<? extends Enum<?>> targetClass;
    private String convertToExcelDataMethod;

    public EnumFormatProperty(Class<? extends Enum<?>> targetClass, String convertToExcelDataMethod) {
        this.targetClass = targetClass;
        this.convertToExcelDataMethod = convertToExcelDataMethod;
    }

    public static EnumFormatProperty build(EnumFormat enumFormat) {
        if (enumFormat == null) {
            return null;
        }
        if (StringUtils.isBlank(enumFormat.convertToExcelDataMethod())) {
            return null;
        }
        return new EnumFormatProperty(enumFormat.targetClass(), enumFormat.convertToExcelDataMethod());
    }

}
