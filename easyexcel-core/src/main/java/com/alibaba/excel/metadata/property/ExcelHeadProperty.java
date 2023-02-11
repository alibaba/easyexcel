package com.alibaba.excel.metadata.property;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.enums.HeadKindEnum;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.Holder;
import com.alibaba.excel.metadata.entity.ExcelExportEntity;
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
    /**
     * Fields ignored
     */
    private Map<String, Field> ignoreMap;

    public ExcelHeadProperty(Holder holder, Class<?> headClazz, List<List<String>> head) {
        this.headClazz = headClazz;
        headMap = new TreeMap<>();
        ignoreMap = MapUtils.newHashMap();
        headKind = HeadKindEnum.NONE;
        headRowNumber = 0;

        if (headClazz == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("The current sheet/table is not specified with headClazz");
            }
            return;
        }

        // dynamic column
        if (holder instanceof AbstractWriteHolder &&
            (!CollectionUtils.isEmpty(((AbstractWriteHolder) holder).getDynamicColumnFieldNames())
                || !CollectionUtils.isEmpty(((AbstractWriteHolder) holder).getDynamicColumnEntities()))) {
            if (!CollectionUtils.isEmpty(((AbstractWriteHolder) holder).getDynamicColumnFieldNames())) {
                initColumnNameProperties(((AbstractWriteHolder) holder).getDynamicColumnFieldNames());
            }
            if (!CollectionUtils.isEmpty(((AbstractWriteHolder) holder).getDynamicColumnEntities())) {
                initColumnEntityProperties(((AbstractWriteHolder) holder).getDynamicColumnEntities());
            }
        } else {
            if (head != null && !head.isEmpty()) {
                int headIndex = 0;
                for (int i = 0; i < head.size(); i++) {
                    if (holder instanceof AbstractWriteHolder) {
                        if (((AbstractWriteHolder)holder).ignore(null, i)) {
                            continue;
                        }
                    }
                    headMap.put(headIndex, new Head(headIndex, null, null, head.get(i), Boolean.FALSE, Boolean.TRUE));
                    headIndex++;
                }
                headKind = HeadKindEnum.STRING;
            }
            // convert headClazz to head
            initColumnProperties(holder);
        }

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

    private void initColumnNameProperties(Collection<String> dynamicColumnFieldNames) {
        headKind = HeadKindEnum.CLASS;
        int i = 0;
        List<Field> fields = FieldUtils.getAllFields(headClazz);

        for (String filedName : dynamicColumnFieldNames) {
            for (Field field : fields) {
                if (filedName.equals(field.getName())) {
                    initOneColumnProperty(i++, field, true);
                    break;
                }
            }
        }

        if (headMap.size() == fields.size()) {
            return;
        }

        for (Field field : fields) {
            if (!dynamicColumnFieldNames.contains(field.getName())) {
                ignoreMap.put(field.getName(), field);
            }
        }
    }

    private void initColumnEntityProperties(Collection<ExcelExportEntity> dynamicColumnEntities) {
        headKind = HeadKindEnum.CLASS;
        int i = 0;
        List<Field> fields = FieldUtils.getAllFields(headClazz);

        for (ExcelExportEntity entity : dynamicColumnEntities) {
            for (Field field : fields) {
                if (field.getName().equals(entity.getKey())) {
                    initOneColumnProperty(i++, field, entity);
                    break;
                }
            }
        }

        if (headMap.size() == fields.size()) {
            return;
        }

        Set<String> keyList = dynamicColumnEntities.stream().map(ExcelExportEntity::getKey).collect(Collectors.toSet());
        for (Field field : fields) {
            if (!keyList.contains(field.getName())) {
                ignoreMap.put(field.getName(), field);
            }
        }
    }

    private void initColumnProperties(Holder holder) {
        if (headClazz == null) {
            return;
        }
        // Declared fields
        Map<Integer, Field> sortedAllFieldMap = MapUtils.newTreeMap();
        Map<Integer, Field> indexFieldMap = MapUtils.newTreeMap();

        boolean needIgnore = (holder instanceof AbstractWriteHolder) && (
            !CollectionUtils.isEmpty(((AbstractWriteHolder)holder).getExcludeColumnFieldNames()) || !CollectionUtils
                .isEmpty(((AbstractWriteHolder)holder).getExcludeColumnIndexes()) || !CollectionUtils
                .isEmpty(((AbstractWriteHolder)holder).getIncludeColumnFieldNames()) || !CollectionUtils
                .isEmpty(((AbstractWriteHolder)holder).getIncludeColumnIndexes()));

        ClassUtils.declaredFields(headClazz, sortedAllFieldMap, indexFieldMap, ignoreMap, needIgnore, holder);

        for (Map.Entry<Integer, Field> entry : sortedAllFieldMap.entrySet()) {
            initOneColumnProperty(entry.getKey(), entry.getValue(), indexFieldMap.containsKey(entry.getKey()));
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
    private void initOneColumnProperty(int index, Field field, Boolean forceIndex) {
        ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
        List<String> tmpHeadList = new ArrayList<String>();
        String fieldName = FieldUtils.resolveCglibFieldName(field);
        boolean notForceName = excelProperty == null || excelProperty.value().length <= 0
            || (excelProperty.value().length == 1 && StringUtils.isEmpty((excelProperty.value())[0]));
        if (headMap.containsKey(index)) {
            tmpHeadList.addAll(headMap.get(index).getHeadNameList());
        } else {
            if (notForceName) {
                tmpHeadList.add(fieldName);
            } else {
                Collections.addAll(tmpHeadList, excelProperty.value());
            }
        }
        Head head = new Head(index, field, fieldName, tmpHeadList, forceIndex, !notForceName);
        headMap.put(index, head);
    }

    /**
     * Initialization column property
     *
     * @param index map index
     * @param field the current field
     * @param entity ExcelExportEntity
     */
    private void initOneColumnProperty(int index, Field field, ExcelExportEntity entity) {
        Head head = new Head(index, field, entity.getKey(), entity.getName());
        headMap.put(index, head);
    }

    public boolean hasHead() {
        return headKind != HeadKindEnum.NONE;
    }

}
