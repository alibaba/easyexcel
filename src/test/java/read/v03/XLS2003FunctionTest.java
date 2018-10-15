package read.v03;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import function.listener.ExcelListener;
import function.model.LoanInfo;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jipengfei on 17/2/19.
 */
public class XLS2003FunctionTest extends TestCase {

    @Test
    public void testExcel2003NoModel() {
        InputStream inputStream = getInputStream("2003.xls");
        try {
            // 解析每行结果在listener中处理
            ExcelListener listener = new ExcelListener();

            ExcelReader excelReader = new ExcelReader(inputStream,  null, listener);
            excelReader.read();
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
    public void testExcel2003WithSheet() {
        InputStream inputStream = getInputStream("loan1.xls");
        try {
            // 解析每行结果在listener中处理
            ExcelListener listener = new ExcelListener();
            ExcelReader reader = new ExcelReader(inputStream, ExcelTypeEnum.XLS, null, listener);
            reader.read(new Sheet(1, 1));

            System.out.println(listener.getDatas());
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
    public void testExcel2003WithReflectModel() {
        InputStream inputStream = getInputStream("loan1.xls");
        try {
            // 解析每行结果在listener中处理
            AnalysisEventListener listener = new ExcelListener();

            ExcelReader excelReader = new ExcelReader(inputStream, ExcelTypeEnum.XLS, null, listener);

            excelReader.read(new Sheet(1, 2, LoanInfo.class));
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
