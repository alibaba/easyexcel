package com.alibaba.excel.context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.hssf.record.crypto.Biff8EncryptionKey;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.enums.WriteTypeEnum;
import com.alibaba.excel.exception.ExcelGenerateException;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.FileUtils;
import com.alibaba.excel.util.StringUtils;
import com.alibaba.excel.util.WorkBookUtil;
import com.alibaba.excel.util.WriteHandlerUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.alibaba.excel.write.metadata.WriteWorkbook;
import com.alibaba.excel.write.metadata.holder.WriteHolder;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.alibaba.excel.write.property.ExcelWriteHeadProperty;

/**
 * A context is the main anchorage point of a excel writer.
 *
 * @author jipengfei
 */
public class WriteContextImpl implements WriteContext {

    private static final Logger LOGGER = LoggerFactory.getLogger(WriteContextImpl.class);

    /**
     * The Workbook currently written
     */
    private WriteWorkbookHolder writeWorkbookHolder;
    /**
     * Current sheet holder
     */
    private WriteSheetHolder writeSheetHolder;
    /**
     * The table currently written
     */
    private WriteTableHolder writeTableHolder;
    /**
     * Configuration of currently operated cell
     */
    private WriteHolder currentWriteHolder;
    /**
     * Prevent multiple shutdowns
     */
    private boolean finished = false;

    public WriteContextImpl(WriteWorkbook writeWorkbook) {
        if (writeWorkbook == null) {
            throw new IllegalArgumentException("Workbook argument cannot be null");
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Begin to Initialization 'WriteContextImpl'");
        }
        initCurrentWorkbookHolder(writeWorkbook);
        WriteHandlerUtils.beforeWorkbookCreate(this);
        try {
            WorkBookUtil.createWorkBook(writeWorkbookHolder);
        } catch (Exception e) {
            throw new ExcelGenerateException("Create workbook failure", e);
        }
        WriteHandlerUtils.afterWorkbookCreate(this);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Initialization 'WriteContextImpl' complete");
        }
    }

    private void initCurrentWorkbookHolder(WriteWorkbook writeWorkbook) {
        writeWorkbookHolder = new WriteWorkbookHolder(writeWorkbook);
        currentWriteHolder = writeWorkbookHolder;
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("CurrentConfiguration is writeWorkbookHolder");
        }
    }

    /**
     * @param writeSheet
     */
    @Override
    public void currentSheet(WriteSheet writeSheet, WriteTypeEnum writeType) {
        if (writeSheet == null) {
            throw new IllegalArgumentException("Sheet argument cannot be null");
        }
        if (writeSheet.getSheetNo() == null || writeSheet.getSheetNo() <= 0) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Sheet number is null");
            }
            writeSheet.setSheetNo(0);
        }
        if (writeWorkbookHolder.getHasBeenInitializedSheet().containsKey(writeSheet.getSheetNo())) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Sheet:{} is already existed", writeSheet.getSheetNo());
            }
            writeSheetHolder = writeWorkbookHolder.getHasBeenInitializedSheet().get(writeSheet.getSheetNo());
            writeSheetHolder.setNewInitialization(Boolean.FALSE);
            writeTableHolder = null;
            currentWriteHolder = writeSheetHolder;
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("CurrentConfiguration is writeSheetHolder");
            }
            return;
        }
        initCurrentSheetHolder(writeSheet);
        WriteHandlerUtils.beforeSheetCreate(this);
        // Initialization current sheet
        initSheet(writeType);
    }

    private void initCurrentSheetHolder(WriteSheet writeSheet) {
        writeSheetHolder = new WriteSheetHolder(writeSheet, writeWorkbookHolder);
        writeWorkbookHolder.getHasBeenInitializedSheet().put(writeSheet.getSheetNo(), writeSheetHolder);
        writeTableHolder = null;
        currentWriteHolder = writeSheetHolder;
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("CurrentConfiguration is writeSheetHolder");
        }
    }

    private void initSheet(WriteTypeEnum writeType) {
        Sheet currentSheet;
        try {
            currentSheet = writeWorkbookHolder.getWorkbook().getSheetAt(writeSheetHolder.getSheetNo());
            writeSheetHolder
                .setCachedSheet(writeWorkbookHolder.getCachedWorkbook().getSheetAt(writeSheetHolder.getSheetNo()));
        } catch (Exception e) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Can not find sheet:{} ,now create it", writeSheetHolder.getSheetNo());
            }
            currentSheet = WorkBookUtil.createSheet(writeWorkbookHolder.getWorkbook(), writeSheetHolder.getSheetName());
            writeSheetHolder.setCachedSheet(currentSheet);
        }
        writeSheetHolder.setSheet(currentSheet);
        WriteHandlerUtils.afterSheetCreate(this);
        if (WriteTypeEnum.ADD.equals(writeType)) {
            // Initialization head
            initHead(writeSheetHolder.excelWriteHeadProperty());
        }
    }

    public void initHead(ExcelWriteHeadProperty excelWriteHeadProperty) {
        if (!currentWriteHolder.needHead() || !currentWriteHolder.excelWriteHeadProperty().hasHead()) {
            return;
        }
        int newRowIndex = writeSheetHolder.getNewRowIndexAndStartDoWrite();
        newRowIndex += currentWriteHolder.relativeHeadRowIndex();
        // Combined head
        addMergedRegionToCurrentSheet(excelWriteHeadProperty, newRowIndex);
        for (int relativeRowIndex = 0, i = newRowIndex; i < excelWriteHeadProperty.getHeadRowNumber() + newRowIndex;
            i++, relativeRowIndex++) {
            WriteHandlerUtils.beforeRowCreate(this, newRowIndex, relativeRowIndex, Boolean.TRUE);
            Row row = WorkBookUtil.createRow(writeSheetHolder.getSheet(), i);
            WriteHandlerUtils.afterRowCreate(this, row, relativeRowIndex, Boolean.TRUE);
            addOneRowOfHeadDataToExcel(row, excelWriteHeadProperty.getHeadMap(), relativeRowIndex);
            WriteHandlerUtils.afterRowDispose(this, row, relativeRowIndex, Boolean.TRUE);
        }
    }

    private void addMergedRegionToCurrentSheet(ExcelWriteHeadProperty excelWriteHeadProperty, int rowIndex) {
        for (com.alibaba.excel.metadata.CellRange cellRangeModel : excelWriteHeadProperty.headCellRangeList()) {
            writeSheetHolder.getSheet().addMergedRegion(new CellRangeAddress(cellRangeModel.getFirstRow() + rowIndex,
                cellRangeModel.getLastRow() + rowIndex, cellRangeModel.getFirstCol(), cellRangeModel.getLastCol()));
        }
    }

    private void addOneRowOfHeadDataToExcel(Row row, Map<Integer, Head> headMap, int relativeRowIndex) {
        for (Map.Entry<Integer, Head> entry : headMap.entrySet()) {
            Head head = entry.getValue();
            int columnIndex = entry.getKey();
            WriteHandlerUtils.beforeCellCreate(this, row, head, columnIndex, relativeRowIndex, Boolean.TRUE);
            Cell cell = row.createCell(columnIndex);
            WriteHandlerUtils.afterCellCreate(this, cell, head, relativeRowIndex, Boolean.TRUE);
            cell.setCellValue(head.getHeadNameList().get(relativeRowIndex));
            WriteHandlerUtils.afterCellDispose(this, (CellData)null, cell, head, relativeRowIndex, Boolean.TRUE);
        }
    }

    @Override
    public void currentTable(WriteTable writeTable) {
        if (writeTable == null) {
            return;
        }
        if (writeTable.getTableNo() == null || writeTable.getTableNo() <= 0) {
            writeTable.setTableNo(0);
        }
        if (writeSheetHolder.getHasBeenInitializedTable().containsKey(writeTable.getTableNo())) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Table:{} is already existed", writeTable.getTableNo());
            }
            writeTableHolder = writeSheetHolder.getHasBeenInitializedTable().get(writeTable.getTableNo());
            writeTableHolder.setNewInitialization(Boolean.FALSE);
            currentWriteHolder = writeTableHolder;
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("CurrentConfiguration is writeTableHolder");
            }
            return;
        }
        initCurrentTableHolder(writeTable);
        initHead(writeTableHolder.excelWriteHeadProperty());
    }

    private void initCurrentTableHolder(WriteTable writeTable) {
        writeTableHolder = new WriteTableHolder(writeTable, writeSheetHolder, writeWorkbookHolder);
        writeSheetHolder.getHasBeenInitializedTable().put(writeTable.getTableNo(), writeTableHolder);
        currentWriteHolder = writeTableHolder;
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("CurrentConfiguration is writeTableHolder");
        }
    }

    @Override
    public WriteWorkbookHolder writeWorkbookHolder() {
        return writeWorkbookHolder;
    }

    @Override
    public WriteSheetHolder writeSheetHolder() {
        return writeSheetHolder;
    }

    @Override
    public WriteTableHolder writeTableHolder() {
        return writeTableHolder;
    }

    @Override
    public WriteHolder currentWriteHolder() {
        return currentWriteHolder;
    }

    @Override
    public void finish(boolean onException) {
        if (finished) {
            return;
        }
        finished = true;
        WriteHandlerUtils.afterWorkbookDispose(this);
        if (writeWorkbookHolder == null) {
            return;
        }
        Throwable throwable = null;
        boolean isOutputStreamEncrypt = false;
        // Determine if you need to write excel
        boolean writeExcel = !onException;
        if (writeWorkbookHolder.getWriteExcelOnException()) {
            writeExcel = Boolean.TRUE;
        }
        // No data is written if an exception is thrown
        if (writeExcel) {
            try {
                isOutputStreamEncrypt = doOutputStreamEncrypt07();
            } catch (Throwable t) {
                throwable = t;
            }
        }

        if (!isOutputStreamEncrypt) {
            try {
                if (writeExcel) {
                    writeWorkbookHolder.getWorkbook().write(writeWorkbookHolder.getOutputStream());
                }
                writeWorkbookHolder.getWorkbook().close();
            } catch (Throwable t) {
                throwable = t;
            }
        }

        try {
            Workbook workbook = writeWorkbookHolder.getWorkbook();
            if (workbook instanceof SXSSFWorkbook) {
                ((SXSSFWorkbook)workbook).dispose();
            }
        } catch (Throwable t) {
            throwable = t;
        }

        try {
            if (writeWorkbookHolder.getAutoCloseStream() && writeWorkbookHolder.getOutputStream() != null) {
                writeWorkbookHolder.getOutputStream().close();
            }
        } catch (Throwable t) {
            throwable = t;
        }

        if (writeExcel && !isOutputStreamEncrypt) {
            try {
                doFileEncrypt07();
            } catch (Throwable t) {
                throwable = t;
            }
        }

        try {
            if (writeWorkbookHolder.getTempTemplateInputStream() != null) {
                writeWorkbookHolder.getTempTemplateInputStream().close();
            }
        } catch (Throwable t) {
            throwable = t;
        }

        clearEncrypt03();

        if (throwable != null) {
            throw new ExcelGenerateException("Can not close IO", throwable);
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Finished write.");
        }
    }

    @Override
    public Sheet getCurrentSheet() {
        return writeSheetHolder.getSheet();
    }

    @Override
    public boolean needHead() {
        return writeSheetHolder.needHead();
    }

    @Override
    public OutputStream getOutputStream() {
        return writeWorkbookHolder.getOutputStream();
    }

    @Override
    public Workbook getWorkbook() {
        return writeWorkbookHolder.getWorkbook();
    }

    private void clearEncrypt03() {
        if (StringUtils.isEmpty(writeWorkbookHolder.getPassword())
            || !ExcelTypeEnum.XLS.equals(writeWorkbookHolder.getExcelType())) {
            return;
        }
        Biff8EncryptionKey.setCurrentUserPassword(null);
    }

    /**
     * To encrypt
     */
    private boolean doOutputStreamEncrypt07() throws Exception {
        if (StringUtils.isEmpty(writeWorkbookHolder.getPassword())
            || !ExcelTypeEnum.XLSX.equals(writeWorkbookHolder.getExcelType())) {
            return false;
        }
        if (writeWorkbookHolder.getFile() != null) {
            return false;
        }
        File tempXlsx = FileUtils.createTmpFile(UUID.randomUUID().toString() + ".xlsx");
        FileOutputStream tempFileOutputStream = new FileOutputStream(tempXlsx);
        try {
            writeWorkbookHolder.getWorkbook().write(tempFileOutputStream);
        } finally {
            try {
                writeWorkbookHolder.getWorkbook().close();
                tempFileOutputStream.close();
            } catch (Exception e) {
                if (!tempXlsx.delete()) {
                    throw new ExcelGenerateException("Can not delete temp File!");
                }
                throw e;
            }
        }
        POIFSFileSystem fileSystem = null;
        try {
            fileSystem = openFileSystemAndEncrypt(tempXlsx);
            fileSystem.writeFilesystem(writeWorkbookHolder.getOutputStream());
        } finally {
            if (fileSystem != null) {
                fileSystem.close();
            }
            if (!tempXlsx.delete()) {
                throw new ExcelGenerateException("Can not delete temp File!");
            }
        }
        return true;
    }

    /**
     * To encrypt
     */
    private void doFileEncrypt07() throws Exception {
        if (StringUtils.isEmpty(writeWorkbookHolder.getPassword())
            || !ExcelTypeEnum.XLSX.equals(writeWorkbookHolder.getExcelType())) {
            return;
        }
        if (writeWorkbookHolder.getFile() == null) {
            return;
        }
        FileOutputStream fileOutputStream = null;
        POIFSFileSystem fileSystem = null;
        try {
            fileSystem = openFileSystemAndEncrypt(writeWorkbookHolder.getFile());
            fileOutputStream = new FileOutputStream(writeWorkbookHolder.getFile());
            fileSystem.writeFilesystem(fileOutputStream);
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            if (fileSystem != null) {
                fileSystem.close();
            }
        }
    }

    private POIFSFileSystem openFileSystemAndEncrypt(File file) throws Exception {
        POIFSFileSystem fileSystem = new POIFSFileSystem();
        Encryptor encryptor = new EncryptionInfo(EncryptionMode.standard).getEncryptor();
        encryptor.confirmPassword(writeWorkbookHolder.getPassword());
        OPCPackage opcPackage = null;
        try {
            opcPackage = OPCPackage.open(file, PackageAccess.READ_WRITE);
            OutputStream outputStream = encryptor.getDataStream(fileSystem);
            opcPackage.save(outputStream);
        } finally {
            opcPackage.close();
        }
        return fileSystem;
    }
}
