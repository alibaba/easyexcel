package com.alibaba.atasuper.api.demo.param;

import java.util.List;

import com.alibaba.atasuper.tools.base.wrapper.param.CorePageQueryParam;

import lombok.Data;
import lombok.NonNull;

/**
 * 综合搜索
 * 慎用，一般涉及到join
 *
 * @author 是仪
 */
@Data
public class DemoComprehensivePageQueryParam extends CorePageQueryParam {

    /**
     * 用户id
     * 传null会报错
     */
    @NonNull
    private Long userId;

    /**
     * 用户id
     * 传null会报错
     */
    @NonNull
    private List<Long> userIdList;
}
