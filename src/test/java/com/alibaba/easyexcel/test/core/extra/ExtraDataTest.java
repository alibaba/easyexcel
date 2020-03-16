package com.alibaba.easyexcel.test.core.extra;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.enums.CellExtraTypeEnum;

/**
 *
 * @author Jiaju Zhuang
 */
public class ExtraDataTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExtraDataTest.class);
    private static File file03;
    private static File file07;

    @BeforeClass
    public static void init() {
        file03 = TestFileUtil.readFile("extra" + File.separator + "extra.xls");
        file07 = TestFileUtil.readFile("extra" + File.separator + "extra.xlsx");
    }

    @Test
    public void t01Read07() {
        read(file07);
    }

    @Test
    public void t02Read03() {
        read(file03);
    }

    private void read(File file) {
        EasyExcel.read(file, ExtraData.class, new ExtraDataListener()).extraRead(CellExtraTypeEnum.COMMENT)
            .extraRead(CellExtraTypeEnum.HYPERLINK).extraRead(CellExtraTypeEnum.MERGE).sheet().doRead();
    }

}
