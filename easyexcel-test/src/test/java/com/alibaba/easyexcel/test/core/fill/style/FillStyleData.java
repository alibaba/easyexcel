package com.alibaba.easyexcel.test.core.fill.style;

import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Jiaju Zhuang
 */
@Getter
@Setter
@EqualsAndHashCode
public class FillStyleData {
    private String name;
    private Double number;
    private Date date;
    private String empty;
}
