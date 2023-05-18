package com.alibaba.easyexcel.test.temp.simple;

import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * write data
 *
 * @author Jiaju Zhuang
 **/
@Getter
@Setter
@EqualsAndHashCode
public class WriteData {
    //    @ContentStyle(locked = true)
    private Date dd;
    //    @ContentStyle(locked = false)
    private float f1;
}
