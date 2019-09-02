package com.alibaba.excel.read.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ConverterKeyBuild;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.enums.HeadKindEnum;
import com.alibaba.excel.event.AbstractIgnoreExceptionReadListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
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
            context.readRowHolder().setCurrentRowAnalysisResult(buildUserModel(cellDataMap, currentReadHolder));
            return;
        }
        context.readRowHolder().setCurrentRowAnalysisResult(buildStringList(cellDataMap, currentReadHolder, context));
    }

    private Object buildStringList(Map<Integer, CellData> cellDataMap, ReadHolder currentReadHolder,
        AnalysisContext context) {
        if (context.readWorkbookHolder().getDefaultReturnMap()) {
            Map<Integer, String> map = new HashMap<Integer, String>(cellDataMap.size() * 4 / 3 + 1);
            for (Map.Entry<Integer, CellData> entry : cellDataMap.entrySet()) {
                CellData cellData = entry.getValue();
                if (cellData.getType() == CellDataTypeEnum.EMPTY) {
                    map.put(entry.getKey(), null);
                    continue;
                }
                map.put(entry.getKey(), (String)ConverterUtils.convertToJavaObject(cellData, String.class, null,
                    currentReadHolder.converterMap(), currentReadHolder.globalConfiguration()));
            }
            return map;
        } else {
            // Compatible with the old code the old code returns a list
            List<String> list = new ArrayList<String>();
            for (Map.Entry<Integer, CellData> entry : cellDataMap.entrySet()) {
                CellData cellData = entry.getValue();
                if (cellData.getType() == CellDataTypeEnum.EMPTY) {
                    list.add(null);
                    continue;
                }
                list.add((String)ConverterUtils.convertToJavaObject(cellData, String.class, null,
                    currentReadHolder.converterMap(), currentReadHolder.globalConfiguration()));
            }
            return list;
        }
    }

    private Object buildUserModel(Map<Integer, CellData> cellDataMap, ReadHolder currentReadHolder) {
        ExcelReadHeadProperty excelReadHeadProperty = currentReadHolder.excelReadHeadProperty();
        Object resultModel;
        try {
            resultModel = excelReadHeadProperty.getHeadClazz().newInstance();
        } catch (Exception e) {
            throw new ExcelDataConvertException(
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
            Object value = ConverterUtils.convertToJavaObject(cellData, excelContentProperty.getField().getType(),
                excelContentProperty, currentReadHolder.converterMap(), currentReadHolder.globalConfiguration());
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
