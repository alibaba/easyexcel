package com.alibaba.excel.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ConverterKeyBuild;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.read.metadata.holder.ReadHolder;
import com.alibaba.excel.read.metadata.holder.ReadSheetHolder;

/**
 * Converting objects
 *
 * @author Jiaju Zhuang
 **/
public class ConverterUtils {

    private ConverterUtils() {}

    /**
     * Convert it into a String map
     *
     * @param cellDataMap
     * @param context
     * @return
     */
    public static Map<Integer, String> convertToStringMap(Map<Integer, CellData<?>> cellDataMap, AnalysisContext context) {
        Map<Integer, String> stringMap = MapUtils.newHashMapWithExpectedSize(cellDataMap.size());
        ReadSheetHolder readSheetHolder = context.readSheetHolder();
        int index = 0;
        for (Map.Entry<Integer, CellData<?>> entry : cellDataMap.entrySet()) {
            Integer key = entry.getKey();
            CellData<?> cellData = entry.getValue();
            while (index < key) {
                stringMap.put(index, null);
                index++;
            }
            index++;
            if (cellData.getType() == CellDataTypeEnum.EMPTY) {
                stringMap.put(key, null);
                continue;
            }
            Converter<?> converter =
                readSheetHolder.converterMap().get(ConverterKeyBuild.buildKey(String.class, cellData.getType()));
            if (converter == null) {
                throw new ExcelDataConvertException(context.readRowHolder().getRowIndex(), key, cellData, null,
                    "Converter not found, convert " + cellData.getType() + " to String");
            }
            try {
                stringMap.put(key,
                    (String)(converter.convertToJavaData(cellData, null, readSheetHolder)));
            } catch (Exception e) {
                throw new ExcelDataConvertException(context.readRowHolder().getRowIndex(), key, cellData, null,
                    "Convert data " + cellData + " to String error ", e);
            }
        }
        return stringMap;
    }

    /**
     * Convert it into a Java object
     *
     * @param cellData
     * @param field
     * @param contentProperty
     * @param converterMap
     * @param readSheetHolder
     * @param rowIndex
     * @param columnIndex
     * @return
     */
    public static Object convertToJavaObject(CellData<?> cellData, Field field, ExcelContentProperty contentProperty,
        Map<String, Converter<?>> converterMap, ReadSheetHolder readSheetHolder, Integer rowIndex,
        Integer columnIndex) {
        Class<?> clazz;
        if (field == null) {
            clazz = String.class;
        } else {
            clazz = field.getType();
        }
        if (clazz == CellData.class) {
            Type type = field.getGenericType();
            Class<?> classGeneric;
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType)type;
                classGeneric = (Class<?>)parameterizedType.getActualTypeArguments()[0];
            } else {
                classGeneric = String.class;
            }
            CellData<Object> cellDataReturn = new CellData<Object>(cellData);
            cellDataReturn.setData(doConvertToJavaObject(cellData, classGeneric, contentProperty, converterMap,
                readSheetHolder, rowIndex, columnIndex));
            return cellDataReturn;
        }
        return doConvertToJavaObject(cellData, clazz, contentProperty, converterMap, readSheetHolder, rowIndex,
            columnIndex);
    }

    /**
     * @param cellData
     * @param clazz
     * @param contentProperty
     * @param converterMap
     * @param readSheetHolder
     * @param rowIndex
     * @param columnIndex
     * @return
     */
    private static Object doConvertToJavaObject(CellData<?> cellData, Class<?> clazz,
        ExcelContentProperty contentProperty,
        Map<String, Converter<?>> converterMap, ReadSheetHolder readSheetHolder, Integer rowIndex,
        Integer columnIndex) {
        Converter<?> converter = null;
        if (contentProperty != null) {
            converter = contentProperty.getConverter();
        }
        if (converter == null) {
            converter = converterMap.get(ConverterKeyBuild.buildKey(clazz, cellData.getType()));
        }
        if (converter == null) {
            throw new ExcelDataConvertException(rowIndex, columnIndex, cellData, contentProperty,
                "Converter not found, convert " + cellData.getType() + " to " + clazz.getName());
        }
        try {
            return converter.convertToJavaData(cellData, contentProperty, readSheetHolder);
        } catch (Exception e) {
            throw new ExcelDataConvertException(rowIndex, columnIndex, cellData, contentProperty,
                "Convert data " + cellData + " to " + clazz + " error ", e);
        }
    }
}
