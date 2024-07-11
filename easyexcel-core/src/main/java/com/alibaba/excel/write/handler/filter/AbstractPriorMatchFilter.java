package com.alibaba.excel.write.handler.filter;

import com.alibaba.excel.write.handler.PipeDataWrapper;

import java.util.Objects;

/**
 * Description:
 * Priority matching filter
 *
 * @author linfeng
 */
public abstract class AbstractPriorMatchFilter extends AbstractMatchFilter {

    /**
     * Multi parameter processing, priority processing
     *
     * @param value Pending data
     * @return processed data
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
        return PipeDataWrapper.error(errorPrefix() + String.format("The instruction did not match the result of [%s]", String.join(",", params())));
    }

}
