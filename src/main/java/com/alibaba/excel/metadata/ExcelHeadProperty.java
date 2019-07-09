package com.alibaba.excel.metadata;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.excel.annotation.ExcelColumnNum;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.enums.HeadKindEnum;

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
    private List<Head> headList;

    /**
     * Attributes described by the header
     */
    private List<ExcelColumnProperty> columnPropertyList = new ArrayList<ExcelColumnProperty>();

    /**
     * Attributes described by the header
     */
    private Map<Integer, ExcelColumnProperty> excelColumnPropertyMap1 = new HashMap<Integer, ExcelColumnProperty>();
    /**
     * The types of head
     */
    private HeadKindEnum headKind;
    /**
     * The number of rows in the line with the most rows
     */
    private int headRowNumber;

    public ExcelHeadProperty(Class<? extends BaseRowModel> headClazz, List<List<String>> head) {
        this.headClazz = headClazz;
        headList = new ArrayList<Head>();
        headKind = HeadKindEnum.NONE;
        headRowNumber = 0;
        if (head != null && !head.isEmpty()) {
            int index = 0;
            for (List<String> headData : head) {
                headList.add(new Head(index++, null, headData));
            }
            headKind = HeadKindEnum.STRING;
        } else {
            // convert headClazz to head
            initColumnProperties();
        }

        initHeadRowNumber();
    }

    public static ExcelHeadProperty buildExcelHeadProperty(ExcelHeadProperty excelHeadProperty,
        Class<? extends BaseRowModel> clazz, List<String> headOneRow) {
        if (excelHeadProperty == null) {
            return new ExcelHeadProperty(clazz, new ArrayList<List<String>>());
        }
        if (headOneRow != null) {
            excelHeadProperty.appendOneRow(headOneRow);
        }
        return excelHeadProperty;
    }

    private void initHeadRowNumber() {
        headRowNumber = 0;
        for (Head head : headList) {
            List<String> list = head.getHeadNames();
            if (list != null && list.size() > headRowNumber) {
                headRowNumber = list.size();
            }
        }
    }

    /**
     */
    private void initColumnProperties() {
        if (this.headClazz != null) {
            List<Field> fieldList = new ArrayList<Field>();
            Class tempClass = this.headClazz;
            // When the parent class is null, it indicates that the parent class (Object class) has reached the top
            // level.
            while (tempClass != null) {
                fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
                // Get the parent class and give it to yourself
                tempClass = tempClass.getSuperclass();
            }
            for (Field f : fieldList) {
                initOneColumnProperty(f);
            }
            // 对列排序
            Collections.sort(columnPropertyList);

            int index = 0;
            for (ExcelColumnProperty excelColumnProperty : columnPropertyList) {
                if (excelColumnProperty.getHead() != null && excelColumnProperty.getHead().size() > headRowNumber) {
                    headRowNumber = excelColumnProperty.getHead().size();
                }
                headList
                    .add(new Head(index++, excelColumnProperty.getField().getName(), excelColumnProperty.getHead()));
            }
            headKind = HeadKindEnum.CLASS;
        }
    }

    /**
     * @param f
     */
    private void initOneColumnProperty(Field f) {
        ExcelProperty p = f.getAnnotation(ExcelProperty.class);
        ExcelColumnProperty excelHeadProperty = null;
        if (p != null) {
            excelHeadProperty = new ExcelColumnProperty();
            excelHeadProperty.setField(f);
            excelHeadProperty.setHead(Arrays.asList(p.value()));
            excelHeadProperty.setIndex(p.index());
            excelHeadProperty.setFormat(p.format());
            excelColumnPropertyMap1.put(p.index(), excelHeadProperty);
        } else {
            ExcelColumnNum columnNum = f.getAnnotation(ExcelColumnNum.class);
            if (columnNum != null) {
                excelHeadProperty = new ExcelColumnProperty();
                excelHeadProperty.setField(f);
                excelHeadProperty.setIndex(columnNum.value());
                excelHeadProperty.setFormat(columnNum.format());
                excelColumnPropertyMap1.put(columnNum.value(), excelHeadProperty);
            }
        }
        if (excelHeadProperty != null) {
            this.columnPropertyList.add(excelHeadProperty);
        }

    }

    /**
     * Add one more head under the last head
     */
    public void appendOneRow(List<String> row) {
        int headSize = headList.size();
        for (int i = 0; i < row.size(); i++) {
            String rowData = row.get(i);
            // join
            if (i <= headSize) {
                headList.get(i).getHeadNames().add(rowData);
            } else {
                // create and join
                headList.add(new Head(i, null, rowData));
            }
        }
        initHeadRowNumber();
    }

    /**
     * @param columnNum
     * @return
     */
    public ExcelColumnProperty getExcelColumnProperty(int columnNum) {
        return excelColumnPropertyMap1.get(columnNum);
    }

    public Class<? extends BaseRowModel> getHeadClazz() {
        return headClazz;
    }

    public void setHeadClazz(Class<? extends BaseRowModel> headClazz) {
        this.headClazz = headClazz;
    }

    public List<ExcelColumnProperty> getColumnPropertyList() {
        return columnPropertyList;
    }

    public void setColumnPropertyList(List<ExcelColumnProperty> columnPropertyList) {
        this.columnPropertyList = columnPropertyList;
    }

    public List<Head> getHeadList() {
        return headList;
    }

    public void setHeadList(List<Head> headList) {
        this.headList = headList;
    }

    public HeadKindEnum getHeadKind() {
        return headKind;
    }

    public void setHeadKind(HeadKindEnum headKind) {
        this.headKind = headKind;
    }

    public boolean hasHead() {
        return headKind == HeadKindEnum.NONE;
    }

    public int getHeadRowNumber() {
        return headRowNumber;
    }

    public void setHeadRowNumber(int headRowNumber) {
        this.headRowNumber = headRowNumber;
    }

    /**
     * Calculate all cells that need to be merged
     *
     * @return cells that need to be merged
     */
    public List<CellRange> getCellRangeModels() {
        List<CellRange> cellRanges = new ArrayList<CellRange>();
        for (int i = 0; i < headList.size(); i++) {
            List<String> columnValues = headList.get(i).getHeadNames();
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
        List<String> l = new ArrayList<String>(headList.size());
        for (Head head : headList) {
            List<String> list = head.getHeadNames();
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
     * @param j
     *            current value position
     * @param value
     *            value content
     * @param values
     *            values
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
}
