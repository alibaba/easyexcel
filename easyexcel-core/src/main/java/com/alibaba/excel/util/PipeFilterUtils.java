package com.alibaba.excel.util;

import org.springframework.cglib.beans.BeanMap;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Description:
 *
 * @author linfeng
 * @version 1.0.0
 * @since 2023/5/27 22:51
 */
public class PipeFilterUtils {

    private static final String PIPELINE_FLAG = "|";
    private static final String MULTI_PIPE_FLAG = "\\|";
    private static final String FILTER_PARAM_FLAG = ":";
    private static final String PARAM_FLAG = ",";

    private static final String VAR_FLAG = "\\.";

    /**
     * Return {@code true} if the supplied Collection is {@code null} or empty.
     * Otherwise, return {@code false}.
     * @param collection the Collection to check
     * @return whether the given Collection is empty
     */
    public static boolean isEmpty(@Nullable Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    /**
     * Return {@code true} if the supplied Map is {@code null} or empty.
     * Otherwise, return {@code false}.
     * @param map the Map to check
     * @return whether the given Map is empty
     */
    public static boolean isEmpty(@Nullable Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }

    public static String trim(String source) {
        return StringUtils.isBlank(source) ? "" : source.trim();
    }

    public static String[] getPipelines(String pipelines) {
        return pipelines.split(MULTI_PIPE_FLAG);
    }

    public static String[] getPipeFilter(String pipeline) {
        return pipeline.split(FILTER_PARAM_FLAG);
    }

    public static String[] getPipeFilterParams(String pipeFilter) {
        return pipeFilter.split(PARAM_FLAG);
    }

    /**
     * 是否是管道，是管道返回true，反之返回false
     *
     * @param variable 表达式
     * @return 是否管道
     */
    public static boolean isPipeline(String variable) {
        return Objects.nonNull(variable) && variable.contains(PIPELINE_FLAG);
    }

    /**
     * 获取变量字符串的管道前面的变量
     *
     * @param variable 包含管道的变量字符串
     * @return 管道前面的变量字符串
     */
    public static String getVariableName(String variable) {
        String[] varArray = PipeFilterUtils.getPipelines(variable);
        if (Objects.nonNull(varArray[0])) {
            return varArray[0].trim();
        }
        return null;
    }

    /**
     * 从map中获取值
     * @param dataMap map
     * @param key 例如: demo.test.name
     * @return 返回值
     */
    public static Object getValueOfMap(Map dataMap, String key) {

        if (StringUtils.isBlank(key)) {
            return null;
        }
        Map tempDataMap = dataMap;
        String[] keyArray = key.split(VAR_FLAG);
        for (int i = 0; i < keyArray.length; i++) {
            if (StringUtils.isBlank(keyArray[i])) {
                continue;
            }
            Object obj = tempDataMap.get(keyArray[i]);
            if (Objects.isNull(obj)) {
                continue;
            }
            if (i == keyArray.length - 1) {
                return obj;
            } else {
                if (obj instanceof BeanMap) {
                    tempDataMap = (BeanMap) obj;
                } else if (obj instanceof Map) {
                    tempDataMap = (Map)obj;
                } else {
                    tempDataMap = BeanMapUtils.create(obj);
                }
            }
        }
        return null;
    }

}