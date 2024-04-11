package com.alibaba.excel.read.metadata.holder.xls;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.excel.enums.RowTypeEnum;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.read.metadata.holder.ReadSheetHolder;
import com.alibaba.excel.read.metadata.holder.ReadWorkbookHolder;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * sheet holder
 *
 * @author Jiaju Zhuang
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class XlsReadSheetHolder extends ReadSheetHolder {
    /**
     * Row type.Temporary storage, last set in <code>ReadRowHolder</code>.
     */
    private RowTypeEnum tempRowType;
    /**
     * Temp object index.
     */
    private Integer tempObjectIndex;
    /**
     * Temp object index.
     */
    private Map<Integer, String> objectCacheMap;

    public XlsReadSheetHolder(ReadSheet readSheet, ReadWorkbookHolder readWorkbookHolder) {
        super(readSheet, readWorkbookHolder);
        tempRowType = RowTypeEnum.EMPTY;
        objectCacheMap = new HashMap<Integer, String>(16);
    }
}
