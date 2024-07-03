package com.alibaba.atasuper.web.controller.demo.vo;

import com.alibaba.atasuper.api.user.vo.UserVO;

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
public class DemoPageQueryVO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 姓名，字符串字段一般默认空字符串，不建议使用非空
     */
    private String name;

    /**
     * 创建人
     */
    private UserVO createUser;
}
