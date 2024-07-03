package com.alibaba.atasuper.web.controller.demo;

import com.alibaba.atasuper.api.demo.DemoDTO;
import com.alibaba.atasuper.api.demo.param.DemoCreateParam;
import com.alibaba.atasuper.api.demo.param.DemoPageQueryParam;
import com.alibaba.atasuper.web.controller.demo.request.DemoCreateRequest;
import com.alibaba.atasuper.web.controller.demo.request.DemoPageQueryRequest;
import com.alibaba.atasuper.web.controller.demo.vo.DemoPageQueryVO;
import com.alibaba.atasuper.web.controller.demo.vo.DemoQueryVO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * 转换器
 *
 * @author 是仪
 */
@Mapper(componentModel = "spring")
public abstract class DemoWebConverter {

    /**
     * 转换
     *
     * @param request
     * @return
     */
    public abstract DemoCreateParam request2param(DemoCreateRequest request);

    /**
     * 转换
     *
     * @param dto
     * @return
     */
    public abstract DemoQueryVO dto2voQuery(DemoDTO dto);

    /**
     * 转换
     *
     * @param dto
     * @return
     */
    public abstract DemoPageQueryVO dto2voPageQuery(DemoDTO dto);

    /**
     * 转换
     *
     * @param request
     * @return
     */
    @Mappings({
        @Mapping(target = "userIdWhenPresent", source = "userId"),
    })
    public abstract DemoPageQueryParam request2param(DemoPageQueryRequest request);

}
