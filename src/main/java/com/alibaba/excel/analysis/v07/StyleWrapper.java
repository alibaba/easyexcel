package com.alibaba.excel.analysis.v07;

import com.alibaba.excel.metadata.data.DataFormatData;

import lombok.Data;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

@Data
public class StyleWrapper {

    private XSSFCellStyle xssfCellStyle;
    private DataFormatData dataFormatData;

}
