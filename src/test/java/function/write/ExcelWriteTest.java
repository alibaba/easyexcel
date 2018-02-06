package function.write;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Font;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.metadata.TableStyle;
import com.alibaba.excel.support.ExcelTypeEnum;

import function.model.MultiLineHeadExcelModel;
import function.model.NoAnnModel;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.junit.Test;

/**
 *
 * 测试Excel写多个表格
 * @author jipengfei
 * @date 2017/05/16
 */
public class ExcelWriteTest {

    /**
     * 一个sheet一张表
     *
     * @throws FileNotFoundException
     */
    @Test
    public void test1() throws FileNotFoundException {

        OutputStream out = new FileOutputStream("/Users/jipengfei/77.xlsx");
        try {
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX,true);
            //写第一个sheet, sheet1  数据全是List<String> 无模型映射关系
            Sheet sheet1 = new Sheet(1, 0);
            sheet1.setSheetName("第一个sheet");
            writer.write0(getListString(), sheet1);

            //写第二个sheet sheet2  模型上打有表头的注解，合并单元格
            Sheet sheet2 = new Sheet(2, 3, MultiLineHeadExcelModel.class, "第二个sheet", null);
            sheet2.setTableStyle(getTableStyle1());
            writer.write(getModeldatas(), sheet2);

            //写sheet3  模型上没有注解，表头数据动态传入
            List<List<String>> head = new ArrayList<List<String>>();
            List<String> headCoulumn1 = new ArrayList<String>();
            List<String> headCoulumn2 = new ArrayList<String>();
            List<String> headCoulumn3 = new ArrayList<String>();
            headCoulumn1.add("第一列");
            headCoulumn2.add("第二列");
            headCoulumn3.add("第三列");
            head.add(headCoulumn1);
            head.add(headCoulumn2);
            head.add(headCoulumn3);
            Sheet sheet3 = new Sheet(3, 1, NoAnnModel.class, "第三个sheet", head);
            writer.write(getNoAnnModels(), sheet3);
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

    /**
     * 一个sheet多张表
     *
     * @throws FileNotFoundException
     */
    @Test
    public void test2() throws FileNotFoundException {
        OutputStream out = new FileOutputStream("/Users/jipengfei/77.xlsx");
        try {
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX,false);

            //写sheet1  数据全是List<String> 无模型映射关系
            Sheet sheet1 = new Sheet(1, 0);
            sheet1.setSheetName("第一个sheet");
            Table table1 = new Table(1);
            writer.write0(getListString(), sheet1, table1);
            writer.write0(getListString(), sheet1, table1);

            //写sheet2  模型上打有表头的注解
            Table table2 = new Table(2);
            table2.setTableStyle(getTableStyle1());
            table2.setClazz(MultiLineHeadExcelModel.class);
            writer.write(getModeldatas(), sheet1, table2);

            //写sheet3  模型上没有注解，表头数据动态传入,此情况下模型field顺序与excel现实顺序一致
            List<List<String>> head = new ArrayList<List<String>>();
            List<String> headCoulumn1 = new ArrayList<String>();
            List<String> headCoulumn2 = new ArrayList<String>();
            List<String> headCoulumn3 = new ArrayList<String>();
            headCoulumn1.add("第一列");
            headCoulumn2.add("第二列");
            headCoulumn3.add("第三列");
            head.add(headCoulumn1);
            head.add(headCoulumn2);
            head.add(headCoulumn3);
            Table table3 = new Table(3);
            table3.setHead(head);
            table3.setClazz(NoAnnModel.class);
            table3.setTableStyle(getTableStyle2());
            writer.write(getNoAnnModels(), sheet1, table3);
            writer.write(getNoAnnModels(), sheet1, table3);

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

    private List<List<String>> getListString() {
        List<String> list = new ArrayList<String>();
        list.add("ooo1");
        list.add("ooo2");
        list.add("ooo3");
        list.add("ooo4");
        List<String> list1 = new ArrayList<String>();
        list1.add("ooo1");
        list1.add("ooo2");
        list1.add("ooo3");
        list1.add("ooo4");
        List<List<String>> ll = new ArrayList<List<String>>();
        ll.add(list);
        ll.add(list1);
        return ll;
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

    private List<NoAnnModel> getNoAnnModels() {
        List<NoAnnModel> MODELS = new ArrayList<NoAnnModel>();
        NoAnnModel model1 = new NoAnnModel();
        model1.setP1("111");
        model1.setP2("111");

        NoAnnModel model2 = new NoAnnModel();
        model2.setP1("111");
        model2.setP2("111");
        model2.setP3("22");

        NoAnnModel model3 = new NoAnnModel();
        model3.setP1("111");
        model3.setP2("111");
        model3.setP3("111");

        MODELS.add(model1);
        MODELS.add(model2);
        MODELS.add(model3);

        return MODELS;

    }

    private TableStyle getTableStyle1() {
        TableStyle tableStyle = new TableStyle();
        Font headFont = new Font();
        headFont.setBold(true);
        headFont.setFontHeightInPoints((short)22);
        headFont.setFontName("楷体");
        tableStyle.setTableHeadFont(headFont);
        tableStyle.setTableHeadBackGroundColor(IndexedColors.BLUE);

        Font contentFont = new Font();
        contentFont.setBold(true);
        contentFont.setFontHeightInPoints((short)22);
        contentFont.setFontName("黑体");
        tableStyle.setTableContentFont(contentFont);
        tableStyle.setTableContentBackGroundColor(IndexedColors.GREEN);
        return tableStyle;
    }

    private TableStyle getTableStyle2() {
        TableStyle tableStyle = new TableStyle();
        Font headFont = new Font();
        headFont.setBold(true);
        headFont.setFontHeightInPoints((short)22);
        headFont.setFontName("宋体");
        tableStyle.setTableHeadFont(headFont);
        tableStyle.setTableHeadBackGroundColor(IndexedColors.BLUE);

        Font contentFont = new Font();
        contentFont.setBold(true);
        contentFont.setFontHeightInPoints((short)10);
        contentFont.setFontName("黑体");
        tableStyle.setTableContentFont(contentFont);
        tableStyle.setTableContentBackGroundColor(IndexedColors.RED);
        return tableStyle;
    }
}
