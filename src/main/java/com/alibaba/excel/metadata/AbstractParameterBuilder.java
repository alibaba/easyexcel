package com.alibaba.excel.metadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.alibaba.excel.converters.Converter;

/**
 * ExcelBuilder
 *
 * @author Jiaju Zhuang
 */
public abstract class AbstractParameterBuilder<T extends AbstractParameterBuilder, C extends BasicParameter> {
    /**
     * You can only choose one of the {@link #head(List)} and {@link #head(Class)}
     *
     * @param head
     * @return
     */
    public T head(List<List<String>> head) {
        parameter().setHead(head);
        return self();
    }

    /**
     * You can only choose one of the {@link #head(List)} and {@link #head(Class)}
     *
     * @param clazz
     * @return
     */
    public T head(Class clazz) {
        parameter().setClazz(clazz);
        return self();
    }

    /**
     * Custom type conversions override the default.
     *
     * @param converter
     * @return
     */
    public T registerConverter(Converter converter) {
        if (parameter().getCustomConverterList() == null) {
            parameter().setCustomConverterList(new ArrayList<Converter>());
        }
        parameter().getCustomConverterList().add(converter);
        return self();
    }

    /**
     * true if date uses 1904 windowing, or false if using 1900 date windowing.
     *
     * default is false
     *
     * @param use1904windowing
     * @return
     */
    public T use1904windowing(Boolean use1904windowing) {
        parameter().setUse1904windowing(use1904windowing);
        return self();
    }

    /**
     * A <code>Locale</code> object represents a specific geographical, political, or cultural region. This parameter is
     * used when formatting dates and numbers.
     *
     * @param locale
     * @return
     */
    public T locale(Locale locale) {
        parameter().setLocale(locale);
        return self();
    }

    /**
     * Automatic trim includes sheet name and content
     *
     * @param autoTrim
     * @return
     */
    public T autoTrim(Boolean autoTrim) {
        parameter().setAutoTrim(autoTrim);
        return self();
    }

    @SuppressWarnings("unchecked")
    protected T self() {
        return (T)this;
    }

    /**
     * Get parameter
     *
     * @return
     */
    protected abstract C parameter();
}
