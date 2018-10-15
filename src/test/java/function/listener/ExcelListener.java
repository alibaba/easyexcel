package function.listener;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jipengfei on 17/3/14.
 * 解析监听器，
 * 每解析一行会回调invoke()方法。
 * 整个excel解析结束会执行doAfterAllAnalysed()方法
 *
 * 下面只是我写的一个样例而已，可以根据自己的逻辑修改该类。
 *
 * @author jipengfei
 * @date 2017/03/14
 */
public class ExcelListener extends AnalysisEventListener {

    //自定义用于暂时存储data。
    //可以通过实例获取该值
    private List<Object> datas = new ArrayList<Object>();
    Sheet sheet;

    private ExcelWriter writer;


    public void invoke(Object object, AnalysisContext context) {
       // context.interrupt();

        System.out.println("当前表格数" + context.getCurrentSheet().getSheetNo() + " 当前行：" + context.getCurrentRowNum());
        System.out.println(object);
        datas.add(object);//数据存储到list，供批量处理，或后续自己业务逻辑处理。
        List<List<String>> ll = new ArrayList<List<String>>();
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(byteOut);
            out.writeObject(object);
            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);

            List<String> dest = (List<String>)in.readObject();
            ll.add(dest);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

       // writer.write0(ll, sheet);
        doSomething(object);//根据自己业务做处理

    }

    private void doSomething(Object object) {
        //1、入库调用接口
    }

    public void doAfterAllAnalysed(AnalysisContext context) {
       // writer.finish();
        // datas.clear();//解析结束销毁不用的资源
    }

    public List<Object> getDatas() {
        return datas;
    }

    public void setDatas(List<Object> datas) {
        this.datas = datas;
    }

    public Sheet getSheet() {
        return sheet;
    }

    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
    }

    public ExcelWriter getWriter() {
        return writer;
    }

    public void setWriter(ExcelWriter writer) {
        this.writer = writer;
    }
}
