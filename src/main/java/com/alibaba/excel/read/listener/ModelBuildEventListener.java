package com.alibaba.excel.read.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ConverterKey;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.enums.HeadKindEnum;
import com.alibaba.excel.event.AbstractIgnoreExceptionReadListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.CellData;
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
public class ModelBuildEventListener extends AbstractIgnoreExceptionReadListener<List<CellData>> {

    @Override
    public void invoke(List<CellData> data, AnalysisContext context) {
        ReadHolder currentReadHolder = context.currentReadHolder();
        if (HeadKindEnum.CLASS.equals(currentReadHolder.excelReadHeadProperty().getHeadKind())) {
            context.readRowHolder().setCurrentRowAnalysisResult(buildUserModel(data, currentReadHolder));
            return;
        }
        context.readRowHolder().setCurrentRowAnalysisResult(buildStringList(data, currentReadHolder));
    }

    private Object buildStringList(List<CellData> data, ReadHolder currentReadHolder) {
        List<String> list = new ArrayList<String>();
        for (CellData cellData : data) {
            list.add((String)convertValue(cellData, String.class, null, currentReadHolder.converterMap()));
        }
        return list;
    }

    private Object buildUserModel(List<CellData> data, ReadHolder currentReadHolder) {
        ExcelReadHeadProperty excelReadHeadProperty = currentReadHolder.excelReadHeadProperty();
        Object resultModel;
        try {
            resultModel = excelReadHeadProperty.getHeadClazz().newInstance();
        } catch (Exception e) {
            throw new ExcelDataConvertException(
                "Can not instance class: " + excelReadHeadProperty.getHeadClazz().getName(), e);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        Map<Integer, Head> headMap = excelReadHeadProperty.getHeadMap();
        Map<Integer, ExcelContentProperty> contentPropertyMap = excelReadHeadProperty.getContentPropertyMap();
        for (Map.Entry<Integer, Head> entry : headMap.entrySet()) {
            Integer index = entry.getKey();
            if (index >= data.size()) {
                continue;
            }
            CellData cellData = data.get(index);
            if (cellData.getType() == CellDataTypeEnum.EMPTY) {
                continue;
            }
            ExcelContentProperty excelContentProperty = contentPropertyMap.get(index);
            Object value = convertValue(cellData, excelContentProperty.getField().getClass(), excelContentProperty,
                currentReadHolder.converterMap());
            if (value != null) {
                map.put(excelContentProperty.getField().getName(), value);
            }
        }
        BeanMap.create(resultModel).putAll(map);
        return resultModel;
    }

    private Object convertValue(CellData cellData, Class clazz, ExcelContentProperty contentProperty,
        Map<ConverterKey, Converter> readConverterMap) {
        Converter converter = readConverterMap.get(ConverterKey.buildConverterKey(clazz, cellData.getType()));
        if (converter == null) {
            throw new ExcelDataConvertException(
                "Converter not found, convert " + cellData.getType() + " to " + clazz.getName());
        }
        try {
            return converter.convertToJavaData(cellData, contentProperty);
        } catch (Exception e) {
            throw new ExcelDataConvertException("Convert data " + cellData + " to " + clazz + " error ", e);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {}
}
