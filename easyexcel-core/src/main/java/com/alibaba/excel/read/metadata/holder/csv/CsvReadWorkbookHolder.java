package com.alibaba.excel.read.metadata.holder.csv;

import com.alibaba.excel.read.metadata.ReadWorkbook;
import com.alibaba.excel.read.metadata.holder.ReadWorkbookHolder;
import com.alibaba.excel.support.ExcelTypeEnum;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.csv.CSVFormat;

/**
 * Workbook holder
 *
 * @author Jiaju Zhuang
 */
@Getter
@Setter
@EqualsAndHashCode
public class CsvReadWorkbookHolder extends ReadWorkbookHolder {

    private CSVFormat csvFormat;

    public CsvReadWorkbookHolder(ReadWorkbook readWorkbook) {
        super(readWorkbook);
        setExcelType(ExcelTypeEnum.CSV);
        this.csvFormat = CSVFormat.DEFAULT;
    }
}
