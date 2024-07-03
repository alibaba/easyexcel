package com.alibaba.easytools.spring.exception.convertor;

import com.alibaba.easytools.base.excption.CommonErrorEnum;
import com.alibaba.easytools.base.wrapper.result.ActionResult;

import org.springframework.web.bind.MethodArgumentNotValidException;

/**
 * MethodArgumentNotValidException
 *
 * @author 是仪
 */
public class MethodArgumentNotValidExceptionConvertor implements ExceptionConvertor<MethodArgumentNotValidException> {

    @Override
    public ActionResult convert(MethodArgumentNotValidException exception) {
        String message = ExceptionConvertorUtils.buildMessage(exception.getBindingResult());
        return ActionResult.fail(CommonErrorEnum.PARAM_ERROR, message);
    }
}
