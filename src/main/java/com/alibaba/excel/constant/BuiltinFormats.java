package com.alibaba.excel.constant;

import com.alibaba.excel.util.StringUtils;

/**
 * Excel's built-in format conversion.Currently only supports Chinese.
 *
 * <p>
 * If it is not Chinese, it is recommended to directly modify the builtinFormats, which will better support
 * internationalization in the future.
 *
 * <p>
 * Specific correspondence please see:
 * https://docs.microsoft.com/en-us/dotnet/api/documentformat.openxml.spreadsheet.numberingformat?view=openxml-2.8.1
 *
 * @author Jiaju Zhuang
 **/
public class BuiltinFormats {

    public static String[] builtinFormats = {
        // 0
        "General",
        // 1
        "0",
        // 2
        "0.00",
        // 3
        "#,##0",
        // 4
        "#,##0.00",
        // 5
        "\"$\"#,##0_);(\"$\"#,##0)",
        // 6
        "\"$\"#,##0_);[Red](\"$\"#,##0)",
        // 7
        "\"$\"#,##0.00_);(\"$\"#,##0.00)",
        // 8
        "\"$\"#,##0.00_);[Red](\"$\"#,##0.00)",
        // 9
        "0%",
        // 10
        "0.00%",
        // 11
        "0.00E+00",
        // 12
        "# ?/?",
        // 13
        "# ??/??",
        // 14
        "m/d/yy",
        // 15
        "d-mmm-yy",
        // 16
        "d-mmm",
        // 17
        "mmm-yy",
        // 18
        "h:mm AM/PM",
        // 19
        "h:mm:ss AM/PM",
        // 20
        "h:mm",
        // 21
        "h:mm:ss",
        // 22
        "m/d/yy h:mm",
        // 23-26 No specific correspondence found in the official documentation.
        // 23
        null,
        // 24
        null,
        // 25
        null,
        // 26
        null,
        // 27
        "yyyy\"5E74\"m\"6708\"",
        // 28
        "m\"6708\"d\"65E5\"",
        // 29
        "m\"6708\"d\"65E5\"",
        // 30
        "m-d-yy",
        // 31
        "yyyy\"5E74\"m\"6708\"d\"65E5\"",
        // 32
        "h\"65F6\"mm\"5206\"",
        // 33
        "h\"65F6\"mm\"5206\"ss\"79D2\"",
        // 34
        "4E0A5348/4E0B5348h\"65F6\"mm\"5206\"",
        // 35
        "4E0A5348/4E0B5348h\"65F6\"mm\"5206\"ss\"79D2\"",
        // 36
        "yyyy\"5E74\"m\"6708\"",
        // 37
        "#,##0_);(#,##0)",
        // 38
        "#,##0_);[Red](#,##0)",
        // 39
        "#,##0.00_);(#,##0.00)",
        // 40
        "#,##0.00_);[Red](#,##0.00)",
        // 41
        "_(* #,##0_);_(* (#,##0);_(* \"-\"_);_(@_)",
        // 42
        "_(\"$\"* #,##0_);_(\"$\"* (#,##0);_(\"$\"* \"-\"_);_(@_)",
        // 43
        "_(* #,##0.00_);_(* (#,##0.00);_(* \"-\"??_);_(@_)",
        // 44
        "_(\"$\"* #,##0.00_);_(\"$\"* (#,##0.00);_(\"$\"* \"-\"??_);_(@_)",
        // 45
        "mm:ss",
        // 46
        "[h]:mm:ss",
        // 47
        "mm:ss.0",
        // 48
        "##0.0E+0",
        // 49
        "@",
        // 50
        "yyyy\"5E74\"m\"6708\"",
        // 51
        "m\"6708\"d\"65E5\"",
        // 52
        "yyyy\"5E74\"m\"6708\"",
        // 53
        "m\"6708\"d\"65E5\"",
        // 54
        "m\"6708\"d\"65E5\"",
        // 55
        "4E0A5348/4E0B5348h\"65F6\"mm\"5206\"",
        // 56
        "4E0A5348/4E0B5348h\"65F6\"mm\"5206\"ss\"79D2\"",
        // 57
        "yyyy\"5E74\"m\"6708\"",
        // 58
        "m\"6708\"d\"65E5\"",
        // 59
        "t0",
        // 60
        "t0.00",
        // 61
        "t#,##0",
        // 62
        "t#,##0.00",
        // 63-66 No specific correspondence found in the official documentation.
        // 63
        null,
        // 64
        null,
        // 65
        null,
        // 66
        null,
        // 67
        "t0%",
        // 68
        "t0.00%",
        // 69
        "t# ?/?",
        // 70
        "t# ??/??",
        // 71
        "0E27/0E14/0E1B0E1B0E1B0E1B",
        // 72
        "0E27-0E140E140E14-0E1B0E1B",
        // 73
        "0E27-0E140E140E14",
        // 74
        "0E140E140E14-0E1B0E1B",
        // 75
        "0E0A:0E190E19",
        // 76
        "0E0A:0E190E19:0E170E17",
        // 77
        "0E27/0E14/0E1B0E1B0E1B0E1B 0E0A:0E190E19",
        // 78
        "0E190E19:0E170E17",
        // 79
        "[0E0A]:0E190E19:0E170E17",
        // 80
        "0E190E19:0E170E17.0",
        // 81
        "d/m/bb",
        // end
    };

    public static String getBuiltinFormat(Integer index) {
        if (index == null || index < 0 || index >= builtinFormats.length) {
            return null;
        }
        return builtinFormats[index];
    }

    public static String getFormat(Integer index, String format) {
        if (!StringUtils.isEmpty(format)) {
            return format;
        }
        return getBuiltinFormat(index);
    }

}
