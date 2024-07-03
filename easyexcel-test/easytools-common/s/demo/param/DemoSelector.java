package com.alibaba.atasuper.api.demo.param;

import com.alibaba.atasuper.api.user.param.DomainUserSelector;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 选择器
 *
 * @author 是仪
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DemoSelector {

    /**
     * 用户
     */
    private Boolean user;

    /**
     * 用户
     */
    private DomainUserSelector userDomainUserSelector;

    /**
     * 图片
     */
    private Boolean image;

    /**
     * 创建人
     */
    private Boolean createUser;

    /**
     * 修改人
     */
    private Boolean modifiedUser;

    /**
     * 查询withBlobs
     */
    private Boolean withBlobs;
}
