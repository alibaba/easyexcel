package com.alibaba.easytools.base.enums;

import lombok.Getter;

/**
 * 删除标记枚举
 * <p>
 * 为了兼容唯一主键+逻辑删除。使用DeletedId来标记当前数据是否删除。如果是0 则代表未删除。其他任何情况都代表已经删除。
 * 删除的时候 执行语句：update set deleted_id = di  where 条件=条件；
 *
 * @author 是仪
 */
@Getter
public enum DeletedIdEnum implements BaseEnum<Long> {

    /**
     * 未删除
     */
    NOT_DELETED(0L, "未删除"),

    ;

    final Long code;
    final String description;

    DeletedIdEnum(Long code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 判断 当前数据是否已经逻辑删除
     *
     * @param deletedId 表中的deleted_id
     * @return 是否已经删除
     */
    public static boolean isDeleted(Long deletedId) {
        return !NOT_DELETED.getCode().equals(deletedId);
    }
}
