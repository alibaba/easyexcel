package com.alibaba.excel.write.handler.filter;

import com.alibaba.excel.write.handler.PipeDataWrapper;

import java.util.Objects;

/**
 * Description:
 *
 * @author linfeng
 * @version 1.0.0
 * @since 2023/6/15 9:27
 */
public abstract class AbstractPriorMatchFilter extends AbstractMatchFilter {

    /**
     * 多参数处理,优先级
     *
     * @param value 待处理数据
     * @return 处理后的数据
     */
    @Override
    protected PipeDataWrapper<Object> moreParamsHandle(Object value) {
        for (String center : params()) {
            if (Objects.isNull(center)) {
                continue;
            }
            PipeDataWrapper<Object> itemDataWrapper = instructHandle(value, center);
            if (itemDataWrapper.success()) {
                return itemDataWrapper;
            }
        }
        return PipeDataWrapper.error(errorPrefix() + String.format("指令没有匹配到[%s]结果", String.join(",", params())));
    }

}
