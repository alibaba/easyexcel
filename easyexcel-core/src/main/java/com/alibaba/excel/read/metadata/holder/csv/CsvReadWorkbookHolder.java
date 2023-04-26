package com.alibaba.excel.read.metadata.holder.csv;

import com.alibaba.excel.analysis.csv.BomBufferedInputStream;
import com.alibaba.excel.read.metadata.ReadWorkbook;
import com.alibaba.excel.read.metadata.holder.ReadWorkbookHolder;
import com.alibaba.excel.support.ExcelTypeEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.IOException;
import java.nio.file.Files;

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
    private CSVParser csvParser;

    public CsvReadWorkbookHolder(ReadWorkbook readWorkbook) {
        super(readWorkbook);
        setExcelType(ExcelTypeEnum.CSV);
        this.csvFormat = CSVFormat.DEFAULT;
        // CSV BOM
        if (readWorkbook.getCharset() == null) {
            BomBufferedInputStream bomBufferedInputStream = buildBomBufferedInputStream();
            setInputStream(bomBufferedInputStream);
            setMandatoryUseInputStream(Boolean.TRUE);
            try {
                if (bomBufferedInputStream.hasByteOrderMark()) {
                    setCharset(bomBufferedInputStream.getByteOrderMark().getCharset());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private BomBufferedInputStream buildBomBufferedInputStream() {
        BomBufferedInputStream bomBufferedInputStream;
        try {
            if (Boolean.TRUE.equals(getMandatoryUseInputStream())) {
                bomBufferedInputStream = new BomBufferedInputStream(getInputStream());
            } else if (getFile() != null) {
                bomBufferedInputStream = new BomBufferedInputStream(Files.newInputStream(getFile().toPath()));
            } else {
                bomBufferedInputStream = new BomBufferedInputStream(getInputStream());
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return bomBufferedInputStream;
    }
}
