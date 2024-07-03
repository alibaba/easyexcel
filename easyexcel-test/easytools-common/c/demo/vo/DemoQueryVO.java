package com.alibaba.atasuper.web.controller.demo.vo;

import javax.validation.constraints.NotNull;

import com.alibaba.atasuper.api.demo.enums.DemoStatusEnum;
import com.alibaba.atasuper.api.demo.param.DemoSelector;
import com.alibaba.atasuper.api.image.vo.ImageVO;
import com.alibaba.atasuper.api.user.param.DomainUserSelector;
import com.alibaba.atasuper.api.user.vo.UserVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 单个对象
 *
 * @author 是仪
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DemoQueryVO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 用户id
     */
    @NotNull
    private String userId;

    /**
     * 用户
     *
     * @see DemoSelector#setUser(Boolean)
     * @see DemoSelector#setUserDomainUserSelector(DomainUserSelector)
     */
    @NotNull
    private UserVO user;

    /**
     * 状态，一般非空，还要有默认值
     *
     * @see DemoStatusEnum
     */
    private String status;

    /**
     * 图片的key, 图片存储一般存储key，别存储连接。
     */
    private String imageKey;

    /**
     * 图片
     *
     * @see DemoSelector#setImage(Boolean)
     */
    private ImageVO image;

    /**
     * 姓名，字符串字段一般默认空字符串，不建议使用非空
     */
    private String name;

}
