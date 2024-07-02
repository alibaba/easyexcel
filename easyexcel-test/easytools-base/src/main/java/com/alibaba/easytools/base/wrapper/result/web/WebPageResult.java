package com.alibaba.easytools.base.wrapper.result.web;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import com.alibaba.easytools.base.constant.EasyToolsConstant;
import com.alibaba.easytools.base.enums.BaseErrorEnum;
import com.alibaba.easytools.base.wrapper.Result;
import com.alibaba.easytools.base.wrapper.param.PageQueryParam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * data的返回对象
 * 和 PageResult 比较一致 也可以直接用 PageResult。 这个是部分项目前端需要将 data+pageNo 封装到一起 所以额外创建的类
 *
 * @author 是仪
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class WebPageResult<T> implements Serializable, Result<List<T>> {
    private static final long serialVersionUID = EasyToolsConstant.SERIAL_VERSION_UID;
    /**
     * 是否成功
     *
     * @mock true
     */
    @NotNull
    @Builder.Default
    private Boolean success = Boolean.TRUE;
    /**
     * 异常编码
     */
    private String errorCode;
    /**
     * 异常信息
     */
    private String errorMessage;
    /**
     * 数据信息
     */
    @Builder.Default
    private Page<T> data = new Page<>();
    /**
     * traceId
     */
    private String traceId;

    private WebPageResult(List<T> data, Long total, Long pageNo, Long pageSize) {
        this.success = Boolean.TRUE;
        this.data = new Page<>(data, total, pageNo, pageSize);
    }

    private WebPageResult(List<T> data, Long total, Integer pageNo, Integer pageSize) {
        this.success = Boolean.TRUE;
        this.data = new Page<>(data, total, pageNo, pageSize);
    }

    /**
     * 构建分页返回对象
     *
     * @param data     返回的对象
     * @param total    总的条数
     * @param pageNo   页码
     * @param pageSize 分页大小
     * @param <T>      返回的对象类型
     * @return 分页返回对象
     */
    public static <T> WebPageResult<T> of(List<T> data, Long total, Long pageNo, Long pageSize) {
        return new WebPageResult<>(data, total, pageNo, pageSize);
    }

    /**
     * 构建分页返回对象
     *
     * @param data     返回的对象
     * @param total    总的条数
     * @param pageNo   页码
     * @param pageSize 分页大小
     * @param <T>      返回的对象类型
     * @return 分页返回对象
     */
    public static <T> WebPageResult<T> of(List<T> data, Long total, Integer pageNo, Integer pageSize) {
        return new WebPageResult<>(data, total, pageNo, pageSize);
    }

    /**
     * 构建分页返回对象
     *
     * @param data  返回的对象
     * @param total 总的条数
     * @param param 分页参数
     * @param <T>   返回的对象类型
     * @return 分页返回对象
     */
    public static <T> WebPageResult<T> of(List<T> data, Long total, PageQueryParam param) {
        return new WebPageResult<>(data, total, param.getPageNo(), param.getPageSize());
    }

    /**
     * 构建空的返回对象
     *
     * @param pageNo   页码
     * @param pageSize 分页大小
     * @param <T>      返回的对象类型
     * @return 分页返回对象
     */
    public static <T> WebPageResult<T> empty(Long pageNo, Long pageSize) {
        return of(Collections.emptyList(), 0L, pageNo, pageSize);
    }

    /**
     * 构建空的返回对象
     *
     * @param pageNo   页码
     * @param pageSize 分页大小
     * @param <T>      返回的对象类型
     * @return 分页返回对象
     */
    public static <T> WebPageResult<T> empty(Integer pageNo, Integer pageSize) {
        return of(Collections.emptyList(), 0L, pageNo, pageSize);
    }

    /**
     * 判断是否还有下一页
     * 根据分页大小来计算 防止total为空
     *
     * @return 是否还有下一页
     * @deprecated 使用 {@link #getHasNextPage()} ()}
     */
    @Deprecated
    public boolean hasNextPage() {
        return getHasNextPage();
    }

    public Boolean getHasNextPage() {
        if (data == null) {
            return Boolean.FALSE;
        }
        return data.getHasNextPage();
    }

    /**
     * 返回查询异常信息
     *
     * @param errorCode    错误编码
     * @param errorMessage 错误信息
     * @param <T>          返回的对象
     * @return 分页返回对象
     */
    public static <T> WebPageResult<T> error(String errorCode, String errorMessage) {
        WebPageResult<T> result = new WebPageResult<>();
        result.errorCode = errorCode;
        result.errorMessage = errorMessage;
        result.success = Boolean.FALSE;
        return result;
    }

    /**
     * 返回查询异常信息
     *
     * @param errorEnum 错误枚举
     * @param <T>       返回的对象
     * @return 分页返回对象
     */
    public static <T> WebPageResult<T> error(BaseErrorEnum errorEnum) {
        return error(errorEnum.getCode(), errorEnum.getDescription());
    }

    /**
     * 判断是否存在数据
     *
     * @param pageResult
     * @return 是否存在数据
     */
    public static boolean hasData(WebPageResult<?> pageResult) {
        return pageResult != null && pageResult.getSuccess() && pageResult.getData() != null
            && pageResult.getData().getData() != null && !pageResult.getData().getData().isEmpty();
    }

    /**
     * 将当前的类型转换成另外一个类型
     *
     * @param mapper 转换的方法
     * @param <R>    返回的类型
     * @return 分页返回对象
     */
    public <R> WebPageResult<R> map(Function<T, R> mapper) {
        List<R> returnData = hasData(this) ? getData().getData().stream().map(mapper).collect(Collectors.toList())
            : Collections.emptyList();
        WebPageResult<R> pageResult = new WebPageResult<>();
        pageResult.setSuccess(getSuccess());
        pageResult.setErrorCode(getErrorCode());
        pageResult.setErrorMessage(getErrorMessage());
        pageResult.setTraceId(getTraceId());
        // 重新设置一个分页信息
        Page<R> page = new Page<>();
        pageResult.setData(page);
        page.setData(returnData);
        page.setPageNo(data.getPageNo());
        page.setPageSize(data.getPageSize());
        page.setTotal(data.getTotal());
        return pageResult;
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public void success(boolean success) {
        this.success = success;
    }

    @Override
    public String errorCode() {
        return errorCode;
    }

    @Override
    public void errorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String errorMessage() {
        return errorMessage;
    }

    @Override
    public void errorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * 分页信息
     *
     * @param <T>
     */
    @Data
    public static class Page<T> {
        /**
         * 数据信息
         */
        private List<T> data;
        /**
         * 分页编码
         */
        private Integer pageNo;
        /**
         * 分页大小
         */
        private Integer pageSize;
        /**
         * 总的大小
         */
        private Long total;
        /**
         * 是否存在下一页
         */
        private Boolean hasNextPage;

        public Page() {
            this.pageNo = 1;
            this.pageSize = 10;
            this.total = 0L;
        }

        private Page(List<T> data, Long total, Long pageNo, Long pageSize) {
            this();
            this.data = data;
            this.total = total;
            if (pageNo != null) {
                this.pageNo = Math.toIntExact(pageNo);
            }
            if (pageSize != null) {
                this.pageSize = Math.toIntExact(pageSize);
            }
        }

        private Page(List<T> data, Long total, Integer pageNo, Integer pageSize) {
            this();
            this.data = data;
            this.total = total;
            if (pageNo != null) {
                this.pageNo = pageNo;
            }
            if (pageSize != null) {
                this.pageSize = pageSize;
            }
        }

        public Boolean getHasNextPage() {
            if (hasNextPage == null) {
                hasNextPage = calculateHasNextPage();
            }
            return hasNextPage;
        }

        /**
         * 判断是否还有下一页
         * 根据分页大小来计算 防止total为空
         *
         * @return 是否还有下一页
         */
        public Boolean calculateHasNextPage() {
            // 存在分页大小 根据分页来计算
            if (total > 0) {
                return (long)pageSize * pageNo <= total;
            }
            // 没有数据 肯定没有下一页
            if (data == null || data.isEmpty()) {
                return false;
            }
            // 当前数量小于分页数量
            return data.size() >= pageSize;
        }
    }
}
