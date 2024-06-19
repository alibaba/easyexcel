package com.alibaba.excel.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * 单元格变量工具
 *
 * @author linfeng
 * @version 1.0.0
 * @since 2023/6/7 10:38
 */
public class CellVariableUtils {

    private static final String FILL_PREFIX = "{";
    private static final String FILL_SUFFIX = "}";
    private static final char IGNORE_CHAR = '\\';
    private static final String COLLECTION_PREFIX = ".";

    /**
     * 根据cell值获取单元格变量
     * @author linfeng
     * @param cellValue 单元格值
     * @return 变量集合
     */
    public static List<String> getVariable(String cellValue) {

        List<String> varList = new ArrayList<>();
        int startIndex = 0;
        int length = cellValue.length();
        out:
        while (startIndex < length) {
            int prefixIndex = cellValue.indexOf(FILL_PREFIX, startIndex);
            if (prefixIndex < 0) {
                break;
            }
            if (prefixIndex != 0) {
                char prefixPrefixChar = cellValue.charAt(prefixIndex - 1);
                if (prefixPrefixChar == IGNORE_CHAR) {
                    startIndex = prefixIndex + 1;
                    continue;
                }
            }
            int suffixIndex = -1;
            while (suffixIndex == -1 && startIndex < length) {
                suffixIndex = cellValue.indexOf(FILL_SUFFIX, startIndex + 1);
                if (suffixIndex < 0) {
                    break out;
                }
                startIndex = suffixIndex + 1;
                char prefixSuffixChar = cellValue.charAt(suffixIndex - 1);
                if (prefixSuffixChar == IGNORE_CHAR) {
                    suffixIndex = -1;
                }
            }

            String variable = cellValue.substring(prefixIndex + 1, suffixIndex);
            if (StringUtils.isEmpty(variable)) {
                continue;
            }
            int collectPrefixIndex = variable.indexOf(COLLECTION_PREFIX);
            if (collectPrefixIndex == 0) {
                variable = variable.substring(collectPrefixIndex + 1);
                if (StringUtils.isEmpty(variable)) {
                    continue;
                }
            }
            varList.add(variable);
        }
        return varList;
    }
}
