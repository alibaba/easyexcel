package com.alibaba.excel.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;

/**
 * 类型转换工具类
 *
 * @author jipengfei
 */
public class TypeUtil {

    private static List<SimpleDateFormat> DATE_FORMAT_LIST = new ArrayList<SimpleDateFormat>(4);

    static {
        DATE_FORMAT_LIST.add(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"));
        DATE_FORMAT_LIST.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    public static Object convert(String value, Field field, String format, boolean us) {
        if (isNotEmpty(value)) {
            if (String.class.equals(field.getType())) {
                return value;
            }
            if (Integer.class.equals(field.getType()) || int.class.equals(field.getType())) {
                return Integer.parseInt(value);
            }
            if (Double.class.equals(field.getType()) || double.class.equals(field.getType())) {
                return Double.parseDouble(value);
            }
            if (Boolean.class.equals(field.getType()) || boolean.class.equals(field.getType())) {
                String valueLower = value.toLowerCase();
                if (valueLower.equals("true") || valueLower.equals("false")) {
                    return Boolean.parseBoolean(value.toLowerCase());
                }
                Integer integer = Integer.parseInt(value);
                if (integer == 0) {
                    return false;
                } else {
                    return true;
                }
            }
            if (Long.class.equals(field.getType()) || long.class.equals(field.getType())) {
                return Long.parseLong(value);
            }
            if (Date.class.equals(field.getType())) {
                if (value.contains("-") || value.contains("/") || value.contains(":")) {
                    return getSimpleDateFormatDate(value, format);
                } else {
                    Double d = Double.parseDouble(value);
                    return HSSFDateUtil.getJavaDate(d, us);
                }
            }
            if (BigDecimal.class.equals(field.getType())) {
                return new BigDecimal(value);
            }

        }
        return null;
    }

    public static String getDefaultDateString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);

    }

    public static Date getSimpleDateFormatDate(String value, String format) {
        if (isNotEmpty(value)) {
            Date date = null;
            if (isNotEmpty(format)) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
                try {
                    date = simpleDateFormat.parse(value);
                    return date;
                } catch (ParseException e) {
                }
            }
            for (SimpleDateFormat dateFormat : DATE_FORMAT_LIST) {
                try {
                    date = dateFormat.parse(value);
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

    private static Boolean isNotEmpty(String value) {
        if (value == null) {
            return false;
        }
        if (value.trim().equals("")) {
            return false;
        }
        return true;

    }

    public static String formatFloat(String value) {
        if (value.contains(".")) {
            if (isNumeric(value)) {
                try {
                    BigDecimal decimal = new BigDecimal(value);
                    BigDecimal setScale = decimal.setScale(10, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros();
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


}
