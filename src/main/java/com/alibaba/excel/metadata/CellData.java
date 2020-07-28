package com.alibaba.excel.metadata;

import java.math.BigDecimal;

import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.util.StringUtils;

/**
 * Excel internal cell data.
 *
 * <p>
 *
 * @author Jiaju Zhuang
 */
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
     * The number formatting.Currently only supported when reading
     */
    private Integer dataFormat;
    /**
     * The string of number formatting.Currently only supported when reading
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

    public CellDataTypeEnum getType() {
        return type;
    }

    public void setType(CellDataTypeEnum type) {
        this.type = type;
    }

    public BigDecimal getNumberValue() {
        return numberValue;
    }

    public void setNumberValue(BigDecimal numberValue) {
        this.numberValue = numberValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public Boolean getBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(Boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public Boolean getFormula() {
        return formula;
    }

    public void setFormula(Boolean formula) {
        this.formula = formula;
    }

    public String getFormulaValue() {
        return formulaValue;
    }

    public void setFormulaValue(String formulaValue) {
        this.formulaValue = formulaValue;
    }

    public byte[] getImageValue() {
        return imageValue;
    }

    public void setImageValue(byte[] imageValue) {
        this.imageValue = imageValue;
    }

    public Integer getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(Integer dataFormat) {
        this.dataFormat = dataFormat;
    }

    public String getDataFormatString() {
        return dataFormatString;
    }

    public void setDataFormatString(String dataFormatString) {
        this.dataFormatString = dataFormatString;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
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
                return numberValue.toString();
            case BOOLEAN:
                return booleanValue.toString();
            case DIRECT_STRING:
            case STRING:
            case ERROR:
                return stringValue;
            case IMAGE:
                return "image[" + imageValue.length + "]";
            default:
                return StringUtils.EMPTY;
        }
    }

}
