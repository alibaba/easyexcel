package com.alibaba.excel.read.metadata.property;

import java.util.List;

import com.alibaba.excel.metadata.Holder;
import com.alibaba.excel.metadata.property.ExcelHeadProperty;

/**
 * Define the header attribute of excel
 *
 * @author jipengfei
 */
public class ExcelReadHeadProperty extends ExcelHeadProperty {

    public ExcelReadHeadProperty(Holder holder, Class headClazz, List<List<String>> head) {
        super(holder, headClazz, head);
    }
}
