package com.alibaba.excel.write.executor;

import java.util.List;

import com.alibaba.excel.context.WriteContext;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ConverterKeyBuild;
import com.alibaba.excel.converters.NullableObjectConverter;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.CommentData;
import com.alibaba.excel.metadata.data.FormulaData;
import com.alibaba.excel.metadata.data.HyperlinkData;
import com.alibaba.excel.metadata.data.ImageData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.FileTypeUtils;
import com.alibaba.excel.util.StyleUtil;
import com.alibaba.excel.util.WriteHandlerUtils;
import com.alibaba.excel.write.metadata.holder.WriteHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Sheet;
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

    protected WriteCellData<?> converterAndSet(WriteHolder currentWriteHolder, Class<?> clazz,
        CellDataTypeEnum targetType, Cell cell, Object value, ExcelContentProperty excelContentProperty, Head head,
        Integer relativeRowIndex) {
        boolean needTrim = value != null && (value instanceof String && currentWriteHolder.globalConfiguration()
            .getAutoTrim());
        if (needTrim) {
            value = ((String)value).trim();
        }
        WriteCellData<?> cellData = convert(currentWriteHolder, clazz, targetType, cell, value, excelContentProperty);
        WriteHandlerUtils.afterCellDataConverted(writeContext, cellData, cell, head, relativeRowIndex, Boolean.FALSE);

        // Fill in picture information
        fillImage(cell, cellData.getImageDataList());

        // Fill in comment information
        fillComment(cell, cellData.getCommentData());

        // Fill in hyper link information
        fillHyperLink(cell, cellData.getHyperlinkData());

        // Fill in formula information
        fillFormula(cell, cellData.getFormulaData());

        // Fill in style information
        fillStyle(cell, cellData.getWriteCellStyle());

        if (cellData.getType() == null) {
            cellData.setType(CellDataTypeEnum.EMPTY);
        }
        switch (cellData.getType()) {
            case STRING:
                cell.setCellValue(cellData.getStringValue());
                return cellData;
            case BOOLEAN:
                cell.setCellValue(cellData.getBooleanValue());
                return cellData;
            case NUMBER:
                cell.setCellValue(cellData.getNumberValue().doubleValue());
                return cellData;
            case DATE:
                cell.setCellValue(cellData.getDateValue());
                return cellData;
            case RICH_TEXT_STRING:
                cell.setCellValue(StyleUtil
                    .buildRichTextString(writeContext.writeWorkbookHolder(), cellData.getRichTextStringDataValue()));
                return cellData;
            case EMPTY:
                return cellData;
            default:
                throw new ExcelDataConvertException(cell.getRow().getRowNum(), cell.getColumnIndex(), cellData,
                    excelContentProperty, "Not supported data:" + value + " return type:" + cell.getCellType()
                    + "at row:" + cell.getRow().getRowNum());
        }

    }

    private void fillStyle(Cell cell, WriteCellStyle writeCellStyle) {
        if (writeCellStyle == null) {
            return;
        }
        CellStyle cellStyle = writeContext.writeWorkbookHolder().createCellStyle(writeCellStyle);
        cell.setCellStyle(cellStyle);
    }

    private void fillFormula(Cell cell, FormulaData formulaData) {
        if (formulaData == null) {
            return;
        }
        if (formulaData.getFormulaValue() != null) {
            cell.setCellFormula(formulaData.getFormulaValue());
        }
    }

    private void fillHyperLink(Cell cell, HyperlinkData hyperlinkData) {
        if (hyperlinkData == null) {
            return;
        }
        CreationHelper helper = cell.getSheet().getWorkbook().getCreationHelper();
        Hyperlink hyperlink = helper.createHyperlink(StyleUtil.getHyperlinkType(hyperlinkData.getHyperlinkType()));
        hyperlink.setAddress(hyperlinkData.getAddress());
        hyperlink.setFirstRow(StyleUtil.getCellCoordinate(cell.getRow().getRowNum(), hyperlinkData.getFirstRowIndex(),
            hyperlinkData.getRelativeFirstRowIndex()));
        hyperlink.setFirstColumn(StyleUtil.getCellCoordinate(cell.getColumnIndex(), hyperlinkData.getFirstColumnIndex(),
            hyperlinkData.getRelativeFirstColumnIndex()));
        hyperlink.setLastRow(StyleUtil.getCellCoordinate(cell.getRow().getRowNum(), hyperlinkData.getLastRowIndex(),
            hyperlinkData.getRelativeLastRowIndex()));
        hyperlink.setLastColumn(StyleUtil.getCellCoordinate(cell.getColumnIndex(), hyperlinkData.getLastColumnIndex(),
            hyperlinkData.getRelativeLastColumnIndex()));
        cell.setHyperlink(hyperlink);
    }

    private void fillComment(Cell cell, CommentData commentData) {
        if (commentData == null) {
            return;
        }
        ClientAnchor anchor;
        if (writeContext.writeWorkbookHolder().getExcelType() == ExcelTypeEnum.XLSX) {
            anchor = new XSSFClientAnchor(StyleUtil.getCoordinate(commentData.getLeft()),
                StyleUtil.getCoordinate(commentData.getTop()),
                StyleUtil.getCoordinate(commentData.getRight()),
                StyleUtil.getCoordinate(commentData.getBottom()),
                StyleUtil.getCellCoordinate(cell.getColumnIndex(), commentData.getFirstColumnIndex(),
                    commentData.getRelativeFirstColumnIndex()),
                StyleUtil.getCellCoordinate(cell.getRow().getRowNum(), commentData.getFirstRowIndex(),
                    commentData.getRelativeFirstRowIndex()),
                StyleUtil.getCellCoordinate(cell.getColumnIndex(), commentData.getLastColumnIndex(),
                    commentData.getRelativeLastColumnIndex()) + 1,
                StyleUtil.getCellCoordinate(cell.getRow().getRowNum(), commentData.getLastRowIndex(),
                    commentData.getRelativeLastRowIndex()) + 1);
        } else {
            anchor = new HSSFClientAnchor(StyleUtil.getCoordinate(commentData.getLeft()),
                StyleUtil.getCoordinate(commentData.getTop()),
                StyleUtil.getCoordinate(commentData.getRight()),
                StyleUtil.getCoordinate(commentData.getBottom()),
                (short)StyleUtil.getCellCoordinate(cell.getColumnIndex(), commentData.getFirstColumnIndex(),
                    commentData.getRelativeFirstColumnIndex()),
                StyleUtil.getCellCoordinate(cell.getRow().getRowNum(), commentData.getFirstRowIndex(),
                    commentData.getRelativeFirstRowIndex()),
                (short)(StyleUtil.getCellCoordinate(cell.getColumnIndex(), commentData.getLastColumnIndex(),
                    commentData.getRelativeLastColumnIndex()) + 1),
                StyleUtil.getCellCoordinate(cell.getRow().getRowNum(), commentData.getLastRowIndex(),
                    commentData.getRelativeLastRowIndex()) + 1);
        }
        Comment comment = cell.getSheet().createDrawingPatriarch().createCellComment(anchor);
        if (commentData.getRichTextStringData() != null) {
            comment.setString(
                StyleUtil.buildRichTextString(writeContext.writeWorkbookHolder(), commentData.getRichTextStringData()));
        }
        if (commentData.getAuthor() != null) {
            comment.setAuthor(commentData.getAuthor());
        }
        cell.setCellComment(comment);
    }

    protected void fillImage(Cell cell, List<ImageData> imageDataList) {
        if (CollectionUtils.isEmpty(imageDataList)) {
            return;
        }
        Sheet sheet = cell.getSheet();
        Drawing<?> drawing = sheet.getDrawingPatriarch();
        if (drawing == null) {
            drawing = sheet.createDrawingPatriarch();
        }
        CreationHelper helper = sheet.getWorkbook().getCreationHelper();
        for (ImageData imageData : imageDataList) {
            int index = sheet.getWorkbook().addPicture(imageData.getImage(),
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
            anchor.setRow1(StyleUtil.getCellCoordinate(cell.getRow().getRowNum(), imageData.getFirstRowIndex(),
                imageData.getRelativeFirstRowIndex()));
            anchor.setCol1(StyleUtil.getCellCoordinate(cell.getColumnIndex(), imageData.getFirstColumnIndex(),
                imageData.getRelativeFirstColumnIndex()));
            anchor.setRow2(StyleUtil.getCellCoordinate(cell.getRow().getRowNum(), imageData.getLastRowIndex(),
                imageData.getRelativeLastRowIndex()) + 1);
            anchor.setCol2(StyleUtil.getCellCoordinate(cell.getColumnIndex(), imageData.getLastColumnIndex(),
                imageData.getRelativeLastColumnIndex()) + 1);
            if (imageData.getAnchorType() != null) {
                anchor.setAnchorType(imageData.getAnchorType().getValue());
            }
            drawing.createPicture(anchor, index);
        }
    }

    protected WriteCellData<?> convert(WriteHolder currentWriteHolder, Class<?> clazz, CellDataTypeEnum targetType,
        Cell cell, Object value, ExcelContentProperty excelContentProperty) {
        // This means that the user has defined the data.
        if (clazz == WriteCellData.class) {
            if (value == null) {
                return new WriteCellData<>(CellDataTypeEnum.EMPTY);
            }
            WriteCellData<?> cellDataValue = (WriteCellData<?>)value;
            if (cellDataValue.getType() != null) {
                return cellDataValue;
            } else {
                if (cellDataValue.getData() == null) {
                    cellDataValue.setType(CellDataTypeEnum.EMPTY);
                    return cellDataValue;
                }
            }
            WriteCellData<?> cellDataReturn = doConvert(currentWriteHolder, cellDataValue.getData().getClass(),
                targetType, cell, cellDataValue.getData(), excelContentProperty);

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
        return doConvert(currentWriteHolder, clazz, targetType, cell, value, excelContentProperty);
    }

    private WriteCellData<?> doConvert(WriteHolder currentWriteHolder, Class<?> clazz, CellDataTypeEnum targetType,
        Cell cell, Object value, ExcelContentProperty excelContentProperty) {
        Converter<?> converter = null;
        if (excelContentProperty != null) {
            converter = excelContentProperty.getConverter();
        }
        if (converter == null) {
            // csv is converted to string by default
            if (writeContext.writeWorkbookHolder().getExcelType() == ExcelTypeEnum.CSV) {
                targetType = CellDataTypeEnum.STRING;
            }
            converter = currentWriteHolder.converterMap().get(ConverterKeyBuild.buildKey(clazz, targetType));
        }
        if (converter == null) {
            throw new ExcelDataConvertException(cell.getRow().getRowNum(), cell.getColumnIndex(),
                new WriteCellData<>(CellDataTypeEnum.EMPTY), excelContentProperty,
                "Can not find 'Converter' support class " + clazz.getSimpleName() + ".");
        }
        if (value == null && !(converter instanceof NullableObjectConverter)) {
            return new WriteCellData<>(CellDataTypeEnum.EMPTY);
        }
        WriteCellData<?> cellData;
        try {
            cellData = ((Converter<Object>)converter).convertToExcelData(
                new WriteConverterContext<>(value, excelContentProperty, writeContext));
        } catch (Exception e) {
            throw new ExcelDataConvertException(cell.getRow().getRowNum(), cell.getColumnIndex(),
                new WriteCellData<>(CellDataTypeEnum.EMPTY), excelContentProperty,
                "Convert data:" + value + " error,at row:" + cell.getRow().getRowNum(), e);
        }
        if (cellData == null || cellData.getType() == null) {
            throw new ExcelDataConvertException(cell.getRow().getRowNum(), cell.getColumnIndex(),
                new WriteCellData<>(CellDataTypeEnum.EMPTY), excelContentProperty,
                "Convert data:" + value + " return null,at row:" + cell.getRow().getRowNum());
        }
        return cellData;
    }
}
