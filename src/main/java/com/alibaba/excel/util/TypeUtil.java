package com.alibaba.excel.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.ExcelColumnProperty;
import com.alibaba.excel.metadata.ExcelHeadProperty;

/**
 * @author jipengfei
 */
public class TypeUtil {

    private static List<String> DATE_FORMAT_LIST = new ArrayList<String>(4);

    static {
        DATE_FORMAT_LIST.add("yyyy/MM/dd HH:mm:ss");
        DATE_FORMAT_LIST.add("yyyy-MM-dd HH:mm:ss");
        DATE_FORMAT_LIST.add("yyyyMMdd HH:mm:ss");
    }

    public static Boolean isNum(Field field) {
        if (field == null) {
            return false;
        }
        if (Integer.class.equals(field.getType()) || int.class.equals(field.getType())) {
            return true;
        }
        if (Double.class.equals(field.getType()) || double.class.equals(field.getType())) {
            return true;
        }

        if (Long.class.equals(field.getType()) || long.class.equals(field.getType())) {
            return true;
        }

        if (BigDecimal.class.equals(field.getType())) {
            return true;
        }
        return false;
    }

    public static Boolean isNum(Object cellValue) {
        if (cellValue instanceof Integer || cellValue instanceof Double || cellValue instanceof Short
                        || cellValue instanceof Long || cellValue instanceof Float || cellValue instanceof BigDecimal) {
            return true;
        }
        return false;
    }

    public static String getDefaultDateString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);

    }

    public static Date getSimpleDateFormatDate(String value, String format) {
        if (!StringUtils.isEmpty(value)) {
            Date date = null;
            if (!StringUtils.isEmpty(format)) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
                try {
                    date = simpleDateFormat.parse(value);
                    return date;
                } catch (ParseException e) {
                }
            }
            for (String dateFormat : DATE_FORMAT_LIST) {
                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
                    date = simpleDateFormat.parse(value);
                } catch (ParseException e) {
                }
                if (date != null) {
                    break;
                }
            }

            return date;

        }
        return null;

    }


    public static String formatFloat(String value) {
        if (null != value && value.contains(".")) {
            if (isNumeric(value)) {
                try {
                    BigDecimal decimal = new BigDecimal(value);
                    BigDecimal setScale = decimal.setScale(10, RoundingMode.HALF_DOWN).stripTrailingZeros();
                    return setScale.toPlainString();
                } catch (Exception e) {
                }
            }
        }
        return value;
    }

    public static String formatFloat0(String value, int n) {
        if (null != value && value.contains(".")) {
            if (isNumeric(value)) {
                try {
                    BigDecimal decimal = new BigDecimal(value);
                    BigDecimal setScale = decimal.setScale(n, RoundingMode.HALF_DOWN);
                    return setScale.toPlainString();
                } catch (Exception e) {
                }
            }
        }
        return value;
    }

    public static final Pattern pattern = Pattern.compile("[\\+\\-]?[\\d]+([\\.][\\d]*)?([Ee][+-]?[\\d]+)?$");

    private static boolean isNumeric(String str) {
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static String formatDate(Date cellValue, String format) {
        SimpleDateFormat simpleDateFormat;
        if (!StringUtils.isEmpty(format)) {
            simpleDateFormat = new SimpleDateFormat(format);
        } else {
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        return simpleDateFormat.format(cellValue);
    }
}
