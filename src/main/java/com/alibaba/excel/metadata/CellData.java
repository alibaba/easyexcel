package com.alibaba.excel.metadata;

import com.alibaba.excel.enums.CellDataTypeEnum;

/**
 * excel internal cell data
 *
 * @author zhuangjiaju
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

    /**
     * Support only when reading
     */
    private Boolean readIsFormula;
    /**
     * Support only when reading
     */
    private String readFormula;

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
        this.readIsFormula = Boolean.FALSE;
    }

    public CellData(Double doubleValue) {
        if (doubleValue == null) {
            throw new IllegalArgumentException("DoubleValue can not be null");
        }
        this.type = CellDataTypeEnum.NUMBER;
        this.doubleValue = doubleValue;
        this.readIsFormula = Boolean.FALSE;
    }

    public CellData(Boolean booleanValue) {
        if (booleanValue == null) {
            throw new IllegalArgumentException("BooleanValue can not be null");
        }
        this.type = CellDataTypeEnum.BOOLEAN;
        this.booleanValue = booleanValue;
        this.readIsFormula = Boolean.FALSE;
    }

    public CellData(CellDataTypeEnum type) {
        if (type == null) {
            throw new IllegalArgumentException("Type can not be null");
        }
        this.type = type;
        this.readIsFormula = Boolean.FALSE;
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

    public Boolean getReadIsFormula() {
        return readIsFormula;
    }

    public void setReadIsFormula(Boolean readIsFormula) {
        this.readIsFormula = readIsFormula;
    }

    public String getReadFormula() {
        return readFormula;
    }

    public void setReadFormula(String readFormula) {
        this.readFormula = readFormula;
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
