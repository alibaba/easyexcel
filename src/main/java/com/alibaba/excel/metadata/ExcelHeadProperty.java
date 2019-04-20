package com.alibaba.excel.metadata;

import com.alibaba.excel.annotation.ExcelColumnNum;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.FieldIgnore;
import com.alibaba.excel.annotation.SimpleExcel;
import com.alibaba.excel.support.LanguageAdapter;
import com.alibaba.excel.support.LanguageAdapterHolder;

import com.alibaba.excel.util.StringUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Define the header attribute of excel
 *
 * @author jipengfei
 */
public class ExcelHeadProperty {

    /**
     * Custom class
     */
    private Class<? extends BaseRowModel> headClazz;

    /**
     * A two-dimensional array describing the header
     */
    private List<List<String>> head = new ArrayList<List<String>>();

    /**
     * Attributes described by the header
     */
    private List<ExcelColumnProperty> columnPropertyList = new ArrayList<ExcelColumnProperty>();

    /**
     * Attributes described by the header
     */
    private Map<Integer, ExcelColumnProperty> excelColumnPropertyMap1 = new HashMap<Integer, ExcelColumnProperty>();

    public ExcelHeadProperty(Class<? extends BaseRowModel> headClazz, List<List<String>> head) {
        this.headClazz = headClazz;
        this.head = head;
        initColumnProperties();
    }

    /**
     */
    private void initColumnProperties() {
        if (this.headClazz != null) {
            List<Field> fieldList = getFieldList(this.headClazz);
            List<List<String>> headList = new ArrayList<List<String>>();
            for (int i = 0; i < fieldList.size(); i++) {
                Field f = fieldList.get(i);
                initOneColumnProperty(f,i);
            }
            //对列排序
            Collections.sort(columnPropertyList);
            if (head == null || head.size() == 0) {
                for (ExcelColumnProperty excelColumnProperty : columnPropertyList) {
                    headList.add(excelColumnProperty.getHead());
                }
                this.head = headList;
            }
        }
    }

    private List<Field> getFieldList(Class type) {
        List<Field> fieldList = new ArrayList<Field>();
        boolean simpleMode = type.isAnnotationPresent(SimpleExcel.class);
        Class tempClass = type;
        while (tempClass != null && !tempClass.equals(BaseRowModel.class)) {
            List<Field> distField = new ArrayList<Field>();
            for (Field field : tempClass.getDeclaredFields()) {
                if (simpleMode) {
                    if (!Modifier.isStatic(field.getModifiers()) && !Modifier
                        .isFinal(field.getModifiers()) && !field
                        .isAnnotationPresent(FieldIgnore.class)) {
                        distField.add(field);
                    }
                } else {
                    distField.add(field);
                }
            }
            distField.addAll(fieldList);
            //Get the parent class and give it to yourself
            tempClass = tempClass.getSuperclass();
            fieldList = distField;
        }
        return fieldList;
    }

    /**
     * @param f
     */
    private void initOneColumnProperty(Field f,int defaultIndex) {
        ExcelProperty p = f.getAnnotation(ExcelProperty.class);
        ExcelColumnProperty excelHeadProperty = null;
        if (p != null) {
            List<String> headList = translateHead(Arrays.asList(p.value()));
            excelHeadProperty = new ExcelColumnProperty();
            excelHeadProperty.setField(f);
            excelHeadProperty.setHead(headList);
            excelHeadProperty.setIndex(p.index());
            excelHeadProperty.setFormat(p.format());
            excelColumnPropertyMap1.put(p.index(), excelHeadProperty);
        } else if(f.isAnnotationPresent(ExcelColumnNum.class)){
            ExcelColumnNum columnNum = f.getAnnotation(ExcelColumnNum.class);
            if (columnNum != null) {
                excelHeadProperty = new ExcelColumnProperty();
                excelHeadProperty.setField(f);
                excelHeadProperty.setIndex(columnNum.value());
                excelHeadProperty.setFormat(columnNum.format());
                excelColumnPropertyMap1.put(columnNum.value(), excelHeadProperty);
            }
        } else if(headClazz.isAnnotationPresent(SimpleExcel.class)) {
            SimpleExcel annotation = headClazz.getAnnotation(SimpleExcel.class);
            String langPrefix=annotation.langPrefix();
            if(StringUtils.hasText(langPrefix)){
                langPrefix=langPrefix.endsWith(".")?langPrefix:(langPrefix+".");
            }
            List<String> strings = translateHead(Arrays.asList(langPrefix + f.getName()));
            translateHead(strings);
            excelHeadProperty = new ExcelColumnProperty();
            excelHeadProperty.setField(f);
            excelHeadProperty.setHead(strings);
            excelHeadProperty.setIndex(defaultIndex);
            excelHeadProperty.setFormat("");
            excelColumnPropertyMap1.put(defaultIndex, excelHeadProperty);
        }
        if (excelHeadProperty != null) {
            this.columnPropertyList.add(excelHeadProperty);
        }

    }

    private List<String> translateHead( List<String> headList){
        LanguageAdapter languageAdapter = LanguageAdapterHolder.getLanguageAdapter();
        if(languageAdapter!=null){
            List<String> translateList = new ArrayList<String>(headList.size());
            for(String head:headList){
                translateList.add(languageAdapter.translate(head));
            }
            return translateList;
        }
        return headList;
    }

    /**
     *
     */
    public void appendOneRow(List<String> row) {

        for (int i = 0; i < row.size(); i++) {
            List<String> list;
            if (head.size() <= i) {
                list = new ArrayList<String>();
                head.add(list);
            } else {
                list = head.get(0);
            }
            list.add(row.get(i));
        }

    }

    /**
     * @param columnNum
     * @return
     */
    public ExcelColumnProperty getExcelColumnProperty(int columnNum) {
        return excelColumnPropertyMap1.get(columnNum);
    }

    public Class getHeadClazz() {
        return headClazz;
    }

    public void setHeadClazz(Class headClazz) {
        this.headClazz = headClazz;
    }

    public List<List<String>> getHead() {
        return this.head;
    }

    public void setHead(List<List<String>> head) {
        this.head = head;
    }

    public List<ExcelColumnProperty> getColumnPropertyList() {
        return columnPropertyList;
    }

    public void setColumnPropertyList(List<ExcelColumnProperty> columnPropertyList) {
        this.columnPropertyList = columnPropertyList;
    }

    /**
     * Calculate all cells that need to be merged
     *
     * @return cells that need to be merged
     */
    public List<CellRange> getCellRangeModels() {
        List<CellRange> cellRanges = new ArrayList<CellRange>();
        for (int i = 0; i < head.size(); i++) {
            List<String> columnValues = head.get(i);
            for (int j = 0; j < columnValues.size(); j++) {
                int lastRow = getLastRangNum(j, columnValues.get(j), columnValues);
                int lastColumn = getLastRangNum(i, columnValues.get(j), getHeadByRowNum(j));
                if ((lastRow > j || lastColumn > i) && lastRow >= 0 && lastColumn >= 0) {
                    cellRanges.add(new CellRange(j, lastRow, i, lastColumn));
                }
            }
        }
        return cellRanges;
    }

    public List<String> getHeadByRowNum(int rowNum) {
        List<String> l = new ArrayList<String>(head.size());
        for (List<String> list : head) {
            if (list.size() > rowNum) {
                l.add(list.get(rowNum));
            } else {
                l.add(list.get(list.size() - 1));
            }
        }
        return l;
    }

    /**
     * Get the last consecutive string position
     *
     * @param j      current value position
     * @param value  value content
     * @param values values
     * @return the last consecutive string position
     */
    private int getLastRangNum(int j, String value, List<String> values) {
        if (value == null) {
            return -1;
        }
        if (j > 0) {
            String preValue = values.get(j - 1);
            if (value.equals(preValue)) {
                return -1;
            }
        }
        int last = j;
        for (int i = last + 1; i < values.size(); i++) {
            String current = values.get(i);
            if (value.equals(current)) {
                last = i;
            } else {
                // if i>j && !value.equals(current) Indicates that the continuous range is exceeded
                if (i > j) {
                    break;
                }
            }
        }
        return last;

    }

    public int getRowNum() {
        int headRowNum = 0;
        for (List<String> list : head) {
            if (list != null && list.size() > 0) {
                if (list.size() > headRowNum) {
                    headRowNum = list.size();
                }
            }
        }
        return headRowNum;
    }

}
