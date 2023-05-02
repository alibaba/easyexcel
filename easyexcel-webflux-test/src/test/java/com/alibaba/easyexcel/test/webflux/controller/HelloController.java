package com.alibaba.easyexcel.test.webflux.controller;

import com.alibaba.easyexcel.test.webflux.entity.DemoData;
import com.alibaba.easyexcel.test.webflux.service.HelloService;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;


/**
 * @author gongxuanzhang
 **/
@RestController
@Slf4j
public class HelloController {

    @Autowired
    private HelloService helloService;

    @GetMapping(value = "/download")
    public ResponseEntity<Mono<Resource>> exportExcel() {
        String fileName = "export.xlsx";
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
            .body(helloService.generateExcel().flatMap(x -> {
                Resource resource = new InputStreamResource(x);
                return Mono.just(resource);
            }));
    }

    @PostMapping("/upload")
    public Mono<String> uploadFile(@RequestPart("file") FilePart filePart) {
        //  可以直接把上个方法导出的excel导入到这里
        Flux<DataBuffer> dataBufferFlux = filePart.content();
        Mono<DataBuffer> monoDataBuffer = DataBufferUtils.join(dataBufferFlux);
        return monoDataBuffer.flatMap(dataBuffer -> {
            try (InputStream inputStream = dataBuffer.asInputStream()) {
                EasyExcel.read(inputStream, DemoData.class, new PageReadListener<DemoData>(dataList -> {
                    for (DemoData demoData : dataList) {
                        log.info("读取到一条数据{}", demoData);
                    }
                })).sheet().doRead();
                DataBufferUtils.release(dataBuffer);
                return Mono.just("File uploaded successfully");
            } catch (IOException e) {
                DataBufferUtils.release(dataBuffer);
                return Mono.error(e);
            }
        });
    }


}
