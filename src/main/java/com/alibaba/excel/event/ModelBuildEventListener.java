package com.alibaba.excel.event;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelGenerateException;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.ExcelColumnProperty;
import com.alibaba.excel.metadata.ExcelHeadProperty;
import com.alibaba.excel.util.TypeUtil;

import net.sf.cglib.beans.BeanMap;

/**
 * @author jipengfei
 */
public class ModelBuildEventListener extends AnalysisEventListener<Object> {
    private final Collection<Converter> converters;
    public ModelBuildEventListener(Collection<Converter> converters) {
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
        for (int i = 0; i < stringList.size(); i++) {
            ExcelColumnProperty columnProperty = excelHeadProperty.getExcelColumnProperty(i);
            if (columnProperty != null) {
                Object value = convertValue(stringList.get(i), columnProperty);
                if (value != null) {
                    map.put(columnProperty.getField().getName(), value);
                }
            }
        }
        BeanMap.create(resultModel).putAll(map);
        return resultModel;
    }

    private Object convertValue(CellData cellData, ExcelColumnProperty columnProperty) {

//        columnProperty.getField().getClass(), cellData.getType();


        for (Converter c : converters) {
//            c.convertToJavaData(cellData,columnProperty);

            if (c.support(columnProperty)) {
                return c.convert(value, columnProperty);
            }
        }
        return null;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
