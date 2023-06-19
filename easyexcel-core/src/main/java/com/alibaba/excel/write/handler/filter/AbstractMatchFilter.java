package com.alibaba.excel.write.handler.filter;

import com.alibaba.excel.util.BeanMapUtils;
import com.alibaba.excel.util.PipeFilterUtils;
import com.alibaba.excel.util.StringUtils;
import com.alibaba.excel.write.handler.BasePipeFilter;
import com.alibaba.excel.write.handler.PipeDataWrapper;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Description:
 *
 * @author linfeng
 * @version 1.0.0
 * @since 2023/6/15 8:51
 */
public abstract class AbstractMatchFilter extends BasePipeFilter<Object, Object> {

    /**
     * 字符串匹配
     *
     * @param source 源字符串
     * @param match  匹配字符串
     * @return 是否匹配
     */
    protected abstract boolean strMatch(String source, String match);

    @Override
    public PipeDataWrapper<Object> apply(PipeDataWrapper<Object> wrapper) {

        // 验证
        if (!verify(wrapper)) {
            return wrapper;
        }

        if (PipeFilterUtils.isEmpty(params())) {
            return PipeDataWrapper.error(errorPrefix() + "指令缺失参数");
        }

        Object value = wrapper.getData();
        if (Objects.isNull(value)) {
            return PipeDataWrapper.error(errorPrefix() + "传入数据不能为空");
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
            return PipeDataWrapper.error(errorPrefix() + "指令参数不能为空");
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
        if (CollectionUtils.isEmpty(error)) {
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
                return PipeDataWrapper.error(errorPrefix() + String.format("没有包含[%s]的数据", center));
            } else {
                return PipeDataWrapper.success(result);
            }
        } else if (value instanceof String) {
            String col = (String) value;
            if (strMatch(col, center)) {
                return PipeDataWrapper.success(col);
            } else {
                return PipeDataWrapper.error(errorPrefix() + String.format("没有包含[%s]的数据", center));
            }
        } else if (value instanceof Map) {

            //noinspection unchecked
            Map<Object, Object> colMap = (Map<Object, Object>) value;
            for (Map.Entry<Object, Object> entry : colMap.entrySet()) {
                if (Objects.isNull(entry.getKey())) {
                    continue;
                }
                if (strMatch(entry.getKey().toString(), center)) {
                    return PipeDataWrapper.success(entry.getValue());
                }
            }
            return PipeDataWrapper.error(errorPrefix() + String.format("没有包含[%s]的数据", center));
        } else {

            return PipeDataWrapper.error(errorPrefix() + "传入数据不是集合或字符串", value);
        }
    }


}
