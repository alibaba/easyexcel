package com.alibaba.easytools.spring.exception.convertor;

import com.alibaba.easytools.base.excption.CommonErrorEnum;
import com.alibaba.easytools.base.wrapper.result.ActionResult;

import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * MaxUploadSizeExceededException
 *
 * @author 是仪
 */
public class MaxUploadSizeExceededExceptionConvertor implements ExceptionConvertor<MaxUploadSizeExceededException> {

    @Override
    public ActionResult convert(MaxUploadSizeExceededException exception) {
        return ActionResult.fail(CommonErrorEnum.MAX_UPLOAD_SIZE);
    }
}
