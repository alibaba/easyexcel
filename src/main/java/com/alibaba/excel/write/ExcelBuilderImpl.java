package com.alibaba.excel.write;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelList;
import com.alibaba.excel.enums.DynamicDirectionEnum;
import com.alibaba.excel.metadata.Head;
import org.apache.poi.ss.util.CellRangeAddress;

import com.alibaba.excel.context.WriteContext;
import com.alibaba.excel.context.WriteContextImpl;
import com.alibaba.excel.enums.WriteTypeEnum;
import com.alibaba.excel.exception.ExcelGenerateException;
import com.alibaba.excel.util.FileUtils;
import com.alibaba.excel.write.executor.ExcelWriteAddExecutor;
import com.alibaba.excel.write.executor.ExcelWriteFillExecutor;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.alibaba.excel.write.metadata.WriteWorkbook;
import com.alibaba.excel.write.metadata.fill.FillConfig;

/**
 * @author jipengfei
 */
public class ExcelBuilderImpl implements ExcelBuilder {

    private WriteContext context;
    private ExcelWriteFillExecutor excelWriteFillExecutor;
    private ExcelWriteAddExecutor excelWriteAddExecutor;

    static {
        // Create temporary cache directory at initialization time to avoid POI concurrent write bugs
        FileUtils.createPoiFilesDirectory();
    }

    public ExcelBuilderImpl(WriteWorkbook writeWorkbook) {
        try {
            context = new WriteContextImpl(writeWorkbook);
        } catch (RuntimeException e) {
            finishOnException();
            throw e;
        } catch (Throwable e) {
            finishOnException();
            throw new ExcelGenerateException(e);
        }
    }

    @Override
    public void addContent(List data, WriteSheet writeSheet) {
        addContent(data, writeSheet, null);
    }

    @Override
    public void addContent(List data, WriteSheet writeSheet, WriteTable writeTable) {
        try {
            dynamicMergeHead(data);
            context.currentSheet(writeSheet, WriteTypeEnum.ADD);
            context.currentTable(writeTable);
            if (excelWriteAddExecutor == null) {
                excelWriteAddExecutor = new ExcelWriteAddExecutor(context);
            }
            excelWriteAddExecutor.add(data);
        } catch (RuntimeException e) {
            finishOnException();
            throw e;
        } catch (Throwable e) {
            finishOnException();
            throw new ExcelGenerateException(e);
        }
    }

    private void dynamicMergeHead(List data) {
        if (!context.currentWriteHolder().needHead() || !context.currentWriteHolder().excelWriteHeadProperty().hasHead()) {
            return;
        }
        Map<String, Integer> dynamicMap = new HashMap<>();
        Object object = data.get(0);
        Class<?> aClass = object.getClass();
        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            ExcelIgnore excelIgnore = field.getAnnotation(ExcelIgnore.class);
            if (excelIgnore != null) {
                continue;
            }
            ExcelList excelList = field.getAnnotation(ExcelList.class);
            if (excelList != null && DynamicDirectionEnum.ORIENTATION.equals(excelList.direction())) {
                dynamicMap.put(field.getName(), maxFieldCount(data, field.getName()));
            }
        }
        if (context instanceof WriteContextImpl) {
            ((WriteContextImpl) context).setDynamicMap(dynamicMap);
        }
        if (dynamicMap.size() > 0) {
            Map<Integer, Head> headMap = context.currentWriteHolder().excelWriteHeadProperty().getHeadMap();
            Map<Integer, Head> newHeadMap = new HashMap<>();
            int index = 0;
            for (Map.Entry<Integer, Head> entry : headMap.entrySet()) {
                Head currentHead = entry.getValue();
                String fieldName = currentHead.getFieldName();
                if (dynamicMap.containsKey(fieldName)) {
                    Integer maxCount = dynamicMap.get(fieldName);
                    index += maxCount - 1;
                    for (int i=0; i<maxCount; i++) {
                        Head newHead = new Head(currentHead.getColumnIndex() + i, currentHead.getFieldName(),
                            currentHead.getHeadNameList(), currentHead.getForceIndex(), currentHead.getForceName());
                        newHeadMap.put(currentHead.getColumnIndex() + i, newHead);
                    }
                } else {
                    Head newHead = new Head(currentHead.getColumnIndex() + index, currentHead.getFieldName(),
                        currentHead.getHeadNameList(), currentHead.getForceIndex(), currentHead.getForceName());
                    newHeadMap.put(currentHead.getColumnIndex() + index, newHead);
                }
            }
            context.currentWriteHolder().excelWriteHeadProperty().setHeadMap(newHeadMap);
        }
    }

    private int maxFieldCount(List data, String fieldName) {
        int maxFieldCount = 0;
        try {
            for (Object obj : data) {
                Class<?> aClass = obj.getClass();
                Field field = aClass.getDeclaredField(fieldName);
                field.setAccessible(true);
                Object fieldValue = field.get(obj);
                if (fieldValue instanceof List) {
                    maxFieldCount = Math.max(maxFieldCount, ((List) fieldValue).size());
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return maxFieldCount;
    }

    @Override
    public void fill(Object data, FillConfig fillConfig, WriteSheet writeSheet) {
        try {
            if (context.writeWorkbookHolder().getTempTemplateInputStream() == null) {
                throw new ExcelGenerateException("Calling the 'fill' method must use a template.");
            }
            context.currentSheet(writeSheet, WriteTypeEnum.FILL);
            if (excelWriteFillExecutor == null) {
                excelWriteFillExecutor = new ExcelWriteFillExecutor(context);
            }
            excelWriteFillExecutor.fill(data, fillConfig);
        } catch (RuntimeException e) {
            finishOnException();
            throw e;
        } catch (Throwable e) {
            finishOnException();
            throw new ExcelGenerateException(e);
        }
    }

    private void finishOnException() {
        finish(true);
    }

    @Override
    public void finish(boolean onException) {
        if (context != null) {
            context.finish(onException);
        }
    }

    @Override
    public void merge(int firstRow, int lastRow, int firstCol, int lastCol) {
        CellRangeAddress cra = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
        context.writeSheetHolder().getSheet().addMergedRegion(cra);
    }

    @Override
    public WriteContext writeContext() {
        return context;
    }
}
