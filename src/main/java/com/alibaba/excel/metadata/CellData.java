package com.alibaba.excel.metadata;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.excel.annotation.write.style.ImagePosition;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.property.ImagePositionProperty;
import com.alibaba.excel.util.StringUtils;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;

import lombok.Data;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.RichTextString;

/**
 * Excel internal cell data.
 *
 * <p>
 *
 * @author Jiaju Zhuang
 */
@Data
public class CellData<T> extends AbstractCell {
    private CellDataTypeEnum type;
    /**
     * {@link CellDataTypeEnum#NUMBER}
     */
    private BigDecimal numberValue;
    /**
     * {@link CellDataTypeEnum#STRING} and{@link CellDataTypeEnum#ERROR}
     */
    private String stringValue;
    /**
     * {@link CellDataTypeEnum#BOOLEAN}
     */
    private Boolean booleanValue;

    /**
     * The resulting converted data.
     */
    private T data;

    /**
     * formula
     */
    private FormulaData formulaData;
    /**
     * data format
     */
    private DataFormat dataFormat;

    public CellData() {}

    public CellData(T data) {
        this.data = data;
    }

    public CellData(String stringValue) {
        this(CellDataTypeEnum.STRING, stringValue);
    }

    public CellData(CellDataTypeEnum type, String stringValue) {
        if (type != CellDataTypeEnum.STRING && type != CellDataTypeEnum.ERROR) {
            throw new IllegalArgumentException("Only support CellDataTypeEnum.STRING and  CellDataTypeEnum.ERROR");
        }
        if (stringValue == null) {
            throw new IllegalArgumentException("StringValue can not be null");
        }
        this.type = type;
        this.stringValue = stringValue;
    }

    public CellData(BigDecimal numberValue) {
        if (numberValue == null) {
            throw new IllegalArgumentException("DoubleValue can not be null");
        }
        this.type = CellDataTypeEnum.NUMBER;
        this.numberValue = numberValue;
    }

    public CellData(byte[] imageValue) {
        if (imageValue == null) {
            throw new IllegalArgumentException("ImageValue can not be null");
        }
        this.type = CellDataTypeEnum.IMAGE;
        this.imageValue = imageValue;
    }

    public CellData(byte[] imageValue, ImagePosition imagePosition) {
        if (imageValue == null) {
            throw new IllegalArgumentException("ImageValue can not be null");
        }
        if (imagePosition == null) {
            throw new IllegalArgumentException("ImagePosition can not be null");
        }
        this.type = CellDataTypeEnum.IMAGE;
        this.imageValue = imageValue;
        this.imagePositionProperty = ImagePositionProperty.build(imagePosition);
        this.useImagePositionProperty = true;
        this.formula = Boolean.FALSE;
    }

    public CellData(Boolean booleanValue) {
        if (booleanValue == null) {
            throw new IllegalArgumentException("BooleanValue can not be null");
        }
        this.type = CellDataTypeEnum.BOOLEAN;
        this.booleanValue = booleanValue;
    }

    public CellData(Date dateValue) {
        if (dateValue == null) {
            throw new IllegalArgumentException("DateValue can not be null");
        }
        this.type = CellDataTypeEnum.DATE;
        this.dateValue = dateValue;
        this.formula = Boolean.FALSE;
    }

    public CellData(CellDataTypeEnum type) {
        if (type == null) {
            throw new IllegalArgumentException("Type can not be null");
        }
        this.type = type;
    }

    /**
     * Ensure that the object does not appear null
     */
    public void checkEmpty() {
        if (type == null) {
            type = CellDataTypeEnum.EMPTY;
        }
        switch (type) {
            case STRING:
            case ERROR:
                if (StringUtils.isEmpty(stringValue)) {
                    type = CellDataTypeEnum.EMPTY;
                }
                return;
            case NUMBER:
                if (numberValue == null) {
                    type = CellDataTypeEnum.EMPTY;
                }
                return;
            case BOOLEAN:
                if (booleanValue == null) {
                    type = CellDataTypeEnum.EMPTY;
                }
                return;
            default:
        }
    }

    public static CellData<?> newEmptyInstance() {
        return newEmptyInstance(null, null);
    }

    public static CellData<?> newEmptyInstance(Integer rowIndex, Integer columnIndex) {
        CellData<?> cellData = new CellData<>(CellDataTypeEnum.EMPTY);
        cellData.setRowIndex(rowIndex);
        cellData.setColumnIndex(columnIndex);
        return cellData;
    }

    public static CellData<?> newInstance(Boolean booleanValue) {
        return newInstance(booleanValue, null, null);
    }

    public static CellData<?> newInstance(Boolean booleanValue, Integer rowIndex, Integer columnIndex) {
        CellData<?> cellData = new CellData<>(booleanValue);
        cellData.setRowIndex(rowIndex);
        cellData.setColumnIndex(columnIndex);
        return cellData;
    }

    public static CellData<?> newInstance(String stringValue, Integer rowIndex, Integer columnIndex) {
        CellData<?> cellData = new CellData<>(stringValue);
        cellData.setRowIndex(rowIndex);
        cellData.setColumnIndex(columnIndex);
        return cellData;
    }

    public static CellData<?> newInstance(BigDecimal numberValue, Integer rowIndex, Integer columnIndex) {
        CellData<?> cellData = new CellData<>(numberValue);
        cellData.setRowIndex(rowIndex);
        cellData.setColumnIndex(columnIndex);
        return cellData;
    }

}
