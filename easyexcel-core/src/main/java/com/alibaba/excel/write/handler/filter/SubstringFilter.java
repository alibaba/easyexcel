package com.alibaba.excel.write.handler.filter;

import com.alibaba.excel.util.PipeFilterUtils;
import com.alibaba.excel.util.StringUtils;
import com.alibaba.excel.write.handler.BasePipeFilter;
import com.alibaba.excel.write.handler.PipeDataWrapper;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * Description:
 *
 * @author linfeng
 * @version 1.0.0
 * @since 2023/6/17 11:40
 */
@Slf4j
public class SubstringFilter extends BasePipeFilter<Object, Object> {

    private static final int PARAMS_NUM = 2;

    @Override
    protected String filterName() {
        return "substring";
    }

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

        if (PipeFilterUtils.isEmpty(params()) || params().size() != PARAMS_NUM) {
            return PipeDataWrapper.error(errorPrefix() + "[substring:beginIndex,endIndex]传入参数下标为空或是超过两个");
        }

        String begin = params().get(0);
        if (StringUtils.isBlank(begin)) {
            return PipeDataWrapper.error(errorPrefix() + "[substring:beginIndex,endIndex]中传入参数beginIndex为空");
        }

        String end = params().get(1);
        if (StringUtils.isBlank(end)) {
            return PipeDataWrapper.error(errorPrefix() + "[substring:beginIndex,endIndex]中传入参数endIndex为空");
        }

        if (!(value instanceof String)) {
            return PipeDataWrapper.error(errorPrefix() + "指令输入数据不是字符串");
        }

        try {
            int length = ((String) value).length();
            int beginIndex = Integer.parseInt(begin);
            int endIndex = Integer.parseInt(end);
            if (endIndex > length) {
                endIndex = length;
            }
            int subLen = endIndex - beginIndex;
            if (subLen < 0) {
                return PipeDataWrapper.error(errorPrefix() + "下标错误,beginIndex==endIndex");
            }
            return PipeDataWrapper.success(((String) value).substring(beginIndex, endIndex));
        } catch (NumberFormatException e) {
            log.warn(e.getMessage(), e);
            return PipeDataWrapper.error(errorPrefix() + "下标转换错误");
        }
    }
}
