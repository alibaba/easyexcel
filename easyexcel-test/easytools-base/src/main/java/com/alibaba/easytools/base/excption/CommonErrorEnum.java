package com.alibaba.easytools.base.excption;

import com.alibaba.easytools.base.enums.BaseErrorEnum;

import lombok.Getter;

/**
 * 通用的返回码定义
 *
 * @author 是仪
 */
@Getter
public enum CommonErrorEnum implements BaseErrorEnum {
    /**
     * 通用业务异常
     */
    COMMON_BUSINESS_ERROR("填写的信息异常，请尝试刷新页面"),
    /**
     * 通用系统异常
     */
    COMMON_SYSTEM_ERROR("系统开小差啦，请尝试刷新页面或者联系管理员"),
    /**
     * 通用系统异常
     */
    PARAM_ERROR("参数错误"),
    /**
     * 找不到数据
     */
    DATA_NOT_FOUND("找不到对应数据"),
    /**
     * 没有权限
     */
    PERMISSION_DENIED("权限不够"),

    /**
     * 超过最大上传
     */
    MAX_UPLOAD_SIZE("上传的文件超过最大限制"),

    /**
     * 需要登陆
     * 前端需要跳转到登陆界面
     */
    NEED_LOGGED_IN("未登录，请重新登陆"),

    /**
     * 没有登录
     * 代表用户没有登陆，不需要跳转展示为空即可
     */
    NOT_LOGGED_IN("无法获取登陆信息，请尝试刷新页面或者重新登陆"),

    /**
     * 超过访问限制
     */
    ACCESS_LIMIT_EXCEEDED("超过访问限制"),

    /**
     * 找不到指定页面
     */
    PAGE_NOT_FOUND("找不到指定页面"),

    /**
     * 上传文件失败
     */
    FAILED_TO_UPLOAD_FILE("上传文件失败"),


    /**
     * metaq重推专用异常
     */
    METAQ_RECONSUME_LATER("metaq重推专用异常"),

    ;

    CommonErrorEnum(String description) {
        this.description = description;
    }

    final String description;

    @Override
    public String getCode() {
        return this.name();
    }
}
