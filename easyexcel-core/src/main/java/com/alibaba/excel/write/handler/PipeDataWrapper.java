package com.alibaba.excel.write.handler;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * Description:
 * filter 响应对象
 *
 * @author linfeng
 * @version 1.0.0
 * @since 2023/6/14 15:56
 */
@Data
@AllArgsConstructor
public class PipeDataWrapper<R> implements Serializable {
    private int status;
    private String message;
    private R data;

    public static <R> PipeDataWrapper<R> success(R data) {
        return new PipeDataWrapper<>(200, "OK", data);
    }

    public static <R> PipeDataWrapper<R> error(String error, R data) {
        return new PipeDataWrapper<>(500, error, data);
    }

    public static <R> PipeDataWrapper<R> error(String error) {
        return new PipeDataWrapper<>(500, error, null);
    }

    public boolean success() {
        return status == 200;
    }
}
