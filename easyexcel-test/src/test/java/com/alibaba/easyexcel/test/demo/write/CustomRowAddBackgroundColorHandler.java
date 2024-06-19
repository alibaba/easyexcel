package com.alibaba.easyexcel.test.demo.write;

import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 为指定行添加背景颜色
 * @author raxcl
 */
public class CustomRowAddBackgroundColorHandler implements CellWriteHandler {
    private final int colorIndex;
    private final List<Integer> rowList;


    public CustomRowAddBackgroundColorHandler(int colorIndex, Integer ...row) {
        this.colorIndex = colorIndex;
        this.rowList = new ArrayList<>();
        rowList.addAll(Stream.of(row).collect(Collectors.toList()));
    }

    @Override
    public void afterCellDispose(CellWriteHandlerContext context) {
        Cell cell = context.getCell();
        int rowIndex = cell.getRowIndex();

        // 自定义样式处理
        // 当前事件会在 数据设置到poi的cell里面才会回调
        // 指定行设置背景颜色
        if (rowList.contains(rowIndex)) {
            // 第一个单元格
            // 只要不是头 一定会有数据 当然fill的情况 可能要context.getCellDataList() ,这个需要看模板，因为一个单元格会有多个 WriteCellData
            WriteCellData<?> cellData = context.getFirstCellData();
            // 这里需要去cellData 获取样式
            // 很重要的一个原因是 WriteCellStyle 和 dataFormatData绑定的 简单的说 比如你加了 DateTimeFormat
            // ，已经将writeCellStyle里面的dataFormatData 改了 如果你自己new了一个WriteCellStyle，可能注解的样式就失效了
            // 然后 getOrCreateStyle 用于返回一个样式，如果为空，则创建一个后返回
            WriteCellStyle writeCellStyle = cellData.getOrCreateStyle();
            //writeCellStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
            writeCellStyle.setFillForegroundColor((short) this.colorIndex);
            // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND
            writeCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        }
    }
}
