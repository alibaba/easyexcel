package com.alibaba.excel.converters.localdate;

import java.time.LocalDate;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.DateUtils;
import com.alibaba.excel.util.WorkBookUtil;

/**
 * LocalDate converter
 *
 * @author ywzou
 */
public class LocalDateConverter implements Converter<LocalDate> {

	@Override
	public Class<LocalDate> supportJavaTypeKey() {
		return LocalDate.class;
	}

	@Override
	public WriteCellData<?> convertToExcelData(LocalDate value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
		WriteCellData<?> cellData = new WriteCellData<>(value);
		String format = null;
		if (contentProperty != null && contentProperty.getDateTimeFormatProperty() != null) {
			format = contentProperty.getDateTimeFormatProperty().getFormat();
		}
		WorkBookUtil.fillDataFormat(cellData, format, DateUtils.DATE_FORMAT_10);
		return cellData;
	}
}
