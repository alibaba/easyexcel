package com.alibaba.excel.analysis.v07.handlers;

import com.alibaba.excel.analysis.v07.XlsxCellHandler;
import org.apache.poi.ss.util.CellRangeAddress;
import org.xml.sax.Attributes;

import java.util.ArrayList;
import java.util.List;

/**
 * Merged cell handler.
 *
 * @author zuozhu.meng
 * @date 2019 /12/12
 */
public class MergedCellHandler implements XlsxCellHandler {
    private static final String MERGE_CELL_NAME = "mergeCell";
    private static final String REF = "ref";
    private List<CellRangeAddress> cellRangeAddresses = new ArrayList<CellRangeAddress>();

    @Override
    public boolean support(String name) {
        return MERGE_CELL_NAME.equals(name);
    }

    @Override
    public void startHandle(String name, Attributes attributes) {
        //获取合并单元格区域信息
        String ref = attributes.getValue(REF);
        if (MERGE_CELL_NAME.equals(name) && ref != null) {
            CellRangeAddress cellRangeAddress = CellRangeAddress.valueOf(ref);
            cellRangeAddresses.add(cellRangeAddress);
        }
    }

    @Override
    public void endHandle(String name) {

    }

    public List<CellRangeAddress> getCellRangeAddresses() {
        return cellRangeAddresses;
    }

}
