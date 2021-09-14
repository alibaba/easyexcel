package com.alibaba.excel.read.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.enums.HeadKindEnum;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.read.metadata.holder.ReadSheetHolder;
import com.alibaba.excel.read.metadata.property.ExcelReadHeadProperty;
import com.alibaba.excel.util.BeanMapUtils;
import com.alibaba.excel.util.ConverterUtils;
import com.alibaba.excel.util.FieldUtils;
import com.alibaba.excel.util.MapUtils;

/**
 * Convert to the object the user needs
 *
 * @author jipengfei
 */
public class ModelBuildEventListener implements ReadListener<Map<Integer, ReadCellData<?>>> {

    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> cellDataMap, AnalysisContext context) {}

    @Override
    public void invoke(Map<Integer, ReadCellData<?>> cellDataMap, AnalysisContext context) {
        ReadSheetHolder readSheetHolder = context.readSheetHolder();
        if (HeadKindEnum.CLASS.equals(readSheetHolder.excelReadHeadProperty().getHeadKind())) {
            context.readRowHolder()
                .setCurrentRowAnalysisResult(buildUserModel(cellDataMap, readSheetHolder, context));
            return;
        }
        context.readRowHolder().setCurrentRowAnalysisResult(buildStringList(cellDataMap, readSheetHolder, context));
    }

    private Object buildStringList(Map<Integer, ReadCellData<?>> cellDataMap, ReadSheetHolder readSheetHolder,
        AnalysisContext context) {
        int index = 0;
        if (context.readWorkbookHolder().getDefaultReturnMap()) {
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
                    (String)ConverterUtils.convertToJavaObject(cellData, null, null, readSheetHolder.converterMap(),
                        context, context.readRowHolder().getRowIndex(), key));
            }
            int headSize = readSheetHolder.excelReadHeadProperty().getHeadMap().size();
            while (index < headSize) {
                map.put(index, null);
                index++;
            }
            return map;
        } else {
            // Compatible with the old code the old code returns a list
            List<String> list = new ArrayList<>();
            for (Map.Entry<Integer, ReadCellData<?>> entry : cellDataMap.entrySet()) {
                Integer key = entry.getKey();
                ReadCellData<?> cellData = entry.getValue();
                while (index < key) {
                    list.add(null);
                    index++;
                }
                index++;
                list.add(
                    (String)ConverterUtils.convertToJavaObject(cellData, null, null, readSheetHolder.converterMap(),
                        context, context.readRowHolder().getRowIndex(), key));
            }
            int headSize = readSheetHolder.excelReadHeadProperty().getHeadMap().size();
            while (index < headSize) {
                list.add(null);
                index++;
            }
            return list;
        }
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
        Map<String, Object> map = MapUtils.newHashMapWithExpectedSize(headMap.size());
        Map<Integer, ExcelContentProperty> contentPropertyMap = excelReadHeadProperty.getContentPropertyMap();
        for (Map.Entry<Integer, Head> entry : headMap.entrySet()) {
            Integer index = entry.getKey();
            if (!cellDataMap.containsKey(index)) {
                continue;
            }
            ReadCellData<?> cellData = cellDataMap.get(index);
            ExcelContentProperty excelContentProperty = contentPropertyMap.get(index);
            Object value = ConverterUtils.convertToJavaObject(cellData, excelContentProperty.getField(),
                excelContentProperty, readSheetHolder.converterMap(), context,
                context.readRowHolder().getRowIndex(), index);
            if (value != null) {
                map.put(FieldUtils.resolveCglibFieldName(excelContentProperty.getField()), value);
            }
        }
        BeanMapUtils.create(resultModel).putAll(map);
        return resultModel;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {}
}
