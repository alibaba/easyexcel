package com.alibaba.easyexcel.test.demo.write;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.handler.context.SheetWriteHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFTextBox;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextCharacterProperties;

import java.awt.Color;
import java.lang.reflect.Field;

/**
 * 自定义拦截器.对第一列第一行和第二行的数据新增下拉框，显示 测试1 测试2
 *
 * @author Jiaju Zhuang
 */
@Slf4j
public class CustomSheetWriteHandler implements SheetWriteHandler {

    @Override
    public void afterSheetCreate(SheetWriteHandlerContext context) {
        log.info("第{}个Sheet写入成功。", context.getWriteSheetHolder().getSheetNo());


        // 区间设置 第一列第一行和第二行的数据。由于第一行是头，所以第一、二行的数据实际上是第二三行
        CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(1, 2, 0, 0);
        DataValidationHelper helper = context.getWriteSheetHolder().getSheet().getDataValidationHelper();
        DataValidationConstraint constraint = helper.createExplicitListConstraint(new String[]{"测试1", "测试2"});
        DataValidation dataValidation = helper.createValidation(constraint, cellRangeAddressList);
        context.getWriteSheetHolder().getSheet().addValidationData(dataValidation);

        Sheet sheet = context.getWriteSheetHolder().getSheet();
        SXSSFDrawing a = (SXSSFDrawing) sheet.createDrawingPatriarch();
        XSSFDrawing drawing = null;
        try {
            Field field = a.getClass().getDeclaredField("_drawing");
            field.setAccessible(true);
            drawing = (XSSFDrawing) field.get(a);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        XSSFClientAnchor anchor = drawing.createAnchor(0, 0,
            600, 1400, 5, 30, 11, 37);
        XSSFTextBox textBox1 = drawing.createTextbox(anchor);
        textBox1.setLineStyleColor(0, 0, 0);
        java.awt.Color col = Color.white;
        textBox1.setFillColor(col.getRed(), col.getGreen(), col.getBlue());
        //富文本字符串
        XSSFRichTextString address = new XSSFRichTextString("测试");
        textBox1.setText(address);
        //文字字符属性
        CTTextCharacterProperties rpr = textBox1.getCTShape().getTxBody().getPArray(0).getRArray(0).getRPr();
        //设置字体
        rpr.addNewLatin().setTypeface("Trebuchet MS");
        //设置字体大小9pt
        rpr.setSz(900);
        //设置字体颜色，蓝色
        col = Color.blue;
        rpr.addNewSolidFill().addNewSrgbClr().setVal(new byte[]{(byte) col.getRed(), (byte) col.getGreen(),
            (byte) col.getBlue()});

    }
}
