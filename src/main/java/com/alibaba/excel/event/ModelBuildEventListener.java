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
import com.alibaba.excel.metadata.ExcelColumnProperty;
import com.alibaba.excel.metadata.ExcelHeadProperty;

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
        if (excelHeadProperty == null) {
            return resultModel;
        }
        Map map = new HashMap();
        for (int i = 0; i < cellDataList.size(); i++) {
            ExcelColumnProperty columnProperty = excelHeadProperty.getExcelColumnProperty(i);
            if (columnProperty != null) {
                CellData cellData = cellDataList.get(i);
                if (cellData.getType() == CellDataTypeEnum.EMPTY) {
                    continue;
                }
                Object value = convertValue(cellDataList.get(i), columnProperty.getField().getClass(), columnProperty);
                if (value != null) {
                    map.put(columnProperty.getField().getName(), value);
                }
            }
        }
        BeanMap.create(resultModel).putAll(map);
        return resultModel;
    }

    private Object convertValue(CellData cellData, Class clazz, ExcelColumnProperty columnProperty) {
        Converter converter = converters.get(ConverterKey.buildConverterKey(clazz, cellData.getType()));
        if (converter == null) {
            throw new ExcelDataConvertException(
                "Converter not found, converte " + cellData.getType() + " to " + clazz.getName());
        }
        try {
            return converter.convertToJavaData(cellData, columnProperty);
        } catch (Exception e) {
            throw new ExcelDataConvertException("Convert data " + cellData + " to " + clazz + " error ", e);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
