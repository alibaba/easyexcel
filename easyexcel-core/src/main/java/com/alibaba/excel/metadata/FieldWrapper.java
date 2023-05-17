package com.alibaba.excel.metadata;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * filed wrapper
 *
 * @author Jiaju Zhuang
 */
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class FieldWrapper {

    /**
     * field
     */
    private Field field;

    /**
     * The field name matching cglib
     */
    private String fieldName;

    /**
     * The name of the sheet header.
     *
     * @see ExcelProperty
     */
    private String[] heads;
}
