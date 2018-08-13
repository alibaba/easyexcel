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

/**
 * 表头信息
 *
 * @author jipengfei
 */
public class ExcelHeadProperty {

    /**
     * 表头数据对应的Class
     */
    private Class<? extends BaseRowModel> headClazz;

    /**
     * 表头名称
     */
    private List<List<String>> head = new ArrayList<List<String>>();

    /**
     * Excel每列表头数据
     */
    private List<ExcelColumnProperty> columnPropertyList = new ArrayList<ExcelColumnProperty>();

    /**
     * key:Excel列号，value:表头数据
     */
    private Map<Integer, ExcelColumnProperty> excelColumnPropertyMap1 = new HashMap<Integer, ExcelColumnProperty>();

    public ExcelHeadProperty(Class<? extends BaseRowModel> headClazz, List<List<String>> head) {
        this.headClazz = headClazz;
        this.head = head;
        initColumnProperties();
    }

    /**
     * 初始化每列
     */
    private void initColumnProperties() {
        if (this.headClazz != null) {
            Field[] fields = this.headClazz.getDeclaredFields();
            List<List<String>> headList = new ArrayList<List<String>>();
            for (Field f : fields) {
                initOneColumnProperty(f);
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

    /**
     * 初始化一列
     *
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
     * 将表头的一行数据，转换为一列一列形式，组成表头
     *
     * @param row 表头中的一行数据
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
     * 根据Excel中的列号，获取Excel的表头信息
     *
     * @param columnNum 列号
     * @return ExcelColumnProperty
     */
    public ExcelColumnProperty getExcelColumnProperty(int columnNum) {
        ExcelColumnProperty excelColumnProperty = excelColumnPropertyMap1.get(columnNum);
        if (excelColumnProperty == null) {
            if (head != null && head.size() > columnNum) {
                List<String> columnHead = head.get(columnNum);
                for (ExcelColumnProperty columnProperty : columnPropertyList) {
                    if (headEquals(columnHead, columnProperty.getHead())) {
                        return columnProperty;
                    }
                }
            }
        }
        return excelColumnProperty;
    }

    /**
     * 根据Excel中的列号，获取Excel的表头信息
     *
     * @param columnNum 列号
     * @return ExcelColumnProperty
     */
    public ExcelColumnProperty getExcelColumnProperty1(int columnNum) {
        return excelColumnPropertyMap1.get(columnNum);

    }

    /**
     * 判断表头是否相同
     *
     * @param columnHead
     * @param head
     * @return
     */
    private boolean headEquals(List<String> columnHead, List<String> head) {
        boolean result = true;
        if (columnHead == null || head == null || columnHead.size() != head.size()) {
            return false;
        } else {
            for (int i = 0; i < head.size(); i++) {
                if (!head.get(i).equals(columnHead.get(i))) {
                    result = false;
                    break;
                }
            }
        }
        return result;
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

    public List<CellRange> getCellRangeModels() {
        List<CellRange> rangs = new ArrayList<CellRange>();
        for (int i = 0; i < head.size(); i++) {
            List<String> columnvalues = head.get(i);
            for (int j = 0; j < columnvalues.size(); j++) {
                int lastRow = getLastRangRow(j, columnvalues.get(j), columnvalues);
                int lastColumn = getLastRangColumn(columnvalues.get(j), getHeadByRowNum(j), i);
                if (lastRow >= 0 && lastColumn >= 0 && (lastRow > j || lastColumn > i)) {
                    rangs.add(new CellRange(j, lastRow, i, lastColumn));
                }

            }
        }
        return rangs;
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
     * @param value
     * @param headByRowNum
     * @param i
     * @return
     */
    private int getLastRangColumn(String value, List<String> headByRowNum, int i) {
        if (headByRowNum.indexOf(value) < i) {
            return -1;
        } else {
            return headByRowNum.lastIndexOf(value);
        }
    }

    private int getLastRangRow(int j, String value, List<String> columnvalue) {

        if (columnvalue.indexOf(value) < j) {
            return -1;
        }
        if (value != null && value.equals(columnvalue.get(columnvalue.size() - 1))) {
            return getRowNum() - 1;
        } else {
            return columnvalue.lastIndexOf(value);
        }
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
