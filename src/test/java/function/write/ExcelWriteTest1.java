package function.write;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import function.listener.ExcelListener;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jipengfei
 * @date 2017/08/15
 */
public class ExcelWriteTest1 {

    @Test
    public void test(){
        OutputStream out = null;
        try {
            out = new FileOutputStream("/Users/jipengfei/79.xlsx");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX, false);

            //写sheet1  数据全是List<String> 无模型映射关系
            Sheet sheet1 = new Sheet(1, 0);
            sheet1.setSheetName("第一个sheet");
            List<String> list = new ArrayList<String>();
            list.add("1");list.add("2");list.add("3");
            List<String> list1 = new ArrayList<String>();
            list1.add("1");list1.add("2");list1.add("3");
            List<List<String>> lll = new ArrayList<List<String>>();
            lll.add(list);
            writer.write0(lll,sheet1);
            writer.write0(lll,sheet1);
            writer.finish();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testWriteAndRead(){
        OutputStream out = null;
        try {
            out = new FileOutputStream("/Users/jipengfei/79.xlsx");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            SXSSFWorkbook wb = new SXSSFWorkbook(10000);
            SXSSFSheet sheet = wb.createSheet("11111");
            Row row = sheet.createRow(0);
            Cell cell1 = row.createCell(0);
            cell1.setCellValue("1111");
            Cell cell2 = row.createCell(1);
            cell2.setCellValue("22222");
            Cell cell3 = row.createCell(2);
            cell3.setCellValue("33333");



            Row row1 = sheet.createRow(1);
            Cell cell21 = row1.createCell(0);
            cell21.setCellValue("444");
            Cell cell22 = row1.createCell(1);
            cell22.setCellValue("555");
            Cell cell23 = row1.createCell(2);
            cell23.setCellValue("666");
            wb.write(out);
            out.close();




            InputStream inputStream = new FileInputStream("/Users/jipengfei/79.xlsx");

            AnalysisEventListener listener = new ExcelListener();

            ExcelReader reader = new ExcelReader(inputStream, ExcelTypeEnum.XLSX, null, listener);
            reader.read(new Sheet(1));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private InputStream getInputStream(String fileName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream("" + fileName);

    }
}
