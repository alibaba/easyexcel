package com.alibaba.excel.write.property;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentLoopMerge;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import com.alibaba.excel.annotation.write.style.OnceAbsoluteMerge;
import com.alibaba.excel.enums.HeadKindEnum;
import com.alibaba.excel.metadata.CellRange;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.Holder;
import com.alibaba.excel.metadata.property.ColumnWidthProperty;
import com.alibaba.excel.metadata.property.ExcelHeadProperty;
import com.alibaba.excel.metadata.property.FontProperty;
import com.alibaba.excel.metadata.property.LoopMergeProperty;
import com.alibaba.excel.metadata.property.OnceAbsoluteMergeProperty;
import com.alibaba.excel.metadata.property.RowHeightProperty;
import com.alibaba.excel.metadata.property.StyleProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Define the header attribute of excel
 *
 * @author jipengfei
 */
@Getter
@Setter
@EqualsAndHashCode
public class ExcelWriteHeadProperty extends ExcelHeadProperty {

    private RowHeightProperty headRowHeightProperty;
    private RowHeightProperty contentRowHeightProperty;
    private OnceAbsoluteMergeProperty onceAbsoluteMergeProperty;

    public ExcelWriteHeadProperty(Holder holder, Class<?> headClazz, List<List<String>> head) {
        super(holder, headClazz, head);
        if (getHeadKind() != HeadKindEnum.CLASS) {
            return;
        }
        this.headRowHeightProperty =
            RowHeightProperty.build(headClazz.getAnnotation(HeadRowHeight.class));
        this.contentRowHeightProperty =
            RowHeightProperty.build(headClazz.getAnnotation(ContentRowHeight.class));
        this.onceAbsoluteMergeProperty =
            OnceAbsoluteMergeProperty.build(headClazz.getAnnotation(OnceAbsoluteMerge.class));

        ColumnWidth parentColumnWidth = headClazz.getAnnotation(ColumnWidth.class);
        HeadStyle parentHeadStyle = headClazz.getAnnotation(HeadStyle.class);
        HeadFontStyle parentHeadFontStyle = headClazz.getAnnotation(HeadFontStyle.class);


        for (Map.Entry<Integer, Head> entry : getHeadMap().entrySet()) {
            Head headData = entry.getValue();
            if (headData == null) {
                throw new IllegalArgumentException(
                    "Passing in the class and list the head, the two must be the same size.");
            }
            Field field = headData.getField();

            ColumnWidth columnWidth = field.getAnnotation(ColumnWidth.class);
            if (columnWidth == null) {
                columnWidth = parentColumnWidth;
            }
            headData.setColumnWidthProperty(ColumnWidthProperty.build(columnWidth));


            HeadStyle headStyle = field.getAnnotation(HeadStyle.class);
            if (headStyle == null) {
                headStyle = parentHeadStyle;
            }
            headData.setHeadStyleProperty(StyleProperty.build(headStyle));

            HeadFontStyle headFontStyle = field.getAnnotation(HeadFontStyle.class);
            if (headFontStyle == null) {
                headFontStyle = parentHeadFontStyle;
            }
            headData.setHeadFontProperty(FontProperty.build(headFontStyle));

            headData.setLoopMergeProperty(LoopMergeProperty.build(field.getAnnotation(ContentLoopMerge.class)));
        }
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
                    String key = k + "-" + j;
                    if (headList.get(k).getHeadNameList().get(j).equals(headName) && !alreadyRangeSet.contains(key)) {
                        alreadyRangeSet.add(key);
                        lastCol = k;
                    } else {
                        break;
                    }
                }
                Set<String> tempAlreadyRangeSet = new HashSet<>();
                outer:
                for (int k = j + 1; k < headNameList.size(); k++) {
                    for (int l = i; l <= lastCol; l++) {
                        String key = l + "-" + k;
                        if (headList.get(l).getHeadNameList().get(k).equals(headName) && !alreadyRangeSet.contains(
                            key)) {
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
                cellRangeList
                    .add(new CellRange(j, lastRow, head.getColumnIndex(), headList.get(lastCol).getColumnIndex()));
            }
        }
        return cellRangeList;
    }
}
