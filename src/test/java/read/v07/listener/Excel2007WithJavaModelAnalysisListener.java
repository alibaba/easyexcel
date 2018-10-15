package read.v07.listener;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import javamodel.ExcelRowJavaModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jipengfei
 */
public class Excel2007WithJavaModelAnalysisListener extends AnalysisEventListener {

    private ExcelWriter excelWriter;

    public ExcelWriter getExcelWriter() {
        return excelWriter;
    }

    public void setExcelWriter(ExcelWriter excelWriter) {
        this.excelWriter = excelWriter;
    }

    public void invoke(Object object, AnalysisContext context) {
        List< BaseRowModel> ll = new ArrayList();
        ll.add((BaseRowModel)object);
        Sheet sheet = context.getCurrentSheet();
        sheet.setClazz(ExcelRowJavaModel.class);
        excelWriter.write(ll,sheet);
    }

    public void doAfterAllAnalysed(AnalysisContext context) {

    }

}
