package com.alibaba.excel.write.metadata.holder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ConverterKey;
import com.alibaba.excel.enums.HeadKindEnum;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.read.metadata.read.ReadConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.metadata.property.ExcelHeadProperty;
import com.alibaba.excel.util.StringUtils;

/**
 * TODO
 *
 * @author 罗成
 **/
public class UTils {


    private void buildHead(AnalysisContext analysisContext, List<CellData> cellDataList) {
        if (!HeadKindEnum.CLASS.equals(analysisContext.currentConfiguration().excelHeadProperty().getHeadKind())) {
            return;
        }
        List<String> dataList = (List<String>)buildStringList(cellDataList, analysisContext.currentConfiguration());
        ExcelHeadProperty excelHeadPropertyData = analysisContext.currentConfiguration().excelHeadProperty();
        Map<Integer, Head> headMapData = excelHeadPropertyData.getHeadMap();
        Map<Integer, ExcelContentProperty> contentPropertyMapData = excelHeadPropertyData.getContentPropertyMap();
        Map<Integer, Head> tmpHeadMap = new HashMap<Integer, Head>(headMapData.size() * 4 / 3 + 1);
        Map<Integer, ExcelContentProperty> tmpContentPropertyMap =
            new HashMap<Integer, ExcelContentProperty>(contentPropertyMapData.size() * 4 / 3 + 1);
        for (Map.Entry<Integer, Head> entry : headMapData.entrySet()) {
            Head headData = entry.getValue();
            if (headData.getForceIndex()) {
                tmpHeadMap.put(entry.getKey(), headData);
                tmpContentPropertyMap.put(entry.getKey(), contentPropertyMapData.get(entry.getKey()));
                continue;
            }
            String headName = headData.getHeadNameList().get(0);
            for (int i = 0; i < dataList.size(); i++) {
                String headString = dataList.get(i);
                if (StringUtils.isEmpty(headString)) {
                    continue;
                }
                if (analysisContext.currentSheetHolder().getAutoTrim()) {
                    headString = headString.trim();
                }
                if (headName.equals(headString)) {
                    headData.setColumnIndex(i);
                    tmpHeadMap.put(i, headData);
                    tmpContentPropertyMap.put(i, contentPropertyMapData.get(entry.getKey()));
                    break;
                }
            }
        }
        excelHeadPropertyData.setHeadMap(tmpHeadMap);
        excelHeadPropertyData.setContentPropertyMap(tmpContentPropertyMap);
    }

    private Object buildStringList(List<CellData> data, ReadConfiguration readConfiguration) {
        List<String> list = new ArrayList<String>();
        for (CellData cellData : data) {
            Converter converter = readConfiguration.readConverterMap()
                .get(ConverterKey.buildConverterKey(String.class, cellData.getType()));
            if (converter == null) {
                throw new ExcelDataConvertException(
                    "Converter not found, convert " + cellData.getType() + " to String");
            }
            try {
                list.add((String)(converter.convertToJavaData(cellData, null)));
            } catch (Exception e) {
                throw new ExcelDataConvertException("Convert data " + cellData + " to String error ", e);
            }
        }
        return list;
    }


}
