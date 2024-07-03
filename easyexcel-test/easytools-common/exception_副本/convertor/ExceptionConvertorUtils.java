package com.alibaba.easytools.spring.exception.convertor;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import com.alibaba.easytools.base.constant.SymbolConstant;
import com.alibaba.easytools.base.excption.BusinessException;
import com.alibaba.easytools.base.wrapper.result.ActionResult;

import com.google.common.collect.Maps;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * 转换工具类
 *
 * @author 是仪
 */
public class ExceptionConvertorUtils {

    /**
     * 所有的异常处理转换器
     */
    public static final Map<Class<?>, ExceptionConvertor> EXCEPTION_CONVERTOR_MAP = Maps.newHashMap();

    static {
        EXCEPTION_CONVERTOR_MAP.put(MethodArgumentNotValidException.class,
            new MethodArgumentNotValidExceptionConvertor());
        EXCEPTION_CONVERTOR_MAP.put(BindException.class, new BindExceptionConvertor());
        EXCEPTION_CONVERTOR_MAP.put(BusinessException.class, new BusinessExceptionConvertor());
        EXCEPTION_CONVERTOR_MAP.put(MissingServletRequestParameterException.class, new ParamExceptionConvertor());
        EXCEPTION_CONVERTOR_MAP.put(IllegalArgumentException.class, new ParamExceptionConvertor());
        EXCEPTION_CONVERTOR_MAP.put(MethodArgumentTypeMismatchException.class,
            new MethodArgumentTypeMismatchExceptionConvertor());
        EXCEPTION_CONVERTOR_MAP.put(MaxUploadSizeExceededException.class,
            new MaxUploadSizeExceededExceptionConvertor());
        EXCEPTION_CONVERTOR_MAP.put(HttpRequestMethodNotSupportedException.class, new BusinessExceptionConvertor());
        EXCEPTION_CONVERTOR_MAP.put(ConstraintViolationException.class, new ConstraintViolationExceptionConvertor());
        EXCEPTION_CONVERTOR_MAP.put(HttpMessageNotReadableException.class,
            new ParamExceptionConvertor());
    }

    /**
     * 默认转换器
     */
    public static ExceptionConvertor DEFAULT_EXCEPTION_CONVERTOR = new DefaultExceptionConvertor();

    /**
     * 提取ConstraintViolationException中的错误消息
     *
     * @param e
     * @return
     */
    public static String buildMessage(ConstraintViolationException e) {
        if (e == null || CollectionUtils.isEmpty(e.getConstraintViolations())) {
            return null;
        }
        int index = 1;
        StringBuilder msg = new StringBuilder();
        msg.append("请检查以下信息：");
        for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
            msg.append(index++);
            // 得到错误消息
            msg.append(SymbolConstant.DOT);
            msg.append(" 字段(");
            msg.append(constraintViolation.getPropertyPath());
            msg.append(")传入的值为：\"");
            msg.append(constraintViolation.getInvalidValue());
            msg.append("\"，校验失败,原因是：");
            msg.append(constraintViolation.getMessage());
            msg.append(SymbolConstant.SEMICOLON);
        }
        return msg.toString();
    }

    /**
     * 提取BindingResult中的错误消息
     *
     * @param result
     * @return
     */
    public static String buildMessage(BindingResult result) {
        List<ObjectError> errors = result.getAllErrors();
        if (CollectionUtils.isEmpty(errors)) {
            return null;
        }

        int index = 1;
        StringBuilder msg = new StringBuilder();
        msg.append("请检查以下信息：");
        for (ObjectError e : errors) {
            msg.append(index++);
            // 得到错误消息
            msg.append(SymbolConstant.DOT);
            msg.append(" ");
            msg.append("字段(");
            msg.append(e.getObjectName());
            if (e instanceof FieldError) {
                FieldError fieldError = (FieldError)e;
                msg.append(SymbolConstant.DOT);
                msg.append(fieldError.getField());
            }
            msg.append(")");
            if (e instanceof FieldError) {
                FieldError fieldError = (FieldError)e;
                msg.append("传入的值为：\"");
                msg.append(fieldError.getRejectedValue());
                msg.append("\",");
            }
            msg.append("校验失败,原因是：");
            msg.append(e.getDefaultMessage());
            msg.append(SymbolConstant.SEMICOLON);
        }
        return msg.toString();
    }

    /**
     * 拼接头的日志信息
     *
     * @param request
     * @return
     */
    public static String buildHeaderString(HttpServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headName = headerNames.nextElement();
            stringBuilder.append(headName);
            stringBuilder.append(SymbolConstant.COLON);
            stringBuilder.append(request.getHeader(headName));
            stringBuilder.append(SymbolConstant.COMMA);
        }
        return stringBuilder.toString();
    }

    /**
     * 转换结果
     *
     * @param exception
     * @return
     */
    public static ActionResult convert(Throwable exception) {
        ExceptionConvertor exceptionConvertor = EXCEPTION_CONVERTOR_MAP.get(exception.getClass());
        if (exceptionConvertor == null) {
            exceptionConvertor = DEFAULT_EXCEPTION_CONVERTOR;
        }
        return exceptionConvertor.convert(exception);
    }

}
