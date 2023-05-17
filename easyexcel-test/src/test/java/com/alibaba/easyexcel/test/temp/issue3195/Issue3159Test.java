package com.alibaba.easyexcel.test.temp.issue3195;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Map;

@Slf4j
public class Issue3159Test {


    /**
     * 当用户手动停止执行时，doAfterAllAnalysed方法应该仍然回调
     **/
    @Test
    public void callBackRead() {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        ModifyHasNextListener listener = new ModifyHasNextListener();
        EasyExcel.read(fileName, listener).doReadAll();
        Assertions.assertTrue(listener.willTure);
    }


    public static class ModifyHasNextListener extends AnalysisEventListener<Map<Integer, String>> {

        boolean hasNext = true;

        boolean willTure = false;

        @Override
        public void invoke(Map<Integer, String> data, AnalysisContext context) {
            if (!hasNext) {
                return;
            }
            hasNext = false;
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            willTure = true;
        }

        @Override
        public boolean hasNext(AnalysisContext context) {
            return hasNext;
        }
    }

}
