package com.alibaba.easyexcel.test.core.converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelCommonException;
import com.alibaba.excel.util.DateUtils;
import com.alibaba.fastjson.JSON;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

    /**
     * @author Jiaju Zhuang
     */
    public class converterDataListener_2502 extends AnalysisEventListener<converterWriteData_2502> {
        private static final Logger LOGGER = LoggerFactory.getLogger(converterDataListener_2502.class);
        private final List<converterWriteData_2502> list = new ArrayList<>();

        @Override
        public void invoke(converterWriteData_2502 data, AnalysisContext context) {
            list.add(data);
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            System.out.println("Finished.");
        }


    }
