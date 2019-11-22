package com.alibaba.excel.metadata;

import com.alibaba.excel.converters.Converter;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Write/read holder
 *
 * @author Jiaju Zhuang
 */
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
    private Class clazz;
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
    private Map<String, Converter> converterMap;

    public AbstractHolder(BasicParameter basicParameter, AbstractHolder prentAbstractHolder) {
        this.newInitialization = Boolean.TRUE;
        final List<List<String>> basicHead = basicParameter.getHead();
        if (basicHead == null && basicParameter.getClazz() == null && prentAbstractHolder != null) {
            this.clazz = prentAbstractHolder.getClazz();
            this.head = prentAbstractHolder.getHead();
        } else {
            this.clazz = basicParameter.getClazz();
            if (basicHead != null) {
                //The basicParameter head list may not implement the List#add(T t) method.
                final List<List<String>> safeBasicHead = new LinkedList<List<String>>();
                for (List<String> headStringList : basicHead) {
                    safeBasicHead.add(new LinkedList<String>(headStringList));
                }
                this.head = safeBasicHead;
            }
        }
        this.globalConfiguration = new GlobalConfiguration();
        if (basicParameter.getAutoTrim() == null) {
            if (prentAbstractHolder == null) {
                globalConfiguration.setAutoTrim(Boolean.TRUE);
            } else {
                globalConfiguration.setAutoTrim(prentAbstractHolder.getGlobalConfiguration().getAutoTrim());
            }
        } else {
            globalConfiguration.setAutoTrim(basicParameter.getAutoTrim());
        }
    }

    public Boolean getNewInitialization() {
        return newInitialization;
    }

    public void setNewInitialization(Boolean newInitialization) {
        this.newInitialization = newInitialization;
    }

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

    public GlobalConfiguration getGlobalConfiguration() {
        return globalConfiguration;
    }

    public void setGlobalConfiguration(GlobalConfiguration globalConfiguration) {
        this.globalConfiguration = globalConfiguration;
    }

    public Map<String, Converter> getConverterMap() {
        return converterMap;
    }

    public void setConverterMap(Map<String, Converter> converterMap) {
        this.converterMap = converterMap;
    }

    @Override
    public Map<String, Converter> converterMap() {
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
