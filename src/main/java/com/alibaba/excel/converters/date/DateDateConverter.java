package com.alibaba.excel.converters.date;

import java.util.Date;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.WorkBookUtil;
import com.alibaba.excel.write.metadata.holder.WriteHolder;

/**
 * Date and date converter
 *
 * @author Jiaju Zhuang
 */
public class DateDateConverter implements Converter<Date> {
    @Override
    public Class<Date> supportJavaTypeKey() {
        return Date.class;
    }

    @Override
    public CellData<?> convertToExcelData(Date value, ExcelContentProperty contentProperty,
        WriteHolder currentWriteHolder) throws Exception {
        CellData<?> cellData = new CellData<>(value);
        if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null
            || contentProperty.getDateTimeFormatProperty().getFormat() == null) {
            return cellData;
        }
        WorkBookUtil.fillDataFormat(cellData, currentWriteHolder,
            contentProperty.getDateTimeFormatProperty().getFormat());
        return cellData;
    }
}
