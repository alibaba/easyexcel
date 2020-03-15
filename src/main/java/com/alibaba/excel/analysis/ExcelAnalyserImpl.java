package com.alibaba.excel.analysis;

import java.io.InputStream;
import java.util.List;

import org.apache.poi.hssf.record.crypto.Biff8EncryptionKey;
import org.apache.poi.poifs.crypt.Decryptor;
import org.apache.poi.poifs.filesystem.DocumentFactoryHelper;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.analysis.v03.XlsSaxAnalyser;
import com.alibaba.excel.analysis.v07.XlsxSaxAnalyser;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.context.xls.DefaultXlsReadContext;
import com.alibaba.excel.context.xls.XlsReadContext;
import com.alibaba.excel.context.xlsx.DefaultXlsxReadContext;
import com.alibaba.excel.context.xlsx.XlsxReadContext;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.exception.ExcelAnalysisStopException;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.read.metadata.ReadWorkbook;
import com.alibaba.excel.read.metadata.holder.ReadWorkbookHolder;
import com.alibaba.excel.read.metadata.holder.xls.XlsReadWorkbookHolder;
import com.alibaba.excel.read.metadata.holder.xlsx.XlsxReadWorkbookHolder;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.CollectionUtils;
import com.alibaba.excel.util.DateUtils;
import com.alibaba.excel.util.FileUtils;
import com.alibaba.excel.util.NumberDataFormatterUtils;
import com.alibaba.excel.util.StringUtils;

/**
 * @author jipengfei
 */
public class ExcelAnalyserImpl implements ExcelAnalyser {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelAnalyserImpl.class);

    private AnalysisContext analysisContext;

    private ExcelReadExecutor excelReadExecutor;
    /**
     * Prevent multiple shutdowns
     */
    private boolean finished = false;

    public ExcelAnalyserImpl(ReadWorkbook readWorkbook) {
        try {
            choiceExcelExecutor(readWorkbook);
        } catch (RuntimeException e) {
            finish();
            throw e;
        } catch (Throwable e) {
            finish();
            throw new ExcelAnalysisException(e);
        }
    }

    private void choiceExcelExecutor(ReadWorkbook readWorkbook) throws Exception {
        ExcelTypeEnum excelType = ExcelTypeEnum.valueOf(readWorkbook);
        switch (excelType) {
            case XLS:
                POIFSFileSystem poifsFileSystem;
                if (readWorkbook.getFile() != null) {
                    poifsFileSystem = new POIFSFileSystem(readWorkbook.getFile());
                } else {
                    poifsFileSystem = new POIFSFileSystem(readWorkbook.getInputStream());
                }
                // So in encrypted excel, it looks like XLS but it's actually XLSX
                if (poifsFileSystem.getRoot().hasEntry(Decryptor.DEFAULT_POIFS_ENTRY)) {
                    InputStream decryptedStream = null;
                    try {
                        decryptedStream = DocumentFactoryHelper
                            .getDecryptedStream(poifsFileSystem.getRoot().getFileSystem(), readWorkbook.getPassword());
                        XlsxReadContext xlsxReadContext = new DefaultXlsxReadContext(readWorkbook, ExcelTypeEnum.XLSX);
                        analysisContext = xlsxReadContext;
                        excelReadExecutor = new XlsxSaxAnalyser(xlsxReadContext, decryptedStream);
                        return;
                    } finally {
                        IOUtils.closeQuietly(decryptedStream);
                        // as we processed the full stream already, we can close the filesystem here
                        // otherwise file handles are leaked
                        poifsFileSystem.close();
                    }
                }
                if (readWorkbook.getPassword() != null) {
                    Biff8EncryptionKey.setCurrentUserPassword(readWorkbook.getPassword());
                }
                XlsReadContext xlsReadContext = new DefaultXlsReadContext(readWorkbook, ExcelTypeEnum.XLS);
                xlsReadContext.xlsReadWorkbookHolder().setPoifsFileSystem(poifsFileSystem);
                analysisContext = xlsReadContext;
                excelReadExecutor = new XlsSaxAnalyser(xlsReadContext);
                break;
            case XLSX:
                XlsxReadContext xlsxReadContext = new DefaultXlsxReadContext(readWorkbook, ExcelTypeEnum.XLSX);
                analysisContext = xlsxReadContext;
                excelReadExecutor = new XlsxSaxAnalyser(xlsxReadContext, null);
                break;
            default:
                break;
        }
    }

    @Override
    public void analysis(List<ReadSheet> readSheetList, Boolean readAll) {
        try {
            if (!readAll && CollectionUtils.isEmpty(readSheetList)) {
                throw new IllegalArgumentException("Specify at least one read sheet.");
            }
            analysisContext.readWorkbookHolder().setParameterSheetDataList(readSheetList);
            analysisContext.readWorkbookHolder().setReadAll(readAll);
            try {
                excelReadExecutor.execute();
            } catch (ExcelAnalysisStopException e) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Custom stop!");
                }
            }
        } catch (RuntimeException e) {
            finish();
            throw e;
        } catch (Throwable e) {
            finish();
            throw new ExcelAnalysisException(e);
        }
    }

    @Override
    public void finish() {
        if (finished) {
            return;
        }
        finished = true;
        if (analysisContext == null || analysisContext.readWorkbookHolder() == null) {
            return;
        }
        ReadWorkbookHolder readWorkbookHolder = analysisContext.readWorkbookHolder();

        Throwable throwable = null;

        try {
            if (readWorkbookHolder.getReadCache() != null) {
                readWorkbookHolder.getReadCache().destroy();
            }
        } catch (Throwable t) {
            throwable = t;
        }
        try {
            if ((readWorkbookHolder instanceof XlsxReadWorkbookHolder)
                && ((XlsxReadWorkbookHolder)readWorkbookHolder).getOpcPackage() != null) {
                ((XlsxReadWorkbookHolder)readWorkbookHolder).getOpcPackage().revert();
            }
        } catch (Throwable t) {
            throwable = t;
        }
        try {
            if ((readWorkbookHolder instanceof XlsReadWorkbookHolder)
                && ((XlsReadWorkbookHolder)readWorkbookHolder).getPoifsFileSystem() != null) {
                ((XlsReadWorkbookHolder)readWorkbookHolder).getPoifsFileSystem().close();
            }
        } catch (Throwable t) {
            throwable = t;
        }
        try {
            if (analysisContext.readWorkbookHolder().getAutoCloseStream()
                && readWorkbookHolder.getInputStream() != null) {
                readWorkbookHolder.getInputStream().close();
            }
        } catch (Throwable t) {
            throwable = t;
        }
        try {
            if (readWorkbookHolder.getTempFile() != null) {
                FileUtils.delete(readWorkbookHolder.getTempFile());
            }
        } catch (Throwable t) {
            throwable = t;
        }

        clearEncrypt03();

        removeThreadLocalCache();

        if (throwable != null) {
            throw new ExcelAnalysisException("Can not close IO.", throwable);
        }
    }

    private void removeThreadLocalCache() {
        NumberDataFormatterUtils.removeThreadLocalCache();
        DateUtils.removeThreadLocalCache();
    }

    private void clearEncrypt03() {
        if (StringUtils.isEmpty(analysisContext.readWorkbookHolder().getPassword())
            || !ExcelTypeEnum.XLS.equals(analysisContext.readWorkbookHolder().getExcelType())) {
            return;
        }
        Biff8EncryptionKey.setCurrentUserPassword(null);
    }

    @Override
    public ExcelReadExecutor excelExecutor() {
        return excelReadExecutor;
    }

    @Override
    public AnalysisContext analysisContext() {
        return analysisContext;
    }
}
