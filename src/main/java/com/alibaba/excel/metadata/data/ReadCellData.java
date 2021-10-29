package com.alibaba.excel.metadata.data;

import java.math.BigDecimal;

import com.alibaba.excel.enums.CellDataTypeEnum;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * read cell data
 *
 * @author Jiaju Zhuang
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class ReadCellData<T> extends CellData<T> {
    /**
     * data format.
     */
    private DataFormatData dataFormatData;

    public ReadCellData(CellDataTypeEnum type) {
        super();
        if (type == null) {
            throw new IllegalArgumentException("Type can not be null");
        }
        setType(type);
    }

    public ReadCellData(T data) {
        super();
        setData(data);
    }

    public ReadCellData(String stringValue) {
        this(CellDataTypeEnum.STRING, stringValue);
    }

    public ReadCellData(CellDataTypeEnum type, String stringValue) {
        super();
        if (type != CellDataTypeEnum.STRING && type != CellDataTypeEnum.ERROR) {
            throw new IllegalArgumentException("Only support CellDataTypeEnum.STRING and  CellDataTypeEnum.ERROR");
        }
        if (stringValue == null) {
            throw new IllegalArgumentException("StringValue can not be null");
        }
        setType(type);
        setStringValue(stringValue);
    }

    public ReadCellData(BigDecimal numberValue) {
        super();
        if (numberValue == null) {
            throw new IllegalArgumentException("DoubleValue can not be null");
        }
        setType(CellDataTypeEnum.NUMBER);
        setNumberValue(numberValue);
    }

    public ReadCellData(Boolean booleanValue) {
        super();
        if (booleanValue == null) {
            throw new IllegalArgumentException("BooleanValue can not be null");
        }
        setType(CellDataTypeEnum.BOOLEAN);
        setBooleanValue(booleanValue);
    }

    public static ReadCellData<?> newEmptyInstance() {
        return newEmptyInstance(null, null);
    }

    public static ReadCellData<?> newEmptyInstance(Integer rowIndex, Integer columnIndex) {
        ReadCellData<?> cellData = new ReadCellData<>(CellDataTypeEnum.EMPTY);
        cellData.setRowIndex(rowIndex);
        cellData.setColumnIndex(columnIndex);
        return cellData;
    }

    public static ReadCellData<?> newInstance(Boolean booleanValue) {
        return newInstance(booleanValue, null, null);
    }

    public static ReadCellData<?> newInstance(Boolean booleanValue, Integer rowIndex, Integer columnIndex) {
        ReadCellData<?> cellData = new ReadCellData<>(booleanValue);
        cellData.setRowIndex(rowIndex);
        cellData.setColumnIndex(columnIndex);
        return cellData;
    }

    public static ReadCellData<?> newInstance(String stringValue, Integer rowIndex, Integer columnIndex) {
        ReadCellData<?> cellData = new ReadCellData<>(stringValue);
        cellData.setRowIndex(rowIndex);
        cellData.setColumnIndex(columnIndex);
        return cellData;
    }

    public static ReadCellData<?> newInstance(BigDecimal numberValue, Integer rowIndex, Integer columnIndex) {
        ReadCellData<?> cellData = new ReadCellData<>(numberValue);
        cellData.setRowIndex(rowIndex);
        cellData.setColumnIndex(columnIndex);
        return cellData;
    }

    @Override
    public ReadCellData<Object> clone() {
        ReadCellData<Object> readCellData = new ReadCellData<>();
        readCellData.setType(getType());
        readCellData.setNumberValue(getNumberValue());
        readCellData.setStringValue(getStringValue());
        readCellData.setBooleanValue(getBooleanValue());
        readCellData.setData(getData());
        if (getDataFormatData() != null) {
            readCellData.setDataFormatData(getDataFormatData().clone());
        }
        if (getFormulaData() != null) {
            readCellData.setFormulaData(getFormulaData().clone());
        }
        return readCellData;
    }

}
