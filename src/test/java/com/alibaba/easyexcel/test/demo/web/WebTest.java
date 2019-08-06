package com.alibaba.easyexcel.test.demo.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.excel.EasyExcelFactory;

/**
 * web读写案例
 *
 * @author zhuangjiaju
 **/
@Controller
public class WebTest {
    /**
     * 文件下载
     * <li>1. 创建excel对应的实体对象 参照{@link DownloadData}
     * <li>2. 设置返回的 参数
     * <li>3. 直接写，这里注意，finish的时候会自动关闭OutputStream,当然你外面再关闭异常问题不大
     */
    @GetMapping("download")
    public void download(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=demo.xlsx");
        EasyExcelFactory.write(response.getOutputStream(), DownloadData.class).sheet("模板").doWrite(data()).finish();
    }

    /**
     * 文件上传
     * <li>1. 创建excel对应的实体对象 参照{@link UploadData}
     * <li>2. 由于默认异步读取excel，所以需要创建excel一行一行的回调监听器，参照{@link UploadDataListener}
     * <li>3. 直接读即可
     */
    @PostMapping("upload")
    @ResponseBody
    public String upload(MultipartFile file) throws IOException {
        EasyExcelFactory.read(file.getInputStream(), UploadData.class, new UploadDataListener()).sheet().doRead()
            .finish();
        return "success";
    }

    private List<DownloadData> data() {
        List<DownloadData> list = new ArrayList<DownloadData>();
        for (int i = 0; i < 10; i++) {
            DownloadData data = new DownloadData();
            data.setString("字符串" + 0);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }
}
