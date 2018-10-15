package javamodel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

public class IdentificationExcel extends BaseRowModel{
	@ExcelProperty(index=0)
	private String materialnumber;
	
	@ExcelProperty(index=17)
	private String unit;
	
	@ExcelProperty(index=42)
	private String specproc;

	public String getMaterialnumber() {
		return materialnumber;
	}

	public void setMaterialnumber(String materialnumber) {
		this.materialnumber = materialnumber;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getSpecproc() {
		return specproc;
	}

	public void setSpecproc(String specproc) {
		this.specproc = specproc;
	}

	@Override
	public String toString() {
		return "IdentificationExcel{" +
			"materialnumber='" + materialnumber + '\'' +
			", unit='" + unit + '\'' +
			", specproc='" + specproc + '\'' +
			'}';
	}
}
