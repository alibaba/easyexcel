package com.alibaba.easytools.base.enums.oss;

import com.alibaba.easytools.base.enums.BaseEnum;

/**
 * oss枚举
 *
 * @author 是仪
 */
public interface BaseOssKindEnum extends BaseEnum<String> {

    /**
     * 获取权限控制
     *
     * @return
     */
    OssObjectAclEnum getOssObjectAcl();

    /**
     * 样式处理
     *
     * @return
     */
    String getProcess();

}
