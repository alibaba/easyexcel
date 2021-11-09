package com.alibaba.excel.write.executor;

import java.util.List;

import com.alibaba.excel.context.WriteContext;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ConverterKeyBuild;
import com.alibaba.excel.converters.NullableObjectConverter;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.exception.ExcelWriteDataConvertException;
import com.alibaba.excel.metadata.data.CommentData;
import com.alibaba.excel.metadata.data.FormulaData;
import com.alibaba.excel.metadata.data.HyperlinkData;
import com.alibaba.excel.metadata.data.ImageData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.DateUtils;
import com.alibaba.excel.util.FileTypeUtils;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.util.StyleUtil;
import com.alibaba.excel.util.WorkBookUtil;
import com.alibaba.excel.util.WriteHandlerUtils;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;

/**
 * Excel write Executor
 *
 * @author Jiaju Zhuang
 */
public abstract class AbstractExcelWriteExecutor implements ExcelWriteExecutor {
    protected WriteContext writeContext;

    public AbstractExcelWriteExecutor(WriteContext writeContext) {
        this.writeContext = writeContext;
    }

    /**
     * Transform the data and then to set into the cell
     *
     * @param cellWriteHandlerContext context
     */
    protected void converterAndSet(CellWriteHandlerContext cellWriteHandlerContext) {

        WriteCellData<?> cellData = convert(cellWriteHandlerContext);
        cellWriteHandlerContext.setCellDataList(ListUtils.newArrayList(cellData));
        cellWriteHandlerContext.setFirstCellData(cellData);

        WriteHandlerUtils.afterCellDataConverted(cellWriteHandlerContext);

        // Fill in picture information
        fillImage(cellWriteHandlerContext, cellData.getImageDataList());

        // Fill in comment information
        fillComment(cellWriteHandlerContext, cellData.getCommentData());

        // Fill in hyper link information
        fillHyperLink(cellWriteHandlerContext, cellData.getHyperlinkData());

        // Fill in formula information
        fillFormula(cellWriteHandlerContext, cellData.getFormulaData());

        // Fill index
        cellData.setRowIndex(cellWriteHandlerContext.getRowIndex());
        cellData.setColumnIndex(cellWriteHandlerContext.getColumnIndex());

        if (cellData.getType() == null) {
            cellData.setType(CellDataTypeEnum.EMPTY);
        }
        Cell cell = cellWriteHandlerContext.getCell();
        switch (cellData.getType()) {
            case STRING:
                cell.setCellValue(cellData.getStringValue());
                return;
            case BOOLEAN:
                cell.setCellValue(cellData.getBooleanValue());
                return;
            case NUMBER:
                cell.setCellValue(cellData.getNumberValue().doubleValue());
                return;
            case DATE:
                cell.setCellValue(cellData.getDateValue());
                return;
            case RICH_TEXT_STRING:
                cell.setCellValue(StyleUtil
                    .buildRichTextString(writeContext.writeWorkbookHolder(), cellData.getRichTextStringDataValue()));
                return;
            case EMPTY:
                return;
            default:
                throw new ExcelWriteDataConvertException(cellWriteHandlerContext,
                    "Not supported data:" + cellWriteHandlerContext.getOriginalValue() + " return type:"
                        + cellData.getType()
                        + "at row:" + cellWriteHandlerContext.getRowIndex());
        }

    }

    private void fillFormula(CellWriteHandlerContext cellWriteHandlerContext, FormulaData formulaData) {
        if (formulaData == null) {
            return;
        }
        Cell cell = cellWriteHandlerContext.getCell();
        if (formulaData.getFormulaValue() != null) {
            cell.setCellFormula(formulaData.getFormulaValue());
        }
    }

    private void fillHyperLink(CellWriteHandlerContext cellWriteHandlerContext, HyperlinkData hyperlinkData) {
        if (hyperlinkData == null) {
            return;
        }
        Integer rowIndex = cellWriteHandlerContext.getRowIndex();
        Integer columnIndex = cellWriteHandlerContext.getColumnIndex();
        Workbook workbook = cellWriteHandlerContext.getWriteWorkbookHolder().getWorkbook();
        Cell cell = cellWriteHandlerContext.getCell();

        CreationHelper helper = workbook.getCreationHelper();
        Hyperlink hyperlink = helper.createHyperlink(StyleUtil.getHyperlinkType(hyperlinkData.getHyperlinkType()));
        hyperlink.setAddress(hyperlinkData.getAddress());
        hyperlink.setFirstRow(StyleUtil.getCellCoordinate(rowIndex, hyperlinkData.getFirstRowIndex(),
            hyperlinkData.getRelativeFirstRowIndex()));
        hyperlink.setFirstColumn(StyleUtil.getCellCoordinate(columnIndex, hyperlinkData.getFirstColumnIndex(),
            hyperlinkData.getRelativeFirstColumnIndex()));
        hyperlink.setLastRow(StyleUtil.getCellCoordinate(rowIndex, hyperlinkData.getLastRowIndex(),
            hyperlinkData.getRelativeLastRowIndex()));
        hyperlink.setLastColumn(StyleUtil.getCellCoordinate(columnIndex, hyperlinkData.getLastColumnIndex(),
            hyperlinkData.getRelativeLastColumnIndex()));
        cell.setHyperlink(hyperlink);
    }

    private void fillComment(CellWriteHandlerContext cellWriteHandlerContext, CommentData commentData) {
        if (commentData == null) {
            return;
        }
        ClientAnchor anchor;
        Integer rowIndex = cellWriteHandlerContext.getRowIndex();
        Integer columnIndex = cellWriteHandlerContext.getColumnIndex();
        Sheet sheet = cellWriteHandlerContext.getWriteSheetHolder().getSheet();
        Cell cell = cellWriteHandlerContext.getCell();

        if (writeContext.writeWorkbookHolder().getExcelType() == ExcelTypeEnum.XLSX) {
            anchor = new XSSFClientAnchor(StyleUtil.getCoordinate(commentData.getLeft()),
                StyleUtil.getCoordinate(commentData.getTop()),
                StyleUtil.getCoordinate(commentData.getRight()),
                StyleUtil.getCoordinate(commentData.getBottom()),
                StyleUtil.getCellCoordinate(columnIndex, commentData.getFirstColumnIndex(),
                    commentData.getRelativeFirstColumnIndex()),
                StyleUtil.getCellCoordinate(rowIndex, commentData.getFirstRowIndex(),
                    commentData.getRelativeFirstRowIndex()),
                StyleUtil.getCellCoordinate(columnIndex, commentData.getLastColumnIndex(),
                    commentData.getRelativeLastColumnIndex()) + 1,
                StyleUtil.getCellCoordinate(rowIndex, commentData.getLastRowIndex(),
                    commentData.getRelativeLastRowIndex()) + 1);
        } else {
            anchor = new HSSFClientAnchor(StyleUtil.getCoordinate(commentData.getLeft()),
                StyleUtil.getCoordinate(commentData.getTop()),
                StyleUtil.getCoordinate(commentData.getRight()),
                StyleUtil.getCoordinate(commentData.getBottom()),
                (short)StyleUtil.getCellCoordinate(columnIndex, commentData.getFirstColumnIndex(),
                    commentData.getRelativeFirstColumnIndex()),
                StyleUtil.getCellCoordinate(rowIndex, commentData.getFirstRowIndex(),
                    commentData.getRelativeFirstRowIndex()),
                (short)(StyleUtil.getCellCoordinate(columnIndex, commentData.getLastColumnIndex(),
                    commentData.getRelativeLastColumnIndex()) + 1),
                StyleUtil.getCellCoordinate(rowIndex, commentData.getLastRowIndex(),
                    commentData.getRelativeLastRowIndex()) + 1);
        }

        Comment comment = sheet.createDrawingPatriarch().createCellComment(anchor);
        if (commentData.getRichTextStringData() != null) {
            comment.setString(
                StyleUtil.buildRichTextString(writeContext.writeWorkbookHolder(), commentData.getRichTextStringData()));
        }
        if (commentData.getAuthor() != null) {
            comment.setAuthor(commentData.getAuthor());
        }
        cell.setCellComment(comment);
    }

    protected void fillImage(CellWriteHandlerContext cellWriteHandlerContext, List<ImageData> imageDataList) {
        if (CollectionUtils.isEmpty(imageDataList)) {
            return;
        }
        Integer rowIndex = cellWriteHandlerContext.getRowIndex();
        Integer columnIndex = cellWriteHandlerContext.getColumnIndex();
        Sheet sheet = cellWriteHandlerContext.getWriteSheetHolder().getSheet();
        Workbook workbook = cellWriteHandlerContext.getWriteWorkbookHolder().getWorkbook();

        Drawing<?> drawing = sheet.getDrawingPatriarch();
        if (drawing == null) {
            drawing = sheet.createDrawingPatriarch();
        }
        CreationHelper helper = sheet.getWorkbook().getCreationHelper();
        for (ImageData imageData : imageDataList) {
            int index = workbook.addPicture(imageData.getImage(),
                FileTypeUtils.getImageTypeFormat(imageData.getImage()));
            ClientAnchor anchor = helper.createClientAnchor();
            if (imageData.getTop() != null) {
                anchor.setDy1(StyleUtil.getCoordinate(imageData.getTop()));
            }
            if (imageData.getRight() != null) {
                anchor.setDx2(-StyleUtil.getCoordinate(imageData.getRight()));
            }
            if (imageData.getBottom() != null) {
                anchor.setDy2(-StyleUtil.getCoordinate(imageData.getBottom()));
            }
            if (imageData.getLeft() != null) {
                anchor.setDx1(StyleUtil.getCoordinate(imageData.getLeft()));
            }
            anchor.setRow1(StyleUtil.getCellCoordinate(rowIndex, imageData.getFirstRowIndex(),
                imageData.getRelativeFirstRowIndex()));
            anchor.setCol1(StyleUtil.getCellCoordinate(columnIndex, imageData.getFirstColumnIndex(),
                imageData.getRelativeFirstColumnIndex()));
            anchor.setRow2(StyleUtil.getCellCoordinate(rowIndex, imageData.getLastRowIndex(),
                imageData.getRelativeLastRowIndex()) + 1);
            anchor.setCol2(StyleUtil.getCellCoordinate(columnIndex, imageData.getLastColumnIndex(),
                imageData.getRelativeLastColumnIndex()) + 1);
            if (imageData.getAnchorType() != null) {
                anchor.setAnchorType(imageData.getAnchorType().getValue());
            }
            drawing.createPicture(anchor, index);
        }
    }

    protected WriteCellData<?> convert(CellWriteHandlerContext cellWriteHandlerContext) {
        // This means that the user has defined the data.
        if (cellWriteHandlerContext.getOriginalFieldClass() == WriteCellData.class) {
            if (cellWriteHandlerContext.getOriginalValue() == null) {
                return new WriteCellData<>(CellDataTypeEnum.EMPTY);
            }
            WriteCellData<?> cellDataValue = (WriteCellData<?>)cellWriteHandlerContext.getOriginalValue();
            if (cellDataValue.getType() != null) {
                // Configuration information may not be read here
                fillProperty(cellDataValue, cellWriteHandlerContext.getExcelContentProperty());

                return cellDataValue;
            } else {
                if (cellDataValue.getData() == null) {
                    cellDataValue.setType(CellDataTypeEnum.EMPTY);
                    return cellDataValue;
                }
            }
            WriteCellData<?> cellDataReturn = doConvert(cellWriteHandlerContext);

            if (cellDataValue.getImageDataList() != null) {
                cellDataReturn.setImageDataList(cellDataValue.getImageDataList());
            }
            if (cellDataValue.getCommentData() != null) {
                cellDataReturn.setCommentData(cellDataValue.getCommentData());
            }
            if (cellDataValue.getHyperlinkData() != null) {
                cellDataReturn.setHyperlinkData(cellDataValue.getHyperlinkData());
            }
            // The formula information is subject to user input
            if (cellDataValue.getFormulaData() != null) {
                cellDataReturn.setFormulaData(cellDataValue.getFormulaData());
            }
            if (cellDataValue.getWriteCellStyle() != null) {
                cellDataReturn.setWriteCellStyle(cellDataValue.getWriteCellStyle());
            }
            return cellDataReturn;
        }
        return doConvert(cellWriteHandlerContext);
    }

    private void fillProperty(WriteCellData<?> cellDataValue, ExcelContentProperty excelContentProperty) {
        switch (cellDataValue.getType()) {
            case DATE:
                String dateFormat = null;
                if (excelContentProperty != null && excelContentProperty.getDateTimeFormatProperty() != null) {
                    dateFormat = excelContentProperty.getDateTimeFormatProperty().getFormat();
                }
                WorkBookUtil.fillDataFormat(cellDataValue, dateFormat, DateUtils.defaultDateFormat);
                return;
            case NUMBER:
                String numberFormat = null;
                if (excelContentProperty != null && excelContentProperty.getNumberFormatProperty() != null) {
                    numberFormat = excelContentProperty.getNumberFormatProperty().getFormat();
                }
                WorkBookUtil.fillDataFormat(cellDataValue, numberFormat, null);
                return;
            default:
                return;
        }
    }

    private WriteCellData<?> doConvert(CellWriteHandlerContext cellWriteHandlerContext) {
        ExcelContentProperty excelContentProperty = cellWriteHandlerContext.getExcelContentProperty();

        Converter<?> converter = null;
        if (excelContentProperty != null) {
            converter = excelContentProperty.getConverter();
        }
        if (converter == null) {
            // csv is converted to string by default
            if (writeContext.writeWorkbookHolder().getExcelType() == ExcelTypeEnum.CSV) {
                cellWriteHandlerContext.setTargetCellDataType(CellDataTypeEnum.STRING);
            }
            converter = writeContext.currentWriteHolder().converterMap().get(
                ConverterKeyBuild.buildKey(cellWriteHandlerContext.getOriginalFieldClass(),
                    cellWriteHandlerContext.getTargetCellDataType()));
        }
        if (cellWriteHandlerContext.getOriginalValue() == null && !(converter instanceof NullableObjectConverter)) {
            return new WriteCellData<>(CellDataTypeEnum.EMPTY);
        }
        if (converter == null) {
            throw new ExcelWriteDataConvertException(cellWriteHandlerContext,
                "Can not find 'Converter' support class " + cellWriteHandlerContext.getOriginalFieldClass()
                    .getSimpleName() + ".");
        }
        WriteCellData<?> cellData;
        try {
            cellData = ((Converter<Object>)converter).convertToExcelData(
                new WriteConverterContext<>(cellWriteHandlerContext.getOriginalValue(), excelContentProperty,
                    writeContext));
        } catch (Exception e) {
            throw new ExcelWriteDataConvertException(cellWriteHandlerContext,
                "Convert data:" + cellWriteHandlerContext.getOriginalValue() + " error, at row:"
                    + cellWriteHandlerContext.getRowIndex(), e);
        }
        if (cellData == null || cellData.getType() == null) {
            throw new ExcelWriteDataConvertException(cellWriteHandlerContext,
                "Convert data:" + cellWriteHandlerContext.getOriginalValue()
                    + " return is null or return type is null, at row:"
                    + cellWriteHandlerContext.getRowIndex());
        }
        return cellData;
    }
}
