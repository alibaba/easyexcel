package com.alibaba.excel.read.builder;

import com.alibaba.excel.util.StringUtils;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import org.apache.poi.hssf.record.crypto.Biff8EncryptionKey;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.IOException;

public class CSV extends Sheets {

    @Override
    void createWorkBook(WriteWorkbookHolder writeWorkbookHolder) throws IOException {
        HSSFWorkbook hssfWorkbook;
                if (writeWorkbookHolder.getTempTemplateInputStream() != null) {
                    hssfWorkbook = new HSSFWorkbook(
                        new POIFSFileSystem(writeWorkbookHolder.getTempTemplateInputStream()));
                } else {
                    hssfWorkbook = new HSSFWorkbook();
                }
                writeWorkbookHolder.setCachedWorkbook(hssfWorkbook);
                writeWorkbookHolder.setWorkbook(hssfWorkbook);
                if (writeWorkbookHolder.getPassword() != null) {
                    Biff8EncryptionKey.setCurrentUserPassword(writeWorkbookHolder.getPassword());
                    hssfWorkbook.writeProtectWorkbook(writeWorkbookHolder.getPassword(), StringUtils.EMPTY);
                }
                return;
    }
}
