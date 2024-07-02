package com.alibaba.easytools.base.wrapper.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.alibaba.easytools.base.constant.EasyToolsConstant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Range;

/**
 * 分页查询的参数
 *
 * @author zhuangjiaju
 * @date 2021/06/26
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PageQueryRequest implements Serializable {
    private static final long serialVersionUID = EasyToolsConstant.SERIAL_VERSION_UID;
    /**
     * 页码
     */
    @NotNull(message = "分页页码不能为空")
    @Builder.Default
    private Integer pageNo = 1;
    /**
     * 分页条数
     */
    @NotNull(message = "分页大小不能为空")
    @Range(min = 1, max = EasyToolsConstant.MAX_PAGE_SIZE,
        message = "分页大小必须在1-" + EasyToolsConstant.MAX_PAGE_SIZE + "之间")
    @Builder.Default
    private Integer pageSize = 10;

}
