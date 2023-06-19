package com.alibaba.excel.write.handler;

import com.alibaba.easyexcel.test.demo.fill.FillData;
import com.alibaba.excel.util.ListUtils;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Description:
 *
 * @author linfeng
 * @version 1.0.0
 * @since 2023/6/17 13:02
 */
class PipeFilterFactoryTest {

    private List<FillData> dataPipe() {
        List<FillData> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            FillData fillData = new FillData();
            list.add(fillData);
            fillData.setName(" 张三 ");
            fillData.setNumber(5.2);
            fillData.setDate(new Date());
            if (i == 0) {
                fillData.setImages(Arrays.asList("http://www.baidu.com/images/m100-1.1.jpg"
                    , "http://www.baidu.com/images/m100-1.2.jpg"
                    , "http://www.baidu.com/images/m100-1.3.jpg"
                    , "http://www.baidu.com/images/m100-1.4.jpg"
                    , "http://www.baidu.com/images/m100-1.5.jpg"));
            } else {
                fillData.setImages(Arrays.asList("http://www.baidu.com/images/m100-1.1.jpg"
                    , "http://www.baidu.com/images/m100-1.2.jpg"
                    , "http://www.baidu.com/images/m100-1.3.jpg"
                    , "http://www.baidu.com/images/m100-1.4.jpg"
                    , "http://www.baidu.com/images/m100-1.5.jpg"
                    , "http://www.baidu.com/images/K100-1.2.jpg"));
            }
        }
        return list;
    }

    @Test
    void testEndsWithError() {
        PipeFilterFactory pipeFilterFactory = PipeFilterFactory.createPipeFilter(null);
        pipeFilterFactory.addParams("test | ends-with :  ");
        val apply = pipeFilterFactory.apply(PipeDataWrapper.success(Arrays.asList("http://www.baidu.com/images/m100-1.1.jpg"
            , "http://www.baidu.com/images/m100-1.2.jpg"
            , "http://www.baidu.com/images/m100-1.3.jpg"
            , "http://www.baidu.com/images/m100-1.4.jpg"
            , "http://www.baidu.com/images/m100-1.5.jpg"
            , "http://www.baidu.com/images/K100-1.2.jpg")));
        System.out.println(apply.getMessage());
        Assert.isTrue(!apply.success(), "成功");
    }

    @Test
    void testEndsWith() {
        PipeFilterFactory pipeFilterFactory = PipeFilterFactory.createPipeFilter(null);
        pipeFilterFactory.addParams("test | ends-with : m100-1.3.jpg,m100-1.5.jpg,m100-1.4.jpg ");
        val apply = pipeFilterFactory.apply(PipeDataWrapper.success(Arrays.asList("http://www.baidu.com/images/m100-1.1.jpg"
            , "http://www.baidu.com/images/m100-1.2.jpg"
            , "http://www.baidu.com/images/m100-1.3.jpg"
            , "http://www.baidu.com/images/m100-1.4.jpg"
            , "http://www.baidu.com/images/m100-1.5.jpg"
            , "http://www.baidu.com/images/K100-1.2.jpg")));
        Assert.isTrue(apply.success(), "失败");
    }

    @Test
    void testProEndsWith() {
        PipeFilterFactory pipeFilterFactory = PipeFilterFactory.createPipeFilter(null);
        pipeFilterFactory.addParams("test | prior-ends-with : m100-1.3.jpg,m100-1.5.jpg,m100-1.4.jpg ");
        val apply = pipeFilterFactory.apply(PipeDataWrapper.success(Arrays.asList("http://www.baidu.com/images/m100-1.1.jpg"
            , "http://www.baidu.com/images/m100-1.2.jpg"
            , "http://www.baidu.com/images/m100-1.3.jpg"
            , "http://www.baidu.com/images/m100-1.4.jpg"
            , "http://www.baidu.com/images/m100-1.5.jpg"
            , "http://www.baidu.com/images/K100-1.2.jpg")));
        Assert.isTrue(apply.success(), "失败");
    }


    @Test
    void testSubstr() {
        PipeFilterFactory pipeFilterFactory = PipeFilterFactory.createPipeFilter(null);
        pipeFilterFactory.addParams("test | substring : 0,10 ");
        val apply = pipeFilterFactory.apply(PipeDataWrapper.success("这个示例程序创建了两个 double 类型的变量 a 和 b，分别赋值为 10 和 5，然后调用 Calculator 类中的四个方法，输出运算结果。其中，divide 方法在除数为0时会抛出异常，这里使用了 try-catch 语句捕获异常，并输出错误信息。"));
        Assert.isTrue(apply.success(), "失败");
    }

    @Test
    void testEquals() {
//
        Map<String, Object> titleMap = new HashMap<>(2);
        titleMap.put("eBayTitle", "eBayTitle这个示例程序创建了两个 double 类型的变量 a 和 b，分别赋值为 10 和 5，然后调用 Calculator 类中的四个方法，输出运算结果");
        titleMap.put("amazonTitle", "amazonTitle其中，divide 方法在除数为0时会抛出异常，这里使用了 try-catch 语句捕获异常，并输出错误信息。");
        PipeFilterFactory pipeFilterFactory = PipeFilterFactory.createPipeFilter(null);
        pipeFilterFactory.addParams("PublishCenter.titleMap | prior-equals:amazonTitle,eBayTitle | substring:0,60");
        val apply = pipeFilterFactory.apply(PipeDataWrapper.success(titleMap));
        Assert.isTrue(apply.success() && apply.getData().toString().startsWith("amazonTitle"), "失败");
    }

    @Test
    void testNumber() {
//        ebayManno.price | cal-mul:1.4,int | cal-add:0.99,number_2
        PipeFilterFactory pipeFilterFactory = PipeFilterFactory.createPipeFilter(null);
        pipeFilterFactory.addParams("ebayManno.price | cal-mul:2,int | cal-add:0.99,number_2");
        val apply = pipeFilterFactory.apply(PipeDataWrapper.success("100"));
        Assert.isTrue(apply.success() && apply.getData().toString().equals("200.99"), "失败");
    }
}
