package com.alibaba.easytools.spring.oss;

import com.aliyun.oss.model.CannedAccessControlList;
import lombok.Getter;

/**
 * 默认的oss枚举
 *
 * @author 是仪
 */
@Getter
public enum DefaultOssKindEnum implements OssKindEnum {
    /**
     * 默认
     */
    DEFAULT("default", CannedAccessControlList.Private, null),

    // 分号
    ;

    /**
     * 样式的格式
     */
    final String code;
    /**
     * 这里只支持 私有（每次访问都要授权 比如身份证）和共有读（获得连接了可以永久访问）
     */
    final CannedAccessControlList objectAcl;

    /**
     * 默认的一些处理 目前主要是样式处理
     */
    final String process;

    DefaultOssKindEnum(String code, CannedAccessControlList objectAcl, String process) {
        this.code = code;
        this.objectAcl = objectAcl;
        this.process = process;
    }

    @Override
    public String getDescription() {
        return this.code;
    }
}
