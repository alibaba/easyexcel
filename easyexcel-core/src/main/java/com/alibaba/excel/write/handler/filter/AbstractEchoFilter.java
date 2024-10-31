package com.alibaba.excel.write.handler.filter;

import com.alibaba.excel.write.handler.BasePipeFilter;

/**
 * Description:
 * abstract echo
 *
 * @author linfeng
 */
public abstract class AbstractEchoFilter extends BasePipeFilter<Object, Object> {

    protected enum Delimiter {
        /**
         * blank
         */
        BLANK("blank", " "),
        /**
         * enter
         */
        WRAP("wrap", "\n"),
        /**
         * comma
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
