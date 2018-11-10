package function.read;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import function.listener.ExcelListener;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jipengfei on 17/3/15.
 *
 * @author jipengfei
 * @date 2017/03/15
 */
public class ExelAllDataTypeTest extends TestCase {
    // 创建没有自定义模型,没有sheet的解析器,默认解析所有sheet解析结果以List<String>的方式通知监听者
    @Test
    public void testExcel2007WithReflectModel() {
        InputStream inputStream = getInputStream("77.xlsx");

        try {
            // 解析每行结果在listener中处理
            AnalysisEventListener listener = new ExcelListener();

            new ExcelReader(inputStream, ExcelTypeEnum.XLSX, null, listener).read(new Sheet(1, 1, null));
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

    /**
     * InputStream in = FileMagic.prepareToCheckMagic(inputStream);
     * FileMagic fileMagic =  FileMagic.valueOf(in);
     *
     * FileMagic.valueOf需要读取8个byte来判定文件类型，这个peek操作会影响原始的inputStream，导致后续真正读取文件的时候报错
     *
     * @throws FileNotFoundException
     */
    @Test
    public void testNotMarkSupported() throws FileNotFoundException {
        InputStream inputStream = new FileInputStream("src/test/resources/1111.xlsx");
        System.out.println("markSupported=" + inputStream.markSupported());
        ExcelListener listener = new ExcelListener();
        new ExcelReader(inputStream, null, listener, true).read();
        Assert.assertTrue(listener.getDatas().size() > 0);
    }

    private InputStream getInputStream(String fileName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream("" + fileName);

    }
}
