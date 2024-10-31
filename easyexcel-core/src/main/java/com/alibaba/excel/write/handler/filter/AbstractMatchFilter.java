package com.alibaba.excel.write.handler.filter;

import com.alibaba.excel.util.BeanMapUtils;
import com.alibaba.excel.util.PipeFilterUtils;
import com.alibaba.excel.util.StringUtils;
import com.alibaba.excel.write.handler.BasePipeFilter;
import com.alibaba.excel.write.handler.PipeDataWrapper;

import java.util.*;

/**
 * Description:
 * abstract match filter
 *
 * @author linfeng
 */
public abstract class AbstractMatchFilter extends BasePipeFilter<Object, Object> {

    /**
     * String Matching
     *
     * @param source source string
     * @param match  Match String
     * @return Match or not
     */
    protected abstract boolean strMatch(String source, String match);

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

        Object dataObj;
        if (value instanceof String || value instanceof Collection || value instanceof Map) {
            dataObj = value;
        } else {
            dataObj = BeanMapUtils.create(value);
        }

        if (params().size() == 1) {

            return singleParamsHandle(dataObj);
        } else {

            return moreParamsHandle(dataObj);
        }
    }

    /**
     * 单参数处理
     *
     * @param value 待处理数据
     * @return 处理后的数据
     */
    private PipeDataWrapper<Object> singleParamsHandle(Object value) {
        String center = params().get(0);
        if (StringUtils.isBlank(center)) {
            return PipeDataWrapper.error(errorPrefix() + "instruction parameter cannot be empty");
        }

        return instructHandle(value, center);
    }

    /**
     * 多参数处理
     *
     * @param value 待处理数据
     * @return 处理后的数据
     */
    protected PipeDataWrapper<Object> moreParamsHandle(Object value) {
        List<Object> result = new ArrayList<>();
        List<String> error = new ArrayList<>();
        for (String center : params()) {
            if (Objects.isNull(center)) {
                continue;
            }
            PipeDataWrapper<Object> itemDataWrapper = instructHandle(value, center);
            if (itemDataWrapper.success()) {
                result.add(itemDataWrapper.getData());
            } else {
                error.add(itemDataWrapper.getMessage());
            }
        }
        if (PipeFilterUtils.isEmpty(error)) {
            return PipeDataWrapper.success(result);
        } else {
            return PipeDataWrapper.error(errorPrefix() + String.join(",", error), result);
        }
    }

    /**
     * 指令处理
     *
     * @param value  待处理的值
     * @param center 待匹配的字符串
     * @return 数据包裹
     */
    protected PipeDataWrapper<Object> instructHandle(Object value, String center) {

        if (value instanceof Collection) {
            String result = null;
            //noinspection unchecked
            Collection<Object> collection = (Collection<Object>) value;
            for (Object col : collection) {
                if (Objects.isNull(col)) {
                    continue;
                }
                if (col instanceof String) {
                    String cel = (String) col;
                    if (strMatch(cel, center)) {
                        result = cel;
                        break;
                    }
                }
            }
            if (StringUtils.isBlank(result)) {
                return PipeDataWrapper.error(errorPrefix() + String.format("No data containing [%s]", center));
            } else {
                return PipeDataWrapper.success(result);
            }
        } else if (value instanceof String) {
            String col = (String) value;
            if (strMatch(col, center)) {
                return PipeDataWrapper.success(col);
            } else {
                return PipeDataWrapper.error(errorPrefix() + String.format("No data containing [%s]", center));
            }
        } else if (value instanceof Map) {

            //noinspection unchecked
            Map<Object, Object> colMap = (Map<Object, Object>) value;
            for (Map.Entry<Object, Object> entry : colMap.entrySet()) {
                if (Objects.isNull(entry.getKey())) {
                    continue;
                }
                if (strMatch(entry.getKey().toString(), center)) {
                    if (Objects.nonNull(entry.getValue())) {
                        return PipeDataWrapper.success(entry.getValue().toString());
                    } else {
                        return PipeDataWrapper.success("");
                    }
                }
            }
            return PipeDataWrapper.error(errorPrefix() + String.format("No data containing [%s]", center));
        } else {

            return PipeDataWrapper.error(errorPrefix() + "the incoming data is not a collection or string", value.toString());
        }
    }


}
