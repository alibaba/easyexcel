package com.alibaba.excel.write.handler.filter;

import com.alibaba.excel.util.PipeFilterUtils;
import com.alibaba.excel.util.StringUtils;
import com.alibaba.excel.write.handler.BasePipeFilter;
import com.alibaba.excel.write.handler.PipeDataWrapper;
import lombok.extern.slf4j.Slf4j;

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
public class ListRangeFilter extends BasePipeFilter<Object, Object> {

    private static final int PARAMS_NUM = 2;

    /**
     * list-range:index,count Starting from index, obtain count quantity
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

        Object value = wrapper.getData();
        if (Objects.isNull(value)) {
            return PipeDataWrapper.error(errorPrefix() + "Incoming data cannot be empty");
        }

        if (!(value instanceof Collection)) {
            return PipeDataWrapper.error(errorPrefix() + "The incoming data is not a collection");
        }

        @SuppressWarnings("unchecked")
        List<Object> collection = (List<Object>) value;

        if (PipeFilterUtils.isEmpty(params()) || params().size() > PARAMS_NUM) {
            return PipeDataWrapper.error(errorPrefix() + "[list-range:index,count] the input parameter subscript is empty or exceeds two");
        }

        String index = params().get(0);
        if (StringUtils.isBlank(index)) {
            return PipeDataWrapper.error(errorPrefix() + "[list-range:index,count] the parameter index passed in is empty");
        }

        String count = params().get(1);
        if (StringUtils.isBlank(count)) {
            return PipeDataWrapper.error(errorPrefix() + "[list-range:index,count] the parameter count passed in is empty");
        }

        try {
            int fromIndex = Integer.parseInt(index);
            if (fromIndex < 0) {
                fromIndex = 0;
            }
            if (fromIndex >= 1) {
                fromIndex -= 1;
            }

            int countInt = Integer.parseInt(count);
            if (countInt < 1) {
                return PipeDataWrapper.error(errorPrefix() + "[list-range:index,count] count parameter must be greater than 0");
            }

            int toIndex = countInt + fromIndex;
            int size = collection.size();
            if (toIndex > size) {
                log.warn("error:[list-range:index,count] the count parameter value is greater than the size of the input set");
                toIndex = size;
            }

            List<String> collect = collection.stream().map(Object::toString).collect(Collectors.toList());
            return PipeDataWrapper.success(collect.subList(fromIndex, toIndex));
        } catch (NumberFormatException e) {
            log.warn(e.getMessage(), e);
            return PipeDataWrapper.error(errorPrefix() + "[list-range:index,count] Index or count parameter conversion error");
        }
    }

    @Override
    protected String filterName() {
        return "list-range";
    }
}
