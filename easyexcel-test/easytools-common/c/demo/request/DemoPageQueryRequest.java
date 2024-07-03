package com.alibaba.atasuper.web.controller.demo.request;

import com.alibaba.atasuper.tools.base.wrapper.request.WebPageRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 列表对象
 *
 * @author 是仪
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DemoPageQueryRequest extends WebPageRequest {

    /**
     * 用户id
     */
    private String userId;
}
