package com.alibaba.excel.write.metadata.fill;

import java.util.Collection;

/**
 * Multiple lists are supported when packing
 *
 * @author Jiaju Zhuang
 **/
public class FillWrapper {
    /**
     * The collection prefix that needs to be filled.
     */
    private String name;
    /**
     * Data that needs to be filled.
     */
    private Collection collectionData;

    public FillWrapper(Collection collectionData) {
        this.collectionData = collectionData;
    }

    public FillWrapper(String name, Collection collectionData) {
        this.name = name;
        this.collectionData = collectionData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection getCollectionData() {
        return collectionData;
    }

    public void setCollectionData(Collection collectionData) {
        this.collectionData = collectionData;
    }
}
