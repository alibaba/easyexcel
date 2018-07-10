package com.alibaba.excel.write;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.ExcelColumnProperty;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.EasyExcelTempFile;
import com.alibaba.excel.write.context.GenerateContext;
import com.alibaba.excel.write.context.GenerateContextImpl;
import com.alibaba.excel.write.exception.ExcelGenerateException;

import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory;

/**
 * @author jipengfei
 */
public class ExcelBuilderImpl implements ExcelBuilder 
{
	private Log log = LogFactory.getLog( this.getClass());
	
    private GenerateContext context;

    public void init(OutputStream out, ExcelTypeEnum excelType, boolean needHead) {
        //初始化时候创建临时缓存目录，用于规避POI在并发写bug
        EasyExcelTempFile.createPOIFilesDirectory();

        context = new GenerateContextImpl(out, excelType, needHead);
    }

    public void addContent(List data) {
        if (data != null && data.size() > 0) {
            int rowNum = context.getCurrentSheet().getLastRowNum();
            if (rowNum == 0) {
                Row row = context.getCurrentSheet().getRow(0);
                if(row ==null) {
                    if (context.getExcelHeadProperty() == null || !context.needHead()) {
                        rowNum = -1;
                    }
                }
            }
            for (int i = 0; i < data.size(); i++) {
                int n = i + rowNum + 1;
                addOneRowOfDataToExcel(data.get(i), n);
            }
        }
    }

    public void addContent(List data, Sheet sheetParam) {
        context.buildCurrentSheet(sheetParam);
        addContent(data);
    }

    public void addContent(List data, Sheet sheetParam, Table table) {
        context.buildCurrentSheet(sheetParam);
        context.buildTable(table);
        addContent(data);
    }

    public void finish() {
        try {
            context.getWorkbook().write(context.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addOneRowOfDataToExcel(List<String> oneRowData, Row row) {
        if (oneRowData != null && oneRowData.size() > 0) {
            for (int i = 0; i < oneRowData.size(); i++) {
                Cell cell = row.createCell(i);
                cell.setCellStyle(context.getCurrentContentStyle());
                cell.setCellValue(oneRowData.get(i));
            }
        }
    }

    private void addOneRowOfDataToExcel(Object oneRowData, Row row) {
        int i = 0;
        for (ExcelColumnProperty excelHeadProperty : context.getExcelHeadProperty().getColumnPropertyList()) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(context.getCurrentContentStyle());
            String cellValue = null;
            try {
                cellValue = BeanUtils.getProperty(oneRowData, excelHeadProperty.getField().getName());
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (cellValue != null) {
                cell.setCellValue( cellValue);
            } else {
                cell.setCellValue("");
            }
            i++;
        }
    }

    private void addOneRowOfDataToExcel(Object oneRowData, int n) {
        Row row = context.getCurrentSheet().createRow(n);
        if (oneRowData instanceof List) {
            addOneRowOfDataToExcel((List<String>)oneRowData, row);
        } else {
            addOneRowOfDataToExcel(oneRowData, row);
        }
    }
    
    
    public void addMergedContent(List<? extends BaseRowModel> data, Sheet sheetParam,String columnName) {
        context.buildCurrentSheet(sheetParam);
        addMergedContent(data,columnName);
    }

	private void addMergedContent( List<? extends BaseRowModel> set, String columnName) 
	{
		org.apache.poi.ss.usermodel.Sheet sheet = context.getCurrentSheet();
		List<ExcelColumnProperty> properties = context.getExcelHeadProperty().getColumnPropertyList() ;
		List<ExcelColumnProperty> columnProperties = properties.stream().filter( x-> x.getField().getName().equals(columnName)).collect(Collectors.toList()) ;
		
		if( null==columnProperties || 1 > columnProperties.size())
			throw new ExcelGenerateException("column names do not exist");

		ExcelColumnProperty columnProperty = columnProperties.get(0) ;
		
		//排序
    	set = set.stream().sorted((Object x,Object y)->
		{
			int result = -1 ;
			try 
			{
				result = BeanUtils.getProperty( x, columnName).compareTo(
						BeanUtils.getProperty( y, columnName));
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}).collect(Collectors.toList());
		
    	//保存
    	addContent(set) ;
		
		//合并
		//参数说明：1：开始行 2：结束行  3：开始列 4：结束列
		//合并 第二行到第四行的 第六列到第八列 sheet.addMergedRegion(new CellRangeAddress(1,3,5,7));
		int index = 1 ;
		int startRow = 1 ;
		int endRow = 0 ;
		int columnIndex = columnProperty.getIndex() ;
		
		String value = null ;
		
		ArrayList<CellRangeAddress> list = new ArrayList<CellRangeAddress>();
		
		for ( Object x : set )
		{
			try 
			{
				String foo = BeanUtils.getProperty( x, columnName) ;
				
				if( 1==index)
				{
					value = foo ;
					++index;
					continue ;
				}
				 
				if( ! foo.equals(value))
				{
					endRow = index -1;

					if( endRow > startRow)
						list.add( new CellRangeAddress(startRow,endRow,columnIndex,columnIndex) ) ;
					
					startRow = index ; 
					value = foo ;
				} 
				//最后一行 与之前行 等值 情况
				else if( index==set.size()) {
					endRow = index ;
					if( endRow > startRow)
						list.add( new CellRangeAddress(startRow,endRow,columnIndex,columnIndex) ) ;
				} 
				else
					;
				
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			 ++ index;
		}
		
		list.forEach( y -> sheet.addMergedRegion(y));
		
	}

}
