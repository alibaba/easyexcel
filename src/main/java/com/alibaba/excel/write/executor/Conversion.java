package com.alibaba.excel.write.executor;

import com.alibaba.excel.context.WriteContext;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ConverterKeyBuild;
import com.alibaba.excel.converters.NullableObjectConverter;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.exception.ExcelWriteDataConvertException;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;

public class Conversion {
    protected static WriteContext writeContext;

    protected static WriteCellData<?> convert(CellWriteHandlerContext cellWriteHandlerContext) {
        // This means that the user has defined the data.
        if (cellWriteHandlerContext.getOriginalFieldClass() == WriteCellData.class) {
            if (cellWriteHandlerContext.getOriginalValue() == null) {
                return new WriteCellData<>(CellDataTypeEnum.EMPTY);
            }
            WriteCellData<?> cellDataValue = (WriteCellData<?>)cellWriteHandlerContext.getOriginalValue();
            if (cellDataValue.getType() != null) {
                // Configuration information may not be read here
                AbstractExcelWriteFiller.fillProperty(cellDataValue, cellWriteHandlerContext.getExcelContentProperty());

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
//

    private static WriteCellData<?> doConvert(CellWriteHandlerContext cellWriteHandlerContext) {
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
