package com.alibaba.excel.event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ConverterKey;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.exception.ExcelGenerateException;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.metadata.property.ExcelHeadProperty;

import net.sf.cglib.beans.BeanMap;

/**
 * @author jipengfei
 */
public class ModelBuildEventListener extends AnalysisEventListener<Object> {
    private final Map<ConverterKey, Converter> converters;

    public ModelBuildEventListener(Map<ConverterKey, Converter> converters) {
        this.converters = converters;
    }

    @Override
    public void invoke(Object object, AnalysisContext context) {
        if (context.getExcelHeadProperty() != null && context.getExcelHeadProperty().getHeadClazz() != null) {
            try {
                @SuppressWarnings("unchecked")
                Object resultModel = buildUserModel(context, (List<CellData>)object);
                context.setCurrentRowAnalysisResult(resultModel);
            } catch (Exception e) {
                throw new ExcelGenerateException(e);
            }
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private Object buildUserModel(AnalysisContext context, List<CellData> cellDataList) throws Exception {
        ExcelHeadProperty excelHeadProperty = context.getExcelHeadProperty();
        Object resultModel = excelHeadProperty.getHeadClazz().newInstance();
        Map<String,Object> map = new HashMap<String,Object>();
        for (int i = 0; i < cellDataList.size(); i++) {
            ExcelContentProperty contentProperty = excelHeadProperty.getContentPropertyMap().get(i);
            if (contentProperty != null) {
                CellData cellData = cellDataList.get(i);
                if (cellData.getType() == CellDataTypeEnum.EMPTY) {
                    continue;
                }
                Object value = convertValue(cellDataList.get(i), contentProperty.getField().getClass(), contentProperty);
                if (value != null) {
                    map.put(contentProperty.getField().getName(), value);
                }
            }
        }
        BeanMap.create(resultModel).putAll(map);
        return resultModel;
    }

    private Object convertValue(CellData cellData, Class clazz, ExcelContentProperty contentProperty) {
        Converter converter = converters.get(ConverterKey.buildConverterKey(clazz, cellData.getType()));
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
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
