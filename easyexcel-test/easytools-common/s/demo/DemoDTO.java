package com.alibaba.atasuper.api.demo;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.alibaba.atasuper.api.demo.enums.DemoStatusEnum;
import com.alibaba.atasuper.api.demo.param.DemoSelector;
import com.alibaba.atasuper.api.image.dto.ImageDTO;
import com.alibaba.atasuper.api.user.dto.DomainUserDTO;
import com.alibaba.atasuper.api.user.param.DomainUserSelector;
import com.alibaba.easytools.base.constant.EasyToolsConstant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * TODO 示例对象 仅放需要使用的字段
 *
 * @author 是仪
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DemoDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = EasyToolsConstant.SERIAL_VERSION_UID;

    /**
     * 主键
     */
    private Long id;

    /**
     * 用户id
     */
    @NotNull
    private Long userId;

    /**
     * 用户
     *
     * @see DemoSelector#setUser(Boolean)
     * @see DemoSelector#setUserDomainUserSelector(DomainUserSelector)
     */
    @NotNull
    private DomainUserDTO user;

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
    private ImageDTO image;

    /**
     * 姓名，字符串字段一般默认空字符串，不建议使用非空
     */
    private String name;

    /**
     * 加入时间，必须gmt开头，日期字段没办法默认值，直接默认空
     */
    private Date gmtJoin;

    /**
     * 被访问次数，统计型的数据一般默认0，建议使用bigint代替int，设置非空
     */
    private Long viewedCount;

    /**
     * 角色id, 这种外键没有的情况下用空 别用0
     */
    private Long roleId;

    /**
     * 是否同意协议 ，布尔值
     */
    private Boolean agreeProtocol;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 创建人用户id
     */
    private Long createUserId;

    /**
     * 创建人
     *
     * @see DemoSelector#setCreateUser(Boolean)
     */
    private DomainUserDTO createUser;

    /**
     * 修改人用户id
     */
    private Long modifiedUserId;

    /**
     * 修改人
     *
     * @see DemoSelector#setModifiedUser(Boolean)
     */
    private DomainUserDTO modifiedUser;

    /**
     * 备注字段，一般设置可以为空
     *
     * @see DemoSelector#setWithBlobs(Boolean)
     */
    private String remark;

    /**
     * 是否已经被删除
     * 这个只有在 {@code fill} 时才不会为空 其他任何情况都会为空
     */
    private Boolean deleted;

}
