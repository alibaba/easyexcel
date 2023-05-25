package com.alibaba.excel.write.metadata.fill;

import org.junit.jupiter.api.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Description:
 *
 * @author linfeng
 * @version 1.0.0
 * @since 2023/5/25 15:48
 */
class FillFunctionWrapperTest {

    @Test
    void getFillFunctionClassT() {

        FillConfig fillConfig = new FillConfig();
//
        Function<String, ?> function = new Function<String, FillConfig>() {
            @Override
            public FillConfig apply(String s) {
                return fillConfig ;
            }
        };
//
//        FillFunctionWrapper functionWrapper = new FillFunctionWrapper(function, 10);
//
//        System.out.println(functionWrapper.getFillParameterizedType());
    }
}
