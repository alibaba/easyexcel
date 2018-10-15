package function.read;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import function.listener.ExcelListener;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by jipengfei on 17/3/22.
 *
 * @author jipengfei
 * @date 2017/03/22
 */
public class ReadSheets {
    @Test
    public void ReadSheets2007() {
        InputStream inputStream = getInputStream("2007NoModelMultipleSheet.xlsx");
        try {
            ExcelListener listener = new ExcelListener();
            listener.setSheet(new Sheet(1));
            ExcelWriter excelWriter = new ExcelWriter(new FileOutputStream("/Users/jipengfei/77.xlsx"), ExcelTypeEnum.XLSX,false);
            listener.setWriter(excelWriter);
            ExcelReader reader = new ExcelReader(inputStream, ExcelTypeEnum.XLSX, null, listener);
            List<Sheet> sheets = reader.getSheets();
            System.out.println(sheets);
            for (Sheet sheet:sheets) {
                sheet.setHeadLineMun(1);
                reader.read(sheet);
            }
         // reader.read(new Sheet(1));
            excelWriter.finish();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void ReadSheets2003() {
        InputStream inputStream = getInputStream("2003.xls");
        try {
            AnalysisEventListener listener = new ExcelListener();

            ExcelReader reader = new ExcelReader(inputStream, ExcelTypeEnum.XLS, null, listener);
            reader.read();
            List<Sheet> sheets = reader.getSheets();
            System.out.println(sheets);
            for (Sheet sheet:sheets) {
                sheet.setHeadLineMun(1);
                reader.read(sheet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private InputStream getInputStream(String fileName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream("" + fileName);

    }
}
