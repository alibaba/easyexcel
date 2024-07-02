package com.alibaba.easytools.base.excption;

import lombok.Getter;

/**
 * mq重推专用异常
 *
 * @author 是仪
 */

@Getter
public class MetaqReconsumeLaterBusinessException extends BusinessException {

    public MetaqReconsumeLaterBusinessException() {
        super(CommonErrorEnum.METAQ_RECONSUME_LATER, "mq消息重试");
    }

    public MetaqReconsumeLaterBusinessException(String message) {
        super(CommonErrorEnum.METAQ_RECONSUME_LATER, message);
    }

    public static MetaqReconsumeLaterBusinessException of(String message) {
        return new MetaqReconsumeLaterBusinessException(message);
    }

    public static MetaqReconsumeLaterBusinessException newInstance() {
        return new MetaqReconsumeLaterBusinessException();
    }

}
