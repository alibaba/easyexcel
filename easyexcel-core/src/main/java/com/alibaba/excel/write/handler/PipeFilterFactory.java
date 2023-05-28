package com.alibaba.excel.write.handler;

import com.alibaba.excel.context.WriteContext;
import com.alibaba.excel.exception.ExcelRuntimeException;
import com.alibaba.excel.util.StringUtils;
import com.alibaba.excel.util.PipeFilterUtils;
import com.alibaba.excel.write.handler.filter.TrimFilter;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Description:
 *
 * @author linfeng
 * @version 1.0.0
 * @since 2023/5/27 22:19
 */
public class PipeFilterFactory extends PipeFilter<Object, Object> {

    private static final Map<String, Supplier<PipeFilter<Object, Object>>> PIPE_FILTER_MAP = new HashMap<>();

    static {
        // 初始化内置管道过滤器
        PIPE_FILTER_MAP.put("trim", TrimFilter::new);
    }

    private PipeFilterFactory(WriteContext writeContext) {
        if (Objects.nonNull(writeContext)
            && Objects.nonNull(writeContext.writeWorkbookHolder())
            && Objects.nonNull(writeContext.writeWorkbookHolder().getWriteWorkbook())
            && PipeFilterUtils.isEmpty(writeContext.writeWorkbookHolder().getWriteWorkbook().getCustomPipeFilterMap())) {
            PIPE_FILTER_MAP.putAll(writeContext.writeWorkbookHolder().getWriteWorkbook().getCustomPipeFilterMap());
        }
    }

    /**
     * 创建管道过滤器
     *
     * @param writeContext 写操作上下文
     * @return 管道过滤器
     */
    public static PipeFilterFactory createPipeFilter(WriteContext writeContext) {
        return new PipeFilterFactory(writeContext);
    }

    @Override
    public Object apply(Object value) {

        if (PipeFilterUtils.isEmpty(params())) {
            throw new ExcelRuntimeException("管道字符串格式不正确");
        }

        String variable = params().get(0);
        if (StringUtils.isBlank(variable)) {
            throw new ExcelRuntimeException("管道字符串格式不正确");
        }

        String[] pipeArray = PipeFilterUtils.getPipelines(variable);
        Objects.requireNonNull(pipeArray, "管道字符串格式不正确");
        if (pipeArray.length <= 1) {
            throw new ExcelRuntimeException("管道字符串格式不正确");
        }

        List<PipeFilter<Object, Object>> pipeFilterList = new ArrayList<>();
        for (int i = 1; i < pipeArray.length; i++) {
            if (StringUtils.isBlank(pipeArray[i])) {
                continue;
            }

            String[] expressArray = PipeFilterUtils.getPipeFilter(pipeArray[i]);
            if (StringUtils.isBlank(expressArray[0])) {
                continue;
            }

            String filterName = PipeFilterUtils.trimAndLowerCase(expressArray[0]);
            if (StringUtils.isBlank(filterName)) {
                continue;
            }

            Supplier<PipeFilter<Object, Object>> supplier = PIPE_FILTER_MAP.get(filterName);
            if (Objects.isNull(supplier)) {
                throw new ExcelRuntimeException(String.format("没有[%s]的管道过滤器", filterName));
            }
            PipeFilter<Object, Object> pipeFilter = supplier.get();
            if (Objects.nonNull(pipeFilter)) {
                pipeFilterList.add(pipeFilter);
            }
            if (expressArray.length > 1 && StringUtils.isNotBlank(expressArray[1])) {
                String[] paramArray = PipeFilterUtils.getPipeFilterParams(PipeFilterUtils.trimAndLowerCase(expressArray[1]));
                pipeFilter.addParams(paramArray);
            }
        }

        if (PipeFilterUtils.isEmpty(pipeFilterList)) {
            return value;
        }
        // 构建pipeline
        Function<Object, Object> currFilter = pipeFilterList.get(0);
        for (int i = 1; i < pipeFilterList.size(); i++) {
            currFilter = currFilter.andThen(pipeFilterList.get(i));
        }
        return currFilter.apply(value);
    }
}
