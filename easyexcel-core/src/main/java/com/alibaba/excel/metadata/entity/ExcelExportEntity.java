package com.alibaba.excel.metadata.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * Excel export dynamic column entity
 *
 * @author wangfarui
 * @since 2022/6/27
 */
@Getter
@Setter
public class ExcelExportEntity {

    /**
     * data field name
     */
    private String key;

    /**
     * Table column name
     */
    private String name;

    public ExcelExportEntity() {}

    public ExcelExportEntity(String key, String name) {
        this.key = key;
        this.name = name;
    }
}
