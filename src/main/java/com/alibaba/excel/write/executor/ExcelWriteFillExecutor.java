package com.alibaba.excel.write.executor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.alibaba.excel.context.WriteContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.enums.WriteDirectionEnum;
import com.alibaba.excel.enums.WriteTemplateAnalysisCellTypeEnum;
import com.alibaba.excel.exception.ExcelGenerateException;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.CollectionUtils;
import com.alibaba.excel.util.StringUtils;
import com.alibaba.excel.util.WriteHandlerUtils;
import com.alibaba.excel.write.metadata.fill.AnalysisCell;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.fill.FillWrapper;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;

import net.sf.cglib.beans.BeanMap;

/**
 * Fill the data into excel
 *
 * @author Jiaju Zhuang
 */
public class ExcelWriteFillExecutor extends AbstractExcelWriteExecutor {

    private static final String ESCAPE_FILL_PREFIX = "\\\\\\{";
    private static final String ESCAPE_FILL_SUFFIX = "\\\\\\}";
    private static final String FILL_PREFIX = "{";
    private static final String FILL_SUFFIX = "}";
    private static final char IGNORE_CHAR = '\\';
    private static final String COLLECTION_PREFIX = ".";
    /**
     * Fields to replace in the template
     */
    private final Map<String, List<AnalysisCell>> templateAnalysisCache = new HashMap<String, List<AnalysisCell>>(8);
    /**
     * Collection fields to replace in the template
     */
    private final Map<String, List<AnalysisCell>> templateCollectionAnalysisCache =
        new HashMap<String, List<AnalysisCell>>(8);
    /**
     * Style cache for collection fields
     */
    private final Map<String, Map<AnalysisCell, CellStyle>> collectionFieldStyleCache =
        new HashMap<String, Map<AnalysisCell, CellStyle>>(8);
    /**
     * Row height cache for collection
     */
    private final Map<String, Short> collectionRowHeightCache = new HashMap<String, Short>(8);
    /**
     * Last index cache for collection fields
     */
    private final Map<String, Map<AnalysisCell, Integer>> collectionLastIndexCache =
        new HashMap<String, Map<AnalysisCell, Integer>>(8);

    private final Map<String, Integer> relativeRowIndexMap = new HashMap<String, Integer>(8);
    /**
     * The data prefix that is populated this time
     */
    private String currentDataPrefix;
    /**
     * The unique data encoding for this fill
     */
    private String currentUniqueDataFlag;

    public ExcelWriteFillExecutor(WriteContext writeContext) {
        super(writeContext);
    }

    public void fill(Object data, FillConfig fillConfig) {
        if (data == null) {
            data = new HashMap<String, Object>(16);
        }
        if (fillConfig == null) {
            fillConfig = FillConfig.builder().build(true);
        }
        fillConfig.init();

        Object realData;
        if (data instanceof FillWrapper) {
            FillWrapper fillWrapper = (FillWrapper) data;
            currentDataPrefix = fillWrapper.getName();
            realData = fillWrapper.getCollectionData();
        } else {
            realData = data;
            currentDataPrefix = null;
        }
        currentUniqueDataFlag = uniqueDataFlag(writeContext.writeSheetHolder(), currentDataPrefix);

        // processing data
        if (realData instanceof Collection) {
            List<AnalysisCell> analysisCellList = readTemplateData(templateCollectionAnalysisCache);
            Collection collectionData = (Collection) realData;
            if (CollectionUtils.isEmpty(collectionData)) {
                return;
            }
            Iterator iterator = collectionData.iterator();
            if (WriteDirectionEnum.VERTICAL.equals(fillConfig.getDirection()) && fillConfig.getForceNewRow()) {
                shiftRows(collectionData.size(), analysisCellList);
            }
            while (iterator.hasNext()) {
                doFill(analysisCellList, iterator.next(), fillConfig, getRelativeRowIndex());
            }
        } else {
            doFill(readTemplateData(templateAnalysisCache), realData, fillConfig, null);
        }
    }

    private void shiftRows(int size, List<AnalysisCell> analysisCellList) {
        if (CollectionUtils.isEmpty(analysisCellList)) {
            return;
        }
        int maxRowIndex = 0;
        Map<AnalysisCell, Integer> collectionLastIndexMap = collectionLastIndexCache.get(currentUniqueDataFlag);
        for (AnalysisCell analysisCell : analysisCellList) {
            if (collectionLastIndexMap != null) {
                Integer lastRowIndex = collectionLastIndexMap.get(analysisCell);
                if (lastRowIndex != null) {
                    if (lastRowIndex > maxRowIndex) {
                        maxRowIndex = lastRowIndex;
                    }
                    continue;
                }
            }
            if (analysisCell.getRowIndex() > maxRowIndex) {
                maxRowIndex = analysisCell.getRowIndex();
            }
        }
        Sheet cachedSheet = writeContext.writeSheetHolder().getCachedSheet();
        int lastRowIndex = cachedSheet.getLastRowNum();
        if (maxRowIndex >= lastRowIndex) {
            return;
        }
        Sheet sheet = writeContext.writeSheetHolder().getCachedSheet();
        int number = size;
        if (collectionLastIndexMap == null) {
            number--;
        }
        if (number <= 0) {
            return;
        }
        sheet.shiftRows(maxRowIndex + 1, lastRowIndex, number, true, false);

        // The current data is greater than unity rowindex increase
        String tablePrefix = tablePrefix(currentUniqueDataFlag);
        increaseRowIndex(templateAnalysisCache, number, maxRowIndex, tablePrefix);
        increaseRowIndex(templateCollectionAnalysisCache, number, maxRowIndex, tablePrefix);
    }

    private void increaseRowIndex(Map<String, List<AnalysisCell>> templateAnalysisCache, int number, int maxRowIndex,
        String tablePrefix) {
        for (Map.Entry<String, List<AnalysisCell>> entry : templateAnalysisCache.entrySet()) {
            if (!tablePrefix.equals(tablePrefix(entry.getKey()))) {
                continue;
            }
            for (AnalysisCell analysisCell : entry.getValue()) {
                if (analysisCell.getRowIndex() > maxRowIndex) {
                    analysisCell.setRowIndex(analysisCell.getRowIndex() + number);
                }
            }
        }
    }

    private void doFill(List<AnalysisCell> analysisCellList, Object oneRowData, FillConfig fillConfig,
        Integer relativeRowIndex) {
        Map dataMap;
        if (oneRowData instanceof Map) {
            dataMap = (Map) oneRowData;
        } else {
            dataMap = BeanMap.create(oneRowData);
        }
        WriteSheetHolder writeSheetHolder = writeContext.writeSheetHolder();
        Map<String, ExcelContentProperty> fieldNameContentPropertyMap =
            writeContext.currentWriteHolder().excelWriteHeadProperty().getFieldNameContentPropertyMap();
        for (AnalysisCell analysisCell : analysisCellList) {
            Cell cell = getOneCell(analysisCell, fillConfig);
            if (analysisCell.getOnlyOneVariable()) {
                String variable = analysisCell.getVariableList().get(0);
                if (writeContext.currentWriteHolder().ignore(variable, analysisCell.getColumnIndex())) {
                    continue;
                }
                if (!dataMap.containsKey(variable)) {
                    continue;
                }
                Object value = dataMap.get(variable);
                CellData cellData = converterAndSet(writeSheetHolder, value == null ? null : value.getClass(), cell,
                    value, fieldNameContentPropertyMap.get(variable), null, relativeRowIndex);
                WriteHandlerUtils.afterCellDispose(writeContext, cellData, cell, null, relativeRowIndex, Boolean.FALSE);
            } else {
                StringBuilder cellValueBuild = new StringBuilder();
                int index = 0;
                List<CellData> cellDataList = new ArrayList<CellData>();
                for (String variable : analysisCell.getVariableList()) {
                    cellValueBuild.append(analysisCell.getPrepareDataList().get(index++));
                    if (writeContext.currentWriteHolder().ignore(variable, analysisCell.getColumnIndex())) {
                        continue;
                    }
                    if (!dataMap.containsKey(variable)) {
                        continue;
                    }
                    Object value = dataMap.get(variable);
                    CellData cellData = convert(writeSheetHolder, value == null ? null : value.getClass(), cell, value,
                        fieldNameContentPropertyMap.get(variable));
                    cellDataList.add(cellData);
                    CellDataTypeEnum type = cellData.getType();
                    if (type != null) {
                        switch (type) {
                            case STRING:
                                cellValueBuild.append(cellData.getStringValue());
                                break;
                            case BOOLEAN:
                                cellValueBuild.append(cellData.getBooleanValue());
                                break;
                            case NUMBER:
                                cellValueBuild.append(cellData.getNumberValue());
                                break;
                            default:
                                break;
                        }
                    }
                }
                cellValueBuild.append(analysisCell.getPrepareDataList().get(index));
                cell.setCellValue(cellValueBuild.toString());
                WriteHandlerUtils.afterCellDispose(writeContext, cellDataList, cell, null, relativeRowIndex,
                    Boolean.FALSE);
            }
        }
    }

    private Integer getRelativeRowIndex() {
        Integer relativeRowIndex = relativeRowIndexMap.get(currentUniqueDataFlag);
        if (relativeRowIndex == null) {
            relativeRowIndex = 0;
        } else {
            relativeRowIndex++;
        }
        relativeRowIndexMap.put(currentUniqueDataFlag, relativeRowIndex);
        return relativeRowIndex;
    }

    private Cell getOneCell(AnalysisCell analysisCell, FillConfig fillConfig) {
        Sheet cachedSheet = writeContext.writeSheetHolder().getCachedSheet();
        if (WriteTemplateAnalysisCellTypeEnum.COMMON.equals(analysisCell.getCellType())) {
            return cachedSheet.getRow(analysisCell.getRowIndex()).getCell(analysisCell.getColumnIndex());
        }
        Sheet sheet = writeContext.writeSheetHolder().getSheet();

        Map<AnalysisCell, Integer> collectionLastIndexMap = collectionLastIndexCache.get(currentUniqueDataFlag);
        if (collectionLastIndexMap == null) {
            collectionLastIndexMap = new HashMap<AnalysisCell, Integer>(16);
            collectionLastIndexCache.put(currentUniqueDataFlag, collectionLastIndexMap);
        }
        boolean isOriginalCell = false;
        Integer lastRowIndex;
        Integer lastColumnIndex;
        switch (fillConfig.getDirection()) {
            case VERTICAL:
                lastRowIndex = collectionLastIndexMap.get(analysisCell);
                if (lastRowIndex == null) {
                    lastRowIndex = analysisCell.getRowIndex();
                    collectionLastIndexMap.put(analysisCell, lastRowIndex);
                    isOriginalCell = true;
                } else {
                    collectionLastIndexMap.put(analysisCell, ++lastRowIndex);
                }
                lastColumnIndex = analysisCell.getColumnIndex();
                break;
            case HORIZONTAL:
                lastRowIndex = analysisCell.getRowIndex();
                lastColumnIndex = collectionLastIndexMap.get(analysisCell);
                if (lastColumnIndex == null) {
                    lastColumnIndex = analysisCell.getColumnIndex();
                    collectionLastIndexMap.put(analysisCell, lastColumnIndex);
                    isOriginalCell = true;
                } else {
                    collectionLastIndexMap.put(analysisCell, ++lastColumnIndex);
                }
                break;
            default:
                throw new ExcelGenerateException("The wrong direction.");
        }
        Row row = sheet.getRow(lastRowIndex);
        if (row == null) {
            row = cachedSheet.getRow(lastRowIndex);
            if (row == null) {
                WriteHandlerUtils.beforeRowCreate(writeContext, lastRowIndex, null, Boolean.FALSE);
                if (fillConfig.getForceNewRow()) {
                    row = cachedSheet.createRow(lastRowIndex);
                } else {
                    row = sheet.createRow(lastRowIndex);
                }
                checkRowHeight(analysisCell, fillConfig, isOriginalCell, row);
                WriteHandlerUtils.afterRowCreate(writeContext, row, null, Boolean.FALSE);
            } else {
                checkRowHeight(analysisCell, fillConfig, isOriginalCell, row);
            }
        }
        Cell cell = row.getCell(lastColumnIndex);
        if (cell == null) {
            WriteHandlerUtils.beforeCellCreate(writeContext, row, null, lastColumnIndex, null, Boolean.FALSE);
            cell = row.createCell(lastColumnIndex);
            WriteHandlerUtils.afterCellCreate(writeContext, cell, null, null, Boolean.FALSE);
        }

        Map<AnalysisCell, CellStyle> collectionFieldStyleMap = collectionFieldStyleCache.get(currentUniqueDataFlag);
        if (collectionFieldStyleMap == null) {
            collectionFieldStyleMap = new HashMap<AnalysisCell, CellStyle>(16);
            collectionFieldStyleCache.put(currentUniqueDataFlag, collectionFieldStyleMap);
        }
        if (isOriginalCell) {
            collectionFieldStyleMap.put(analysisCell, cell.getCellStyle());
        } else {
            CellStyle cellStyle = collectionFieldStyleMap.get(analysisCell);
            if (cellStyle != null) {
                cell.setCellStyle(cellStyle);
            }
        }
        return cell;
    }

    private void checkRowHeight(AnalysisCell analysisCell, FillConfig fillConfig, boolean isOriginalCell, Row row) {
        if (!analysisCell.getFirstRow() || !WriteDirectionEnum.VERTICAL.equals(fillConfig.getDirection())) {
            return;
        }
        if (isOriginalCell) {
            collectionRowHeightCache.put(currentUniqueDataFlag, row.getHeight());
            return;
        }
        Short rowHeight = collectionRowHeightCache.get(currentUniqueDataFlag);
        if (rowHeight != null) {
            row.setHeight(rowHeight);
        }
    }

    private List<AnalysisCell> readTemplateData(Map<String, List<AnalysisCell>> analysisCache) {
        List<AnalysisCell> analysisCellList = analysisCache.get(currentUniqueDataFlag);
        if (analysisCellList != null) {
            return analysisCellList;
        }
        Sheet sheet = writeContext.writeSheetHolder().getCachedSheet();
        Map<String, Set<Integer>> firstRowCache = new HashMap<String, Set<Integer>>(8);
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            for (int j = 0; j < row.getLastCellNum(); j++) {
                Cell cell = row.getCell(j);
                if (cell == null) {
                    continue;
                }
                String preparedData = prepareData(cell, i, j, firstRowCache);
                // Prevent empty data from not being replaced
                if (preparedData != null) {
                    cell.setCellValue(preparedData);
                }
            }
        }
        return analysisCache.get(currentUniqueDataFlag);
    }

    /**
     * To prepare data
     *
     * @param cell
     * @param rowIndex
     * @param columnIndex
     * @param firstRowCache
     * @return Returns the data that the cell needs to replace
     */
    private String prepareData(Cell cell, int rowIndex, int columnIndex, Map<String, Set<Integer>> firstRowCache) {
        if (!CellType.STRING.equals(cell.getCellTypeEnum())) {
            return null;
        }
        String value = cell.getStringCellValue();
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        StringBuilder preparedData = new StringBuilder();
        AnalysisCell analysisCell = null;

        int startIndex = 0;
        int length = value.length();
        int lastPrepareDataIndex = 0;
        out: while (startIndex < length) {
            int prefixIndex = value.indexOf(FILL_PREFIX, startIndex);
            if (prefixIndex < 0) {
                break out;
            }
            if (prefixIndex != 0) {
                char prefixPrefixChar = value.charAt(prefixIndex - 1);
                if (prefixPrefixChar == IGNORE_CHAR) {
                    startIndex = prefixIndex + 1;
                    continue;
                }
            }
            int suffixIndex = -1;
            while (suffixIndex == -1 && startIndex < length) {
                suffixIndex = value.indexOf(FILL_SUFFIX, startIndex + 1);
                if (suffixIndex < 0) {
                    break out;
                }
                startIndex = suffixIndex + 1;
                char prefixSuffixChar = value.charAt(suffixIndex - 1);
                if (prefixSuffixChar == IGNORE_CHAR) {
                    suffixIndex = -1;
                }
            }
            if (analysisCell == null) {
                analysisCell = initAnalysisCell(rowIndex, columnIndex);
            }
            String variable = value.substring(prefixIndex + 1, suffixIndex);
            if (StringUtils.isEmpty(variable)) {
                continue;
            }
            int collectPrefixIndex = variable.indexOf(COLLECTION_PREFIX);
            if (collectPrefixIndex > -1) {
                if (collectPrefixIndex != 0) {
                    analysisCell.setPrefix(variable.substring(0, collectPrefixIndex));
                }
                variable = variable.substring(collectPrefixIndex + 1);
                if (StringUtils.isEmpty(variable)) {
                    continue;
                }
                analysisCell.setCellType(WriteTemplateAnalysisCellTypeEnum.COLLECTION);
            }
            analysisCell.getVariableList().add(variable);
            if (lastPrepareDataIndex == prefixIndex) {
                analysisCell.getPrepareDataList().add(StringUtils.EMPTY);
            } else {
                String data = convertPrepareData(value.substring(lastPrepareDataIndex, prefixIndex));
                preparedData.append(data);
                analysisCell.getPrepareDataList().add(data);
                analysisCell.setOnlyOneVariable(Boolean.FALSE);
            }
            lastPrepareDataIndex = suffixIndex + 1;
        }
        return dealAnalysisCell(analysisCell, value, rowIndex, lastPrepareDataIndex, length, firstRowCache,
            preparedData);
    }

    private String dealAnalysisCell(AnalysisCell analysisCell, String value, int rowIndex, int lastPrepareDataIndex,
        int length, Map<String, Set<Integer>> firstRowCache, StringBuilder preparedData) {
        if (analysisCell != null) {
            if (lastPrepareDataIndex == length) {
                analysisCell.getPrepareDataList().add(StringUtils.EMPTY);
            } else {
                analysisCell.getPrepareDataList().add(convertPrepareData(value.substring(lastPrepareDataIndex)));
                analysisCell.setOnlyOneVariable(Boolean.FALSE);
            }
            String uniqueDataFlag = uniqueDataFlag(writeContext.writeSheetHolder(), analysisCell.getPrefix());
            if (WriteTemplateAnalysisCellTypeEnum.COMMON.equals(analysisCell.getCellType())) {
                List<AnalysisCell> analysisCellList = templateAnalysisCache.get(uniqueDataFlag);
                if (analysisCellList == null) {
                    analysisCellList = new ArrayList<AnalysisCell>();
                    templateAnalysisCache.put(uniqueDataFlag, analysisCellList);
                }
                analysisCellList.add(analysisCell);
            } else {
                Set<Integer> uniqueFirstRowCache = firstRowCache.get(uniqueDataFlag);
                if (uniqueFirstRowCache == null) {
                    uniqueFirstRowCache = new HashSet<Integer>();
                    firstRowCache.put(uniqueDataFlag, uniqueFirstRowCache);
                }
                if (!uniqueFirstRowCache.contains(rowIndex)) {
                    analysisCell.setFirstRow(Boolean.TRUE);
                    uniqueFirstRowCache.add(rowIndex);
                }

                List<AnalysisCell> collectionAnalysisCellList = templateCollectionAnalysisCache.get(uniqueDataFlag);
                if (collectionAnalysisCellList == null) {
                    collectionAnalysisCellList = new ArrayList<AnalysisCell>();
                    templateCollectionAnalysisCache.put(uniqueDataFlag, collectionAnalysisCellList);
                }
                collectionAnalysisCellList.add(analysisCell);
            }
            return preparedData.toString();
        }
        return null;
    }

    private AnalysisCell initAnalysisCell(Integer rowIndex, Integer columnIndex) {
        AnalysisCell analysisCell = new AnalysisCell();
        analysisCell.setRowIndex(rowIndex);
        analysisCell.setColumnIndex(columnIndex);
        analysisCell.setOnlyOneVariable(Boolean.TRUE);
        List<String> variableList = new ArrayList<String>();
        analysisCell.setVariableList(variableList);
        List<String> prepareDataList = new ArrayList<String>();
        analysisCell.setPrepareDataList(prepareDataList);
        analysisCell.setCellType(WriteTemplateAnalysisCellTypeEnum.COMMON);
        analysisCell.setFirstRow(Boolean.FALSE);
        return analysisCell;
    }

    private String convertPrepareData(String prepareData) {
        prepareData = prepareData.replaceAll(ESCAPE_FILL_PREFIX, FILL_PREFIX);
        prepareData = prepareData.replaceAll(ESCAPE_FILL_SUFFIX, FILL_SUFFIX);
        return prepareData;
    }

    private String uniqueDataFlag(WriteSheetHolder writeSheetHolder, String wrapperName) {
        String prefix;
        if (writeSheetHolder.getSheetNo() != null) {
            prefix = writeSheetHolder.getSheetNo().toString();
        } else {
            prefix = writeSheetHolder.getSheetName().toString();
        }
        if (StringUtils.isEmpty(wrapperName)) {
            return prefix + "-";
        }
        return prefix + "-" + wrapperName;
    }

    private String tablePrefix(String uniqueDataFlag) {
        return uniqueDataFlag.substring(0, uniqueDataFlag.indexOf("-") + 1);
    }

}
