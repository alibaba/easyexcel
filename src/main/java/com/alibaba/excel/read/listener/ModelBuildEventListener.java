package com.alibaba.excel.read.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.enums.HeadKindEnum;
import com.alibaba.excel.event.AbstractIgnoreExceptionReadListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.read.metadata.holder.ReadHolder;
import com.alibaba.excel.read.metadata.property.ExcelReadHeadProperty;
import com.alibaba.excel.util.ConverterUtils;

import net.sf.cglib.beans.BeanMap;

/**
 * Convert to the object the user needs
 *
 * @author jipengfei
 */
public class ModelBuildEventListener extends AbstractIgnoreExceptionReadListener<Map<Integer, CellData>> {

    @Override
    public void invokeHead(Map<Integer, CellData> cellDataMap, AnalysisContext context) {}

    @Override
    public void invoke(Map<Integer, CellData> cellDataMap, AnalysisContext context) {
        ReadHolder currentReadHolder = context.currentReadHolder();
        if (HeadKindEnum.CLASS.equals(currentReadHolder.excelReadHeadProperty().getHeadKind())) {
            context.readRowHolder()
                .setCurrentRowAnalysisResult(buildUserModel(cellDataMap, currentReadHolder, context));
            return;
        }
        context.readRowHolder().setCurrentRowAnalysisResult(buildStringList(cellDataMap, currentReadHolder, context));
    }

    private Object buildStringList(Map<Integer, CellData> cellDataMap, ReadHolder currentReadHolder,
        AnalysisContext context) {
        int index = 0;
        if (context.readWorkbookHolder().getDefaultReturnMap()) {
            Map<Integer, String> map = new LinkedHashMap<Integer, String>(cellDataMap.size() * 4 / 3 + 1);
            for (Map.Entry<Integer, CellData> entry : cellDataMap.entrySet()) {
                Integer key = entry.getKey();
                CellData cellData = entry.getValue();
                while (index < key) {
                    map.put(index, null);
                    index++;
                }
                index++;
                if (cellData.getType() == CellDataTypeEnum.EMPTY) {
                    map.put(key, null);
                    continue;
                }
                map.put(key,
                    (String)ConverterUtils.convertToJavaObject(cellData, null, null, currentReadHolder.converterMap(),
                        currentReadHolder.globalConfiguration(), context.readRowHolder().getRowIndex(), key));
            }
            int headSize = currentReadHolder.excelReadHeadProperty().getHeadMap().size();
            while (index < headSize) {
                map.put(index, null);
                index++;
            }
            return map;
        } else {
            // Compatible with the old code the old code returns a list
            List<String> list = new ArrayList<String>();
            for (Map.Entry<Integer, CellData> entry : cellDataMap.entrySet()) {
                Integer key = entry.getKey();
                CellData cellData = entry.getValue();
                while (index < key) {
                    list.add(null);
                    index++;
                }
                index++;
                if (cellData.getType() == CellDataTypeEnum.EMPTY) {
                    list.add(null);
                    continue;
                }
                list.add(
                    (String)ConverterUtils.convertToJavaObject(cellData, null, null, currentReadHolder.converterMap(),
                        currentReadHolder.globalConfiguration(), context.readRowHolder().getRowIndex(), key));
            }
            int headSize = currentReadHolder.excelReadHeadProperty().getHeadMap().size();
            while (index < headSize) {
                list.add(null);
                index++;
            }
            return list;
        }
    }

    private Object buildUserModel(Map<Integer, CellData> cellDataMap, ReadHolder currentReadHolder,
        AnalysisContext context) {
        ExcelReadHeadProperty excelReadHeadProperty = currentReadHolder.excelReadHeadProperty();
        Object resultModel;
        try {
            resultModel = excelReadHeadProperty.getHeadClazz().newInstance();
        } catch (Exception e) {
            throw new ExcelDataConvertException(context.readRowHolder().getRowIndex(), 0,
                new CellData(CellDataTypeEnum.EMPTY), null,
                "Can not instance class: " + excelReadHeadProperty.getHeadClazz().getName(), e);
        }
        Map<Integer, Head> headMap = excelReadHeadProperty.getHeadMap();
        Map<String, Object> map = new HashMap<String, Object>(headMap.size() * 4 / 3 + 1);
        Map<Integer, ExcelContentProperty> contentPropertyMap = excelReadHeadProperty.getContentPropertyMap();
        for (Map.Entry<Integer, Head> entry : headMap.entrySet()) {
            Integer index = entry.getKey();
            if (!cellDataMap.containsKey(index)) {
                continue;
            }
            CellData cellData = cellDataMap.get(index);
            if (cellData.getType() == CellDataTypeEnum.EMPTY) {
                continue;
            }
            ExcelContentProperty excelContentProperty = contentPropertyMap.get(index);
            Object value = ConverterUtils.convertToJavaObject(cellData, excelContentProperty.getField(),
                excelContentProperty, currentReadHolder.converterMap(), currentReadHolder.globalConfiguration(),
                context.readRowHolder().getRowIndex(), index);
            if (value != null) {
                map.put(excelContentProperty.getField().getName(), value);
            }
        }
        BeanMap.create(resultModel).putAll(map);
        return resultModel;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {}
}
