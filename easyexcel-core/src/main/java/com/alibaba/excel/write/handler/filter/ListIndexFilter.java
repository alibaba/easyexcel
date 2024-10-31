package com.alibaba.excel.write.handler.filter;

import com.alibaba.excel.util.PipeFilterUtils;
import com.alibaba.excel.util.StringUtils;
import com.alibaba.excel.write.handler.BasePipeFilter;
import com.alibaba.excel.write.handler.PipeDataWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @author linfeng
 * @version 1.0.0
 * @since 2023/5/30 16:52
 */
@Slf4j
public class ListIndexFilter extends BasePipeFilter<Object, Object> {

    /**
     * 参数格式  list-index:n n表示下标，从1开始
     *
     * @param wrapper the function argument
     * @return
     */
    @Override
    public PipeDataWrapper<Object> apply(PipeDataWrapper<Object> wrapper) {

        // 验证
        if (!verify(wrapper)) {
            return wrapper;
        }

        if (PipeFilterUtils.isEmpty(params())) {
            return PipeDataWrapper.error(errorPrefix() + "missing parameter for instruction");
        }

        Object value = wrapper.getData();
        if (Objects.isNull(value)) {
            return PipeDataWrapper.error(errorPrefix() + "Incoming data cannot be empty");
        }

        if (!(value instanceof Collection)) {
            return PipeDataWrapper.error(errorPrefix() + "The incoming data is not a collection");
        }

        @SuppressWarnings("unchecked")
        List<Object> collection = (List<Object>) value;

        if (CollectionUtils.isEmpty(collection)) {
            return PipeDataWrapper.error(errorPrefix() + "Incoming data cannot be empty");
        }

        if (PipeFilterUtils.isEmpty(params()) || params().size() > 1) {
            return PipeDataWrapper.error(errorPrefix() + "The index of the passed in parameter is empty or exceeds one");
        }

        String index = params().get(0);
        if (StringUtils.isBlank(index)) {
            return PipeDataWrapper.error(errorPrefix() + "The index of the incoming parameter is empty");
        }

        try {
            int ind = Integer.parseInt(index);
            if (ind < 0) {
                ind = 0;
            }
            if (ind >= 1) {
                ind -= 1;
            }
            if (ind >= collection.size() - 1) {
                return PipeDataWrapper.error(errorPrefix() + "The index of the passed in parameter exceeds the maximum length of the set");
            }
            List<String> collect = collection.stream().map(Object::toString).collect(Collectors.toList());
            return PipeDataWrapper.success(collect.get(ind));
        } catch (NumberFormatException e) {

            log.warn(e.getMessage(), e);
            return PipeDataWrapper.error(errorPrefix() + "Index conversion error");
        }
    }

    @Override
    protected String filterName() {
        return "list-index";
    }
}
