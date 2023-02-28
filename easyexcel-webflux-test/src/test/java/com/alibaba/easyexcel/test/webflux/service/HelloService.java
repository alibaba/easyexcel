package com.alibaba.easyexcel.test.webflux.service;

import com.alibaba.easyexcel.test.webflux.entity.DemoData;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.ListUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;

/**
 * @author gxz gongxuanzhangmelt@gmail.com
 **/
@Component
public class HelloService {


    public Mono<ByteArrayInputStream> generateExcel() {
        return Mono.fromCallable(() -> {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            EasyExcel.write(stream, DemoData.class)
                .sheet("模板")
                .doWrite(this::data);
            return new ByteArrayInputStream(stream.toByteArray());
        }).subscribeOn(Schedulers.boundedElastic());
    }

    private List<DemoData> data() {
        List<DemoData> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDoubleData(1.1);
            data.setDate(new Date());
            list.add(data);
        }
        return list;
    }


}
