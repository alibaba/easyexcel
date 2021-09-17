package com.alibaba.excel.metadata.data;

import lombok.Data;

/**
 * formula
 *
 * @author Jiaju Zhuang
 */
@Data
public class FormulaData {
    /**
     * formula
     */
    private String formulaValue;

    @Override
    public FormulaData clone() {
        FormulaData formulaData = new FormulaData();
        formulaData.setFormulaValue(getFormulaValue());
        return formulaData;
    }
}
