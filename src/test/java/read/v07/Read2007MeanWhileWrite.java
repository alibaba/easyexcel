package read.v07;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import javamodel.ExcelRowJavaModel;
import javamodel.ExcelRowJavaModel1;
import org.junit.Test;
import read.v07.listener.Excel2007NoJavaModelAnalysisListener;
import read.v07.listener.Excel2007WithJavaModelAnalysisListener;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author jipengfei
 * @date 2017/08/27
 */
public class Read2007MeanWhileWrite {

    @Test
    public void noModel() {

        InputStream inputStream = getInputStream("2007NoModelMultipleSheet.xlsx");
        try {
            Excel2007NoJavaModelAnalysisListener listener = new Excel2007NoJavaModelAnalysisListener();
            ExcelWriter excelWriter = new ExcelWriter(new FileOutputStream("/Users/jipengfei/77.xlsx"),
                ExcelTypeEnum.XLSX, false);
            listener.setExcelWriter(excelWriter);
            ExcelReader reader = new ExcelReader(inputStream, ExcelTypeEnum.XLSX, null, listener);
            List<Sheet> sheets = reader.getSheets();
            System.out.println(sheets);
            for (Sheet sheet : sheets) {
                sheet.setHeadLineMun(1);
                reader.read(sheet);
            }
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
    public void withModel() {

        InputStream inputStream = getInputStream("2007WithModelMultipleSheet.xlsx");
        try {
            Excel2007WithJavaModelAnalysisListener listener = new Excel2007WithJavaModelAnalysisListener();
            ExcelWriter excelWriter = new ExcelWriter(new FileOutputStream("/Users/jipengfei/78.xlsx"),
                ExcelTypeEnum.XLSX, true);
            listener.setExcelWriter(excelWriter);
            ExcelReader reader = new ExcelReader(inputStream, ExcelTypeEnum.XLSX, null, listener);
            List<Sheet> sheets = reader.getSheets();
            for (Sheet sheet : sheets) {
                sheet.setHeadLineMun(1);
                if (sheet.getSheetNo() == 1) {
                    sheet.setHeadLineMun(2);
                    sheet.setClazz(ExcelRowJavaModel.class);
                }
                if (sheet.getSheetNo() == 2) {
                    sheet.setHeadLineMun(1);
                    sheet.setClazz(ExcelRowJavaModel1.class);
                }
                reader.read(sheet);
            }
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

    private InputStream getInputStream(String fileName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream("" + fileName);

    }
}
