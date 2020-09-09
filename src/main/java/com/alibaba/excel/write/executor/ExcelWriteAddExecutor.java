package com.alibaba.excel.write.executor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

import com.alibaba.excel.annotation.ExcelList;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.context.WriteContextImpl;
import com.alibaba.excel.enums.DynamicDirectionEnum;
import org.apache.commons.collections4.ListUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.alibaba.excel.context.WriteContext;
import com.alibaba.excel.enums.HeadKindEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.ClassUtils;
import com.alibaba.excel.util.CollectionUtils;
import com.alibaba.excel.util.WorkBookUtil;
import com.alibaba.excel.util.WriteHandlerUtils;
import com.alibaba.excel.write.metadata.holder.WriteHolder;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;

import net.sf.cglib.beans.BeanMap;

/**
 * Add the data into excel
 *
 * @author Jiaju Zhuang
 */
public class ExcelWriteAddExecutor extends AbstractExcelWriteExecutor {

    public ExcelWriteAddExecutor(WriteContext writeContext) {
        super(writeContext);
    }

    public void add(List data) {
        if (CollectionUtils.isEmpty(data)) {
            data = new ArrayList();
        }
        WriteSheetHolder writeSheetHolder = writeContext.writeSheetHolder();
        int newRowIndex = writeSheetHolder.getNewRowIndexAndStartDoWrite();
        if (writeSheetHolder.isNew() && !writeSheetHolder.getExcelWriteHeadProperty().hasHead()) {
            newRowIndex += writeContext.currentWriteHolder().relativeHeadRowIndex();
        }
        // BeanMap is out of order,so use sortedAllFiledMap
        Map<Integer, Field> sortedAllFiledMap = new TreeMap<Integer, Field>();
        int relativeRowIndex = 0;
        for (Object oneRowData : data) {
            if (needConvertToList(oneRowData)) {
                List<List<Object>> dynamicDataList = changeJavaObject2BasicType(oneRowData);
                for (List list : dynamicDataList) {
                    int n = relativeRowIndex + newRowIndex;
                    addOneRowOfDataToExcel(list, n, relativeRowIndex, sortedAllFiledMap);
                    relativeRowIndex++;
                }
            } else {
                int n = relativeRowIndex + newRowIndex;
                addOneRowOfDataToExcel(oneRowData, n, relativeRowIndex, sortedAllFiledMap);
                relativeRowIndex++;
            }
        }
//        WriteContextImpl context = (WriteContextImpl)writeContext;
//        context.initHead(writeContext.writeSheetHolder().excelWriteHeadProperty());
    }

    private void addOneRowOfDataToExcel(Object oneRowData, int n, int relativeRowIndex,
        Map<Integer, Field> sortedAllFiledMap) {
        if (oneRowData == null) {
            return;
        }
        WriteHandlerUtils.beforeRowCreate(writeContext, n, relativeRowIndex, Boolean.FALSE);
        Row row = WorkBookUtil.createRow(writeContext.writeSheetHolder().getSheet(), n);
        WriteHandlerUtils.afterRowCreate(writeContext, row, relativeRowIndex, Boolean.FALSE);
        if (oneRowData instanceof List) {
            addBasicTypeToExcel((List) oneRowData, row, relativeRowIndex);
        } else {
            addJavaObjectToExcel(oneRowData, row, relativeRowIndex, sortedAllFiledMap);
        }
        WriteHandlerUtils.afterRowDispose(writeContext, row, relativeRowIndex, Boolean.FALSE);
    }

    private boolean needConvertToList(Object oneRowData) {
        boolean isNeed = false;

        Class<?> aClass = oneRowData.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();

        for (Field field : declaredFields) {
            ExcelIgnore excelIgnore = field.getAnnotation(ExcelIgnore.class);
            if (excelIgnore != null) {
                continue;
            }
            ExcelList excelList = field.getAnnotation(ExcelList.class);
            if (excelList != null) {
                isNeed = true;
            }
        }
        return isNeed;
    }

    private List<List<Object>> changeJavaObject2BasicType(Object oneRowData) {
        List<Object> dataList = new ArrayList<>();

        Class<?> aClass = oneRowData.getClass();

        Field[] declaredFields = aClass.getDeclaredFields();

        Map<Integer, List<Object>> listMap = new HashMap<>();
        for (Field field : declaredFields) {
            field.setAccessible(true);

            ExcelIgnore excelIgnore = field.getAnnotation(ExcelIgnore.class);
            if (excelIgnore != null) {
                continue;
            }
            ExcelList excelList = field.getAnnotation(ExcelList.class);

            try {
                Object fieldValue = field.get(oneRowData);
                if (fieldValue instanceof List && excelList != null) {

                    List tmpList = (List) field.get(oneRowData);

                    if (DynamicDirectionEnum.PORTRAIT.equals(excelList.direction())) {
                        if (!CollectionUtils.isEmpty(tmpList)) {
                            dataList.add(tmpList.get(0));
                            listMap.put(dataList.size() - 1, tmpList);
                        } else {
                            dataList.add(null);
                        }
                    } else if (DynamicDirectionEnum.ORIENTATION.equals(excelList.direction())) {
                        if (!CollectionUtils.isEmpty(tmpList)) {
                            Map<String, Integer> dynamicMap = ((WriteContextImpl) writeContext).getDynamicMap();
                            Integer maxCount = dynamicMap.get(field.getName());
                            if (tmpList.size() < maxCount) {
                                int align = maxCount - tmpList.size();
                                for (int i=0; i<align; i++) {
                                    tmpList.add(null);
                                }
                            }
                            dataList.addAll(tmpList);
                        } else {
                            dataList.add(null);
                        }
                    }
                } else {
                    dataList.add(field.get(oneRowData));
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        List<List<Object>> allDataList = new ArrayList<>();
        if (listMap.size() > 0) {

            allDataList.add(dataList);
            List<List<Object>> tmpList = new ArrayList<>();

            for (Map.Entry<Integer, List<Object>> entry : listMap.entrySet()) {
                Integer key = entry.getKey();
                List<Object> fieldValueList = entry.getValue();
                for (Object fieldValue : fieldValueList) {
                    for (List<Object> rows : allDataList) {
                        rows.set(key, fieldValue);
                        List<Object> copyList = new ArrayList<>(rows.size());
                        copyList.addAll(rows);
                        tmpList.add(copyList);
                    }
                }
                allDataList.clear();
                allDataList.addAll(tmpList);
                tmpList.clear();
            }
        } else {
            allDataList.add(dataList);
        }
        return allDataList;
    }

    private void addBasicTypeToExcel(List<Object> oneRowData, Row row, int relativeRowIndex) {
        if (CollectionUtils.isEmpty(oneRowData)) {
            return;
        }
        Map<Integer, Head> headMap = writeContext.currentWriteHolder().excelWriteHeadProperty().getHeadMap();
        int dataIndex = 0;
        int cellIndex = 0;
        for (Map.Entry<Integer, Head> entry : headMap.entrySet()) {
            if (dataIndex >= oneRowData.size()) {
                return;
            }
            cellIndex = entry.getKey();
            Head head = entry.getValue();
            doAddBasicTypeToExcel(oneRowData, head, row, relativeRowIndex, dataIndex++, cellIndex);
        }
        // Finish
        if (dataIndex >= oneRowData.size()) {
            return;
        }
        if (cellIndex != 0) {
            cellIndex++;
        }
        int size = oneRowData.size() - dataIndex;
        for (int i = 0; i < size; i++) {
            doAddBasicTypeToExcel(oneRowData, null, row, relativeRowIndex, dataIndex++, cellIndex++);
        }
    }

    private void doAddBasicTypeToExcel(List<Object> oneRowData, Head head, Row row, int relativeRowIndex, int dataIndex,
        int cellIndex) {
        WriteHandlerUtils.beforeCellCreate(writeContext, row, head, cellIndex, relativeRowIndex, Boolean.FALSE);
        Cell cell = WorkBookUtil.createCell(row, cellIndex);
        WriteHandlerUtils.afterCellCreate(writeContext, cell, head, relativeRowIndex, Boolean.FALSE);
        Object value = oneRowData.get(dataIndex);
        CellData cellData = converterAndSet(writeContext.currentWriteHolder(), value == null ? null : value.getClass(),
            cell, value, null, head, relativeRowIndex);
        WriteHandlerUtils.afterCellDispose(writeContext, cellData, cell, head, relativeRowIndex, Boolean.FALSE);
    }

    private void addJavaObjectToExcel(Object oneRowData, Row row, int relativeRowIndex,
        Map<Integer, Field> sortedAllFiledMap) {
        WriteHolder currentWriteHolder = writeContext.currentWriteHolder();
        BeanMap beanMap = BeanMap.create(oneRowData);
        Set<String> beanMapHandledSet = new HashSet<String>();
        int cellIndex = 0;
        // If it's a class it needs to be cast by type
        if (HeadKindEnum.CLASS.equals(writeContext.currentWriteHolder().excelWriteHeadProperty().getHeadKind())) {
            Map<Integer, Head> headMap = writeContext.currentWriteHolder().excelWriteHeadProperty().getHeadMap();
            Map<Integer, ExcelContentProperty> contentPropertyMap =
                writeContext.currentWriteHolder().excelWriteHeadProperty().getContentPropertyMap();
            for (Map.Entry<Integer, ExcelContentProperty> entry : contentPropertyMap.entrySet()) {
                cellIndex = entry.getKey();
                ExcelContentProperty excelContentProperty = entry.getValue();
                String name = excelContentProperty.getField().getName();
                if (!beanMap.containsKey(name)) {
                    continue;
                }
                Head head = headMap.get(cellIndex);
                WriteHandlerUtils.beforeCellCreate(writeContext, row, head, cellIndex, relativeRowIndex, Boolean.FALSE);
                Cell cell = WorkBookUtil.createCell(row, cellIndex);
                WriteHandlerUtils.afterCellCreate(writeContext, cell, head, relativeRowIndex, Boolean.FALSE);
                Object value = beanMap.get(name);
                CellData cellData = converterAndSet(currentWriteHolder, excelContentProperty.getField().getType(), cell,
                    value, excelContentProperty, head, relativeRowIndex);
                WriteHandlerUtils.afterCellDispose(writeContext, cellData, cell, head, relativeRowIndex, Boolean.FALSE);
                beanMapHandledSet.add(name);
            }
        }
        // Finish
        if (beanMapHandledSet.size() == beanMap.size()) {
            return;
        }
        Map<String, Field> ignoreMap = writeContext.currentWriteHolder().excelWriteHeadProperty().getIgnoreMap();
        initSortedAllFiledMapFieldList(oneRowData.getClass(), sortedAllFiledMap);

        for (Map.Entry<Integer, Field> entry : sortedAllFiledMap.entrySet()) {
            cellIndex = entry.getKey();
            Field field = entry.getValue();
            String filedName = field.getName();
            boolean uselessData = !beanMap.containsKey(filedName) || beanMapHandledSet.contains(filedName)
                || ignoreMap.containsKey(filedName);
            if (uselessData) {
                continue;
            }
            Object value = beanMap.get(filedName);
            WriteHandlerUtils.beforeCellCreate(writeContext, row, null, cellIndex, relativeRowIndex, Boolean.FALSE);
            Cell cell = WorkBookUtil.createCell(row, cellIndex);
            WriteHandlerUtils.afterCellCreate(writeContext, cell, null, relativeRowIndex, Boolean.FALSE);
            CellData cellData = converterAndSet(currentWriteHolder, value == null ? null : value.getClass(), cell,
                value, null, null, relativeRowIndex);

            WriteHandlerUtils.afterCellDispose(writeContext, cellData, cell, null, relativeRowIndex, Boolean.FALSE);
        }
    }

    private void initSortedAllFiledMapFieldList(Class clazz, Map<Integer, Field> sortedAllFiledMap) {
        if (!sortedAllFiledMap.isEmpty()) {
            return;
        }
        WriteWorkbookHolder writeWorkbookHolder = writeContext.writeWorkbookHolder();
        boolean needIgnore =
            !CollectionUtils.isEmpty(writeWorkbookHolder.getExcludeColumnFiledNames()) || !CollectionUtils
                .isEmpty(writeWorkbookHolder.getExcludeColumnIndexes()) || !CollectionUtils
                .isEmpty(writeWorkbookHolder.getIncludeColumnFiledNames()) || !CollectionUtils
                .isEmpty(writeWorkbookHolder.getIncludeColumnIndexes());
        ClassUtils.declaredFields(clazz, sortedAllFiledMap,
            writeWorkbookHolder.getWriteWorkbook().getConvertAllFiled(), needIgnore, writeWorkbookHolder);
    }

}
