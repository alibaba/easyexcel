package com.alibaba.easytools.base.enums;

/**
 * 排序方向的枚举
 *
 * @author 是仪
 */
public enum OrderByDirectionEnum implements BaseEnum<String> {

    /**
     * 升序
     */
    ASC,
    /**
     * 降序
     */
    DESC,

    /**
     * 智能排序
     * 需要人工处理的排序 也就是先不指定，到下一层去解析后处理
     */
    SMART,
    ;

    @Override
    public String getCode() {
        return this.name();
    }

    @Override
    public String getDescription() {
        return this.name();
    }
}
