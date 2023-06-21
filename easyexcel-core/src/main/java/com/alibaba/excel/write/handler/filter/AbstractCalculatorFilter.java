package com.alibaba.excel.write.handler.filter;

import com.alibaba.excel.util.PipeFilterUtils;
import com.alibaba.excel.util.StringUtils;
import com.alibaba.excel.write.handler.BasePipeFilter;
import com.alibaba.excel.write.handler.PipeDataWrapper;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Description:
 * abstract calculator filter
 *
 * @author linfeng
 */
@Slf4j
public abstract class AbstractCalculatorFilter extends BasePipeFilter<Object, Object> {

    public static final String INT = "int";
    public static final String NUMBER = "number";
    public static final String REGEX = "_";

    public static class Calculator {

        public static double add(double a, double b) {
            return a + b;
        }

        public static double subtract(double a, double b) {
            return a - b;
        }

        public static double multiply(double a, double b) {
            return a * b;
        }

        public static double divide(double a, double b) {
            if (b == 0) {
                throw new IllegalArgumentException("Divider cannot be 0");
            }
            return a / b;
        }
    }

    @Override
    public PipeDataWrapper<Object> apply(PipeDataWrapper<Object> wrapper) {

        // 验证
        if (!verify(wrapper)) {
            return wrapper;
        }

        Object value = wrapper.getData();
        if (Objects.isNull(value)) {
            return PipeDataWrapper.error(errorPrefix() + "incoming data cannot be empty");
        }

        if (PipeFilterUtils.isEmpty(params())) {
            return PipeDataWrapper.error(errorPrefix() + "missing parameter for instruction");
        }

        String params1 = params().get(0);
        if (StringUtils.isBlank(params1)) {
            return PipeDataWrapper.error(errorPrefix() + "instruction parameter is empty");
        }

        double number1 = 0D;
        if (value instanceof Number) {
            number1 = ((Number) value).doubleValue();
        } else if (value instanceof String) {
            try {
                number1 = Double.parseDouble((String) value);
            } catch (Exception e) {
                number1 = 0D;
            }
        }

        double number2;
        try {
            number2 = Double.parseDouble(params1);
        } catch (Exception e) {
            number2 = 0D;
        }

        double result = calculator(number1, number2);

        return showDataWrapper(result);

    }

    /**
     * Construct pipeline data wrapping objects based on calculation results
     *
     * @param result Calculation results
     * @return Pipe Data Wrapper
     */
    private PipeDataWrapper<Object> showDataWrapper(double result) {
        if (params().size() <= 1) {
            return PipeDataWrapper.success(result);
        } else {
            String params2 = params().get(1);
            if (StringUtils.isBlank(params2)) {
                return PipeDataWrapper.success(result);
            }
            String[] params2Array = params2.split(REGEX);
            if (INT.equalsIgnoreCase(params2Array[0])) {

                return PipeDataWrapper.success(BigDecimal.valueOf(result).setScale(0, RoundingMode.HALF_UP).intValue());
            } else if (NUMBER.equalsIgnoreCase(params2Array[0])) {
                if (params2Array.length > 1) {
                    if (StringUtils.isNumeric(params2Array[1])) {
                        int scale = Integer.parseInt(params2Array[1]);
                        BigDecimal bigDecimal = BigDecimal.valueOf(result).setScale(scale, RoundingMode.HALF_UP);
                        if (scale == 0) {
                            return PipeDataWrapper.success(bigDecimal.intValue());
                        } else {
                            return PipeDataWrapper.success(bigDecimal.doubleValue());
                        }
                    } else {
                        log.warn("The digit parameter passed by the Calculator instruction [cal_add, cal_sub, cal_mul, cal_div] is not a number");
                        return PipeDataWrapper.success(result);
                    }
                } else {
                    return PipeDataWrapper.success(result);
                }
            } else {
                return PipeDataWrapper.success(result);
            }
        }
    }

    /**
     * calculator
     *
     * @param number1 number
     * @param number2 number
     * @return Calculation results
     */
    private double calculator(double number1, double number2) {
        double result;
        switch (filterName()) {
            case "cal-add":
                result = Calculator.add(number1, number2);
                break;
            case "cal-sub":
                result = Calculator.subtract(number1, number2);
                break;
            case "cal-mul":
                result = Calculator.multiply(number1, number2);
                break;
            case "cal-div":
                if (number2 == 0) {
                    throw new RuntimeException("The cal_div instruction with a divisor of 0 is not supported");
                }
                result = Calculator.divide(number1, number2);
                break;
            default:
                throw new RuntimeException("The Calculator instruction is not supported");
        }
        return result;
    }
}
