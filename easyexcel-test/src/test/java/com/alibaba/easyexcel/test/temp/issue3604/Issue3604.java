package com.alibaba.easyexcel.test.temp.issue3604;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.enums.WriteDirectionEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.fill.FillWrapper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author youlingdada
 * @version 1.0
 * createDate 2023/12/16 16:40
 */
public class Issue3604 {

    @Test
    public void test1() {
        // 准备 10 行数据，基本上每行都有 aaa 和 bbb 两个 key，值分别为 a1 和 b1, a2 和 b2, ...
        // 唯独第 5 行，故意删除掉 bbb 这个 key
        List<Map<String, Object>> dataLineList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Map<String, Object> m = new HashMap<>();
            m.put("aaa", "a" + i);
            m.put("bbb", "b" + i);
            if (i == 5) {
                m.remove("bbb");
            }
            dataLineList.add(m);
        }
        String targetFile = TestFileUtil.getPath() + "temp/issue3604" + File.separator + "result3.xlsx";
        String templateFile = TestFileUtil.getPath() + "temp/issue3604" + File.separator + "template3.xlsx";

        ExcelWriter excelWriter = EasyExcel.write(targetFile).withTemplate(templateFile).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        FillConfig fillConfig = FillConfig.builder().direction(WriteDirectionEnum.VERTICAL).build();
        excelWriter.fill(new FillWrapper("dataLine", dataLineList), fillConfig, writeSheet);
        excelWriter.writeContext().writeWorkbookHolder().getWorkbook().setForceFormulaRecalculation(true);
        excelWriter.finish();
    }
}
