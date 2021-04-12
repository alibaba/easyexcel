package com.alibaba.excel.metadata;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.util.StringUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * Excel internal cell data.
 *
 * <p>
 *
 * @author Jiaju Zhuang
 */
@Getter
@Setter
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
    private Boolean formula;
    private String formulaValue;
    private byte[] imageValue;
    /**
     * Support only when writing.
     */
    private Date dateValue;
    /**
     * The number formatting.
     */
    private Short dataFormat;
    /**
     * The string of number formatting.
     */
    private String dataFormatString;
    /**
     * The resulting converted data.
     */
    private T data;

    public CellData(CellData<T> other) {
        this.type = other.type;
        this.numberValue = other.numberValue;
        this.stringValue = other.stringValue;
        this.booleanValue = other.booleanValue;
        this.formula = other.formula;
        this.formulaValue = other.formulaValue;
        this.imageValue = other.imageValue;
        this.dataFormat = other.dataFormat;
        this.dataFormatString = other.dataFormatString;
        this.data = other.data;
        setRowIndex(other.getRowIndex());
        setColumnIndex(other.getColumnIndex());
    }

    public CellData() {}

    public CellData(T data) {
        this.data = data;
    }

    public CellData(T data, String formulaValue) {
        this.data = data;
        this.formula = Boolean.TRUE;
        this.formulaValue = formulaValue;
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
        this.formula = Boolean.FALSE;
    }

    public CellData(BigDecimal numberValue) {
        if (numberValue == null) {
            throw new IllegalArgumentException("DoubleValue can not be null");
        }
        this.type = CellDataTypeEnum.NUMBER;
        this.numberValue = numberValue;
        this.formula = Boolean.FALSE;
    }

    public CellData(byte[] imageValue) {
        if (imageValue == null) {
            throw new IllegalArgumentException("ImageValue can not be null");
        }
        this.type = CellDataTypeEnum.IMAGE;
        this.imageValue = imageValue;
        this.formula = Boolean.FALSE;
    }

    public CellData(Boolean booleanValue) {
        if (booleanValue == null) {
            throw new IllegalArgumentException("BooleanValue can not be null");
        }
        this.type = CellDataTypeEnum.BOOLEAN;
        this.booleanValue = booleanValue;
        this.formula = Boolean.FALSE;
    }

    public CellData(CellDataTypeEnum type) {
        if (type == null) {
            throw new IllegalArgumentException("Type can not be null");
        }
        this.type = type;
        this.formula = Boolean.FALSE;
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

    public static CellData newEmptyInstance() {
        return newEmptyInstance(null, null);
    }

    public static CellData newEmptyInstance(Integer rowIndex, Integer columnIndex) {
        CellData cellData = new CellData(CellDataTypeEnum.EMPTY);
        cellData.setRowIndex(rowIndex);
        cellData.setColumnIndex(columnIndex);
        return cellData;
    }

    public static CellData newInstance(Boolean booleanValue) {
        return newInstance(booleanValue, null, null);
    }

    public static CellData newInstance(Boolean booleanValue, Integer rowIndex, Integer columnIndex) {
        CellData cellData = new CellData(booleanValue);
        cellData.setRowIndex(rowIndex);
        cellData.setColumnIndex(columnIndex);
        return cellData;
    }

    public static CellData newInstance(String stringValue, Integer rowIndex, Integer columnIndex) {
        CellData cellData = new CellData(stringValue);
        cellData.setRowIndex(rowIndex);
        cellData.setColumnIndex(columnIndex);
        return cellData;
    }

    public static CellData newInstance(BigDecimal numberValue, Integer rowIndex, Integer columnIndex) {
        CellData cellData = new CellData(numberValue);
        cellData.setRowIndex(rowIndex);
        cellData.setColumnIndex(columnIndex);
        return cellData;
    }

    @Override
    public String toString() {
        if (type == null) {
            return StringUtils.EMPTY;
        }
        switch (type) {
            case NUMBER:
                if (numberValue == null) {
                    return StringUtils.EMPTY;
                }
                return numberValue.toString();
            case BOOLEAN:
                if (booleanValue == null) {
                    return StringUtils.EMPTY;
                }
                return booleanValue.toString();
            case DIRECT_STRING:
            case STRING:
            case ERROR:
                return stringValue;
            case DATE:
                if (dateValue == null) {
                    return StringUtils.EMPTY;
                }
                return dateValue.toString();
            case IMAGE:
                if (imageValue == null) {
                    return StringUtils.EMPTY;
                }
                return "image[" + imageValue.length + "]";
            default:
                return StringUtils.EMPTY;
        }
    }

}
