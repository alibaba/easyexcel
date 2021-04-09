package com.alibaba.excel.util;

import java.lang.reflect.Field;

/**
 * Field utils
 *
 * @author Jiaju Zhuang
 **/
public class FieldUtils {

    private static final int START_RESOLVE_FIELD_LENGTH = 2;

    /**
     * Parsing the name matching cglib。
     * <ul>
     *     <ul>null -> null</ul>
     *     <ul>string1 -> string1</ul>
     *     <ul>String2 -> string2</ul>
     *     <ul>sTring3 -> STring3</ul>
     *     <ul>STring4 -> STring4</ul>
     *     <ul>STRING5 -> STRING5</ul>
     *     <ul>STRing6 -> STRing6</ul>
     * </ul>
     *
     * @param field field
     * @return field name.
     */
    public static String resolveCglibFieldName(Field field) {
        if (field == null) {
            return null;
        }
        String fieldName = field.getName();
        if (StringUtils.isBlank(fieldName) || fieldName.length() < START_RESOLVE_FIELD_LENGTH) {
            return fieldName;
        }
        char firstChar = fieldName.charAt(0);
        char secondChar = fieldName.charAt(1);
        if (Character.isUpperCase(firstChar) == Character.isUpperCase(secondChar)) {
            return fieldName;
        }
        if (Character.isUpperCase(firstChar)) {
            return buildFieldName(Character.toLowerCase(firstChar), fieldName);
        }
        return buildFieldName(Character.toUpperCase(firstChar), fieldName);
    }

    private static String buildFieldName(char firstChar, String fieldName) {
        return firstChar + fieldName.substring(1);
    }
}
