package com.alibaba.excel.metadata;

import java.util.List;
import java.util.Locale;

import com.alibaba.excel.converters.Converter;

/**
 * Basic parameter
 *
 * @author Jiaju Zhuang
 **/
public class BasicParameter {
    /**
     * You can only choose one of the {@link BasicParameter#head} and {@link BasicParameter#clazz}
     */
    private List<List<String>> head;
    /**
     * You can only choose one of the {@link BasicParameter#head} and {@link BasicParameter#clazz}
     */
    private Class clazz;
    /**
     * Custom type conversions override the default
     */
    private List<Converter> customConverterList;
    /**
     * Automatic trim includes sheet name and content
     */
    private Boolean autoTrim;
    /**
     * true if date uses 1904 windowing, or false if using 1900 date windowing.
     *
     * default is false
     *
     * @return
     */
    private Boolean use1904windowing;
    /**
     * A <code>Locale</code> object represents a specific geographical, political, or cultural region. This parameter is
     * used when formatting dates and numbers.
     */
    private Locale locale;

    /**
     * Whether to use scientific Format.
     *
     * default is false
     */
    private Boolean useScientificFormat;

    public List<List<String>> getHead() {
        return head;
    }

    public void setHead(List<List<String>> head) {
        this.head = head;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public List<Converter> getCustomConverterList() {
        return customConverterList;
    }

    public void setCustomConverterList(List<Converter> customConverterList) {
        this.customConverterList = customConverterList;
    }

    public Boolean getAutoTrim() {
        return autoTrim;
    }

    public void setAutoTrim(Boolean autoTrim) {
        this.autoTrim = autoTrim;
    }

    public Boolean getUse1904windowing() {
        return use1904windowing;
    }

    public void setUse1904windowing(Boolean use1904windowing) {
        this.use1904windowing = use1904windowing;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Boolean getUseScientificFormat() {
        return useScientificFormat;
    }

    public void setUseScientificFormat(Boolean useScientificFormat) {
        this.useScientificFormat = useScientificFormat;
    }
}
