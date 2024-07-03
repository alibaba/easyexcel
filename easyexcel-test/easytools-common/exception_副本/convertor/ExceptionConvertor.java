package com.alibaba.easytools.spring.exception.convertor;

import com.alibaba.easytools.base.wrapper.result.ActionResult;

/**
 * 异常转换器
 *
 * @author 是仪
 */
public interface ExceptionConvertor<T extends Throwable> {

    /**
     * 转换异常
     *
     * @param exception
     * @return
     */
    ActionResult convert(T exception);
}
