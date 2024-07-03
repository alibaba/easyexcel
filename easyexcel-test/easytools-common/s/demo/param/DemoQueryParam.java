package com.alibaba.atasuper.api.demo.param;

import java.util.List;

import com.alibaba.atasuper.tools.base.wrapper.param.CoreQueryParam;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * 单表普通查询
 * 最多返回500条
 *
 * @author 是仪
 */
@Data
@NoArgsConstructor
public class DemoQueryParam extends CoreQueryParam {

    /**
     * id列表
     * 传null会报错
     */
    @NonNull
    private List<Long> idList;

    /**
     * 用户id
     * 传null会报错
     */
    @NonNull
    private Long userId;

    /**
     * 用户id
     * 传null不查询
     */
    private Long userIdWhenPresent;

    /**
     * 用户id列表
     * 传null不查询
     */
    @NonNull
    private List<Long> userIdList;

}
