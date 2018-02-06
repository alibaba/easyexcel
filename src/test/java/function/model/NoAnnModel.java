package function.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

/**
 * @author jipengfei
 * @date 2017/05/16
 */
public class NoAnnModel extends BaseRowModel {

    @ExcelProperty(index = 0)
    private String p1;

    @ExcelProperty(index = 1)
    private String p2;

    @ExcelProperty(index = 2)
    private String p3;

    public String getP1() {
        return p1;
    }

    public void setP1(String p1) {
        this.p1 = p1;
    }

    public String getP2() {
        return p2;
    }

    public void setP2(String p2) {
        this.p2 = p2;
    }

    public String getP3() {
        return p3;
    }

    public void setP3(String p3) {
        this.p3 = p3;
    }
}
