package com.alibaba.excel.write.property;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.alibaba.excel.converters.ConverterKeyBuild;
import com.alibaba.excel.converters.DefaultConverterLoader;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.enums.HeadKindEnum;
import com.alibaba.excel.metadata.CellRange;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.Holder;
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

    public ExcelWriteHeadProperty(Holder holder, Class headClazz, List<List<String>> head, Boolean convertAllFiled) {
        super(holder, headClazz, head, convertAllFiled);
        if (getHeadKind() != HeadKindEnum.CLASS) {
            return;
        }
        this.headRowHeightProperty =
            RowHeightProperty.build((HeadRowHeight)headClazz.getAnnotation(HeadRowHeight.class));
        this.contentRowHeightProperty =
            RowHeightProperty.build((ContentRowHeight)headClazz.getAnnotation(ContentRowHeight.class));

        ColumnWidth parentColumnWidth = (ColumnWidth)headClazz.getAnnotation(ColumnWidth.class);
        for (Map.Entry<Integer, ExcelContentProperty> entry : getContentPropertyMap().entrySet()) {
            Integer index = entry.getKey();
            ExcelContentProperty excelContentPropertyData = entry.getValue();
            Field field = excelContentPropertyData.getField();
            Head headData = getHeadMap().get(index);
            ColumnWidth columnWidth = field.getAnnotation(ColumnWidth.class);
            if (columnWidth == null) {
                columnWidth = parentColumnWidth;
            }
            headData.setColumnWidthProperty(ColumnWidthProperty.build(columnWidth));

            // If have @NumberFormat, 'NumberStringConverter' is specified by default
            if (excelContentPropertyData.getConverter() == null) {
                NumberFormat numberFormat = field.getAnnotation(NumberFormat.class);
                if (numberFormat != null) {
                    excelContentPropertyData.setConverter(DefaultConverterLoader.loadAllConverter()
                        .get(ConverterKeyBuild.buildKey(field.getType(), CellDataTypeEnum.STRING)));
                }
            }
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
        Set<String> alreadyRangeSet = new HashSet<String>();
        List<Head> headList = new ArrayList<Head>(getHeadMap().values());
        for (int i = 0; i < headList.size(); i++) {
            Head head = headList.get(i);
            List<String> headNameList = head.getHeadNameList();
            for (int j = 0; j < headNameList.size(); j++) {
                if (alreadyRangeSet.contains(i + "-" + j)) {
                    continue;
                }
                alreadyRangeSet.add(i + "-" + j);
                String headName = headNameList.get(j);
                int lastCol = i;
                int lastRow = j;
                for (int k = i + 1; k < headList.size(); k++) {
                    if (headList.get(k).getHeadNameList().get(j).equals(headName)) {
                        alreadyRangeSet.add(k + "-" + j);
                        lastCol = k;
                    } else {
                        break;
                    }
                }
                Set<String> tempAlreadyRangeSet = new HashSet<String>();
                outer:
                for (int k = j + 1; k < headNameList.size(); k++) {
                    for (int l = i; l <= lastCol; l++) {
                        if (headList.get(l).getHeadNameList().get(k).equals(headName)) {
                            tempAlreadyRangeSet.add(l + "-" + k);
                        } else {
                            break outer;
                        }
                    }
                    lastRow = k;
                    alreadyRangeSet.addAll(tempAlreadyRangeSet);
                }
                if (j == lastRow && i == lastCol) {
                    continue;
                }
                cellRangeList.add(new CellRange(j, lastRow, i, lastCol));
            }
        }
        return cellRangeList;
    }
}
