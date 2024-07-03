package com.alibaba.atasuper.web.controller.demo;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.alibaba.atasuper.api.demo.param.DemoPageQueryParam;
import com.alibaba.atasuper.api.demo.param.DemoPageQueryParam.OrderCondition;
import com.alibaba.atasuper.api.demo.param.DemoSelector;
import com.alibaba.atasuper.api.demo.DemoCoreService;
import com.alibaba.atasuper.api.user.param.DomainUserSelector;
import com.alibaba.atasuper.tools.base.wrapper.result.AtaPageResult;
import com.alibaba.atasuper.tools.base.wrapper.result.AtaPojoResult;
import com.alibaba.atasuper.web.controller.demo.request.DemoCreateRequest;
import com.alibaba.atasuper.web.controller.demo.request.DemoPageQueryRequest;
import com.alibaba.atasuper.web.controller.demo.vo.DemoPageQueryVO;
import com.alibaba.atasuper.web.controller.demo.vo.DemoQueryVO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO 模板的Controller 方法用到才复制 别全复制 参考：<a href="https://aliyuque.antfin.com/ctoo-pic/rd/ixq0lu">...</a>
 *
 * @author 是仪
 */
@RestController
@RequestMapping(path = "/api/v1/demo")
@Slf4j
public class DemoController {

    private static final DemoSelector DEMO_SELECTOR_GET = DemoSelector.builder()
        .user(Boolean.TRUE)
        .userDomainUserSelector(DomainUserSelector.builder()
            .medals(Boolean.TRUE)
            .build())
        .image(Boolean.TRUE)
        .build();

    private static final DemoSelector DEMO_SELECTOR_PAGE_QUERY = DemoSelector.builder()
        .createUser(Boolean.TRUE)
        .build();

    @Resource
    private DemoCoreService demoCoreService;
    @Resource
    private DemoWebConverter demoWebConverter;

    /**
     * 创建一条数据
     *
     * @param request 创建参数
     * @return id
     */
    @PostMapping("create")
    public AtaPojoResult<Long> create(@Valid @RequestBody DemoCreateRequest request) {
        return AtaPojoResult.of(demoCoreService.create(demoWebConverter.request2param(request)));
    }

    /**
     * 查询一条数据
     *
     * @param id 主键
     * @return
     */
    @GetMapping("query")
    public AtaPojoResult<DemoQueryVO> query(@Valid @NotNull Long id) {
        return AtaPojoResult.of(
            demoWebConverter.dto2voQuery(demoCoreService.queryExistentWithPermission(id, DEMO_SELECTOR_GET)));
    }

    /**
     * 分页查询列表数据
     *
     * @param request request
     * @return
     */
    @GetMapping("page-query")
    public AtaPageResult<DemoPageQueryVO> pageQuery(@Valid DemoPageQueryRequest request) {
        DemoPageQueryParam demoPageQueryParam = demoWebConverter.request2param(request);
        demoPageQueryParam.orderBy(OrderCondition.GMT_MODIFIED_DESC);
        return demoCoreService.pageQueryWithPermission(demoPageQueryParam, DEMO_SELECTOR_PAGE_QUERY)
            .map(demoWebConverter::dto2voPageQuery);
    }

}
