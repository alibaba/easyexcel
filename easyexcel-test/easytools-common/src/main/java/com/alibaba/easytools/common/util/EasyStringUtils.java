package com.alibaba.easytools.common.util;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * String工具类
 *
 * @author Jiaju Zhuang
 */
public class EasyStringUtils {
    /**
     * 0的字符
     */
    private static final char ZERO_CHAR = '0';

    /**
     * 去掉工号前面的0
     *
     * @param userId 工号
     * @return 修改后的工号
     */
    public static String cutUserId(String userId) {
        if (!org.apache.commons.lang3.StringUtils.isNumeric(userId)) {
            return userId;
        }
        int startIndex = 0;
        for (int i = 0; i < userId.length(); i++) {
            char c = userId.charAt(i);
            // 查询第一个不是0的位置
            if (ZERO_CHAR == c) {
                startIndex = i + 1;
            } else {
                break;
            }
        }
        // 可能整个账号都是0
        if (startIndex == userId.length()) {
            return "0";
        }
        return userId.substring(startIndex);
    }

    /**
     * 去除花名后面的工号
     *
     * @param name 姓名或者花名
     * @return 去除工号后的姓名或者花名
     */
    public static String cutName(String name, String workNo) {
        if (StringUtils.isBlank(workNo) || StringUtils.isBlank(name)) {
            return name;
        }
        // 这里可能会出现 0结的情况
        String cutName = RegExUtils.removeFirst(name, workNo);
        int lastIndex = cutName.length();
        for (int i = cutName.length() - 1; i >= 0; i--) {
            char c = cutName.charAt(i);
            // 查询第最后一个不是0的位置
            if (ZERO_CHAR == c) {
                lastIndex = i;
            } else {
                break;
            }
        }
        return cutName.substring(0, lastIndex);
    }

    /**
     * 增加工号前面的0
     *
     * @param userId 工号
     * @return 修改后的工号
     */
    public static String padUserId(String userId) {
        if (!StringUtils.isNumeric(userId)) {
            return userId;
        }
        return StringUtils.leftPad(userId, 6, '0');
    }

    /**
     * 构建展示的名称
     *
     * @param name     姓名
     * @param nickName 花名
     * @return 展示名称 姓名（花名）
     */
    public static String buildShowName(String name, String nickName) {
        StringBuilder showName = new StringBuilder();
        if (StringUtils.isNotBlank(name)) {
            showName.append(name);
        }
        if (StringUtils.isNotBlank(nickName)) {
            showName.append("(");
            showName.append(nickName);
            showName.append(")");
        }
        return showName.toString();
    }

    /**
     * 将多个字符串 拼接在一起
     *
     * @param delimiter 分隔符 不能为空
     * @param elements  字符串 可以为空 会忽略空的字符串
     * @return
     */
    public static String join(CharSequence delimiter, CharSequence... elements) {
        if (elements == null) {
            return null;
        }
        List<CharSequence> charSequenceList = Arrays.stream(elements).filter(
            org.apache.commons.lang3.StringUtils::isNotBlank).collect(Collectors.toList());
        if (charSequenceList.isEmpty()) {
            return null;
        }
        return String.join(delimiter, charSequenceList);
    }

    /**
     * 限制一个string字符串的长度 ，超过长度 会用... 替换
     *
     * @param str    字符串
     * @param length 限制长度
     * @return
     */
    public static String limitString(String str, int length) {
        if (Objects.isNull(str)) {
            return null;
        }
        String limitString = StringUtils.substring(str, 0, length);
        if (limitString.length() == length) {
            limitString += "...";
        }
        return limitString;
    }

    /**
     * 根据冒号拼接在一起
     *
     * @param objs 对象
     * @return 拼接完成的数据
     */
    public static String joinWithColon(Object... objs) {
        return StringUtils.join(objs, ":");
    }

}
