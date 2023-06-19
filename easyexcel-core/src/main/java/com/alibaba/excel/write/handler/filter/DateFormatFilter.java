package com.alibaba.excel.write.handler.filter;

import com.alibaba.excel.util.DateUtils;
import com.alibaba.excel.util.PipeFilterUtils;
import com.alibaba.excel.util.StringUtils;
import com.alibaba.excel.write.handler.BasePipeFilter;
import com.alibaba.excel.write.handler.PipeDataWrapper;

import java.util.Date;
import java.util.Objects;

/**
 * Description:
 *
 * @author linfeng
 * @version 1.0.0
 * @since 2023/5/30 10:11
 */
public class DateFormatFilter extends BasePipeFilter<Object, Object> {

    private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Override
    protected String filterName() {
        return "date-format";
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

        if (value instanceof Date) {

            String format;
            if (PipeFilterUtils.isEmpty(params())) {
                format = DEFAULT_FORMAT;
            } else {
                format = StringUtils.isBlank(params().get(0)) ? DEFAULT_FORMAT : params().get(0);
            }
            Date date = (Date) value;
            return PipeDataWrapper.success(DateUtils.format(date, format));
        } else if (value instanceof String) {

            return PipeDataWrapper.success(value);
        }

        return PipeDataWrapper.error(errorPrefix() + "指令传入数据不是Date或字符串");
    }
}
