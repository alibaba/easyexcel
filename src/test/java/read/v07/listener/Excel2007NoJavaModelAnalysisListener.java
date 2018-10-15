package read.v07.listener;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jipengfei
 * @date 2017/08/27
 */
public class Excel2007NoJavaModelAnalysisListener extends AnalysisEventListener {

    private ExcelWriter excelWriter;

    public ExcelWriter getExcelWriter() {
        return excelWriter;
    }

    public void setExcelWriter(ExcelWriter excelWriter) {
        this.excelWriter = excelWriter;
    }

    public void invoke(Object object, AnalysisContext context) {
        List<List<String>> ll = new ArrayList<List<String>>();
        ll.add((List<String>)object);
        System.out.println(object);
        excelWriter.write0(ll, context.getCurrentSheet());
    }

    public void doAfterAllAnalysed(AnalysisContext context) {

    }

}
