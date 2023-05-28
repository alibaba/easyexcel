package com.alibaba.excel.write.metadata.fill.pipe.handler;

import com.alibaba.excel.write.metadata.fill.pipe.PipeFilter;

import java.util.Objects;

/**
 * Description:
 *
 * @author linfeng
 * @version 1.0.0
 * @since 2023/5/26 8:34
 */
public class TrimFilter extends PipeFilter<Object, Object> {

    @Override
    public Object apply(Object value) {
        Objects.requireNonNull(value);
        if (value instanceof String) {
            return value.toString().trim();
        }
        throw new RuntimeException("传入对象必须是字符串");
    }
}
