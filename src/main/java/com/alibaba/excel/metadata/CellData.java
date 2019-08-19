package com.alibaba.excel.metadata;

import com.alibaba.excel.enums.CellDataTypeEnum;

/**
 * excel internal cell data
 *
 * @author Jiaju Zhuang
 */
public class CellData {
    private CellDataTypeEnum type;
    /**
     * {@link CellDataTypeEnum#NUMBER}
     */
    private Double doubleValue;
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

    public CellData(CellData other) {
        this.type = other.type;
        this.doubleValue = other.doubleValue;
        this.stringValue = other.stringValue;
        this.booleanValue = other.booleanValue;
        this.formula = other.formula;
        this.formulaValue = other.formulaValue;
        this.imageValue = other.imageValue;
        this.dataFormat = other.dataFormat;
        this.dataFormatString = other.dataFormatString;
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

    public CellData(Double doubleValue) {
        if (doubleValue == null) {
            throw new IllegalArgumentException("DoubleValue can not be null");
        }
        this.type = CellDataTypeEnum.NUMBER;
        this.doubleValue = doubleValue;
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

    public Double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(Double doubleValue) {
        this.doubleValue = doubleValue;
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

    @Override
    public String toString() {
        switch (type) {
            case NUMBER:
                return doubleValue.toString();
            case BOOLEAN:
                return booleanValue.toString();
            case STRING:
            case ERROR:
                return stringValue;
            default:
                return "empty";
        }
    }

}
