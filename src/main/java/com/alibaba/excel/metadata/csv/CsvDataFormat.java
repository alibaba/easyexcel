package com.alibaba.excel.metadata.csv;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.alibaba.excel.constant.BuiltinFormats;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.util.MapUtils;

import org.apache.poi.ss.usermodel.DataFormat;

/**
 * format data
 *
 * @author Jiaju Zhuang
 */
public class CsvDataFormat implements DataFormat {
    /**
     * It is stored in both map and list for easy retrieval
     */
    private final Map<String, Short> formatMap;
    private final List<String> formatList;

    /**
     * Excel's built-in format conversion.
     */
    private final Map<String, Short> builtinFormatsMap;
    private final String[] builtinFormats;

    public CsvDataFormat(Locale locale) {
        formatMap = MapUtils.newHashMap();
        formatList = ListUtils.newArrayList();
        builtinFormatsMap = BuiltinFormats.switchBuiltinFormatsMap(locale);
        builtinFormats = BuiltinFormats.switchBuiltinFormats(locale);
    }

    @Override
    public short getFormat(String format) {
        Short index = builtinFormatsMap.get(format);
        if (index != null) {
            return index;
        }
        index = formatMap.get(format);
        if (index != null) {
            return index;
        }
        short indexPrimitive = (short)(formatList.size() + BuiltinFormats.MIN_CUSTOM_DATA_FORMAT_INDEX);
        index = indexPrimitive;
        formatList.add(format);
        formatMap.put(format, index);
        return indexPrimitive;
    }

    @Override
    public String getFormat(short index) {
        if (index < BuiltinFormats.MIN_CUSTOM_DATA_FORMAT_INDEX) {
            return builtinFormats[index];
        }
        int actualIndex = index - BuiltinFormats.MIN_CUSTOM_DATA_FORMAT_INDEX;
        if (actualIndex < formatList.size()) {
            return formatList.get(actualIndex);
        }
        return null;
    }
}
