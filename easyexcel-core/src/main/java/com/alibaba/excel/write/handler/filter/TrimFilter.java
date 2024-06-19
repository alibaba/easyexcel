package com.alibaba.excel.write.handler.filter;

import com.alibaba.excel.write.handler.BasePipeFilter;
import com.alibaba.excel.write.handler.PipeDataWrapper;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Description:
 * trim pipe filter
 *
 * @author linfeng
 */
public class TrimFilter extends BasePipeFilter<Object, Object> {

    @Override
    public PipeDataWrapper<Object> apply(PipeDataWrapper<Object> wrapper) {

        // 验证
        if (!verify(wrapper)) {
            return wrapper;
        }

        Object value = wrapper.getData();
        if (Objects.isNull(value)) {
            return PipeDataWrapper.error(errorPrefix() + "incoming data cannot be empty");
        }

        if (value instanceof String) {

            return PipeDataWrapper.success(value.toString().trim());
        } else if (value instanceof Collection) {
            //noinspection unchecked
            Collection<Object> valList = (Collection<Object>) value;
            if (CollectionUtils.isEmpty(valList)) {
                return PipeDataWrapper.error(errorPrefix() + "incoming data cannot be empty");
            }
            return PipeDataWrapper.success(valList.stream().filter(Objects::nonNull).map(str -> str.toString().trim()).collect(Collectors.toList()));
        }

        return PipeDataWrapper.error(errorPrefix() + "the instruction input data is not a string or set");
    }

    @Override
    protected String filterName() {
        return "trim";
    }
}
