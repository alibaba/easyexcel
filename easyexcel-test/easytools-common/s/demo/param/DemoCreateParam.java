package com.alibaba.atasuper.api.demo.param;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.alibaba.atasuper.api.demo.enums.DemoStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 创建对象
 *
 * @author 是仪
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DemoCreateParam {

    /**
     * 用户id
     */
    @NotNull
    private Long userId;

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
     * 加入时间，必须gmt开头，日期字段没办法默认值，直接默认空
     */
    private Date gmtJoin;
}
