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
            return PipeDataWrapper.error(errorPrefix() + "传入数据不能为空");
        }

        if (PipeFilterUtils.isEmpty(params()) || params().size() != PARAMS_NUM) {
            return PipeDataWrapper.error(errorPrefix() + "[replace:oldChar,newChar,all]传入参数下标为空或是超过两个");
        }

        String oldChar = params().get(0);
        if (StringUtils.isBlank(oldChar)) {
            return PipeDataWrapper.error(errorPrefix() + "[replace:oldChar,newChar,all]中传入参数oldChar为空");
        }

        String newChar = params().get(1);
        if (StringUtils.isBlank(newChar)) {
            return PipeDataWrapper.error(errorPrefix() + "[replace:oldChar,newChar,all]中传入参数newChar为空");
        }

        String all = params().get(2);
        if (StringUtils.isBlank(all)) {
            return PipeDataWrapper.error(errorPrefix() + "[replace:oldChar,newChar,all]中传入参数all为空,不是1/0");
        }
        if (!StringUtils.isNumeric(all)) {
            return PipeDataWrapper.error(errorPrefix() + "[replace:oldChar,newChar,all]中传入参数all错误,不是1/0");
        }

        if (!(value instanceof String)) {
            return PipeDataWrapper.error(errorPrefix() + "指令输入数据不是字符串");
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
            return PipeDataWrapper.error(errorPrefix() + "[replace:oldChar,newChar,all]中传入参数all转换数字错误");
        }
    }
}
