package com.alibaba.excel.write.executor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.alibaba.excel.context.WriteContext;
import com.alibaba.excel.enums.HeadKindEnum;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.BeanMapUtils;
import com.alibaba.excel.util.ClassUtils;
import com.alibaba.excel.util.FieldUtils;
import com.alibaba.excel.util.WorkBookUtil;
import com.alibaba.excel.util.WriteHandlerUtils;
import com.alibaba.excel.write.metadata.CollectionRowData;
import com.alibaba.excel.write.metadata.MapRowData;
import com.alibaba.excel.write.metadata.RowData;
import com.alibaba.excel.write.metadata.holder.WriteHolder;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;

import net.sf.cglib.beans.BeanMap;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * Add the data into excel
 *
 * @author Jiaju Zhuang
 */
public class ExcelWriteAddExecutor extends AbstractExcelWriteExecutor {

    public ExcelWriteAddExecutor(WriteContext writeContext) {
        super(writeContext);
    }

    public void add(Collection<?> data) {
        if (CollectionUtils.isEmpty(data)) {
            data = new ArrayList<>();
        }
        WriteSheetHolder writeSheetHolder = writeContext.writeSheetHolder();
        int newRowIndex = writeSheetHolder.getNewRowIndexAndStartDoWrite();
        if (writeSheetHolder.isNew() && !writeSheetHolder.getExcelWriteHeadProperty().hasHead()) {
            newRowIndex += writeContext.currentWriteHolder().relativeHeadRowIndex();
        }
        // BeanMap is out of order, so use sortedAllFiledMap
        Map<Integer, Field> sortedAllFiledMap = new TreeMap<>();
        int relativeRowIndex = 0;
        for (Object oneRowData : data) {
            int lastRowIndex = relativeRowIndex + newRowIndex;
            addOneRowOfDataToExcel(oneRowData, lastRowIndex, relativeRowIndex, sortedAllFiledMap);
            relativeRowIndex++;
        }
    }

    private void addOneRowOfDataToExcel(Object oneRowData, int rowIndex, int relativeRowIndex,
        Map<Integer, Field> sortedAllFiledMap) {
        if (oneRowData == null) {
            return;
        }
        WriteHandlerUtils.beforeRowCreate(writeContext, rowIndex, relativeRowIndex, Boolean.FALSE);
        Row row = WorkBookUtil.createRow(writeContext.writeSheetHolder().getSheet(), rowIndex);
        WriteHandlerUtils.afterRowCreate(writeContext, row, rowIndex, relativeRowIndex, Boolean.FALSE);

        if (oneRowData instanceof Collection<?>) {
            addBasicTypeToExcel(new CollectionRowData((Collection<?>)oneRowData), row, rowIndex, relativeRowIndex);
        } else if (oneRowData instanceof Map) {
            addBasicTypeToExcel(new MapRowData((Map<Integer, ?>)oneRowData), row, rowIndex, relativeRowIndex);
        } else {
            addJavaObjectToExcel(oneRowData, row, rowIndex, relativeRowIndex, sortedAllFiledMap);
        }
        WriteHandlerUtils.afterRowDispose(writeContext, row, rowIndex, relativeRowIndex, Boolean.FALSE);
    }

    private void addBasicTypeToExcel(RowData oneRowData, Row row, int rowIndex, int relativeRowIndex) {
        if (oneRowData.isEmpty()) {
            return;
        }
        Map<Integer, Head> headMap = writeContext.currentWriteHolder().excelWriteHeadProperty().getHeadMap();
        int dataIndex = 0;
        int maxCellIndex = -1;
        for (Map.Entry<Integer, Head> entry : headMap.entrySet()) {
            if (dataIndex >= oneRowData.size()) {
                return;
            }
            int columnIndex = entry.getKey();
            Head head = entry.getValue();
            doAddBasicTypeToExcel(oneRowData, head, row, rowIndex, relativeRowIndex, dataIndex++, columnIndex);
            maxCellIndex = Math.max(maxCellIndex, columnIndex);
        }
        // Finish
        if (dataIndex >= oneRowData.size()) {
            return;
        }
        // fix https://github.com/alibaba/easyexcel/issues/1702
        // If there is data, it is written to the next cell
        maxCellIndex++;

        int size = oneRowData.size() - dataIndex;
        for (int i = 0; i < size; i++) {
            doAddBasicTypeToExcel(oneRowData, null, row, rowIndex, relativeRowIndex, dataIndex++, maxCellIndex++);
        }
    }

    private void doAddBasicTypeToExcel(RowData oneRowData, Head head, Row row, int rowIndex, int relativeRowIndex,
        int dataIndex, int columnIndex) {
        ExcelContentProperty excelContentProperty = ClassUtils.declaredExcelContentProperty(null,
            writeContext.currentWriteHolder().excelWriteHeadProperty().getHeadClazz(),
            head == null ? null : head.getFieldName());

        WriteHandlerUtils.beforeCellCreate(writeContext, row, head, columnIndex, relativeRowIndex, Boolean.FALSE,
            excelContentProperty);
        Cell cell = WorkBookUtil.createCell(row, columnIndex);
        WriteHandlerUtils.afterCellCreate(writeContext, cell, row, head, columnIndex, relativeRowIndex, Boolean.FALSE,
            excelContentProperty);
        Object value = oneRowData.get(dataIndex);
        WriteCellData<?> cellData = converterAndSet(writeContext.currentWriteHolder(),
            FieldUtils.getFieldClass(value), null, cell, row, value, null, head, relativeRowIndex,
            rowIndex, columnIndex);
        WriteHandlerUtils.afterCellDispose(writeContext, cellData, cell, row, head, columnIndex, relativeRowIndex,
            Boolean.FALSE,
            excelContentProperty);
    }

    private void addJavaObjectToExcel(Object oneRowData, Row row, int rowIndex, int relativeRowIndex,
        Map<Integer, Field> sortedAllFiledMap) {
        WriteHolder currentWriteHolder = writeContext.currentWriteHolder();
        BeanMap beanMap = BeanMapUtils.create(oneRowData);
        Set<String> beanMapHandledSet = new HashSet<String>();
        int maxCellIndex = -1;
        // If it's a class it needs to be cast by type
        if (HeadKindEnum.CLASS.equals(writeContext.currentWriteHolder().excelWriteHeadProperty().getHeadKind())) {
            Map<Integer, Head> headMap = writeContext.currentWriteHolder().excelWriteHeadProperty().getHeadMap();
            for (Map.Entry<Integer, Head> entry : headMap.entrySet()) {
                int columnIndex = entry.getKey();
                Head head = entry.getValue();
                String name = head.getFieldName();
                if (!beanMap.containsKey(name)) {
                    continue;
                }
                ExcelContentProperty excelContentProperty = ClassUtils.declaredExcelContentProperty(beanMap,
                    currentWriteHolder.excelWriteHeadProperty().getHeadClazz(), name);
                WriteHandlerUtils.beforeCellCreate(writeContext, row, head, columnIndex, relativeRowIndex,
                    Boolean.FALSE, excelContentProperty);
                Cell cell = WorkBookUtil.createCell(row, columnIndex);
                WriteHandlerUtils.afterCellCreate(writeContext, cell, row, head, columnIndex, relativeRowIndex,
                    Boolean.FALSE,
                    excelContentProperty);
                Object value = beanMap.get(name);
                WriteCellData<?> cellData = converterAndSet(currentWriteHolder, head.getField().getType(),
                    null, cell, row, value, excelContentProperty, head, relativeRowIndex, rowIndex, columnIndex);
                WriteHandlerUtils.afterCellDispose(writeContext, cellData, cell, row, head, columnIndex,
                    relativeRowIndex, Boolean.FALSE,
                    excelContentProperty);
                beanMapHandledSet.add(name);
                maxCellIndex = Math.max(maxCellIndex, columnIndex);
            }
        }
        // Finish
        if (beanMapHandledSet.size() == beanMap.size()) {
            return;
        }
        maxCellIndex++;

        Map<String, Field> ignoreMap = writeContext.currentWriteHolder().excelWriteHeadProperty().getIgnoreMap();
        initSortedAllFiledMapFieldList(oneRowData.getClass(), sortedAllFiledMap);
        for (Map.Entry<Integer, Field> entry : sortedAllFiledMap.entrySet()) {
            Field field = entry.getValue();
            String filedName = FieldUtils.resolveCglibFieldName(field);
            boolean uselessData = !beanMap.containsKey(filedName) || beanMapHandledSet.contains(filedName)
                || ignoreMap.containsKey(filedName);
            if (uselessData) {
                continue;
            }
            Object value = beanMap.get(filedName);
            ExcelContentProperty excelContentProperty = ClassUtils.declaredExcelContentProperty(beanMap,
                currentWriteHolder.excelWriteHeadProperty().getHeadClazz(), filedName);
            WriteHandlerUtils.beforeCellCreate(writeContext, row, null, maxCellIndex, relativeRowIndex, Boolean.FALSE,
                excelContentProperty);
            // fix https://github.com/alibaba/easyexcel/issues/1870
            // If there is data, it is written to the next cell
            Cell cell = WorkBookUtil.createCell(row, maxCellIndex);
            WriteHandlerUtils.afterCellCreate(writeContext, cell, row, null, maxCellIndex, relativeRowIndex,
                Boolean.FALSE,
                excelContentProperty);
            WriteCellData<?> cellData = converterAndSet(currentWriteHolder,
                FieldUtils.getFieldClass(beanMap, filedName, value), null, cell, row, value, null, null,
                relativeRowIndex, rowIndex, maxCellIndex);
            WriteHandlerUtils.afterCellDispose(writeContext, cellData, cell, row, null, maxCellIndex, relativeRowIndex,
                Boolean.FALSE,
                excelContentProperty);
            maxCellIndex++;
        }
    }

    private void initSortedAllFiledMapFieldList(Class<?> clazz, Map<Integer, Field> sortedAllFiledMap) {
        if (!sortedAllFiledMap.isEmpty()) {
            return;
        }

        WriteSheetHolder writeSheetHolder = writeContext.writeSheetHolder();
        boolean needIgnore =
            !CollectionUtils.isEmpty(writeSheetHolder.getExcludeColumnFieldNames()) || !CollectionUtils
                .isEmpty(writeSheetHolder.getExcludeColumnIndexes()) || !CollectionUtils
                .isEmpty(writeSheetHolder.getIncludeColumnFieldNames()) || !CollectionUtils
                .isEmpty(writeSheetHolder.getIncludeColumnIndexes());
        ClassUtils.declaredFields(clazz, sortedAllFiledMap, needIgnore, writeSheetHolder);
    }

}
