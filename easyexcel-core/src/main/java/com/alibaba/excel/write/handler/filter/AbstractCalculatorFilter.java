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
 *
 * @author linfeng
 * @version 1.0.0
 * @since 2023/6/17 10:11
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
                throw new IllegalArgumentException("除数不能为0");
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
            return PipeDataWrapper.error(errorPrefix() + "传入数据不能为空");
        }

        if (PipeFilterUtils.isEmpty(params())) {
            return PipeDataWrapper.error(errorPrefix() + "指令缺失参数");
        }

        String params1 = params().get(0);
        if (StringUtils.isBlank(params1)) {
            return PipeDataWrapper.error(errorPrefix() + "指令参数为空");
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
     * 最终显示结果
     *
     * @param result 结果数据
     * @return 显示样式
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
                        log.warn("Calculator指令[cal-add,cal-sub,cal-mul,cal-div]传递的位数参数不是数字");
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
     * 计算
     *
     * @param number1 数字
     * @param number2 数字
     * @return 计算结果
     */
    private double calculator(double number1, double number2) {
        double result = 0D;
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
                    throw new RuntimeException("cal-div指令除数为0不支持");
                }
                result = Calculator.divide(number1, number2);
                break;
            default:
                throw new RuntimeException("Calculator指令不支持");
        }
        return result;
    }
}
