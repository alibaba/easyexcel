package com.alibaba.excel.read.listener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.enums.HeadKindEnum;
import com.alibaba.excel.enums.ReadDefaultReturnEnum;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.DataFormatData;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.metadata.holder.ReadSheetHolder;
import com.alibaba.excel.read.metadata.property.ExcelReadHeadProperty;
import com.alibaba.excel.support.cglib.beans.BeanMap;
import com.alibaba.excel.util.BeanMapUtils;
import com.alibaba.excel.util.ClassUtils;
import com.alibaba.excel.util.ConverterUtils;
import com.alibaba.excel.util.DateUtils;
import com.alibaba.excel.util.MapUtils;


/**
 * Convert to the object the user needs
 *
 * @author jipengfei
 */
public class ModelBuildEventListener implements IgnoreExceptionReadListener<Map<Integer, ReadCellData<?>>> {

    @Override
    public void invoke(Map<Integer, ReadCellData<?>> cellDataMap, AnalysisContext context) {
        ReadSheetHolder readSheetHolder = context.readSheetHolder();
        if (HeadKindEnum.CLASS.equals(readSheetHolder.excelReadHeadProperty().getHeadKind())) {
            context.readRowHolder()
                .setCurrentRowAnalysisResult(buildUserModel(cellDataMap, readSheetHolder, context));
            return;
        }
        context.readRowHolder().setCurrentRowAnalysisResult(buildNoModel(cellDataMap, readSheetHolder, context));
    }

    private Object buildNoModel(Map<Integer, ReadCellData<?>> cellDataMap, ReadSheetHolder readSheetHolder,
        AnalysisContext context) {
        int index = 0;
        Map<Integer, Object> map = MapUtils.newLinkedHashMapWithExpectedSize(cellDataMap.size());
        for (Map.Entry<Integer, ReadCellData<?>> entry : cellDataMap.entrySet()) {
            Integer key = entry.getKey();
            ReadCellData<?> cellData = entry.getValue();
            while (index < key) {
                map.put(index, null);
                index++;
            }
            index++;

            ReadDefaultReturnEnum readDefaultReturn = context.readWorkbookHolder().getReadDefaultReturn();
            if (readDefaultReturn == ReadDefaultReturnEnum.STRING) {
                // string
                map.put(key,
                    (String)ConverterUtils.convertToJavaObject(cellData, null, null, readSheetHolder.converterMap(),
                        context, context.readRowHolder().getRowIndex(), key));
            } else {
                // retrun ReadCellData
                ReadCellData<?> convertedReadCellData = convertReadCellData(cellData,
                    context.readWorkbookHolder().getReadDefaultReturn(), readSheetHolder, context, key);
                if (readDefaultReturn == ReadDefaultReturnEnum.READ_CELL_DATA) {
                    map.put(key, convertedReadCellData);
                } else {
                    map.put(key, convertedReadCellData.getData());
                }
            }
        }
        // fix https://github.com/alibaba/easyexcel/issues/2014
        int headSize = calculateHeadSize(readSheetHolder);
        while (index < headSize) {
            map.put(index, null);
            index++;
        }
        return map;
    }

    private ReadCellData convertReadCellData(ReadCellData<?> cellData, ReadDefaultReturnEnum readDefaultReturn,
        ReadSheetHolder readSheetHolder, AnalysisContext context, Integer columnIndex) {
        Class<?> classGeneric;
        switch (cellData.getType()) {
            case STRING:
            case DIRECT_STRING:
            case ERROR:
            case EMPTY:
                classGeneric = String.class;
                break;
            case BOOLEAN:
                classGeneric = Boolean.class;
                break;
            case NUMBER:
                DataFormatData dataFormatData = cellData.getDataFormatData();
                if (dataFormatData != null && DateUtils.isADateFormat(dataFormatData.getIndex(),
                    dataFormatData.getFormat())) {
                    classGeneric = LocalDateTime.class;
                } else {
                    classGeneric = BigDecimal.class;
                }
                break;
            default:
                classGeneric = ConverterUtils.defaultClassGeneric;
                break;
        }

        return (ReadCellData)ConverterUtils.convertToJavaObject(cellData, null, ReadCellData.class,
            classGeneric, null, readSheetHolder.converterMap(), context, context.readRowHolder().getRowIndex(),
            columnIndex);
    }

    private int calculateHeadSize(ReadSheetHolder readSheetHolder) {
        if (readSheetHolder.excelReadHeadProperty().getHeadMap().size() > 0) {
            return readSheetHolder.excelReadHeadProperty().getHeadMap().size();
        }
        if (readSheetHolder.getMaxNotEmptyDataHeadSize() != null) {
            return readSheetHolder.getMaxNotEmptyDataHeadSize();
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
            ReadCellData<?> cellData = cellDataMap.get(index);
            Object value = ConverterUtils.convertToJavaObject(cellData, head.getField(),
                ClassUtils.declaredExcelContentProperty(dataMap, readSheetHolder.excelReadHeadProperty().getHeadClazz(),
                    fieldName, readSheetHolder), readSheetHolder.converterMap(), context,
                context.readRowHolder().getRowIndex(), index);
            if (value != null) {
                dataMap.put(fieldName, value);
            }
        }
        return resultModel;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {}
}
