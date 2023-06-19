package com.alibaba.excel.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Description:
 *
 * @author linfeng
 * @version 1.0.0
 * @since 2023/6/15 17:45
 */
class CellVariableUtilsTest {

    @Test
    void getVariable() {

        CellVariableUtils.getVariable("{.ProductCenter.test sdad}");
    }

    @Test
    void testVar() {
        CellVariableUtils.getVariable("{ProductCenter.test sdad.tt}");
    }
}
