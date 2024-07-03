package com.alibaba.easytools.spring.exception.convertor;

import com.alibaba.easytools.base.excption.BusinessException;
import com.alibaba.easytools.base.wrapper.result.ActionResult;

/**
 * BusinessException
 *
 * @author 是仪
 */
public class BusinessExceptionConvertor implements ExceptionConvertor<BusinessException> {

    @Override
    public ActionResult convert(BusinessException exception) {
        return ActionResult.fail(exception.getCode(), exception.getMessage());
    }
}
