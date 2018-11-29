package com.alibaba.easyexcel.test.listen;

import com.alibaba.excel.event.WriteHandler;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

public class AfterWriteHandlerImpl implements WriteHandler {

    //
    CellStyle cellStyle;

    @Override
    public void sheet(int sheetNo, Sheet sheet) {
        Workbook workbook = sheet.getWorkbook();
        //要锁定单元格需先为此表单设置保护密码，设置之后此表单默认为所有单元格锁定，可使用setLocked(false)为指定单元格设置不锁定。
        //设置表单保护密码
        sheet.protectSheet("your password");
        //创建样式
        cellStyle = workbook.createCellStyle();
        //设置是否锁
        cellStyle.setLocked(false);

    }

    @Override
    public void row(int rowNum, Row row) {
        Workbook workbook = row.getSheet().getWorkbook();
        //设置行高
        row.setHeight((short)20);
    }

    @Override
    public void cell(int cellNum, Cell cell) {
        Workbook workbook = cell.getSheet().getWorkbook();
        Sheet currentSheet = cell.getSheet();
        if (cellNum == 4 && cell.getRowIndex() == 30) {
            //设置样式
            //注意：样式最好采用公用样式，样式在创建sheet后创建，如果有多个样式也需要在创建sheet时候创建后面直接使用，不要每个Cell Create 一个样式，不然会导致报错 The maximum number
            // of Cell Styles was exceeded.
            cell.setCellStyle(cellStyle);



            //设置备注
            Drawing draw = currentSheet.createDrawingPatriarch();
            Comment comment = draw.createCellComment(new XSSFClientAnchor(0, 0, 0, 0, 4, 25, 9, 30));
            XSSFRichTextString rtf = new XSSFRichTextString("添加批注内容收到货死的死哦多胡搜idsad是否会杜甫的范德萨发！1111");
            Font commentFormatter = workbook.createFont();
            commentFormatter.setFontName("宋体");
            //设置字体大小
            commentFormatter.setFontHeightInPoints((short)9);
            rtf.applyFont(commentFormatter);
            comment.setString(rtf);
            comment.setAuthor("ceshi");
            cell.setCellComment(comment);
        }
    }
}
