package com.alibaba.excel.write.handler.filter;

import com.alibaba.excel.util.PipeFilterUtils;
import com.alibaba.excel.util.StringUtils;
import com.alibaba.excel.write.handler.PipeDataWrapper;

import java.util.Objects;

/**
 * Description:
 * echo filter
 *
 * @author linfeng
 */
public class EchoFilter extends AbstractEchoFilter {
    @Override
    protected String filterName() {
        return "echo";
    }

    @Override
    public PipeDataWrapper<Object> apply(PipeDataWrapper<Object> wrapper) {

        if (Objects.isNull(wrapper)) {
            return PipeDataWrapper.error(errorPrefix() + "missing parameter for instruction");
        }

        if (!wrapper.success()) {
            return PipeDataWrapper.error(wrapper.getMessage());
        }

        Object value = wrapper.getData();
        if (Objects.isNull(value)) {
            return PipeDataWrapper.success("");
        }

        if (!(value instanceof String)) {
            return PipeDataWrapper.error(errorPrefix() + "The incoming data is not a string");
        }

        String val = (String) value;
        if (StringUtils.isBlank(val)) {
            return PipeDataWrapper.success("");
        }

        if (PipeFilterUtils.isEmpty(params())) {
            return PipeDataWrapper.success("");
        }

        String delimiter = params().get(0);

        Delimiter delimiterEnum = Delimiter.ofValue(delimiter);
        if (Objects.nonNull(delimiterEnum)) {
            return PipeDataWrapper.success(val + delimiterEnum.getDelimiter());
        }

        return PipeDataWrapper.success(val + delimiter);
    }
}
