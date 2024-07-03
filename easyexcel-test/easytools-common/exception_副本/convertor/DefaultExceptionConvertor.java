package com.alibaba.easytools.spring.exception.convertor;

import com.alibaba.easytools.base.excption.BusinessException;
import com.alibaba.easytools.base.excption.CommonErrorEnum;
import com.alibaba.easytools.base.wrapper.result.ActionResult;

/**
 * 默认的异常处理
 * 直接抛出系统异常
 *
 * @author 是仪
 */
public class DefaultExceptionConvertor implements ExceptionConvertor<Throwable> {

    @Override
    public ActionResult convert(Throwable exception) {
        if (exception instanceof BusinessException) {
            BusinessException businessException = (BusinessException)exception;
            return ActionResult.fail(businessException.getCode(), businessException.getMessage());
        }
        return ActionResult.fail(CommonErrorEnum.COMMON_SYSTEM_ERROR);
    }
}
