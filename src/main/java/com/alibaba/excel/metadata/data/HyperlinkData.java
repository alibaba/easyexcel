package com.alibaba.excel.metadata.data;

import lombok.Data;

/**
 * hyperlink
 *
 * @author Jiaju Zhuang
 */
@Data
public class HyperlinkData extends CoordinateData {
    /**
     * Depending on the hyperlink type it can be URL, e-mail, path to a file, etc
     */
    private String address;
    /**
     * hyperlink type
     */
    private HyperlinkType hyperlinkType;

    public enum HyperlinkType {
        /**
         * Not a hyperlink
         */
        NONE(-1),

        /**
         * Link to an existing file or web page
         */
        URL(1),

        /**
         * Link to a place in this document
         */
        DOCUMENT(2),

        /**
         * Link to an E-mail address
         */
        EMAIL(3),

        /**
         * Link to a file
         */
        FILE(4);

        public final int value;

        HyperlinkType(int value) {
            this.value = value;
        }
    }
}
