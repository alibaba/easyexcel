package com.alibaba.excel.analysis.v07;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.excel.analysis.v07.handlers.CountRowCellHandler;
import com.alibaba.excel.analysis.v07.handlers.DefaultCellHandler;
import com.alibaba.excel.analysis.v07.handlers.ProcessResultCellHandler;
import com.alibaba.excel.cache.Cache;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventRegistryCenter;

public class XlsxHandlerFactory {
    public static List<XlsxCellHandler> buildCellHandlers(AnalysisContext analysisContext,
        AnalysisEventRegistryCenter registerCenter, Cache cahe) {
        List<XlsxCellHandler> result = new ArrayList<XlsxCellHandler>();
        result.add(new CountRowCellHandler(analysisContext));
        DefaultCellHandler defaultCellHandler = buildXlsxRowResultHandler(analysisContext, registerCenter, cahe);
        result.add(defaultCellHandler);
        result.add(new ProcessResultCellHandler(registerCenter, defaultCellHandler));
        return result;
    }

    private static DefaultCellHandler buildXlsxRowResultHandler(AnalysisContext analysisContext,
        AnalysisEventRegistryCenter registerCenter, Cache cahe) {
        return new DefaultCellHandler(analysisContext, registerCenter, cahe);
    }
}
