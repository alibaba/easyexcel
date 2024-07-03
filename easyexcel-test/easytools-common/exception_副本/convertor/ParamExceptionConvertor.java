package com.alibaba.easytools.spring.exception.convertor;

import com.alibaba.easytools.base.excption.CommonErrorEnum;
import com.alibaba.easytools.base.wrapper.result.ActionResult;

/**
 * 参数异常 目前包括
 * ConstraintViolationException
 * MissingServletRequestParameterException
 * IllegalArgumentException
 * HttpMessageNotReadableException
 *
 * @author 是仪
 */
public class ParamExceptionConvertor implements ExceptionConvertor<Throwable> {

    @Override
    public ActionResult convert(Throwable exception) {
        return ActionResult.fail(CommonErrorEnum.PARAM_ERROR, exception.getMessage());
    }
}
