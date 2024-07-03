package com.alibaba.easytools.spring.exception;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import com.alibaba.easytools.base.excption.BusinessException;
import com.alibaba.easytools.base.excption.SystemException;
import com.alibaba.easytools.base.wrapper.result.ActionResult;
import com.alibaba.easytools.spring.exception.convertor.ExceptionConvertorUtils;
import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

/**
 * 拦截Controller异常
 *
 * @author 是仪
 */
@ControllerAdvice
@Slf4j
public class EasyControllerExceptionHandler {

    /**
     * 业务异常
     *
     * @param request   request
     * @param exception exception
     * @return return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class, IllegalArgumentException.class,
        MissingServletRequestParameterException.class, MethodArgumentTypeMismatchException.class,
        BusinessException.class, MaxUploadSizeExceededException.class, ClientAbortException.class,
        HttpRequestMethodNotSupportedException.class, HttpMediaTypeNotAcceptableException.class,
        MultipartException.class, MissingRequestHeaderException.class, HttpMediaTypeNotSupportedException.class,
        ConstraintViolationException.class, HttpMessageNotReadableException.class})
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public ActionResult handleBusinessException(HttpServletRequest request, Exception exception) {
        ActionResult result = ExceptionConvertorUtils.convert(exception);
        log.info("发生业务异常{}:{}", request.getRequestURI(), result, exception);
        return result;
    }

    /**
     * 系统异常
     *
     * @param request   request
     * @param exception exception
     * @return return
     */
    @ExceptionHandler({SystemException.class})
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public ActionResult handleSystemException(HttpServletRequest request, Exception exception) {
        ActionResult result = ExceptionConvertorUtils.convert(exception);
        log.error("发生业务异常{}:{}", request.getRequestURI(), result, exception);
        return result;
    }

    /**
     * 未知异常 需要人工介入查看日志
     *
     * @param request   request
     * @param exception exception
     * @return return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public ActionResult handledException(HttpServletRequest request, Exception exception) {
        ActionResult result = ExceptionConvertorUtils.convert(exception);
        log.error("发生未知异常{}:{}:{},请求参数:{}", request.getRequestURI(),
            ExceptionConvertorUtils.buildHeaderString(request), result,
            JSON.toJSONString(request.getParameterMap()), exception);
        return result;
    }

}
