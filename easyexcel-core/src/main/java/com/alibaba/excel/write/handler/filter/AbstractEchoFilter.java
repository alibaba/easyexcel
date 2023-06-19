package com.alibaba.excel.write.handler.filter;

import com.alibaba.excel.write.handler.BasePipeFilter;

/**
 * Description:
 *
 * @author linfeng
 * @version 1.0.0
 * @since 2023/6/18 9:44
 */
public abstract class AbstractEchoFilter extends BasePipeFilter<Object, Object> {

    protected enum Delimiter {
        /**
         * 空格
         */
        BLANK("blank", " "),
        /**
         * 回车
         */
        WRAP("wrap", "\n"),
        /**
         * 逗号
         */
        COMMA("comma", ",");

        private final String value;
        private final String delimiter;

        Delimiter(String value, String delimiter) {
            this.value = value;
            this.delimiter = delimiter;
        }

        public static Delimiter ofValue(String value) {
            for (Delimiter delim : Delimiter.values()) {
                if (delim.value.equalsIgnoreCase(value)) {
                    return delim;
                }
            }
            return null;
        }

        public String getValue() {
            return value;
        }

        public String getDelimiter() {
            return delimiter;
        }
    }
}
