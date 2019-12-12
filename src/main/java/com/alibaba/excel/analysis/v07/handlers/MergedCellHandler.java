package com.alibaba.excel.analysis.v07.handlers;

import com.alibaba.excel.analysis.v07.XlsxCellHandler;
import com.alibaba.excel.context.AnalysisContext;
import org.apache.poi.ss.util.CellRangeAddress;
import org.xml.sax.Attributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Merged cell handler.
 *
 * @author zuozhu.meng
 * @date 2019 /12/12
 */
public class MergedCellHandler implements XlsxCellHandler {
    private static final String MERGE_CELL_NAME = "mergeCell";
    private static final String REF = "ref";
    private final AnalysisContext analysisContext;
    private Map<Integer, List<CellRangeAddress>> sheetAndCellRangeAddresses = new HashMap<Integer,
        List<CellRangeAddress>>();

    public MergedCellHandler(AnalysisContext analysisContext) {
        this.analysisContext = analysisContext;
    }

    @Override
    public boolean support(String name) {
        return MERGE_CELL_NAME.equals(name);
    }

    @Override
    public void startHandle(String name, Attributes attributes) {
        //获取合并单元格区域信息
        String ref = attributes.getValue(REF);
        if (MERGE_CELL_NAME.equals(name) && ref != null) {
            Integer sheetNo = analysisContext.readSheetHolder().getSheetNo();
            List<CellRangeAddress> cellRangeAddresses = sheetAndCellRangeAddresses.get(sheetNo);
            if (cellRangeAddresses == null) {
                cellRangeAddresses = new ArrayList<CellRangeAddress>();
                sheetAndCellRangeAddresses.put(sheetNo, cellRangeAddresses);
            }
            CellRangeAddress cellRangeAddress = CellRangeAddress.valueOf(ref);
            cellRangeAddresses.add(cellRangeAddress);
        }
    }

    @Override
    public void endHandle(String name) {

    }

    /**
     * Gets sheet and merged cell range addresses.
     *
     * @return the sheet and merged cell range addresses
     */
    public Map<Integer, List<CellRangeAddress>> getSheetAndMergedCellRangeAddresses() {
        return sheetAndCellRangeAddresses;
    }

}
