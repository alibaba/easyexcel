package com.alibaba.easytools.base.enums.oss;

import com.alibaba.easytools.base.enums.BaseEnum;

import lombok.Getter;

/**
 * oss权限
 *
 * @author 是仪
 */
@Getter
public enum OssObjectAclEnum implements BaseEnum<String> {
    /**
     * 共有读
     */
    PUBLIC_READ("public-read"),

    /**
     * 私有
     */
    PRIVATE("private"),
    // 分号
    ;

    final String ossAcl;

    OssObjectAclEnum(String ossAcl) {
        this.ossAcl = ossAcl;
    }

    @Override
    public String getCode() {
        return this.name();
    }

    @Override
    public String getDescription() {
        return this.name();
    }
}
