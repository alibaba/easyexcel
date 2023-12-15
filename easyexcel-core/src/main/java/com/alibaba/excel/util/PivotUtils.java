package com.alibaba.excel.util;

import org.apache.commons.compress.utils.Lists;
import org.apache.commons.math3.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author phaeris
 * @since 2023/12/15
 */
public class PivotUtils {

    private PivotUtils() {}

    /**
     * get pivot table head and data
     *
     * @param rowFields    pivot row fields
     * @param columnFields pivot column fields
     * @param aggFields    aggregator fields
     * @param data         original data
     * @return head & data
     */
    public static Pair<List<List<String>>, List<List<Object>>> getHeadAndData(List<String> rowFields, List<String> columnFields, List<String> aggFields,
                                                                              List<Map<String, Object>> data) {
        return getHeadAndData(rowFields, columnFields, aggFields, null, data);
    }

    /**
     * get pivot table head and data
     *
     * @param rowFields    pivot row fields
     * @param columnFields pivot column fields
     * @param aggFields    aggregator fields
     * @param alias        alias mapping
     * @param data         original data
     * @return head & data
     */
    public static Pair<List<List<String>>, List<List<Object>>> getHeadAndData(List<String> rowFields, List<String> columnFields, List<String> aggFields,
                                                                              Map<String, String> alias, List<Map<String, Object>> data) {
        //current row size empty data
        final List<Object> emptyRowData = rowFields.stream().map(x -> null).collect(Collectors.toList());
        //current column size empty data
        final List<Object> emptyColData = columnFields.stream().map(x -> null).collect(Collectors.toList());

        //left top head
        List<List<String>> centerHeadPart = rowFields.stream().map(col -> {
            List<String> centerHead = new ArrayList<>(columnFields);
            centerHead.add(col);
            return centerHead;
        }).collect(Collectors.toList());
        //aggregator head
        List<List<String>> aggHeadPart = Lists.newArrayList();

        //data init, fill row data
        Map<List<Object>, List<Object>> dataList = data.stream()
            .map(row -> getGroup(row, rowFields, emptyRowData))
            .distinct()
            .collect(Collectors.toMap(x -> x, ArrayList::new, (o, n) -> n, LinkedHashMap::new));
        //original data group by columns
        Map<List<Object>, List<Map<String, Object>>> groupByCols = data.stream()
            .collect(Collectors.groupingBy(row -> getGroup(row, columnFields, emptyColData), LinkedHashMap::new, Collectors.toList()));

        //fill aggregator head and data
        for (Map.Entry<List<Object>, List<Map<String, Object>>> eachColData : groupByCols.entrySet()) {
            //each column head
            List<String> colHead = eachColData.getKey().stream()
                .map(x -> Objects.isNull(x) ? StringUtils.EMPTY : String.valueOf(x))
                .collect(Collectors.toList());
            //each col data group by row
            Map<List<Object>, List<Map<String, Object>>> groupByRows = eachColData.getValue()
                .stream()
                .collect(Collectors.groupingBy(x -> getGroup(x, rowFields, emptyRowData)));
            //aggregator head nums: column data size * aggregator nums
            for (String aggField : aggFields) {
                //col head
                List<String> copyColHead = new ArrayList<>(colHead);
                copyColHead.add(aggField);
                aggHeadPart.add(copyColHead);
                //this col all data for each row
                dataList.forEach((originalRowData, finalRowData) -> {
                    List<Map<String, Object>> curRowData = groupByRows.get(originalRowData);
                    if (curRowData != null && !curRowData.isEmpty()) {
                        //group by column and row, get first
                        finalRowData.add(curRowData.get(0).get(aggField));
                    } else {
                        //no match, fill empty
                        finalRowData.add(null);
                    }
                });
            }
        }

        //merge and replace alias head
        centerHeadPart.addAll(aggHeadPart);
        List<List<String>> head = centerHeadPart.stream()
            .map(x -> x.stream().map(c -> getAlias(alias, c)).collect(Collectors.toList()))
            .collect(Collectors.toList());

        return Pair.create(head, new ArrayList<>(dataList.values()));
    }

    /**
     * get group
     *
     * @param row       row data
     * @param fields    fields
     * @param emptyData or else empty data object
     * @return group
     */
    private static List<Object> getGroup(Map<String, Object> row, List<String> fields, List<Object> emptyData) {
        List<Object> dataList = fields.stream()
            .map(row::get)
            .collect(Collectors.toList());
        List<Object> nonNull = dataList.stream().filter(Objects::nonNull).collect(Collectors.toList());
        return nonNull.isEmpty() ? emptyData : dataList;
    }

    /**
     * get head alias
     *
     * @param alias alias mapping
     * @param field field
     * @return alias of field
     */
    private static String getAlias(Map<String, String> alias, String field) {
        return alias != null && !alias.isEmpty() && StringUtils.isNotBlank(alias.get(field))
            ? alias.get(field)
            : field;
    }
}
