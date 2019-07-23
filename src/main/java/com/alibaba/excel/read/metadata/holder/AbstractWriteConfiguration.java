package com.alibaba.excel.read.metadata.holder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ConverterKey;
import com.alibaba.excel.enums.HeadKindEnum;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.AbstractHolder;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.listener.ReadListenerRegistryCenter;
import com.alibaba.excel.read.listener.event.AnalysisFinishEvent;
import com.alibaba.excel.read.metadata.ReadBasicParameter;
import com.alibaba.excel.read.metadata.property.ExcelReadHeadProperty;
import com.alibaba.excel.util.StringUtils;

/**
 * Read Holder
 *
 * @author zhuangjiaju
 */
public abstract class AbstractReadHolder extends AbstractHolder implements ReadHolder, ReadListenerRegistryCenter {
    /**
     * Count the number of added heads when read sheet.
     *
     * <li>0 - This Sheet has no head ,since the first row are the data
     * <li>1 - This Sheet has one row head , this is the default
     * <li>2 - This Sheet has two row head ,since the third row is the data
     */
    private Integer headRowNumber;
    /**
     * Excel head property
     */
    private ExcelReadHeadProperty excelReadHeadProperty;
    /**
     * Read listener
     */
    private List<ReadListener> readListenerList;
    /**
     * Converter for workbook
     */
    private Map<ConverterKey, Converter> converterMap;

    public AbstractReadHolder(ReadBasicParameter readBasicParameter, AbstractReadHolder parentAbstractReadHolder,
        Boolean convertAllFiled) {
        super(readBasicParameter, parentAbstractReadHolder);
        if (readBasicParameter.getUse1904windowing() == null && parentAbstractReadHolder != null) {
            getGlobalConfiguration()
                .setUse1904windowing(parentAbstractReadHolder.getGlobalConfiguration().getUse1904windowing());
        } else {
            getGlobalConfiguration().setUse1904windowing(readBasicParameter.getUse1904windowing());
        }

        if (readBasicParameter.getHeadRowNumber() == null) {
            if (parentAbstractReadHolder == null) {
                this.headRowNumber = 1;
            } else {
                this.headRowNumber = parentAbstractReadHolder.getHeadRowNumber();
            }
        } else {
            this.headRowNumber = readBasicParameter.getHeadRowNumber();
        }
        // Initialization property
        this.excelReadHeadProperty = new ExcelReadHeadProperty(getClazz(), getHead(), convertAllFiled);

        if (parentAbstractReadHolder == null) {
            this.readListenerList = new ArrayList<ReadListener>();
        } else {
            this.readListenerList = new ArrayList<ReadListener>(parentAbstractReadHolder.getReadListenerList());
        }
        if (readBasicParameter.getCustomReadListenerList() != null
            && !readBasicParameter.getCustomReadListenerList().isEmpty()) {
            this.readListenerList.addAll(readBasicParameter.getCustomReadListenerList());
        }

        if (parentAbstractReadHolder == null) {
            this.converterMap = new HashMap<ConverterKey, Converter>();
        } else {
            this.converterMap = new HashMap<ConverterKey, Converter>(parentAbstractReadHolder.getConverterMap());
        }
        if (readBasicParameter.getCustomConverterList() != null
            && !readBasicParameter.getCustomConverterList().isEmpty()) {
            for (Converter converter : readBasicParameter.getCustomConverterList()) {
                converterMap.put(ConverterKey.buildConverterKey(converter), converter);
            }
        }
    }

    @Override
    public void register(AnalysisEventListener listener) {
        readListenerList.add(listener);
    }

    @Override
    public void notifyEndOneRow(AnalysisFinishEvent event, AnalysisContext analysisContext) {
        List<CellData> cellDataList = (List<CellData>)event.getAnalysisResult();
        if (analysisContext.currentRowHolder().getRow().getRowNum() > analysisContext.currentSheetHolder()
            .getReadHeadRowNumber()) {
            for (ReadListener readListener : readListenerList) {
                try {
                    readListener.invoke(analysisContext.currentRowAnalysisResult(), analysisContext);
                } catch (Exception e) {
                    for (ReadListener readListenerException : readListenerList) {
                        try {
                            readListenerException.onException(e, analysisContext);
                        } catch (Exception exception) {
                            throw new ExcelAnalysisException("Listen error!", exception);
                        }
                    }
                }
            }
        }
        // Now is header
        if (analysisContext.currentRowNum().equals(analysisContext.currentSheetHolder().getReadHeadRowNumber())) {
            buildHead(analysisContext, cellDataList);
        }
    }

    @Override
    public void notifyAfterAllAnalysed(AnalysisContext analysisContext) {
        for (ReadListener readListener : readListenerList) {
            readListener.doAfterAllAnalysed(analysisContext);
        }
    }

    private void buildHead(AnalysisContext analysisContext, List<CellData> cellDataList) {
        if (!HeadKindEnum.CLASS.equals(analysisContext.currentConfiguration().excelReadHeadProperty().getHeadKind())) {
            return;
        }
        List<String> dataList = (List<String>)buildStringList(cellDataList, analysisContext.currentConfiguration());
        ExcelReadHeadProperty excelHeadPropertyData = analysisContext.currentConfiguration().excelReadHeadProperty();
        Map<Integer, Head> headMapData = excelHeadPropertyData.getHeadMap();
        Map<Integer, ExcelContentProperty> contentPropertyMapData = excelHeadPropertyData.getContentPropertyMap();
        Map<Integer, Head> tmpHeadMap = new HashMap<Integer, Head>(headMapData.size() * 4 / 3 + 1);
        Map<Integer, ExcelContentProperty> tmpContentPropertyMap =
            new HashMap<Integer, ExcelContentProperty>(contentPropertyMapData.size() * 4 / 3 + 1);
        for (Map.Entry<Integer, Head> entry : headMapData.entrySet()) {
            Head headData = entry.getValue();
            if (headData.getForceIndex()) {
                tmpHeadMap.put(entry.getKey(), headData);
                tmpContentPropertyMap.put(entry.getKey(), contentPropertyMapData.get(entry.getKey()));
                continue;
            }
            String headName = headData.getHeadNameList().get(0);
            for (int i = 0; i < dataList.size(); i++) {
                String headString = dataList.get(i);
                if (StringUtils.isEmpty(headString)) {
                    continue;
                }
                if (analysisContext.currentSheetHolder().getAutoTrim()) {
                    headString = headString.trim();
                }
                if (headName.equals(headString)) {
                    headData.setColumnIndex(i);
                    tmpHeadMap.put(i, headData);
                    tmpContentPropertyMap.put(i, contentPropertyMapData.get(entry.getKey()));
                    break;
                }
            }
        }
        excelHeadPropertyData.setHeadMap(tmpHeadMap);
        excelHeadPropertyData.setContentPropertyMap(tmpContentPropertyMap);
    }

    private Object buildStringList(List<CellData> data,
        com.alibaba.excel.read.metadata.read.ReadConfiguration readConfiguration) {
        List<String> list = new ArrayList<String>();
        for (CellData cellData : data) {
            Converter converter = readConfiguration.readConverterMap()
                .get(ConverterKey.buildConverterKey(String.class, cellData.getType()));
            if (converter == null) {
                throw new ExcelDataConvertException(
                    "Converter not found, convert " + cellData.getType() + " to String");
            }
            try {
                list.add((String)(converter.convertToJavaData(cellData, null)));
            } catch (Exception e) {
                throw new ExcelDataConvertException("Convert data " + cellData + " to String error ", e);
            }
        }
        return list;
    }

    public List<ReadListener> getReadListenerList() {
        return readListenerList;
    }

    public void setReadListenerList(List<ReadListener> readListenerList) {
        this.readListenerList = readListenerList;
    }

    public Map<ConverterKey, Converter> getConverterMap() {
        return converterMap;
    }

    public void setConverterMap(Map<ConverterKey, Converter> converterMap) {
        this.converterMap = converterMap;
    }

    public ExcelReadHeadProperty getExcelReadHeadProperty() {
        return excelReadHeadProperty;
    }

    public void setExcelReadHeadProperty(ExcelReadHeadProperty excelReadHeadProperty) {
        this.excelReadHeadProperty = excelReadHeadProperty;
    }

    public Integer getHeadRowNumber() {
        return headRowNumber;
    }

    public void setHeadRowNumber(Integer headRowNumber) {
        this.headRowNumber = headRowNumber;
    }

    @Override
    public List<ReadListener> readListenerList() {
        return getReadListenerList();
    }

    @Override
    public Map<ConverterKey, Converter> converterMap() {
        return getConverterMap();
    }

    @Override
    public ExcelReadHeadProperty excelReadHeadProperty() {
        return getExcelReadHeadProperty();
    }

}
