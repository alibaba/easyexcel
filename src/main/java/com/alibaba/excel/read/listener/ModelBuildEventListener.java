package com.alibaba.excel.read.listener;

import java.util.HashMap;
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

import net.sf.cglib.beans.BeanMap;

/**
 * Convert to the object the user needs
 *
 * @author jipengfei
 */
public class ModelBuildEventListener extends AbstractIgnoreExceptionReadListener<Map<Integer, CellData>> {

    @Override
    public void invoke(Map<Integer, CellData> cellDataMap, AnalysisContext context) {
        ReadHolder currentReadHolder = context.currentReadHolder();
        if (HeadKindEnum.CLASS.equals(currentReadHolder.excelReadHeadProperty().getHeadKind())) {
            context.readRowHolder().setCurrentRowAnalysisResult(buildUserModel(cellDataMap, currentReadHolder));
            return;
        }
        context.readRowHolder().setCurrentRowAnalysisResult(buildStringList(cellDataMap, currentReadHolder));
    }

    private Object buildStringList(Map<Integer, CellData> cellDataMap, ReadHolder currentReadHolder) {
        Map<Integer, String> map = new HashMap<Integer, String>(cellDataMap.size() * 4 / 3 + 1);
        for (Map.Entry<Integer, CellData> entry : cellDataMap.entrySet()) {
            CellData cellData = entry.getValue();
            if (cellData.getType() == CellDataTypeEnum.EMPTY) {
                map.put(entry.getKey(), null);
                continue;
            }
            map.put(entry.getKey(), (String)convertValue(cellData, String.class, null, currentReadHolder.converterMap(),
                currentReadHolder.globalConfiguration()));
        }
        return map;
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
            Object value = convertValue(cellData, excelContentProperty.getField().getType(), excelContentProperty,
                currentReadHolder.converterMap(), currentReadHolder.globalConfiguration());
            if (value != null) {
                map.put(excelContentProperty.getField().getName(), value);
            }
        }
        BeanMap.create(resultModel).putAll(map);
        return resultModel;
    }

    private Object convertValue(CellData cellData, Class clazz, ExcelContentProperty contentProperty,
        Map<String, Converter> converterMap, GlobalConfiguration globalConfiguration) {
        if (clazz == CellData.class) {
            return new CellData(cellData);
        }
        Converter converter = null;
        if (contentProperty != null) {
            converter = contentProperty.getConverter();
        }
        if (converter == null) {
            converter = converterMap.get(ConverterKeyBuild.buildKey(clazz, cellData.getType()));
        }
        if (converter == null) {
            throw new ExcelDataConvertException(
                "Converter not found, convert " + cellData.getType() + " to " + clazz.getName());
        }
        try {
            return converter.convertToJavaData(cellData, contentProperty, globalConfiguration);
        } catch (Exception e) {
            throw new ExcelDataConvertException("Convert data " + cellData + " to " + clazz + " error ", e);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {}
}
