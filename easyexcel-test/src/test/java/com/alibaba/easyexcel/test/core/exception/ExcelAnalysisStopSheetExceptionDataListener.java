package com.alibaba.easyexcel.test.core.exception;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelAnalysisStopException;
import com.alibaba.excel.exception.ExcelAnalysisStopSheetException;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.fastjson2.JSON;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.SetUtils;
import org.assertj.core.internal.Maps;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jiaju Zhuang
 */
@Getter
@Slf4j
public class ExcelAnalysisStopSheetExceptionDataListener extends AnalysisEventListener<ExceptionData> {

    private Map<Integer, List<String>> dataMap = MapUtils.newHashMap();


    @Override
    public void invoke(ExceptionData data, AnalysisContext context) {
        List<String> sheetDataList = dataMap.computeIfAbsent(context.readSheetHolder().getSheetNo(),
            key -> ListUtils.newArrayList());
        sheetDataList.add(data.getName());
        if (sheetDataList.size() >= 5) {
            throw new ExcelAnalysisStopSheetException();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        List<String> sheetDataList = dataMap.get(context.readSheetHolder().getSheetNo());
        Assertions.assertNotNull(sheetDataList);
        Assertions.assertEquals(5, sheetDataList.size());
    }
}
