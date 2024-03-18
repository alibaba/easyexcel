EasyExcel
======================
[![Build Status](https://github.com/alibaba/easyexcel/actions/workflows/ci.yml/badge.svg?branch=master)](https://github.com/alibaba/easyexcel/actions/workflows/ci.yml?query=branch%3Amaster)
[![Maven central](https://maven-badges.herokuapp.com/maven-central/com.alibaba/easyexcel/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.alibaba/easyexcel)
[![License](http://img.shields.io/:license-apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![](https://img.shields.io/badge/EasyExcel-%E6%9F%A5%E7%9C%8B%E8%B4%A1%E7%8C%AE%E6%8E%92%E8%A1%8C%E6%A6%9C-orange)](https://opensource.alibaba.com/contribution_leaderboard/details?projectValue=easyexcel)

# JAVA解析Excel工具

Java解析、生成Excel比较有名的框架有Apache poi、jxl。但他们都存在一个严重的问题就是非常的耗内存，poi有一套SAX模式的API可以一定程度的解决一些内存溢出的问题，但POI还是有一些缺陷，比如07版Excel解压缩以及解压后存储都是在内存中完成的，内存消耗依然很大。  
easyexcel重写了poi对07版Excel的解析，一个3M的excel用POI sax解析依然需要100M左右内存，改用easyexcel可以降低到几M，并且再大的excel也不会出现内存溢出；03版依赖POI的sax模式，在上层做了模型转换的封装，让使用者更加简单方便

# 🔥阿里巴巴（淘天集团）岗位内推
* 内推优势
    * 更专业的简历指导
    * 更多岗位前置知识
    * 更快的面试进度以及进度查询
* 内推岗位
    * 🔥可在 [淘猫集团岗位搜索](https://talent.taotian.com/off-campus/position-list?lang=zh&search=) 搜索岗位
    * [非淘天集团岗位搜索](https://talent.alibaba.com/) 首页往下拉可以看到各个集团
* 内推
    * 发送简历到 zhuangjiaju.zjj@taobao.com
    * 或者 [联系我们](https://easyexcel.opensource.alibaba.com/community/support) 加入钉钉群联系群主
  
# 网站
*  官方网站：[https://easyexcel.opensource.alibaba.com/](https://easyexcel.opensource.alibaba.com/)
* github地址：[https://github.com/alibaba/easyexcel](https://github.com/alibaba/easyexcel)
* gitee地址：[https://gitee.com/easyexcel/easyexcel](https://gitee.com/easyexcel/easyexcel)

# 16M内存23秒读取75M(46W行25列)的Excel（3.2.1+版本）

当然还有[极速模式](https://easyexcel.opensource.alibaba.com/qa/read#%E5%BC%80%E5%90%AF%E6%80%A5%E9%80%9F%E6%A8%A1%E5%BC%8F)
能更快，但是内存占用会在100M多一点
![img](img/readme/large.png)

# 最新版本

```xml

<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>easyexcel</artifactId>
    <version>3.3.4</version>
</dependency>
```

# 帮忙点个⭐Star

开源不易，如果觉得EasyExcel对您的工作还是有帮助的话，请帮忙在<a target="_blank" href='https://github.com/alibaba/easyexcel'><img src="https://img.shields.io/github/stars/alibaba/easyexcel.svg?style=flat-square&label=Stars&logo=github" alt="github star"/></a>
的右上角点个⭐Star，您的支持是使EasyExcel变得更好最大的动力。

# 如何获取帮助

## 优先建议自己通过文档来解决问题

* [快速开始](https://easyexcel.opensource.alibaba.com/docs/current/)
* [常见问题](https://easyexcel.opensource.alibaba.com/docs/qa/)
* [API](https://easyexcel.opensource.alibaba.com/docs/current/api/)

## 其次建议通过`issues`来解决解决问题

可以尝试在以下2个链接搜索问题，如果不存在可以尝试创建`issue`。

* 去 [github](https://github.com/alibaba/easyexcel/issues) 搜索`issues`
* 去 [gitee](https://gitee.com/easyexcel/easyexcel/issues) 搜索`issues`

通过 `issues` 解决问题，可以给后面遇到相同问题的同学查看，所以比较推荐这种方式。   
不管`github`、`gitee`都会定期有人回答您的问题，比较紧急可以在提完`issue`以后在钉钉群艾特群主并发送`issue`地址帮忙解决。   
`QQ` 公司不让用，有时候也会去看，但是核心肯定还是在钉钉。

## 关注作者：程序员小獭

可以加群交流

![qrcode_for_gh_c43212c8d0ed_258](https://github.com/alibaba/easyexcel/assets/22975773/13ceff34-d547-421b-b9a9-04f388792099)


# 维护者

姬朋飞（玉霄)、庄家钜

# 快速开始

## 读Excel

demo代码地址：[https://github.com/alibaba/easyexcel/blob/master/easyexcel-test/src/test/java/com/alibaba/easyexcel/test/demo/read/ReadTest.java](https://github.com/alibaba/easyexcel/blob/master/easyexcel-test/src/test/java/com/alibaba/easyexcel/test/demo/read/ReadTest.java)   
详细文档地址：[https://easyexcel.opensource.alibaba.com/docs/current/quickstart/read](https://easyexcel.opensource.alibaba.com/docs/current/quickstart/read)

```java
    /**
    * 最简单的读
    * <p>1. 创建excel对应的实体对象 参照{@link DemoData}
    * <p>2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link DemoDataListener}
    * <p>3. 直接读即可
    */
    @Test
    public void simpleRead() {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).sheet().doRead();
    }
```

## 写Excel

demo代码地址：[https://github.com/alibaba/easyexcel/blob/master/easyexcel-test/src/test/java/com/alibaba/easyexcel/test/demo/write/WriteTest.java](https://github.com/alibaba/easyexcel/blob/master/easyexcel-test/src/test/java/com/alibaba/easyexcel/test/demo/write/WriteTest.java)   
详细文档地址：[https://easyexcel.opensource.alibaba.com/docs/current/quickstart/write](https://easyexcel.opensource.alibaba.com/docs/current/quickstart/write)

```java
    /**
     * 最简单的写
     * <p>1. 创建excel对应的实体对象 参照{@link com.alibaba.easyexcel.test.demo.write.DemoData}
     * <p>2. 直接写即可
     */
    @Test
    public void simpleWrite() {
        String fileName=TestFileUtil.getPath()+"write"+System.currentTimeMillis()+".xlsx";
        // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName,DemoData.class).sheet("模板").doWrite(data());
    }
```

## web上传、下载

demo代码地址：[https://github.com/alibaba/easyexcel/blob/master/easyexcel-test/src/test/java/com/alibaba/easyexcel/test/demo/web/WebTest.java](https://github.com/alibaba/easyexcel/blob/master/easyexcel-test/src/test/java/com/alibaba/easyexcel/test/demo/web/WebTest.java)

```java
    /**
    * 文件下载（失败了会返回一个有部分数据的Excel）
    * <p>
    * 1. 创建excel对应的实体对象 参照{@link DownloadData}
    * <p>
    * 2. 设置返回的 参数
    * <p>
    * 3. 直接写，这里注意，finish的时候会自动关闭OutputStream,当然你外面再关闭流问题不大
    */
    @GetMapping("download")
    public void download(HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName=URLEncoder.encode("测试","UTF-8").replaceAll("\\+","%20");
        response.setHeader("Content-disposition","attachment;filename*=utf-8''"+fileName+".xlsx");
        EasyExcel.write(response.getOutputStream(),DownloadData.class).sheet("模板").doWrite(data());
    }
    
    /**
     * 文件上传
     * <p>1. 创建excel对应的实体对象 参照{@link UploadData}
     * <p>2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link UploadDataListener}
     * <p>3. 直接读即可
     */
    @PostMapping("upload")
    @ResponseBody
    public String upload(MultipartFile file)throws IOException{
        EasyExcel.read(file.getInputStream(),UploadData.class,new UploadDataListener(uploadDAO)).sheet().doRead();
        return"success";
    }
```
