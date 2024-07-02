package com.alibaba.easytools.base.wrapper;

/**
 * @author qiuyuyu
 * @date 2022/01/20
 */
public interface Result<T> extends Traceable{
    /**
     * 是否成功
     *
     * @return
     * @mock true
     */
    boolean success();

    /**
     * 设置是否成功
     *
     * @return
     */
    void success(boolean success);

    /**
     * 错误编码
     *
     * @return
     * @mock 000000
     */
    String errorCode();

    /**
     * 设置错误编码
     *
     * @param errorCode
     */
    void errorCode(String errorCode);

    /**
     * 错误信息
     *
     * @return
     */
    String errorMessage();


    /**
     * 设置错误信息
     *
     * @param errorMessage
     */
    void errorMessage(String errorMessage);
}
