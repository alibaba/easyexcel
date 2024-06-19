package com.alibaba.excel.write.handler.filter;

import com.alibaba.excel.util.PipeFilterUtils;
import com.alibaba.excel.util.StringUtils;
import com.alibaba.excel.write.handler.BasePipeFilter;
import com.alibaba.excel.write.handler.PipeDataWrapper;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * Description:
 * Substring
 * @author linfeng
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
            return PipeDataWrapper.error(errorPrefix() + "incoming data cannot be empty");
        }

        if (PipeFilterUtils.isEmpty(params()) || params().size() != PARAMS_NUM) {
            return PipeDataWrapper.error(errorPrefix() + "[substring:beginIndex,endIndex] the input parameter is empty or exceeds two");
        }

        String begin = params().get(0);
        if (StringUtils.isBlank(begin)) {
            return PipeDataWrapper.error(errorPrefix() + "[substring:beginIndex,endIndex] the incoming parameter 'beginIndex' is empty");
        }

        String end = params().get(1);
        if (StringUtils.isBlank(end)) {
            return PipeDataWrapper.error(errorPrefix() + "[substring:beginIndex,endIndex] the parameter 'endIndex' is empty");
        }

        if (!(value instanceof String)) {
            return PipeDataWrapper.error(errorPrefix() + "the instruction input data is not a string");
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
                return PipeDataWrapper.error(errorPrefix() + "index error,beginIndex==endIndex");
            }
            return PipeDataWrapper.success(((String) value).substring(beginIndex, endIndex));
        } catch (NumberFormatException e) {
            log.warn(e.getMessage(), e);
            return PipeDataWrapper.error(errorPrefix() + "index conversion error");
        }
    }
}
