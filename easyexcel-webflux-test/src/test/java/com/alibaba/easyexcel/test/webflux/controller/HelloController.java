package com.alibaba.easyexcel.test.webflux.controller;

import com.alibaba.easyexcel.test.webflux.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


/**
 * @author gongxuanzhang
 **/
@RestController
public class HelloController {

    @Autowired
    private HelloService helloService;

    @GetMapping(value = "/download")
    @ResponseBody
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

}
