package com.alibaba.easytools.spring.exception.convertor;

import com.alibaba.easytools.base.excption.CommonErrorEnum;
import com.alibaba.easytools.base.wrapper.result.ActionResult;

import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * MethodArgumentTypeMismatchException
 *
 * @author 是仪
 */
public class MethodArgumentTypeMismatchExceptionConvertor
    implements ExceptionConvertor<MethodArgumentTypeMismatchException> {

    @Override
    public ActionResult convert(MethodArgumentTypeMismatchException exception) {
        return ActionResult.fail(CommonErrorEnum.PARAM_ERROR, "请输入正确的数据格式");
    }
}
