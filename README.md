EasyExcel
======================
[![Build Status](https://github.com/alibaba/easyexcel/actions/workflows/ci.yml/badge.svg?branch=master)](https://github.com/alibaba/easyexcel/actions/workflows/ci.yml?query=branch%3Amaster)
[![Maven central](https://maven-badges.herokuapp.com/maven-central/com.alibaba/easyexcel/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.alibaba/easyexcel)
[![License](http://img.shields.io/:license-apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

[QQ1群(已满): 662022184](https://jq.qq.com/?_wv=1027&k=1T21jJxh)  
[QQ2群(已满): 1097936804](https://jq.qq.com/?_wv=1027&k=j5zEy6Xl)  
[QQ3群(已满): 453928496](https://qm.qq.com/cgi-bin/qm/qr?k=e2ULsA5A0GldhV2CXJ8sIbAyu9I6qqs7&jump_from=webapi)  
[QQ4群: 496594404](https://qm.qq.com/cgi-bin/qm/qr?k=e_aVG1Q7gi0PJUBkbrUGAgbeO3kUEInK&jump_from=webapi)   
[钉钉1群（已满）: 21960511](https://qr.dingtalk.com/action/joingroup?code=v1,k1,cchz6k12ci9B08NNqhNRFGXocNVHrZtW0kaOtTKg/Rk=&_dt_no_comment=1&origin=11)  
[钉钉2群（已满）: 32796397](https://qr.dingtalk.com/action/joingroup?code=v1,k1,jyU9GtEuNU5S0QTyklqYcYJ8qDZtUuTPMM7uPZTS8Hs=&_dt_no_comment=1&origin=11)  
[钉钉3群（已满）: 33797247](https://qr.dingtalk.com/action/joingroup?code=v1,k1,3UGlEScTGQaHpW2cIRo+gkxJ9EVZ5fz26M6nW3uFP30=&_dt_no_comment=1&origin=11)  
[钉钉4群（已满）: 33491624](https://qr.dingtalk.com/action/joingroup?code=v1,k1,V14Pb65Too70rQkEaJ9ohb6lZBZbtp6jIL/q9EWh9vA=&_dt_no_comment=1&origin=11)  
[钉钉5群（已满）: 32134498](https://h5.dingtalk.com/circle/healthCheckin.html?dtaction=os&corpId=dingb9fa1325d9dccc3ecac589edd02f1650&5233a=71a83&cbdbhh=qwertyuiop)  
[钉钉6群（已满）: 34707941](https://h5.dingtalk.com/circle/healthCheckin.html?dtaction=os&corpId=dingcf68008a1d443ac012d5427bdb061b7a&6ae36c3d-0c80-4=22398493-6c2a-4&cbdbhh=qwertyuiop)  
[钉钉7群: 35235427](https://h5.dingtalk.com/circle/healthCheckin.html?dtaction=os&corpId=ding532b9018c06c7fc8660273c4b78e6440&167fb=ed003&cbdbhh=qwertyuiop)  
[官方网站: https://yuque.com/easyexcel](https://www.yuque.com/easyexcel/doc/easyexcel)  

[常见问题](https://www.yuque.com/easyexcel/faq)
#### 因为公司不方便用QQ，所以建议加钉钉群

# JAVA解析Excel工具EasyExcel
Java解析、生成Excel比较有名的框架有Apache poi、jxl。但他们都存在一个严重的问题就是非常的耗内存，poi有一套SAX模式的API可以一定程度的解决一些内存溢出的问题，但POI还是有一些缺陷，比如07版Excel解压缩以及解压后存储都是在内存中完成的，内存消耗依然很大。easyexcel重写了poi对07版Excel的解析，一个3M的excel用POI sax解析依然需要100M左右内存，改用easyexcel可以降低到几M，并且再大的excel也不会出现内存溢出；03版依赖POI的sax模式，在上层做了模型转换的封装，让使用者更加简单方便

## 64M内存20秒读取75M(46W行25列)的Excel（3.0.2+版本）
当然还有极速模式能更快，但是内存占用会在100M多一点
![img](img/readme/large.png)

## 版本支持
* 2+ 版本支持 Java7和Java6
* 3+ 版本至少 Java8
### 关于版本升级
* 不建议跨大版本升级 尤其跨2个大版本
* 2+ 升级到 3+ 一些不兼容的地方
  * 使用了自定义拦截器去修改样式的会出问题（不会编译报错）
  * 读的时候`invoke`里面抛出异常，不会再额外封装一层`ExcelAnalysisException` （不会编译报错）
  * 样式等注解涉及到 `boolean` or 一些枚举 值的 有变动，新增默认值（会编译报错，注解改就行）
* 大版本升级后建议相关内容重新测试下

### 最新版本
```xml
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>easyexcel</artifactId>
            <version>3.0.5</version>
        </dependency>
```

### easyexcel人员招募
由于工作较忙,有意愿做开源的同学可以报名,主要负责群里回答&issue处理，当然也可以做一些PR.   
由于开源没有任何物质回报，然后现在的维护同学也是课余时间维护的，所以想加入的同学需要持之以恒，而不是一时兴起.   
要求如下：
* 有一定java编码能力 & 良好的编码习惯
* 了解easyexcel 读&写的原理
* 热爱开源项目
* 能长期坚持的去做
* 相对工作没那么忙

## 相关文档
* [快速开始](https://www.yuque.com/easyexcel/doc/easyexcel)
* [关于软件](/abouteasyexcel.md)
* [更新记事](/update.md)
* [贡献代码](https://www.yuque.com/easyexcel/doc/contribute)

## 维护者
姬朋飞（玉霄)、庄家钜、怀宇
## 快速开始
### 读Excel
DEMO代码地址：[https://github.com/alibaba/easyexcel/blob/master/src/test/java/com/alibaba/easyexcel/demo/read/ReadTest.java](/src/test/java/com/alibaba/easyexcel/test/demo/read/ReadTest.java)

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

### 写Excel
DEMO代码地址：[https://github.com/alibaba/easyexcel/blob/master/src/test/java/com/alibaba/easyexcel/test/demo/write/WriteTest.java](/src/test/java/com/alibaba/easyexcel/test/demo/write/WriteTest.java)
```java
    /**
     * 最简单的写
     * <p>1. 创建excel对应的实体对象 参照{@link com.alibaba.easyexcel.test.demo.write.DemoData}
     * <p>2. 直接写即可
     */
    @Test
    public void simpleWrite() {
        String fileName = TestFileUtil.getPath() + "write" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, DemoData.class).sheet("模板").doWrite(data());
    }
```

### web上传、下载
DEMO代码地址：[https://github.com/alibaba/easyexcel/blob/master/src/test/java/com/alibaba/easyexcel/test/demo/web/WebTest.java](/src/test/java/com/alibaba/easyexcel/test/demo/web/WebTest.java)
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
        String fileName = URLEncoder.encode("测试", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), DownloadData.class).sheet("模板").doWrite(data());
    }

    /**
     * 文件上传
     * <p>1. 创建excel对应的实体对象 参照{@link UploadData}
     * <p>2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link UploadDataListener}
     * <p>3. 直接读即可
     */
    @PostMapping("upload")
    @ResponseBody
    public String upload(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), UploadData.class, new UploadDataListener(uploadDAO)).sheet().doRead();
        return "success";
    }
```
### 联系我们
有问题阿里同事可以通过钉钉找到我，阿里外同学可以通过git留言。其他技术非技术相关的也欢迎一起探讨。
