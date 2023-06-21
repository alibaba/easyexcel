package com.alibaba.excel.write.handler.filter;

import com.alibaba.excel.util.PipeFilterUtils;
import com.alibaba.excel.util.StringUtils;
import com.alibaba.excel.write.handler.BasePipeFilter;
import com.alibaba.excel.write.handler.PipeDataWrapper;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * Description:
 * replace
 * @author linfeng
 */
@Slf4j
public class ReplaceFilter extends BasePipeFilter<Object, Object> {

    private static final int PARAMS_NUM = 3;

    @Override
    protected String filterName() {
        return "replace";
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
            return PipeDataWrapper.error(errorPrefix() + "[replace:oldChar,newChar,all] the input parameter is empty or exceeds two");
        }

        String oldChar = params().get(0);
        if (StringUtils.isBlank(oldChar)) {
            return PipeDataWrapper.error(errorPrefix() + "[replace:oldChar,newChar,all] the parameter 'oldChar' passed in is empty");
        }

        String newChar = params().get(1);
        if (StringUtils.isBlank(newChar)) {
            return PipeDataWrapper.error(errorPrefix() + "[replace:oldChar,newChar,all] the parameter 'newChar' passed in is empty");
        }

        String all = params().get(2);
        if (StringUtils.isBlank(all)) {
            return PipeDataWrapper.error(errorPrefix() + "[replace:oldChar,newChar,all] the parameter 'all' passed in is empty, not 1/0");
        }
        if (!StringUtils.isNumeric(all)) {
            return PipeDataWrapper.error(errorPrefix() + "[replace:oldChar,newChar,all] the parameter 'all' passed in, not 1/0");
        }

        if (!(value instanceof String)) {
            return PipeDataWrapper.error(errorPrefix() + "the instruction input data is not a string");
        }

        try {
            int allInt = Integer.parseInt(all);
            if (1 == allInt) {
                return PipeDataWrapper.success(((String) value).replaceAll(oldChar, newChar));
            } else {
                return PipeDataWrapper.success(((String) value).replace(oldChar, newChar));
            }
        } catch (Exception e) {
            log.warn(e.getMessage());
            return PipeDataWrapper.error(errorPrefix() + "[replace:oldChar,newChar,all] the parameter 'all' conversion error");
        }
    }
}
