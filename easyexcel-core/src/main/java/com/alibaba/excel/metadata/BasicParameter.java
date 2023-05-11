package com.alibaba.excel.metadata;

import java.util.List;
import java.util.Locale;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CacheLocationEnum;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Basic parameter
 *
 * @author Jiaju Zhuang
 **/
@Getter
@Setter
@EqualsAndHashCode
public class BasicParameter {
    /**
     * You can only choose one of the {@link BasicParameter#head} and {@link BasicParameter#clazz}
     */
    private List<List<String>> head;
    /**
     * You can only choose one of the {@link BasicParameter#head} and {@link BasicParameter#clazz}
     */
    private Class<?> clazz;
    /**
     * Custom type conversions override the default
     */
    private List<Converter<?>> customConverterList;
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

    /**
     * The cache used when parsing fields such as head.
     *
     * default is THREAD_LOCAL.
     */
    private CacheLocationEnum filedCacheLocation;
}
