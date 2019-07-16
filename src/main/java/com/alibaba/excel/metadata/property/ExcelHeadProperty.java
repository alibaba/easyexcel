package com.alibaba.excel.metadata.property;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import com.alibaba.excel.enums.HeadKindEnum;
import com.alibaba.excel.exception.ExcelGenerateException;
import com.alibaba.excel.metadata.CellRange;
import com.alibaba.excel.metadata.Head;

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
    private RowHeightProperty headRowHeightProperty;
    private RowHeightProperty contentRowHeightProperty;

    public ExcelHeadProperty(Class headClazz, List<List<String>> head, Boolean convertAllFiled) {
        this.headClazz = headClazz;
        headMap = new TreeMap<Integer, Head>();
        contentPropertyMap = new TreeMap<Integer, ExcelContentProperty>();
        headKind = HeadKindEnum.NONE;
        headRowNumber = 0;
        if (head != null && !head.isEmpty()) {
            for (int i = 0; i < head.size(); i++) {
                headMap.put(i, new Head(i, null, head.get(i)));
                contentPropertyMap.put(i, null);
            }
            headKind = HeadKindEnum.STRING;
        } else {
            // convert headClazz to head
            initColumnProperties(convertAllFiled);
        }
        initHeadRowNumber();
        LOGGER.info("The initialization sheet/table 'ExcelHeadProperty' is complete , head kind is {}", headKind);
    }

    public static ExcelHeadProperty buildExcelHeadProperty(ExcelHeadProperty excelHeadProperty, Class clazz,
        List<String> headOneRow) {
        if (excelHeadProperty == null) {
            return new ExcelHeadProperty(clazz, new ArrayList<List<String>>(), false);
        }
        if (headOneRow != null) {
            excelHeadProperty.appendOneRow(headOneRow);
        }
        return excelHeadProperty;
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
            fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            // Get the parent class and give it to yourself
            tempClass = tempClass.getSuperclass();
        }

        // Screening of field
        List<Field> defaultFieldList = new ArrayList<Field>();
        Map<Integer, Field> customFiledMap = new TreeMap<Integer, Field>();
        for (Field field : fieldList) {
            ExcelIgnore excelIgnore = field.getAnnotation(ExcelIgnore.class);
            if (excelIgnore != null) {
                continue;
            }
            ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
            if (excelProperty == null && !convertAllFiled) {
                continue;
            }
            if (excelProperty == null || excelProperty.index() < 0) {
                defaultFieldList.add(field);
                return;
            }
            if (customFiledMap.containsKey(excelProperty.index())) {
                throw new ExcelGenerateException("The index of " + customFiledMap.get(excelProperty.index()).getName()
                    + " and " + field.getName() + " must be inconsistent");
            }
            customFiledMap.put(excelProperty.index(), field);
        }

        HeadStyle headStyle = (HeadStyle)headClazz.getAnnotation(HeadStyle.class);
        ContentStyle contentStyle = (ContentStyle)headClazz.getAnnotation(ContentStyle.class);
        ColumnWidth columnWidth = (ColumnWidth)headClazz.getAnnotation(ColumnWidth.class);
        this.headRowHeightProperty =
            RowHeightProperty.build((HeadRowHeight)headClazz.getAnnotation(HeadRowHeight.class));
        this.contentRowHeightProperty =
            RowHeightProperty.build((ContentRowHeight)headClazz.getAnnotation(ContentRowHeight.class));

        int index = 0;
        for (Field field : defaultFieldList) {
            while (customFiledMap.containsKey(index)) {
                initOneColumnProperty(index, customFiledMap.get(index), headStyle, contentStyle, columnWidth);
                customFiledMap.remove(index);
                index++;
            }
            initOneColumnProperty(index, field, headStyle, contentStyle, columnWidth);
            index++;
        }
        for (Map.Entry<Integer, Field> entry : customFiledMap.entrySet()) {
            initOneColumnProperty(index, entry.getValue(), headStyle, contentStyle, columnWidth);
            index++;
        }
        headKind = HeadKindEnum.CLASS;
    }

    private void initOneColumnProperty(int index, Field field, HeadStyle parentHeadStyle,
        ContentStyle parentContentStyle, ColumnWidth parentColumnWidth) {

        ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
        List<String> tmpHeadList = new ArrayList<String>();
        if (excelProperty != null) {
            tmpHeadList = Arrays.asList(excelProperty.value());
        } else {
            tmpHeadList.add(field.getName());
        }
        Head head = new Head(index, field.getName(), tmpHeadList);
        HeadStyle headStyle = field.getAnnotation(HeadStyle.class);
        if (headStyle == null) {
            headStyle = parentHeadStyle;
        }
        head.setCellStyleProperty(CellStyleProperty.build(headStyle));

        ColumnWidth columnWidth = field.getAnnotation(ColumnWidth.class);
        if (columnWidth == null) {
            columnWidth = parentColumnWidth;
        }
        head.setColumnWidthProperty(ColumnWidthProperty.build(columnWidth));

        ExcelContentProperty excelContentProperty = new ExcelContentProperty();
        excelContentProperty.setHead(head);
        ContentStyle contentStyle = field.getAnnotation(ContentStyle.class);
        if (contentStyle == null) {
            contentStyle = parentContentStyle;
        }
        excelContentProperty.setCellStyleProperty(CellStyleProperty.build(contentStyle));

        excelContentProperty
            .setDateTimeFormatProperty(DateTimeFormatProperty.build(field.getAnnotation(DateTimeFormat.class)));

        excelContentProperty
            .setNumberFormatProperty(NumberFormatProperty.build(field.getAnnotation(NumberFormat.class)));
        headMap.put(index, head);
        contentPropertyMap.put(index, excelContentProperty);
    }

    /**
     * Add one more head under the last head
     */
    public void appendOneRow(List<String> row) {
        for (int i = 0; i < row.size(); i++) {
            String rowData = row.get(i);
            // join
            if (headMap.containsKey(i)) {
                headMap.get(i).getHeadNameList().add(rowData);
            } else {
                // create and join
                int index = ((TreeMap<Integer, Head>)headMap).lastKey() + 1;
                headMap.put(index, new Head(i, null, rowData));
            }
        }
        initHeadRowNumber();
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

    public RowHeightProperty getHeadRowHeightProperty() {
        return headRowHeightProperty;
    }

    public void setHeadRowHeightProperty(RowHeightProperty headRowHeightProperty) {
        this.headRowHeightProperty = headRowHeightProperty;
    }

    public RowHeightProperty getContentRowHeightProperty() {
        return contentRowHeightProperty;
    }

    public void setContentRowHeightProperty(RowHeightProperty contentRowHeightProperty) {
        this.contentRowHeightProperty = contentRowHeightProperty;
    }

    /**
     * Calculate all cells that need to be merged
     *
     * @return cells that need to be merged
     */
    public List<CellRange> headCellRangeList() {
        List<CellRange> cellRangeList = new ArrayList<CellRange>();
        int i = 0;
        for (Map.Entry<Integer, Head> entry : headMap.entrySet()) {
            Head head = entry.getValue();
            List<String> columnValues = head.getHeadNameList();
            for (int j = 0; j < columnValues.size(); j++) {
                int lastRow = getLastRangNum(j, columnValues.get(j), columnValues);
                int lastColumn = getLastRangNum(i, columnValues.get(j), head.getHeadNameList());
                if ((lastRow > j || lastColumn > i) && lastRow >= 0 && lastColumn >= 0) {
                    cellRangeList.add(new CellRange(j, lastRow, i, lastColumn));
                }
            }
            i++;
        }
        return cellRangeList;
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
