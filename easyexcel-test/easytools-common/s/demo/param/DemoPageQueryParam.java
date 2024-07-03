package com.alibaba.atasuper.api.demo.param;

import java.util.List;

import com.alibaba.atasuper.tools.base.wrapper.param.CorePageQueryParam;
import com.alibaba.easytools.base.wrapper.param.OrderBy;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * 单表分页查询
 *
 * @author 是仪
 */
@Data
@NoArgsConstructor
public class DemoPageQueryParam extends CorePageQueryParam {

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

    /**
     * 关键字搜索
     */
    private String keywordWhenPresent;

    @Getter
    public enum OrderCondition implements com.alibaba.easytools.base.wrapper.param.OrderCondition {
        /**
         * 修改时间降序
         */
        GMT_MODIFIED_DESC(OrderBy.asc("GMTMODIFIED")),

        ;
        final OrderBy orderBy;

        OrderCondition(OrderBy orderBy) {
            this.orderBy = orderBy;
        }
    }
}
