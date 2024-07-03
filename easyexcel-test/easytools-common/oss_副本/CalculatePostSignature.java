package com.alibaba.easytools.spring.oss;

import com.aliyun.oss.model.CannedAccessControlList;
import lombok.Data;

/**
 * 计算请求签名对象
 *
 * @author 是仪
 */
@Data
public class CalculatePostSignature {

    /**
     * 请求的host
     */
    private String host;

    /**
     * 请求的策略
     */
    private String policy;

    /**
     * 授权id
     */
    private String accessId;

    /**
     * 授权签名
     */
    private String signature;

    /**
     * 过期时间 时间戳
     */
    private Long expire;

    /**
     * 文件上传的key
     */
    private String key;


    /**
     * 文件连接
     */
    private String url;

    /**
     * 权限控制
     *
     * @see CannedAccessControlList
     */
    private String objectAcl;
}
