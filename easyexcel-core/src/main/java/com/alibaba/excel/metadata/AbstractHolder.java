package com.alibaba.excel.metadata;

import java.util.List;
import java.util.Map;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ConverterKeyBuild.ConverterKey;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Write/read holder
 *
 * @author Jiaju Zhuang
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public abstract class AbstractHolder implements ConfigurationHolder {
    /**
     * Record whether it's new or from cache
     */
    private Boolean newInitialization;
    /**
     * You can only choose one of the {@link AbstractHolder#head} and {@link AbstractHolder#clazz}
     */
    private List<List<String>> head;
    /**
     * You can only choose one of the {@link AbstractHolder#head} and {@link AbstractHolder#clazz}
     */
    private Class<?> clazz;
    /**
     * Some global variables
     */
    private GlobalConfiguration globalConfiguration;
    /**
     * <p>
     * Read key:
     * <p>
     * Write key:
     */
    private Map<ConverterKey, Converter<?>> converterMap;

    public AbstractHolder(BasicParameter basicParameter, AbstractHolder prentAbstractHolder) {
        this.newInitialization = Boolean.TRUE;
        if (basicParameter.getHead() == null && basicParameter.getClazz() == null && prentAbstractHolder != null) {
            this.head = prentAbstractHolder.getHead();
        } else {
            this.head = basicParameter.getHead();
        }
        if (basicParameter.getHead() == null && basicParameter.getClazz() == null && prentAbstractHolder != null) {
            this.clazz = prentAbstractHolder.getClazz();
        } else {
            this.clazz = basicParameter.getClazz();
        }
        this.globalConfiguration = new GlobalConfiguration();
        if (basicParameter.getAutoTrim() == null) {
            if (prentAbstractHolder != null) {
                globalConfiguration.setAutoTrim(prentAbstractHolder.getGlobalConfiguration().getAutoTrim());
            }
        } else {
            globalConfiguration.setAutoTrim(basicParameter.getAutoTrim());
        }

        if (basicParameter.getUse1904windowing() == null) {
            if (prentAbstractHolder != null) {
                globalConfiguration.setUse1904windowing(
                    prentAbstractHolder.getGlobalConfiguration().getUse1904windowing());
            }
        } else {
            globalConfiguration.setUse1904windowing(basicParameter.getUse1904windowing());
        }

        if (basicParameter.getLocale() == null) {
            if (prentAbstractHolder != null) {
                globalConfiguration.setLocale(prentAbstractHolder.getGlobalConfiguration().getLocale());
            }
        } else {
            globalConfiguration.setLocale(basicParameter.getLocale());
        }

    }

    @Override
    public Map<ConverterKey, Converter<?>> converterMap() {
        return getConverterMap();
    }

    @Override
    public GlobalConfiguration globalConfiguration() {
        return getGlobalConfiguration();
    }

    @Override
    public boolean isNew() {
        return getNewInitialization();
    }

}
