package com.alibaba.excel.metadata.csv;

import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.RichTextString;

/**
 * rich text string
 *
 * @author Jiaju Zhuang
 */
public class CsvRichTextString implements RichTextString {
    /**
     * string
     */
    private final String string;

    public CsvRichTextString(String string) {
        this.string = string;
    }

    @Override
    public void applyFont(int startIndex, int endIndex, short fontIndex) {

    }

    @Override
    public void applyFont(int startIndex, int endIndex, Font font) {

    }

    @Override
    public void applyFont(Font font) {

    }

    @Override
    public void clearFormatting() {

    }

    @Override
    public String getString() {
        return string;
    }

    @Override
    public int length() {
        if (string == null) {
            return 0;
        }
        return string.length();
    }

    @Override
    public int numFormattingRuns() {
        return 0;
    }

    @Override
    public int getIndexOfFormattingRun(int index) {
        return 0;
    }

    @Override
    public void applyFont(short fontIndex) {

    }
}
