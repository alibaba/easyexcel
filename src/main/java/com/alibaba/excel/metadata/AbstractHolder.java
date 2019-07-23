package com.alibaba.excel.metadata;

import java.util.List;

/**
 * Write/read holder
 *
 * @author zhuangjiaju
 */
public abstract class AbstractHolder implements Holder {
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

    public AbstractHolder(BasicParameter basicParameter, AbstractHolder prentAbstractHolder) {
        this.newInitialization = Boolean.TRUE;
        this.head = basicParameter.getHead();
        this.clazz = basicParameter.getClazz();
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

    @Override
    public GlobalConfiguration globalConfiguration() {
        return getGlobalConfiguration();
    }

    @Override
    public boolean isNew() {
        return getNewInitialization();
    }

}
