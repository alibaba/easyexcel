package com.alibaba.excel.write.property;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import com.alibaba.excel.enums.HeadKindEnum;
import com.alibaba.excel.metadata.CellRange;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.property.CellStyleProperty;
import com.alibaba.excel.metadata.property.ColumnWidthProperty;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.metadata.property.ExcelHeadProperty;
import com.alibaba.excel.metadata.property.RowHeightProperty;

/**
 * Define the header attribute of excel
 *
 * @author jipengfei
 */
public class ExcelWriteHeadProperty extends ExcelHeadProperty {
    private RowHeightProperty headRowHeightProperty;
    private RowHeightProperty contentRowHeightProperty;

    public ExcelWriteHeadProperty(Class headClazz, List<List<String>> head, Boolean convertAllFiled) {
        super(headClazz, head, convertAllFiled);
        if (getHeadKind() != HeadKindEnum.CLASS) {
            return;
        }
        this.headRowHeightProperty =
            RowHeightProperty.build((HeadRowHeight)headClazz.getAnnotation(HeadRowHeight.class));
        this.contentRowHeightProperty =
            RowHeightProperty.build((ContentRowHeight)headClazz.getAnnotation(ContentRowHeight.class));

        HeadStyle parentHeadStyle = (HeadStyle)headClazz.getAnnotation(HeadStyle.class);
        ContentStyle parentContentStyle = (ContentStyle)headClazz.getAnnotation(ContentStyle.class);
        ColumnWidth parentColumnWidth = (ColumnWidth)headClazz.getAnnotation(ColumnWidth.class);
        for (Map.Entry<Integer, ExcelContentProperty> entry : getContentPropertyMap().entrySet()) {
            Integer index = entry.getKey();
            ExcelContentProperty excelContentPropertyData = entry.getValue();
            Field field = excelContentPropertyData.getField();
            Head headData = getHeadMap().get(index);
            HeadStyle headStyle = field.getAnnotation(HeadStyle.class);
            if (headStyle == null) {
                headStyle = parentHeadStyle;
            }
            headData.setCellStyleProperty(CellStyleProperty.build(headStyle));
            ColumnWidth columnWidth = field.getAnnotation(ColumnWidth.class);
            if (columnWidth == null) {
                columnWidth = parentColumnWidth;
            }
            headData.setColumnWidthProperty(ColumnWidthProperty.build(columnWidth));
            ContentStyle contentStyle = field.getAnnotation(ContentStyle.class);
            if (contentStyle == null) {
                contentStyle = parentContentStyle;
            }
            excelContentPropertyData.setCellStyleProperty(CellStyleProperty.build(contentStyle));
        }

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
        for (Map.Entry<Integer, Head> entry : getHeadMap().entrySet()) {
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
