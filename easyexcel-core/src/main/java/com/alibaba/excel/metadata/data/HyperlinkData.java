package com.alibaba.excel.metadata.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * hyperlink
 *
 * @author Jiaju Zhuang
 */
@Getter
@Setter
@EqualsAndHashCode
public class HyperlinkData extends CoordinateData {
    /**
     * Depending on the hyperlink type it can be URL, e-mail, path to a file, etc
     */
    private String address;
    /**
     * hyperlink type
     */
    private HyperlinkType hyperlinkType;

    @Getter
    public enum HyperlinkType {
        /**
         * Not a hyperlink
         */
        NONE(org.apache.poi.common.usermodel.HyperlinkType.NONE),

        /**
         * Link to an existing file or web page
         */
        URL(org.apache.poi.common.usermodel.HyperlinkType.URL),

        /**
         * Link to a place in this document
         */
        DOCUMENT(org.apache.poi.common.usermodel.HyperlinkType.DOCUMENT),

        /**
         * Link to an E-mail address
         */
        EMAIL(org.apache.poi.common.usermodel.HyperlinkType.EMAIL),

        /**
         * Link to a file
         */
        FILE(org.apache.poi.common.usermodel.HyperlinkType.FILE);

        org.apache.poi.common.usermodel.HyperlinkType value;

        HyperlinkType(org.apache.poi.common.usermodel.HyperlinkType value) {
            this.value = value;
        }
    }
}
