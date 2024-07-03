package com.alibaba.easytools.spring.exception.convertor;

import com.alibaba.easytools.base.excption.CommonErrorEnum;
import com.alibaba.easytools.base.wrapper.result.ActionResult;

import org.springframework.validation.BindException;

/**
 * BindException
 *
 * @author 是仪
 */
public class BindExceptionConvertor implements ExceptionConvertor<BindException> {

    @Override
    public ActionResult convert(BindException exception) {
        String message = ExceptionConvertorUtils.buildMessage(exception.getBindingResult());
        return ActionResult.fail(CommonErrorEnum.PARAM_ERROR, message);
    }
}
