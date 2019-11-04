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
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.CollectionUtils;
import com.alibaba.excel.util.StringUtils;
import com.alibaba.excel.util.WriteHandlerUtils;
import com.alibaba.excel.write.metadata.fill.AnalysisCell;
import com.alibaba.excel.write.metadata.fill.FillConfig;
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
    private Map<Integer, List<AnalysisCell>> templateAnalysisCache = new HashMap<Integer, List<AnalysisCell>>(8);
    /**
     * Collection fields to replace in the template
     */
    private Map<Integer, List<AnalysisCell>> templateCollectionAnalysisCache =
        new HashMap<Integer, List<AnalysisCell>>(8);
    /**
     * Style cache for collection fields
     */
    private Map<Integer, Map<AnalysisCell, CellStyle>> collectionFieldStyleCache =
        new HashMap<Integer, Map<AnalysisCell, CellStyle>>(8);
    /**
     * Row height cache for collection
     */
    private Map<Integer, Short> collectionRowHeightCache = new HashMap<Integer, Short>(8);
    /**
     * Last index cache for collection fields
     */
    private Map<Integer, Map<AnalysisCell, Integer>> collectionLastIndexCache =
        new HashMap<Integer, Map<AnalysisCell, Integer>>(8);

    public ExcelWriteFillExecutor(WriteContext writeContext) {
        super(writeContext);
    }

    public void fill(Object data, FillConfig fillConfig) {
        if (fillConfig == null) {
            fillConfig = FillConfig.builder().build(true);
        }
        fillConfig.init();
        if (data instanceof Collection) {
            List<AnalysisCell> analysisCellList = readTemplateData(templateCollectionAnalysisCache);
            Collection collectionData = (Collection)data;
            if (CollectionUtils.isEmpty(collectionData)) {
                return;
            }
            Iterator iterator = collectionData.iterator();
            if (WriteDirectionEnum.VERTICAL.equals(fillConfig.getDirection()) && fillConfig.getForceNewRow()) {
                shiftRows(collectionData.size(), analysisCellList);
            }
            while (iterator.hasNext()) {
                doFill(analysisCellList, iterator.next(), fillConfig);
            }
        } else {
            doFill(readTemplateData(templateAnalysisCache), data, fillConfig);
        }
    }

    private void shiftRows(int size, List<AnalysisCell> analysisCellList) {
        if (CollectionUtils.isEmpty(analysisCellList)) {
            return;
        }
        int maxRowIndex = 0;
        Integer sheetNo = writeContext.writeSheetHolder().getSheetNo();
        Map<AnalysisCell, Integer> collectionLastIndexMap = collectionLastIndexCache.get(sheetNo);
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
        sheet.shiftRows(maxRowIndex + 1, lastRowIndex, number, true, false);
        for (AnalysisCell analysisCell : templateAnalysisCache.get(writeContext.writeSheetHolder().getSheetNo())) {
            if (analysisCell.getRowIndex() > maxRowIndex) {
                analysisCell.setRowIndex(analysisCell.getRowIndex() + number);
            }
        }
    }

    private void doFill(List<AnalysisCell> analysisCellList, Object oneRowData, FillConfig fillConfig) {
        Map dataMap;
        if (oneRowData instanceof Map) {
            dataMap = (Map)oneRowData;
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
                    value, fieldNameContentPropertyMap.get(variable));
                WriteHandlerUtils.afterCellDispose(writeContext, cellData, cell, null, null, Boolean.FALSE);
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
                WriteHandlerUtils.afterCellDispose(writeContext, cellDataList, cell, null, null, Boolean.FALSE);
            }
        }
    }

    private Cell getOneCell(AnalysisCell analysisCell, FillConfig fillConfig) {
        Sheet cachedSheet = writeContext.writeSheetHolder().getCachedSheet();
        if (WriteTemplateAnalysisCellTypeEnum.COMMON.equals(analysisCell.getCellType())) {
            return cachedSheet.getRow(analysisCell.getRowIndex()).getCell(analysisCell.getColumnIndex());
        }
        Integer sheetNo = writeContext.writeSheetHolder().getSheetNo();
        Sheet sheet = writeContext.writeSheetHolder().getSheet();

        Map<AnalysisCell, Integer> collectionLastIndexMap = collectionLastIndexCache.get(sheetNo);
        if (collectionLastIndexMap == null) {
            collectionLastIndexMap = new HashMap<AnalysisCell, Integer>(16);
            collectionLastIndexCache.put(sheetNo, collectionLastIndexMap);
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
                checkRowHeight(analysisCell, fillConfig, isOriginalCell, row, sheetNo);
                WriteHandlerUtils.afterRowCreate(writeContext, row, null, Boolean.FALSE);
            } else {
                checkRowHeight(analysisCell, fillConfig, isOriginalCell, row, sheetNo);
            }
        }
        Cell cell = row.getCell(lastColumnIndex);
        if (cell == null) {
            WriteHandlerUtils.beforeCellCreate(writeContext, row, null, lastColumnIndex, null, Boolean.FALSE);
            cell = row.createCell(lastColumnIndex);
            WriteHandlerUtils.afterCellCreate(writeContext, cell, null, null, Boolean.FALSE);
        }

        Map<AnalysisCell, CellStyle> collectionFieldStyleMap = collectionFieldStyleCache.get(sheetNo);
        if (collectionFieldStyleMap == null) {
            collectionFieldStyleMap = new HashMap<AnalysisCell, CellStyle>(16);
            collectionFieldStyleCache.put(sheetNo, collectionFieldStyleMap);
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

    private void checkRowHeight(AnalysisCell analysisCell, FillConfig fillConfig, boolean isOriginalCell, Row row,
        Integer sheetNo) {
        if (!analysisCell.getFirstColumn() || !WriteDirectionEnum.VERTICAL.equals(fillConfig.getDirection())) {
            return;
        }
        if (isOriginalCell) {
            collectionRowHeightCache.put(sheetNo, row.getHeight());
            return;
        }
        Short rowHeight = collectionRowHeightCache.get(sheetNo);
        if (rowHeight != null) {
            row.setHeight(rowHeight);
        }
    }

    private List<AnalysisCell> readTemplateData(Map<Integer, List<AnalysisCell>> analysisCache) {
        Integer sheetNo = writeContext.writeSheetHolder().getSheetNo();
        List<AnalysisCell> analysisCellList = analysisCache.get(sheetNo);
        if (analysisCellList != null) {
            return analysisCellList;
        }
        Sheet sheet = writeContext.writeSheetHolder().getCachedSheet();
        analysisCellList = new ArrayList<AnalysisCell>();
        List<AnalysisCell> collectionAnalysisCellList = new ArrayList<AnalysisCell>();
        Set<Integer> firstColumnCache = new HashSet<Integer>();
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
                String preparedData =
                    prepareData(cell, analysisCellList, collectionAnalysisCellList, i, j, firstColumnCache);
                // Prevent empty data from not being replaced
                if (preparedData != null) {
                    cell.setCellValue(preparedData);
                }
            }
        }
        templateAnalysisCache.put(sheetNo, analysisCellList);
        templateCollectionAnalysisCache.put(sheetNo, collectionAnalysisCellList);
        return analysisCache.get(sheetNo);
    }

    /**
     * To prepare data
     *
     * @param cell
     * @param analysisCellList
     * @param collectionAnalysisCellList
     * @param rowIndex
     * @param columnIndex
     * @param firstColumnCache
     * @return Returns the data that the cell needs to replace
     */
    private String prepareData(Cell cell, List<AnalysisCell> analysisCellList,
        List<AnalysisCell> collectionAnalysisCellList, int rowIndex, int columnIndex, Set<Integer> firstColumnCache) {
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
        out:
        while (startIndex < length) {
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
            if (variable.startsWith(COLLECTION_PREFIX)) {
                variable = variable.substring(1);
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
        if (analysisCell != null) {
            if (lastPrepareDataIndex == length) {
                analysisCell.getPrepareDataList().add(StringUtils.EMPTY);
            } else {
                analysisCell.getPrepareDataList().add(convertPrepareData(value.substring(lastPrepareDataIndex)));
                analysisCell.setOnlyOneVariable(Boolean.FALSE);
            }
            if (WriteTemplateAnalysisCellTypeEnum.COMMON.equals(analysisCell.getCellType())) {
                analysisCellList.add(analysisCell);
            } else {
                if (!firstColumnCache.contains(rowIndex)) {
                    analysisCell.setFirstColumn(Boolean.TRUE);
                    firstColumnCache.add(rowIndex);
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
        analysisCell.setFirstColumn(Boolean.FALSE);
        return analysisCell;
    }

    private String convertPrepareData(String prepareData) {
        prepareData = prepareData.replaceAll(ESCAPE_FILL_PREFIX, FILL_PREFIX);
        prepareData = prepareData.replaceAll(ESCAPE_FILL_SUFFIX, FILL_SUFFIX);
        return prepareData;
    }

}
