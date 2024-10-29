package com.alibaba.easyexcel.test.demo.read;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellExtra;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * 测试使用 lombok 的 @Accessors(chain = true) 注解无法映射到对象对应属性的问题
 */
public class TestAccessor {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void importToRegulation () {


        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "testAccessors.xlsx";

        EasyExcelFactory
                .read(fileName, User.class, new UserListener())
                .sheet().doRead();


    }


    @Data
    @Accessors(chain = true)
    public static class User {

        @ExcelProperty(index = 0)
        private String name;
        @ExcelProperty(index = 1)
        private Integer age;

    }


    @Slf4j
    public static class UserListener extends AnalysisEventListener<User> {

        @Override
        public void onException(Exception exception, AnalysisContext context) throws Exception {
            super.onException(exception, context);
        }

        /**
         * 这个每一条数据解析都会来调用
         */
        @Override
        public void invoke(User data, AnalysisContext context) {

            try {
                log.info("解析到一条数据:{}", objectMapper.writeValueAsString(data));
            } catch (JsonProcessingException e) {
                log.error("序列化成 json 失败: {}", e.getMessage());
            }

        }

        @Override
        public void extra(CellExtra extra, AnalysisContext context) {
            super.extra(extra, context);
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext analysisContext) {

        }

        @Override
        public boolean hasNext(AnalysisContext context) {
            return super.hasNext(context);
        }

    }

}
