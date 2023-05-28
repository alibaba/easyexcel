package com.alibaba.excel.write.metadata.fill.pipe;

import com.alibaba.excel.write.handler.PipeFilterFactory;
import org.junit.jupiter.api.Test;

/**
 * Description:
 *
 * @author linfeng
 * @version 1.0.0
 * @since 2023/5/28 11:33
 */
class PipeFilterFactoryTest {

    @Test
    void apply() {
        PipeFilterFactory pipeFilterFactory = PipeFilterFactory.createPipeFilter(null);
        pipeFilterFactory.addParams(" test |  | trim : test1,test2 | ");
        System.out.println(pipeFilterFactory.apply(" data "));
    }
}
