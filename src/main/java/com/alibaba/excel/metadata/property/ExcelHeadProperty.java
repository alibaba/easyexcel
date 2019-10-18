package com.alibaba.excel.metadata.property;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.converters.AutoConverter;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.HeadKindEnum;
import com.alibaba.excel.exception.ExcelCommonException;
import com.alibaba.excel.exception.ExcelGenerateException;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.util.StringUtils;

/**
 * Define the header attribute of excel
 *
 * @author jipengfei
 */
public class ExcelHeadProperty {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelHeadProperty.class);
    /**
     * Custom class
     */
    private Class headClazz;
    /**
     * The types of head
     */
    private HeadKindEnum headKind;
    /**
     * The number of rows in the line with the most rows
     */
    private int headRowNumber;

    /**
     * Configuration header information
     */
    private Map<Integer, Head> headMap;
    /**
     * Configuration column information
     */
    private Map<Integer, ExcelContentProperty> contentPropertyMap;
    /**
     * Fields ignored
     */
    private Map<String, Field> ignoreMap;

    public ExcelHeadProperty(Class headClazz, List<List<String>> head, Boolean convertAllFiled) {
        this.headClazz = headClazz;
        headMap = new TreeMap<Integer, Head>();
        contentPropertyMap = new TreeMap<Integer, ExcelContentProperty>();
        ignoreMap = new HashMap<String, Field>(16);
        headKind = HeadKindEnum.NONE;
        headRowNumber = 0;
        if (head != null && !head.isEmpty()) {
            for (int i = 0; i < head.size(); i++) {
                headMap.put(i, new Head(i, null, head.get(i), Boolean.FALSE, Boolean.TRUE));
                contentPropertyMap.put(i, null);
            }
            headKind = HeadKindEnum.STRING;
        } else {
            // convert headClazz to head
            initColumnProperties(convertAllFiled);
        }
        initHeadRowNumber();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("The initialization sheet/table 'ExcelHeadProperty' is complete , head kind is {}", headKind);
        }
        if (!hasHead()) {
            LOGGER.warn(
                "The table has no header set and all annotations will not be read.If you want to use annotations, please use set head class in ExcelWriterBuilder/ExcelWriterSheetBuilder/ExcelWriterTableBuilder");
        }
    }

    private void initHeadRowNumber() {
        headRowNumber = 0;
        for (Head head : headMap.values()) {
            List<String> list = head.getHeadNameList();
            if (list != null && list.size() > headRowNumber) {
                headRowNumber = list.size();
            }
        }
        for (Head head : headMap.values()) {
            List<String> list = head.getHeadNameList();
            if (list != null && !list.isEmpty() && list.size() < headRowNumber) {
                int lack = headRowNumber - list.size();
                int last = list.size() - 1;
                for (int i = 0; i < lack; i++) {
                    list.add(list.get(last));
                }
            }
        }
    }

    private void initColumnProperties(Boolean convertAllFiled) {
        if (headClazz == null) {
            return;
        }
        List<Field> fieldList = new ArrayList<Field>();
        Class tempClass = headClazz;
        // When the parent class is null, it indicates that the parent class (Object class) has reached the top
        // level.
        while (tempClass != null) {
            Collections.addAll(fieldList, tempClass.getDeclaredFields());
            // Get the parent class and give it to yourself
            tempClass = tempClass.getSuperclass();
        }

        // Screening of field
        List<Field> defaultFieldList = new ArrayList<Field>();
        Map<Integer, Field> customFiledMap = new TreeMap<Integer, Field>();
        for (Field field : fieldList) {

            ExcelIgnore excelIgnore = field.getAnnotation(ExcelIgnore.class);
            if(Modifier.isStatic(field.getModifiers())&&Modifier.isFinal(field.getModifiers())){
                ignoreMap.put(field.getName(),field);
                continue;
            }
            if(Modifier.isTransient(field.getModifiers())){
                ignoreMap.put(field.getName(),field);
                continue;
            }
            if (excelIgnore != null) {
                ignoreMap.put(field.getName(), field);
                continue;
            }
            ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
            if (excelProperty == null && convertAllFiled != null && !convertAllFiled) {
                ignoreMap.put(field.getName(), field);
                continue;
            }
            if (excelProperty == null || excelProperty.index() < 0) {
                defaultFieldList.add(field);
                continue;
            }
            if (customFiledMap.containsKey(excelProperty.index())) {
                throw new ExcelGenerateException("The index of '" + customFiledMap.get(excelProperty.index()).getName()
                    + "' and '" + field.getName() + "' must be inconsistent");
            }
            customFiledMap.put(excelProperty.index(), field);
        }

        int index = 0;
        for (Field field : defaultFieldList) {
            while (customFiledMap.containsKey(index)) {
                initOneColumnProperty(index, customFiledMap.get(index), Boolean.TRUE);
                customFiledMap.remove(index);
                index++;
            }
            initOneColumnProperty(index, field, Boolean.FALSE);
            index++;
        }
        for (Map.Entry<Integer, Field> entry : customFiledMap.entrySet()) {
            initOneColumnProperty(entry.getKey(), entry.getValue(), Boolean.TRUE);
        }
        headKind = HeadKindEnum.CLASS;
    }

    private void initOneColumnProperty(int index, Field field, Boolean forceIndex) {
        ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
        List<String> tmpHeadList = new ArrayList<String>();
        boolean notForceName = excelProperty == null || excelProperty.value().length <= 0
            || (excelProperty.value().length == 1 && StringUtils.isEmpty((excelProperty.value())[0]));
        if (notForceName) {
            tmpHeadList.add(field.getName());
        } else {
            Collections.addAll(tmpHeadList, excelProperty.value());
        }
        Head head = new Head(index, field.getName(), tmpHeadList, forceIndex, !notForceName);
        ExcelContentProperty excelContentProperty = new ExcelContentProperty();
        if (excelProperty != null) {
            Class<? extends Converter> convertClazz = excelProperty.converter();
            if (convertClazz != AutoConverter.class) {
                try {
                    Converter converter = convertClazz.newInstance();
                    excelContentProperty.setConverter(converter);
                } catch (Exception e) {
                    throw new ExcelCommonException("Can not instance custom converter:" + convertClazz.getName());
                }
            }
        }
        excelContentProperty.setHead(head);
        excelContentProperty.setField(field);
        excelContentProperty
            .setDateTimeFormatProperty(DateTimeFormatProperty.build(field.getAnnotation(DateTimeFormat.class)));
        excelContentProperty
            .setNumberFormatProperty(NumberFormatProperty.build(field.getAnnotation(NumberFormat.class)));
        headMap.put(index, head);
        contentPropertyMap.put(index, excelContentProperty);
    }

    public Class getHeadClazz() {
        return headClazz;
    }

    public void setHeadClazz(Class headClazz) {
        this.headClazz = headClazz;
    }

    public HeadKindEnum getHeadKind() {
        return headKind;
    }

    public void setHeadKind(HeadKindEnum headKind) {
        this.headKind = headKind;
    }

    public boolean hasHead() {
        return headKind != HeadKindEnum.NONE;
    }

    public int getHeadRowNumber() {
        return headRowNumber;
    }

    public void setHeadRowNumber(int headRowNumber) {
        this.headRowNumber = headRowNumber;
    }

    public Map<Integer, Head> getHeadMap() {
        return headMap;
    }

    public void setHeadMap(Map<Integer, Head> headMap) {
        this.headMap = headMap;
    }

    public Map<Integer, ExcelContentProperty> getContentPropertyMap() {
        return contentPropertyMap;
    }

    public void setContentPropertyMap(Map<Integer, ExcelContentProperty> contentPropertyMap) {
        this.contentPropertyMap = contentPropertyMap;
    }

    public Map<String, Field> getIgnoreMap() {
        return ignoreMap;
    }

    public void setIgnoreMap(Map<String, Field> ignoreMap) {
        this.ignoreMap = ignoreMap;
    }
}
