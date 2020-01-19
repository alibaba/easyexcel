package com.alibaba.excel.write.merge;

import com.alibaba.excel.enums.MergeTypeEnum;
import com.alibaba.excel.metadata.CellPoint;
import com.alibaba.excel.metadata.Head;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.HashMap;
import java.util.Map;

/**
 * 文本相同单元格合并，支持行合并和列合并, 行列同事合并
 * @author Lucas
 * @date 2020-1-17
 * @since 1.0.0L
 */
public class TextSameMergeStrategy extends AbstractMergeStrategy {

    /**
     * 临时列存储
     */
    Map<String, CellPoint> col = new HashMap<String, CellPoint>();

    /**
     * 临时行存储
     */
    Map<String, CellPoint> row = new HashMap<String, CellPoint>();

    /**
     * 总列数
     */
    private int totalCols;
    /**
     * 总行数
     */
    private int totalRows;

    private int mergeType;

    /**
     * 构造方法传入 总行数、总列数
     * @param totalCols
     * @param totalRows
     * @param mergeType
     */
    public TextSameMergeStrategy(int totalCols, int totalRows, MergeTypeEnum mergeType) {
        this.totalCols = totalCols;
        this.totalRows = totalRows;
        this.mergeType = mergeType.index;
    }

    @Override
    protected void merge(Sheet sheet, Cell cell, Head head, Integer i) {
        // 行合并
        if (this.mergeType == MergeTypeEnum.HORIZONTAL_MERGE.index) {
            this.horizontalMerge(cell, i, sheet);
        }
        // 列合并
        if (this.mergeType == MergeTypeEnum.VERTICAL_MERGE.index) {
            this.verticalMerge(cell, head, i, sheet);
        }
        // 先行后列合并
        if (this.mergeType == MergeTypeEnum.CENTER_MERGE.index) {
            this.horizontalMerge(cell, i, sheet);
            this.verticalMerge(cell, head, i, sheet);
        }
    }

    /**
     * 水平合并 行合并
     */
    private void horizontalMerge(Cell cell, Integer index, Sheet sheet){
        String key = sheet.getSheetName()+"R"+index;
        CellPoint rowCellPoint = row.get(key);
        if(rowCellPoint == null){
            row.put(key, new CellPoint());
            rowCellPoint = row.get(key);
        }
        rowCellPoint.setStartY(cell.getRowIndex());
        rowCellPoint.setEndY(cell.getRowIndex());

        if(rowCellPoint.getText() != null && rowCellPoint.getText().equals(cell.getStringCellValue())){
            rowCellPoint.setEndX(cell.getColumnIndex());
            if(this.totalCols-1 == cell.getColumnIndex()){
                this.executorMerge(rowCellPoint, sheet);
            }
        }else{
            if(rowCellPoint.getStartX() < rowCellPoint.getEndX()){
                this.executorMerge(rowCellPoint, sheet);
            }
            rowCellPoint.setStartX(cell.getColumnIndex());
        }
        rowCellPoint.setText(cell.getStringCellValue());
    }

    /**
     * 垂直合并 列合并
     */
    public void verticalMerge(Cell cell, Head head, Integer index, Sheet sheet){
        CellPoint cellPoint = col.get(sheet.getSheetName()+"C"+cell.getColumnIndex());
        if(cellPoint == null){
            col.put(sheet.getSheetName()+"C"+cell.getColumnIndex(), new CellPoint());
            cellPoint = col.get(sheet.getSheetName()+"C"+cell.getColumnIndex());
        }
        if(cellPoint.getText() != null && cellPoint.getText().equals(cell.getStringCellValue())){
            cellPoint.setEndX(cell.getColumnIndex());
            cellPoint.setEndY(cell.getRowIndex());
            if(index == totalRows-1){
                this.executorMerge(cellPoint, sheet);
            }
        }else{
            if(cellPoint.getStartY() != cellPoint.getEndY()){
                this.executorMerge(cellPoint, sheet);
            }
            cellPoint.setStartX(cell.getColumnIndex());
            cellPoint.setStartY(cell.getRowIndex());
            cellPoint.setEndX(cell.getColumnIndex());
            cellPoint.setEndY(cell.getRowIndex());
        }
        cellPoint.setText(cell.getStringCellValue());
    }

    /**
     * 执行合并
     * @param sheet sheet的对象
     * @param cellPoint 单元格坐标对象
     */
    private void executorMerge(CellPoint cellPoint, Sheet sheet){
        CellRangeAddress cellRangeAddress = new CellRangeAddress(
                cellPoint.getStartY(),
                cellPoint.getEndY(),
                cellPoint.getStartX(),
                cellPoint.getEndX());
        sheet.addMergedRegionUnsafe(cellRangeAddress);
    }
}
