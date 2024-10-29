package com.alibaba.easyexcel.test.core.pivot;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.util.PivotUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.apache.commons.math3.util.Pair;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author phaeris
 * @since 2023/12/15
 */
@SuppressWarnings("unchecked")
public class PivotTest {

    private static final String EXPORT_FILE_PATH = "C:\\Users\\A11-9\\Desktop\\excel\\pivot.xlsx";

    private static final String SHEET_NAME = "pivot";

    public static final List<String> ROW_FIELDS = ListUtils.newArrayList("address");

    public static final List<String> COLUMN_FIELDS = ListUtils.newArrayList("name", "phone");

    public static final List<String> AGG_FIELDS = ListUtils.newArrayList("age", "height");

    private static final Map<String, String> ALIAS;

    private static final List<Map<String, Object>> DATA;

    static {
        Map<String, Object> row1 = new HashMap<>();
        row1.put("address", "上海");
        row1.put("name", "张三");
        row1.put("phone", "110");
        row1.put("age", "18");
        row1.put("height", "170");
        Map<String, Object> row2 = new HashMap<>();
        row2.put("address", "上海");
        row2.put("name", "李四");
        row2.put("phone", "120");
        row2.put("age", "19");
        row2.put("height", "173");
        Map<String, Object> row3 = new HashMap<>();
        row3.put("address", "北京");
        row3.put("name", "王五");
        row3.put("phone", "119");
        row3.put("age", "22");
        row3.put("height", "175");
        Map<String, Object> row4 = new HashMap<>();
        row4.put("address", "北京");
        row4.put("name", "赵六");
        row4.put("phone", "114");
        row4.put("age", "30");
        row4.put("height", "180");
        DATA = ListUtils.newArrayList(row1, row2, row3, row4);
        Map<String, String> alias = new HashMap<>();
        alias.put("name", "姓名");
        alias.put("phone", "手机号");
        alias.put("address", "地址");
        alias.put("age", "年龄");
        alias.put("height", "身高");
        ALIAS = alias;
    }

    @Test
    void testExportPivot() {
        Pair<List<List<String>>, List<List<Object>>> headAndData = PivotUtils.getHeadAndData(ROW_FIELDS, COLUMN_FIELDS, AGG_FIELDS, DATA);
        write(headAndData);
    }

    @Test
    void testExportPivotHaveAlias() {
        Pair<List<List<String>>, List<List<Object>>> headAndData = PivotUtils.getHeadAndData(ROW_FIELDS, COLUMN_FIELDS, AGG_FIELDS, ALIAS, DATA);
        write(headAndData);
    }

    private void write(Pair<List<List<String>>, List<List<Object>>> headAndData) {
        ExcelWriter writer = EasyExcelFactory.write(EXPORT_FILE_PATH).build();
        WriteSheet sheet = EasyExcelFactory.writerSheet(SHEET_NAME)
            .head(headAndData.getKey())
            .build();
        writer.write(headAndData.getValue(), sheet);
        writer.finish();
    }
}
