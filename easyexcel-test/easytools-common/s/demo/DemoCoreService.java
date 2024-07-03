package com.alibaba.atasuper.api.demo;

import java.util.List;

import javax.annotation.Nullable;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.alibaba.atasuper.api.demo.param.DemoComprehensivePageQueryParam;
import com.alibaba.atasuper.api.demo.param.DemoCreateParam;
import com.alibaba.atasuper.api.demo.param.DemoPageQueryParam;
import com.alibaba.atasuper.api.demo.param.DemoQueryParam;
import com.alibaba.atasuper.api.demo.param.DemoSelector;
import com.alibaba.atasuper.api.demo.param.DemoUpdateParam;
import com.alibaba.atasuper.tools.base.exception.DataNotExistsBusinessException;
import com.alibaba.atasuper.tools.base.exception.NoPermissionBusinessException;
import com.alibaba.atasuper.tools.base.wrapper.result.AtaPageResult;

/**
 * TODO 模板的service 方法用到才复制 别全复制 参考：<a href="https://aliyuque.antfin.com/ctoo-pic/rd/ixq0lu">...</a>
 *
 * 思考：
 * 关于 create vs createWithPermission ，修改类似
 * 如果仅仅只是获取用户登陆信息 可以直接用：create
 * 如果需要获取用户登陆信息，还需要获取用户的权限信息，必须用：createWithPermission
 *
 * @author 是仪
 */
public interface DemoCoreService {

    /**
     * 创建一条数据
     *
     * @param param 创建参数
     * @return id
     */
    Long create(@Valid @NotNull DemoCreateParam param);

    /**
     * 创建一条数据，没有权限会抛出异常
     *
     * @param param 创建参数
     * @return id
     * @throws NoPermissionBusinessException 没有查询这条数据的权限
     */
    Long createWithPermission(@Valid @NotNull DemoCreateParam param);

    /**
     * 根据主键修改一条数据
     *
     * @param param 修改参数
     * @return id
     */
    Long update(@Valid @NotNull DemoUpdateParam param);

    /**
     * 根据主键修改一条数据，没有权限会抛出异常
     *
     * @param param 修改参数
     * @return id
     * @throws NoPermissionBusinessException 没有修改这条数据的权限
     */
    Long updateWithPermission(@Valid @NotNull DemoUpdateParam param);

    /**
     * 根据主键删除一条数据
     *
     * @param id 主键id
     * @return id
     */
    Long delete(@Valid @NotNull Long id);

    /**
     * 根据主键删除一条数据，没有权限会抛出异常
     *
     * @param id 主键id
     * @return id
     * @throws NoPermissionBusinessException 没有查询这条数据的权限
     */
    Long deleteWithPermission(@Valid @NotNull Long id);

    /**
     * 查询一条数据，不存在会抛出异常
     *
     * @param id       主键id
     * @param selector 选择器
     * @return 必定返回一条数据
     * @throws DataNotExistsBusinessException 找不到数据的异常
     */
    DemoDTO queryExistent(@Valid @NotNull Long id, @Nullable DemoSelector selector);

    /**
     * 查询一条数据，不存在或者没有权限会抛出异常
     *
     * @param id       主键id
     * @param selector 选择器
     * @return 必定返回一条数据
     * @throws DataNotExistsBusinessException 找不到数据的异常
     * @throws NoPermissionBusinessException 没有查询这条数据的权限
     */
    DemoDTO queryExistentWithPermission(@Valid @NotNull Long id, @Nullable DemoSelector selector);

    /**
     * 查询一条数据
     *
     * @param id       主键id
     * @param selector 选择器
     * @return 返回一条数据，可能为空
     */
    DemoDTO query(@Nullable Long id, @Nullable DemoSelector selector);

    /**
     * 查询一条数据,没有权限会抛出异常
     *
     * @param id       主键id
     * @param selector 选择器
     * @return 返回一条数据，可能为空
     * @throws NoPermissionBusinessException 没有查询这条数据的权限
     */
    DemoDTO queryWithPermission(@Nullable Long id, @Nullable DemoSelector selector);

    /**
     * 根据id列表查询列表数据
     *
     * @param idList   主键id列表
     * @param selector 选择器
     * @return 返回全部查询到的数据
     */
    List<DemoDTO> listQuery(@Nullable List<Long> idList, @Nullable DemoSelector selector);

    /**
     * 根据参数查询列表数据
     *
     * @param param    查询参数
     * @param selector 选择器
     * @return 返回查询数据，最多500条
     */
    List<DemoDTO> listQuery(@Valid @NotNull DemoQueryParam param, @Nullable DemoSelector selector);

    /**
     * 根据参数分页查询列表数据
     *
     * @param param    查询参数
     * @param selector 选择器
     * @return 返回分页查询数据
     */
    AtaPageResult<DemoDTO> pageQuery(@Valid @NotNull DemoPageQueryParam param, @Nullable DemoSelector selector);

    /**
     * 根据参数分页查询列表数据，只会查询有权限的数据
     *
     * @param param    查询参数
     * @param selector 选择器
     * @return 返回分页查询数据
     */
    AtaPageResult<DemoDTO> pageQueryWithPermission(@Valid @NotNull DemoPageQueryParam param,
        @Nullable DemoSelector selector);

    /**
     * 综合搜索
     * 慎用，一般涉及到join
     *
     * @param param    查询参数
     * @param selector 选择器
     * @return 返回分页查询数据
     */
    AtaPageResult<DemoDTO> comprehensivePageQuery(@Valid @NotNull DemoComprehensivePageQueryParam param,
        @Nullable DemoSelector selector);

}

