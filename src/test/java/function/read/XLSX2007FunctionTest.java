package function.read;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import function.listener.ExcelListener;
import function.model.OneRowHeadExcelModel;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jipengfei on 17/2/18.
 */
public class XLSX2007FunctionTest extends TestCase {

    //创建没有自定义模型,没有sheet的解析器,默认解析所有sheet解析结果以List<String>的方式通知监听者
    @Test
    public void testExcel2007NoModel() {
        InputStream inputStream = getInputStream("2007NoModelBigFile.xlsx");
        try {
            // 解析每行结果在listener中处理
            AnalysisEventListener listener = new ExcelListener();

            ExcelReader reader = new ExcelReader(inputStream, ExcelTypeEnum.XLSX, null, listener);

            reader.read();
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
    public void testExcel2007NoModel2() {
        InputStream inputStream = getInputStream("test4.xlsx");
        try {
            // 解析每行结果在listener中处理
            AnalysisEventListener listener = new ExcelListener();

            ExcelReader reader = new ExcelReader(inputStream, ExcelTypeEnum.XLSX, null, listener);

            reader.read();
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

    //创建没有自定义模型,但有规定sheet解析器,解析结果以List<String>的方式通知监听者
    @Test
    public void testExcel2007WithSheet() {
        InputStream inputStream = getInputStream("111.xlsx");

        try {
            // 解析每行结果在listener中处理
            AnalysisEventListener listener = new ExcelListener();

            ExcelReader reader = new ExcelReader(inputStream, ExcelTypeEnum.XLSX, null, listener);
            reader.read(new Sheet(1, 0));
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

    //创建需要反射映射模型的解析器,解析结果List<Object> Object为自定义的模型
    @Test
    public void testExcel2007WithReflectModel() {
        InputStream inputStream = getInputStream("2007.xlsx");
        try {

            // 解析每行结果在listener中处理
            AnalysisEventListener listener = new ExcelListener();

            ExcelReader reader = new ExcelReader(inputStream, ExcelTypeEnum.XLSX, null, listener);

            reader.read(new Sheet(1, 1, OneRowHeadExcelModel.class));
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
    public void testExcel2007MultHeadWithReflectModel() {
        InputStream inputStream = getInputStream("2007_1.xlsx");

        try {

            // 解析每行结果在listener中处理
            AnalysisEventListener listener = new ExcelListener();

            ExcelReader reader = new ExcelReader(inputStream, ExcelTypeEnum.XLSX, null, listener);

            reader.read(new Sheet(1, 4, OneRowHeadExcelModel.class));

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
