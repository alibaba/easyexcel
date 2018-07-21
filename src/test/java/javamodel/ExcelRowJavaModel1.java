package javamodel;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

/**
 * Created by jipengfei on 17/3/15.
 *
 * @author jipengfei
 * @date 2017/03/15
 */
public class ExcelRowJavaModel1 extends BaseRowModel {

    @ExcelProperty(index = 0,value = "银行放款编号")
    private String num;

    @ExcelProperty(index = 1,value = "code")
    private String code;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ExcelRowJavaModel1{" +
            "num='" + num + '\'' +
            ", code='" + code + '\'' +
            '}';
    }
}
