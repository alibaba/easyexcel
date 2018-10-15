package function.read;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import function.listener.ExcelListener;
import function.model.TestModel3;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jipengfei on 17/3/19.
 *
 * @author jipengfei
 * @date 2017/03/19
 */
public class NumTest3 {

    @Test
    public void testExcel2007WithReflectModel() {
        InputStream inputStream = getInputStream("test3.xlsx");
        try {
            AnalysisEventListener listener = new ExcelListener();

            new ExcelReader(inputStream, ExcelTypeEnum.XLSX, null, listener).read(new Sheet(1, 1, TestModel3.class));
        } catch (Exception e) {

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
