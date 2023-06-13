package com.alibaba.excel.write.executor;

import java.util.*;

import com.alibaba.excel.context.WriteContext;
import com.alibaba.excel.enums.HeadKindEnum;
import com.alibaba.excel.metadata.FieldCache;
import com.alibaba.excel.metadata.FieldWrapper;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.BeanMapUtils;
import com.alibaba.excel.util.ClassUtils;
import com.alibaba.excel.util.FieldUtils;
import com.alibaba.excel.util.WorkBookUtil;
import com.alibaba.excel.util.WriteHandlerUtils;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.handler.context.RowWriteHandlerContext;
import com.alibaba.excel.write.metadata.*;
import com.alibaba.excel.write.metadata.holder.WriteHolder;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;

import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.cglib.beans.BeanMap;

/**
 * Add the data into excel
 *
 * @author Jiaju Zhuang
 */
public class ExcelWriteAddExecutor extends AbstractExcelWriteExecutor {

    public ExcelWriteAddExecutor(WriteContext writeContext) {
        super(writeContext);
    }

    public void add(Collection<?> data) {
        if (CollectionUtils.isEmpty(data)) {
            data = new ArrayList<>();
        }
        WriteSheetHolder writeSheetHolder = writeContext.writeSheetHolder();
        int newRowIndex = writeSheetHolder.getNewRowIndexAndStartDoWrite();
        if (writeSheetHolder.isNew() && !writeSheetHolder.getExcelWriteHeadProperty().hasHead()) {
            newRowIndex += writeContext.currentWriteHolder().relativeHeadRowIndex();
        }
        int relativeRowIndex = 0;
        for (Object oneRowData : data) {
            int lastRowIndex = relativeRowIndex + newRowIndex;
            int addRow = addOneRowOfDataToExcel(oneRowData, lastRowIndex, relativeRowIndex, null);
            relativeRowIndex = relativeRowIndex + addRow;
        }
    }

    private int addOneRowOfDataToExcel(Object oneRowData, int rowIndex, int relativeRowIndex, RowDataInfo currentRowInfo) {
        if (oneRowData == null) {
            return 0;
        }
        RowWriteHandlerContext rowWriteHandlerContext = WriteHandlerUtils.createRowWriteHandlerContext(writeContext,
            rowIndex, relativeRowIndex, Boolean.FALSE);
        WriteHandlerUtils.beforeRowCreate(rowWriteHandlerContext);

        Row row = WorkBookUtil.createRow(writeContext.writeSheetHolder().getSheet(), rowIndex);
        rowWriteHandlerContext.setRow(row);

        WriteHandlerUtils.afterRowCreate(rowWriteHandlerContext);
        List<WriteRowStyleDataGroup> nextData = null;
        if (oneRowData instanceof Collection<?>) {
            addBasicTypeToExcel(new CollectionRowData((Collection<?>) oneRowData), row, rowIndex, relativeRowIndex);
        } else if (oneRowData instanceof Map) {
            addBasicTypeToExcel(new MapRowData((Map<Integer, ?>) oneRowData), row, rowIndex, relativeRowIndex);
        } else {
            nextData = addJavaObjectToExcel(oneRowData, row, rowIndex, relativeRowIndex, currentRowInfo);
        }
        WriteHandlerUtils.afterRowDispose(rowWriteHandlerContext);
        int subData = addSubRowData(rowIndex, nextData, currentRowInfo);

        return 1 + subData;
    }

    /**
     * Write nested sub row data
     *
     * @param startIndex    row start index
     * @param subData       sub data
     * @param parentRowInfo parent row info
     * @return write count
     */
    private int addSubRowData(int startIndex, List<WriteRowStyleDataGroup> subData, RowDataInfo parentRowInfo) {
        if (subData == null || subData.isEmpty()) {
            return 0;
        }

        int subRowIndex = startIndex;
        for (WriteRowStyleDataGroup dataGroup : subData) {
            if (dataGroup.getData().isEmpty()) {
                continue;
            }
            Integer currentDepth = Optional.ofNullable(parentRowInfo).map(RowDataInfo::getDepth).orElse(0);
            RowDataInfo newRowDataInfo = new RowDataInfo();
            newRowDataInfo.setDepth(currentDepth + 1);
            newRowDataInfo.setRowStyle(dataGroup.getWriteCellStyle());

            for (Object nextDatum : dataGroup.getData()) {
                int currentIndex = subRowIndex + 1;
                int addIndex = addOneRowOfDataToExcel(nextDatum, currentIndex, currentIndex, newRowDataInfo);
                setGroup(currentIndex, writeContext, newRowDataInfo);
                subRowIndex = subRowIndex + addIndex;
            }
        }
        return subRowIndex - startIndex;
    }

    /**
     * 对于 SXSSF 来说 存在一个固定大小的缓冲区(100), 生产的excel行数据将会放入这个缓冲区中，当写入的数据超过缓冲区大小的时候将会把
     * 之前的数据刷写到硬盘中去  如: [1,2,3...100] -> add(101) -> [2,3,4...101], 而group去设置组范围是设置的缓冲区中的数据，上述
     * 数据如果我这时候设置了 [1,101] 的group， 那么数据 [1] 将不会被设置，因为他早已不在缓冲区中
     * 可参考：  org.apache.poi.xssf.streaming.SXSSFSheet#groupRow
     * <p>
     * 为了解决这个问题，由程序自己去设置每一条数据的权重，所以每次都是去设置一条数据，可以认为每次执行 setGroup 之后会将指定数据的
     * 权重+1， 最终权重一样的连续数据将会被设置成一个组
     *
     */
    private void setGroup(int subRowIndex, WriteContext writeContext, RowDataInfo newRowDataInfo) {
        // 给新添加的数据进行分组
        Sheet sheet = writeContext.writeSheetHolder().getSheet();
        int depth = newRowDataInfo.getDepth();
        for (int i = 0; i < depth; i++) {
            sheet.groupRow(subRowIndex, subRowIndex);
        }
        sheet.setRowSumsBelow(false);
    }


    private void addBasicTypeToExcel(RowData oneRowData, Row row, int rowIndex, int relativeRowIndex) {
        if (oneRowData.isEmpty()) {
            return;
        }
        Map<Integer, Head> headMap = writeContext.currentWriteHolder().excelWriteHeadProperty().getHeadMap();
        int dataIndex = 0;
        int maxCellIndex = -1;
        for (Map.Entry<Integer, Head> entry : headMap.entrySet()) {
            if (dataIndex >= oneRowData.size()) {
                return;
            }
            int columnIndex = entry.getKey();
            Head head = entry.getValue();
            doAddBasicTypeToExcel(oneRowData, head, row, rowIndex, relativeRowIndex, dataIndex++, columnIndex);
            maxCellIndex = Math.max(maxCellIndex, columnIndex);
        }
        // Finish
        if (dataIndex >= oneRowData.size()) {
            return;
        }
        // fix https://github.com/alibaba/easyexcel/issues/1702
        // If there is data, it is written to the next cell
        maxCellIndex++;

        int size = oneRowData.size() - dataIndex;
        for (int i = 0; i < size; i++) {
            doAddBasicTypeToExcel(oneRowData, null, row, rowIndex, relativeRowIndex, dataIndex++, maxCellIndex++);
        }
    }

    private void doAddBasicTypeToExcel(RowData oneRowData, Head head, Row row, int rowIndex, int relativeRowIndex,
                                       int dataIndex, int columnIndex) {
        ExcelContentProperty excelContentProperty = ClassUtils.declaredExcelContentProperty(null,
            writeContext.currentWriteHolder().excelWriteHeadProperty().getHeadClazz(),
            head == null ? null : head.getFieldName(), writeContext.currentWriteHolder());

        CellWriteHandlerContext cellWriteHandlerContext = WriteHandlerUtils.createCellWriteHandlerContext(writeContext,
            row, rowIndex, head, columnIndex, relativeRowIndex, Boolean.FALSE, excelContentProperty);
        WriteHandlerUtils.beforeCellCreate(cellWriteHandlerContext);

        Cell cell = WorkBookUtil.createCell(row, columnIndex);
        cellWriteHandlerContext.setCell(cell);

        WriteHandlerUtils.afterCellCreate(cellWriteHandlerContext);

        cellWriteHandlerContext.setOriginalValue(oneRowData.get(dataIndex));
        cellWriteHandlerContext.setOriginalFieldClass(
            FieldUtils.getFieldClass(cellWriteHandlerContext.getOriginalValue()));
        converterAndSet(cellWriteHandlerContext);

        WriteHandlerUtils.afterCellDispose(cellWriteHandlerContext);
    }

    private List<WriteRowStyleDataGroup> addJavaObjectToExcel(Object oneRowData, Row row, int rowIndex, int relativeRowIndex, RowDataInfo rowDataInfo) {
        List<WriteRowStyleDataGroup> subData = new ArrayList<>();
        WriteHolder currentWriteHolder = writeContext.currentWriteHolder();
        BeanMap beanMap = BeanMapUtils.create(oneRowData);
        // Bean the contains of the Map Key method with poor performance,So to create a keySet here
        Set<String> beanKeySet = new HashSet<>(beanMap.keySet());
        Set<String> beanMapHandledSet = new HashSet<>();
        int maxCellIndex = -1;
        // If it's a class it needs to be cast by type
        if (HeadKindEnum.CLASS.equals(writeContext.currentWriteHolder().excelWriteHeadProperty().getHeadKind())) {
            Map<Integer, Head> headMap = writeContext.currentWriteHolder().excelWriteHeadProperty().getHeadMap();
            for (Map.Entry<Integer, Head> entry : headMap.entrySet()) {
                int columnIndex = entry.getKey();
                Head head = entry.getValue();
                String name = head.getFieldName();
                if (!beanKeySet.contains(name)) {
                    continue;
                }
                // 打了 @ExcelSub 注解的列，这个数据将平铺
                if (head.isSubData()) {
                    beanMapHandledSet.add(name);
                    WriteCellStyle writeCellStyle = groupWriteCellStyle(head, rowDataInfo);
                    WriteRowStyleDataGroup writeRowStyleDataGroup = new WriteRowStyleDataGroup((Collection<Object>) beanMap.get(name), writeCellStyle);
                    subData.add(writeRowStyleDataGroup);
                    continue;
                }

                ExcelContentProperty excelContentProperty = ClassUtils.declaredExcelContentProperty(beanMap,
                    currentWriteHolder.excelWriteHeadProperty().getHeadClazz(), name, currentWriteHolder);
                CellWriteHandlerContext cellWriteHandlerContext = WriteHandlerUtils.createCellWriteHandlerContext(
                    writeContext, row, rowIndex, head, columnIndex, relativeRowIndex, Boolean.FALSE,
                    excelContentProperty);

                cellWriteHandlerContext.setOriginalFieldClass(head.getField().getType());
                cellWriteHandlerContext.setOriginalValue(beanMap.get(name));

                cellWrite(cellWriteHandlerContext, row, columnIndex, rowDataInfo);

                beanMapHandledSet.add(name);
                maxCellIndex = Math.max(maxCellIndex, columnIndex);
            }
        }
        // Finish
        if (beanMapHandledSet.size() == beanMap.size()) {
            return subData;
        }
        maxCellIndex++;

        FieldCache fieldCache = ClassUtils.declaredFields(oneRowData.getClass(), writeContext.currentWriteHolder());
        for (Map.Entry<Integer, FieldWrapper> entry : fieldCache.getSortedFieldMap().entrySet()) {
            FieldWrapper field = entry.getValue();
            String fieldName = field.getFieldName();
            boolean uselessData = !beanKeySet.contains(fieldName) || beanMapHandledSet.contains(fieldName);
            if (uselessData) {
                continue;
            }
            Object value = beanMap.get(fieldName);
            ExcelContentProperty excelContentProperty = ClassUtils.declaredExcelContentProperty(beanMap,
                currentWriteHolder.excelWriteHeadProperty().getHeadClazz(), fieldName, currentWriteHolder);
            CellWriteHandlerContext cellWriteHandlerContext = WriteHandlerUtils.createCellWriteHandlerContext(
                writeContext, row, rowIndex, null, maxCellIndex, relativeRowIndex, Boolean.FALSE, excelContentProperty);

            cellWriteHandlerContext.setOriginalFieldClass(FieldUtils.getFieldClass(beanMap, fieldName, value));
            cellWriteHandlerContext.setOriginalValue(value);

            cellWrite(cellWriteHandlerContext, row, maxCellIndex, rowDataInfo);
            maxCellIndex++;
        }
        return subData;
    }

    private void cellWrite(CellWriteHandlerContext cellWriteHandlerContext, Row row, int columnIndex, RowDataInfo rowDataInfo) {

        WriteHandlerUtils.beforeCellCreate(cellWriteHandlerContext);
        Cell cell = WorkBookUtil.createCell(row, columnIndex);
        cellWriteHandlerContext.setCell(cell);
        WriteHandlerUtils.afterCellCreate(cellWriteHandlerContext);
        converterAndSet(cellWriteHandlerContext);
        mergeRowStyle(cellWriteHandlerContext, rowDataInfo);
        WriteHandlerUtils.afterCellDispose(cellWriteHandlerContext);
    }

    private WriteCellStyle groupWriteCellStyle(Head head, RowDataInfo rowDataInfo) {

        if (head.getSubFillForegroundColors() == null) {
            return null;
        }

        WriteCellStyle result = Optional.ofNullable(WriteCellStyle.build(head.getHeadStyleProperty(), head.getHeadFontProperty()))
            .orElse(new WriteCellStyle());

        Integer depth = Optional.ofNullable(rowDataInfo)
            .map(RowDataInfo::getDepth).orElse(0);


        // 获取下一个颜色
        Short color = head.nextFillForegroundColor(depth);

        result.setFillForegroundColor(color);
        result.setFillPatternType(FillPatternType.SOLID_FOREGROUND);

        return result;
    }

    private void mergeRowStyle(CellWriteHandlerContext cellWriteHandlerContext, RowDataInfo rowDataInfo) {
        if (rowDataInfo == null) {
            return;
        }
        WriteCellStyle rowStyle = rowDataInfo.getRowStyle();
        if (rowStyle == null) {
            return;
        }

        WriteCellStyle target = cellWriteHandlerContext.getFirstCellData().getOrCreateStyle();
        WriteCellStyle.merge(rowStyle, target);
    }


}
