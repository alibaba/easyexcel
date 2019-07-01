package com.alibaba.excel;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.alibaba.excel.converters.BooleanConverter;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ConverterRegistryCenter;
import com.alibaba.excel.converters.DateConverter;
import com.alibaba.excel.converters.DoubleConverter;
import com.alibaba.excel.converters.FloatConverter;
import com.alibaba.excel.converters.IntegerConverter;
import com.alibaba.excel.converters.LongConverter;
import com.alibaba.excel.converters.StringConverter;
import com.alibaba.excel.event.WriteHandler;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.parameter.GenerateParam;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.ExcelBuilder;
import com.alibaba.excel.write.ExcelBuilderImpl;
import com.alibaba.excel.write.MergeStrategy;

/**
 * Excel Writer This tool is used to write value out to Excel via POI. This object can perform the
 * following two functions.
 * 
 * <pre>
 *    1. Create a new empty Excel workbook, write the value to the stream after the value is filled.
 *    2. Edit existing Excel, write the original Excel file, or write it to other places.}
 * </pre>
 * 
 * @author jipengfei
 */
public class ExcelWriter {

    private ExcelBuilder excelBuilder;

    /**
     * Create new writer
     * 
     * @param outputStream the java OutputStream you wish to write the value to
     * @param typeEnum 03 or 07
     */
    public ExcelWriter(OutputStream outputStream, ExcelTypeEnum typeEnum) {
        this(outputStream, typeEnum, true);
    }

    @Deprecated
    private Class<? extends BaseRowModel> objectClass;

    /**
     * @param generateParam
     */
    @Deprecated
    public ExcelWriter(GenerateParam generateParam) {
        this(generateParam.getOutputStream(), generateParam.getType(), generateParam.isNeedHead());
        this.objectClass = generateParam.getClazz();
    }

    /**
     *
     * Create new writer
     * 
     * @param outputStream the java OutputStream you wish to write the value to
     * @param typeEnum 03 or 07
     * @param needHead Do you need to write the header to the file?
     */
    public ExcelWriter(OutputStream outputStream, ExcelTypeEnum typeEnum, boolean needHead) {
        this(null, outputStream, typeEnum, needHead, null, null);
    }

    /**
     * Create new writer
     * 
     * @param templateInputStream Append value after a POI file ,Can be null（the template POI
     *        filesystem that contains the Workbook stream)
     * @param outputStream the java OutputStream you wish to write the value to
     * @param typeEnum 03 or 07
     */
    public ExcelWriter(InputStream templateInputStream, OutputStream outputStream, ExcelTypeEnum typeEnum,
                    Boolean needHead) {
        this(templateInputStream, outputStream, typeEnum, needHead, null, null);
    }


    /**
     * Create new writer
     * 
     * @param templateInputStream Append value after a POI file ,Can be null（the template POI
     *        filesystem that contains the Workbook stream)
     * @param outputStream the java OutputStream you wish to write the value to
     * @param typeEnum 03 or 07
     * @param writeHandler User-defined callback
     */
    public ExcelWriter(InputStream templateInputStream, OutputStream outputStream, ExcelTypeEnum typeEnum,
                    Boolean needHead, WriteHandler writeHandler) {
        this(templateInputStream, outputStream, typeEnum, needHead, writeHandler, null);
    }

    public ExcelWriter(InputStream templateInputStream, OutputStream outputStream, ExcelTypeEnum typeEnum,
                    Boolean needHead, WriteHandler writeHandler, List<Converter> converters) {
        excelBuilder = new ExcelBuilderImpl(templateInputStream, outputStream, typeEnum, needHead, writeHandler,
                        converters);
        if (this.excelBuilder instanceof ConverterRegistryCenter) {
            ConverterRegistryCenter registryCenter = (ConverterRegistryCenter)this.excelBuilder;
            initConverters(registryCenter, converters);
        }
    }

    private void initConverters(ConverterRegistryCenter registryCenter, List<Converter> converters) {
        registerDefaultConverters(registryCenter);
        if (converters != null && converters.size() > 0) {
            for (Converter c : converters) {
                registryCenter.register(c);
            }
        }
    }
    private void registerDefaultConverters(ConverterRegistryCenter registryCenter) {
        registryCenter.register(new StringConverter());
        registryCenter.register(new DateConverter(null));
        registryCenter.register(new IntegerConverter());
        registryCenter.register(new DoubleConverter());
        registryCenter.register(new LongConverter());
        registryCenter.register(new FloatConverter());
        registryCenter.register(new BooleanConverter());
    }
    /**
     * Write value to a sheet
     * 
     * @param data Data to be written
     * @param sheet Write to this sheet
     * @return this current writer
     */
    public ExcelWriter write(List<? extends BaseRowModel> data, Sheet sheet) {
        excelBuilder.addContent(data, sheet);
        return this;
    }


    /**
     * Write value to a sheet
     * 
     * @param data Data to be written
     * @return this current writer
     */
    @Deprecated
    public ExcelWriter write(List data) {
        if (objectClass != null) {
            return this.write(data, new Sheet(1, 0, objectClass));
        } else {
            return this.write0(data, new Sheet(1, 0, objectClass));

        }
    }

    /**
     *
     * Write value to a sheet
     * 
     * @param data Data to be written
     * @param sheet Write to this sheet
     * @return this
     */
    public ExcelWriter write1(List<List<Object>> data, Sheet sheet) {
        excelBuilder.addContent(data, sheet);
        return this;
    }

    /**
     * Write value to a sheet
     * 
     * @param data Data to be written
     * @param sheet Write to this sheet
     * @return this
     */
    public ExcelWriter write0(List<List<String>> data, Sheet sheet) {
        excelBuilder.addContent(data, sheet);
        return this;
    }

    /**
     * Write value to a sheet
     * 
     * @param data Data to be written
     * @param sheet Write to this sheet
     * @param table Write to this table
     * @return this
     */
    public ExcelWriter write(List<? extends BaseRowModel> data, Sheet sheet, Table table) {
        excelBuilder.addContent(data, sheet, table);
        return this;
    }

    /**
     * Write value to a sheet
     * 
     * @param data Data to be written
     * @param sheet Write to this sheet
     * @param table Write to this table
     * @return this
     */
    public ExcelWriter write0(List<List<String>> data, Sheet sheet, Table table) {
        excelBuilder.addContent(data, sheet, table);
        return this;
    }

    /**
     * Merge Cells，Indexes are zero-based.
     *
     * @param strategies the merge strategies.
     */
    public ExcelWriter merge(List<MergeStrategy> strategies) {
        excelBuilder.merge(strategies);
        return this;
    }

    /**
     * Write value to a sheet
     * 
     * @param data Data to be written
     * @param sheet Write to this sheet
     * @param table Write to this table
     * @return
     */
    public ExcelWriter write1(List<List<Object>> data, Sheet sheet, Table table) {
        excelBuilder.addContent(data, sheet, table);
        return this;
    }

    /**
     * Close IO
     */
    public void finish() {
        excelBuilder.finish();
    }
}
