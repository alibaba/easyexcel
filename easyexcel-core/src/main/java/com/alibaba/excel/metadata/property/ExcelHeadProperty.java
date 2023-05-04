package com.alibaba.excel.metadata.property;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.enums.HeadKindEnum;
import com.alibaba.excel.metadata.ConfigurationHolder;
import com.alibaba.excel.metadata.FieldCache;
import com.alibaba.excel.metadata.FieldWrapper;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.Holder;
import com.alibaba.excel.util.ClassUtils;
import com.alibaba.excel.util.FieldUtils;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.excel.util.StringUtils;
import com.alibaba.excel.write.metadata.holder.AbstractWriteHolder;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Define the header attribute of excel
 *
 * @author jipengfei
 */
@Getter
@Setter
@EqualsAndHashCode
public class ExcelHeadProperty {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelHeadProperty.class);
    /**
     * Custom class
     */
    private Class<?> headClazz;
    /**
     * The types of head
     */
    private HeadKindEnum headKind;
    /**
     * The number of rows in the line with the most rows
     */
    private int headRowNumber;
    /**
     * Configuration header information
     */
    private Map<Integer, Head> headMap;

    public ExcelHeadProperty(ConfigurationHolder configurationHolder, Class<?> headClazz, List<List<String>> head) {
        this.headClazz = headClazz;
        headMap = new TreeMap<>();
        headKind = HeadKindEnum.NONE;
        headRowNumber = 0;
        if (head != null && !head.isEmpty()) {
            int headIndex = 0;
            for (int i = 0; i < head.size(); i++) {
                if (configurationHolder instanceof AbstractWriteHolder) {
                    if (((AbstractWriteHolder)configurationHolder).ignore(null, i)) {
                        continue;
                    }
                }
                headMap.put(headIndex, new Head(headIndex, null, null, head.get(i), Boolean.FALSE, Boolean.TRUE));
                headIndex++;
            }
            headKind = HeadKindEnum.STRING;
        }
        // convert headClazz to head
        initColumnProperties(configurationHolder);

        initHeadRowNumber();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("The initialization sheet/table 'ExcelHeadProperty' is complete , head kind is {}", headKind);
        }
    }

    private void initHeadRowNumber() {
        headRowNumber = 0;
        for (Head head : headMap.values()) {
            List<String> list = head.getHeadNameList();
            if (list != null && list.size() > headRowNumber) {
                headRowNumber = list.size();
            }
        }
        for (Head head : headMap.values()) {
            List<String> list = head.getHeadNameList();
            if (list != null && !list.isEmpty() && list.size() < headRowNumber) {
                int lack = headRowNumber - list.size();
                int last = list.size() - 1;
                for (int i = 0; i < lack; i++) {
                    list.add(list.get(last));
                }
            }
        }
    }

    private void initColumnProperties(ConfigurationHolder configurationHolder) {
        if (headClazz == null) {
            return;
        }
        FieldCache fieldCache = ClassUtils.declaredFields(headClazz, configurationHolder);

        for (Map.Entry<Integer, FieldWrapper> entry : fieldCache.getSortedFieldMap().entrySet()) {
            initOneColumnProperty(entry.getKey(), entry.getValue(),
                fieldCache.getIndexFieldMap().containsKey(entry.getKey()));
        }
        headKind = HeadKindEnum.CLASS;
    }

    /**
     * Initialization column property
     *
     * @param index
     * @param field
     * @param forceIndex
     * @return Ignore current field
     */
    private void initOneColumnProperty(int index, FieldWrapper field, Boolean forceIndex) {
        List<String> tmpHeadList = new ArrayList<>();
        boolean notForceName = field.getHeads() == null || field.getHeads().length == 0
            || (field.getHeads().length == 1 && StringUtils.isEmpty(field.getHeads()[0]));
        if (headMap.containsKey(index)) {
            tmpHeadList.addAll(headMap.get(index).getHeadNameList());
        } else {
            if (notForceName) {
                tmpHeadList.add(field.getFieldName());
            } else {
                Collections.addAll(tmpHeadList, field.getHeads());
            }
        }
        Head head = new Head(index, field.getField(), field.getFieldName(), tmpHeadList, forceIndex, !notForceName);
        headMap.put(index, head);
    }

    public boolean hasHead() {
        return headKind != HeadKindEnum.NONE;
    }

}
