package com.alibaba.easyexcel.test.temp.issue2014_3515;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * issue 3515 修复单元测试
 *
 * @see <a href="https://github.com/alibaba/easyexcel/issues/3515">https://github.com/alibaba/easyexcel/issues/3515</a>
 * @author nukiyoam
 */
@Slf4j
public class Issue3515Test {

    @Test
    public void readWithHeadRowNumber(){
        String fileName = String.join(File.separator, TestFileUtil.getPath(), "temp", "issue_3515", "issue_3515.xlsx");
        List<Map<Integer, Object>> data = EasyExcel.read(fileName)
                .sheet(0)
                .headRowNumber(1)
                .doReadSync();
        Assertions.assertEquals(3, data.size());
        data.forEach(it->{
            Assertions.assertEquals(3,it.size());
        });
    }

    @Test
    public void readWithHeadList(){
        String fileName = String.join(File.separator, TestFileUtil.getPath(), "temp", "issue_3515", "issue_3515.xlsx");
        List<List<String>> head = new ArrayList<>();
        head.add(Collections.singletonList("第一列"));
        head.add(Collections.singletonList("第二列"));
        head.add(Collections.singletonList("空列"));
        List<Map<Integer, Object>> data = EasyExcel.read(fileName)
                .sheet(0)
                .head(head)
                .doReadSync();
        Assertions.assertEquals(3, data.size());
        data.forEach(it->{
            Assertions.assertEquals(3,it.size());
        });
    }
}
