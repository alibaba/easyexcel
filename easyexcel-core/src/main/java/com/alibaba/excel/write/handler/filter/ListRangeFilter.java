package com.alibaba.excel.write.handler.filter;

import com.alibaba.excel.util.PipeFilterUtils;
import com.alibaba.excel.util.StringUtils;
import com.alibaba.excel.write.handler.BasePipeFilter;
import com.alibaba.excel.write.handler.PipeDataWrapper;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

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
     * list-range:index,count 从index开始，获取count个数量
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
            return PipeDataWrapper.error(errorPrefix() + "传入数据不能为空");
        }

        if (!(value instanceof Collection)) {
            return PipeDataWrapper.error(errorPrefix() + "传入数据不是集合");
        }

        @SuppressWarnings("unchecked")
        List<Object> collection = (List<Object>) value;

        if (PipeFilterUtils.isEmpty(params()) || params().size() > PARAMS_NUM) {
            return PipeDataWrapper.error(errorPrefix() + "[list-range:index,count]传入参数下标为空或是超过两个");
        }

        String index = params().get(0);
        if (StringUtils.isBlank(index)) {
            return PipeDataWrapper.error(errorPrefix() + "[list-range:index,count]中传入参数index为空");
        }

        String count = params().get(1);
        if (StringUtils.isBlank(count)) {
            return PipeDataWrapper.error(errorPrefix() + "[list-range:index,count]中传入参数count为空");
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
                return PipeDataWrapper.error(errorPrefix() + "[list-range:index,count]count必须大于0");
            }

            int toIndex = countInt + fromIndex;
            int size = collection.size();
            if (toIndex > size) {
                log.warn("错误:[list-range:index,count] count值大于输入集合的大小");
                toIndex = size;
            }

            return PipeDataWrapper.success(collection.subList(fromIndex, toIndex));
        } catch (NumberFormatException e) {
            log.warn(e.getMessage(), e);
            return PipeDataWrapper.error(errorPrefix() + "[list-range:index,count]index或count转换错误");
        }
    }

    @Override
    protected String filterName() {
        return "list-range";
    }
}
