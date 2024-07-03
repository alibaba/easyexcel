package com.alibaba.easytools.spring.oss;

import com.alibaba.easytools.base.enums.BaseEnum;

import com.aliyun.oss.model.CannedAccessControlList;

/**
 * oss枚举
 *
 * @author 是仪
 */
public interface OssKindEnum extends BaseEnum<String> {

    /**
     * 获取权限控制
     *
     * @return
     */
    CannedAccessControlList getObjectAcl();

    /**
     * 样式处理
     *
     * @return
     */
    String getProcess();
}
