package com.alibaba.excel.read.listener;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.enums.HeadKindEnum;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.metadata.holder.ReadSheetHolder;
import com.alibaba.excel.read.metadata.property.ExcelReadHeadProperty;
import com.alibaba.excel.util.BeanMapUtils;
import com.alibaba.excel.util.ClassUtils;
import com.alibaba.excel.util.ConverterUtils;
import com.alibaba.excel.util.MapUtils;

import lombok.val;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cglib.beans.BeanMap;

/**
 * Convert to the object the user needs
 *
 * @author jipengfei
 */
public class ModelBuildEventListener implements IgnoreExceptionReadListener<Map<Integer, ReadCellData<?>>> {
    private boolean isFirstDataRow = true;
    private Map<String, Method> writeMethodMap;

    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> cellDataMap, AnalysisContext context) {
        if (context.readSheetHolder().getMaxDataHeadSize() == null
                || context.readSheetHolder().getMaxDataHeadSize() < CollectionUtils.size(cellDataMap)) {
            context.readSheetHolder().setMaxDataHeadSize(CollectionUtils.size(cellDataMap));
        }
    }

    @Override
    public void invoke(Map<Integer, ReadCellData<?>> cellDataMap, AnalysisContext context) {
        ReadSheetHolder readSheetHolder = context.readSheetHolder();
        if (HeadKindEnum.CLASS.equals(readSheetHolder.excelReadHeadProperty().getHeadKind())) {
            if (isFirstDataRow) {
                isFirstDataRow = false;
                buildWriteMethod(context);
            }
            context.readRowHolder()
                    .setCurrentRowAnalysisResult(buildUserModel(cellDataMap, readSheetHolder, context));
            return;
        }
        context.readRowHolder().setCurrentRowAnalysisResult(buildStringList(cellDataMap, readSheetHolder, context));
    }

    private static String capitalize(String fieldName) {
        if (fieldName == null || "".equals(fieldName)) {
            return null;
        }
        Character letter1 = fieldName.charAt(0);
        Character letter2 = fieldName.length() > 1 ? fieldName.charAt(1) : null;
        String lastLetters = fieldName.length() > 1 ? fieldName.substring(1, fieldName.length()) : "";
        if (letter2 != null && Character.isUpperCase(letter2)) {
            return fieldName;
        }
        letter1 = Character.toUpperCase(letter1);
        return letter1 + lastLetters;
    }

    private void buildWriteMethod(AnalysisContext context) {
        Class<?> clazz = context.readSheetHolder().getClazz();
        ExcelReadHeadProperty excelReadHeadProperty = context.readSheetHolder().excelReadHeadProperty();
        Map<Integer, Head> headMap = excelReadHeadProperty.getHeadMap();
        writeMethodMap = new HashMap<>(headMap.size());
        headMap.forEach((colIndex, head) -> {
            Field field = head.getField();
            String fieldName = head.getFieldName();
            Class<?> fieldType = field.getType();
            String writeMethodName = "set" + capitalize(fieldName);
            Method writeMethod = null;
            try {
                writeMethod = clazz.getMethod(writeMethodName, fieldType);
            } catch (NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
            }
            if (writeMethod != null) {
                writeMethodMap.put(fieldName, writeMethod);
            }
        });
    }

    private Object buildStringList(Map<Integer, ReadCellData<?>> cellDataMap, ReadSheetHolder readSheetHolder,
            AnalysisContext context) {
        int index = 0;
        Map<Integer, String> map = MapUtils.newLinkedHashMapWithExpectedSize(cellDataMap.size());
        for (Map.Entry<Integer, ReadCellData<?>> entry : cellDataMap.entrySet()) {
            Integer key = entry.getKey();
            ReadCellData<?> cellData = entry.getValue();
            while (index < key) {
                map.put(index, null);
                index++;
            }
            index++;
            map.put(key,
                    (String) ConverterUtils.convertToJavaObject(cellData, null, null, readSheetHolder.converterMap(),
                            context, context.readRowHolder().getRowIndex(), key));
        }
        // fix https://github.com/alibaba/easyexcel/issues/2014
        int headSize = calculateHeadSize(readSheetHolder);
        while (index < headSize) {
            map.put(index, null);
            index++;
        }
        return map;
    }

    private int calculateHeadSize(ReadSheetHolder readSheetHolder) {
        if (readSheetHolder.excelReadHeadProperty().getHeadMap().size() > 0) {
            return readSheetHolder.excelReadHeadProperty().getHeadMap().size();
        }
        if (readSheetHolder.getMaxDataHeadSize() != null) {
            return readSheetHolder.getMaxDataHeadSize();
        }
        return 0;
    }

    private Object buildUserModel(Map<Integer, ReadCellData<?>> cellDataMap, ReadSheetHolder readSheetHolder,
            AnalysisContext context) {
        ExcelReadHeadProperty excelReadHeadProperty = readSheetHolder.excelReadHeadProperty();
        Object resultModel;
        try {
            resultModel = excelReadHeadProperty.getHeadClazz().newInstance();
        } catch (Exception e) {
            throw new ExcelDataConvertException(context.readRowHolder().getRowIndex(), 0,
                    new ReadCellData<>(CellDataTypeEnum.EMPTY), null,
                    "Can not instance class: " + excelReadHeadProperty.getHeadClazz().getName(), e);
        }
        Map<Integer, Head> headMap = excelReadHeadProperty.getHeadMap();
        BeanMap dataMap = BeanMapUtils.create(resultModel);
        for (Map.Entry<Integer, Head> entry : headMap.entrySet()) {
            Integer index = entry.getKey();
            Head head = entry.getValue();
            String fieldName = head.getFieldName();
            if (!cellDataMap.containsKey(index)) {
                continue;
            }
            Method writeMethod = writeMethodMap.get(fieldName);
            if (writeMethod == null) {
                continue;
            }
            ReadCellData<?> cellData = cellDataMap.get(index);
            Object value = ConverterUtils.convertToJavaObject(cellData, head.getField(),
                    ClassUtils.declaredExcelContentProperty(dataMap,
                            readSheetHolder.excelReadHeadProperty().getHeadClazz(),
                            fieldName),
                    readSheetHolder.converterMap(), context, context.readRowHolder().getRowIndex(), index);
            if (value != null) {
                // dataMap.put(fieldName, value);
                try {
                    writeMethod.invoke(resultModel, value);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    // e.printStackTrace();
                }
            }
        }
        return resultModel;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
    }
}
