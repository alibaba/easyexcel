package com.alibaba.easytools.spring.exception.convertor;

import javax.validation.ConstraintViolationException;

import com.alibaba.easytools.base.excption.CommonErrorEnum;
import com.alibaba.easytools.base.wrapper.result.ActionResult;

/**
 * ConstraintViolationException
 *
 * @author 是仪
 */
public class ConstraintViolationExceptionConvertor implements ExceptionConvertor<ConstraintViolationException> {

    @Override
    public ActionResult convert(ConstraintViolationException exception) {
        String message = ExceptionConvertorUtils.buildMessage(exception);
        return ActionResult.fail(CommonErrorEnum.PARAM_ERROR, message);
    }
}
