package function.write;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;

import function.model.ExcelPropertyIndexModel;
import function.model.MultiLineHeadExcelModel;
import org.junit.Test;

/**
 * 测试{@link ExcelProperty#index()}
 *
 * @author jipengfei
 * @date 2017/05/31
 */
public class ExcelWriteIndexTest {

    @Test
    public void test1() throws FileNotFoundException {
        OutputStream out = new FileOutputStream("/Users/jipengfei/78.xlsx");
        try {
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            //写第一个sheet, sheet1  数据全是List<String> 无模型映射关系
            Sheet sheet1 = new Sheet(1, 0,ExcelPropertyIndexModel.class);
            writer.write(getData(), sheet1);
            writer.finish();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Test
    public void test2() throws FileNotFoundException {
        OutputStream out = new FileOutputStream("/Users/jipengfei/79.xlsx");
        try {
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            //写第一个sheet, sheet1  数据全是List<String> 无模型映射关系
            Sheet sheet1 = new Sheet(1, 3,MultiLineHeadExcelModel.class);
            writer.write(getModeldatas(), sheet1);
            writer.finish();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<ExcelPropertyIndexModel> getData() {
        List<ExcelPropertyIndexModel> datas = new ArrayList<ExcelPropertyIndexModel>();
        ExcelPropertyIndexModel model = new ExcelPropertyIndexModel();
        model.setAddress("杭州");
        model.setAge("11");
        model.setEmail("7827323@qq.com");
        model.setSax("男");
        model.setHeigh("1123");
        datas.add(model);
        return datas;
    }
    private List<MultiLineHeadExcelModel> getModeldatas() {
        List<MultiLineHeadExcelModel> MODELS = new ArrayList<MultiLineHeadExcelModel>();
        MultiLineHeadExcelModel model1 = new MultiLineHeadExcelModel();
        model1.setP1("111");
        model1.setP2("222");
        model1.setP3(33);
        model1.setP4(44);
        model1.setP5("555");
        model1.setP6("666");
        model1.setP7("777");
        model1.setP8("888");

        MultiLineHeadExcelModel model2 = new MultiLineHeadExcelModel();
        model2.setP1("111");
        model2.setP2("111");
        model2.setP3(11);
        model2.setP4(9);
        model2.setP5("111");
        model2.setP6("111");
        model2.setP7("111");
        model2.setP8("111");

        MultiLineHeadExcelModel model3 = new MultiLineHeadExcelModel();
        model3.setP1("111");
        model3.setP2("111");
        model3.setP3(11);
        model3.setP4(9);
        model3.setP5("111");
        model3.setP6("111");
        model3.setP7("111");
        model3.setP8("111");

        MODELS.add(model1);
        MODELS.add(model2);
        MODELS.add(model3);

        return MODELS;

    }

}
