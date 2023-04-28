# 🔥作者最新开源项目[Chat2DB](https://github.com/alibaba/Chat2DB)
Chat2DB是一个集成了ChatGPT 可以AI自动生成SQL的智能数据库客户端， GitHub地址 [https://github.com/alibaba/Chat2DB](https://github.com/alibaba/Chat2DB)

EasyExcel
======================
[![Build Status](https://github.com/alibaba/easyexcel/actions/workflows/ci.yml/badge.svg?branch=master)](https://github.com/alibaba/easyexcel/actions/workflows/ci.yml?query=branch%3Amaster)
[![Maven central](https://maven-badges.herokuapp.com/maven-central/com.alibaba/easyexcel/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.alibaba/easyexcel)
[![License](http://img.shields.io/:license-apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![](https://img.shields.io/badge/EasyExcel-%E6%9F%A5%E7%9C%8B%E8%B4%A1%E7%8C%AE%E6%8E%92%E8%A1%8C%E6%A6%9C-orange)](https://opensource.alibaba.com/contribution_leaderboard/details?projectValue=easyexcel)

# JAVA解析Excel工具

Java解析、生成Excel比较有名的框架有Apache poi、jxl。但他们都存在一个严重的问题就是非常的耗内存，poi有一套SAX模式的API可以一定程度的解决一些内存溢出的问题，但POI还是有一些缺陷，比如07版Excel解压缩以及解压后存储都是在内存中完成的，内存消耗依然很大。  
easyexcel重写了poi对07版Excel的解析，一个3M的excel用POI sax解析依然需要100M左右内存，改用easyexcel可以降低到几M，并且再大的excel也不会出现内存溢出；03版依赖POI的sax模式，在上层做了模型转换的封装，让使用者更加简单方便

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
    <version>3.2.1</version>
</dependency>
```

# 帮忙点个⭐Star

开源不易，如果觉得EasyExcel对您的工作还是有帮助的话，请帮忙在<a target="_blank" href='https://github.com/alibaba/easyexcel'><img src="https://img.shields.io/github/stars/alibaba/easyexcel.svg?style=flat-square&label=Stars&logo=github" alt="github star"/></a>
的右上角点个⭐Star，您的支持是使EasyExcel变得更好最大的动力。

# 人员招聘

由于工作较忙，需要招聘一些同学加入`EasyExcel`开源，一起跟着`EasyExcel`成长。

## 你将获得

* 认识一帮热爱开源的小伙伴
* 你写的代码可以被无数人看到，提升自我编码能力
* 可能会有一定的物质奖励（在和公司申请，不一定能过）

## 你的工作

* 群&`issue`答疑
* 做一些代码的`PR`合并去修复bug
* 讨论`EasyExcel`规划

## 招聘要求

* 3年以上Java编程经验，基础扎实
* 对技术比较热爱，并且喜欢阅读源码
* 自驱力强，能主动的研究一些问题
* 需要持之以恒，开源需要长期维护

## 联系方式

直接加钉钉群，联系是仪即可

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

## 也可以加入钉钉&QQ群来解决问题

加入钉钉或QQ群，看完公告可以获得帮助 。  
比较推荐钉钉群，`QQ` 公司不让用，当然QQ群也会有热心网友帮忙解决。   
[QQ1群(已满): 662022184](https://jq.qq.com/?_wv=1027&k=1T21jJxh)  
[QQ2群(已满): 1097936804](https://jq.qq.com/?_wv=1027&k=j5zEy6Xl)  
[QQ3群(已满): 453928496](https://qm.qq.com/cgi-bin/qm/qr?k=e2ULsA5A0GldhV2CXJ8sIbAyu9I6qqs7&jump_from=webapi)  
[QQ4群(已满): 496594404](https://qm.qq.com/cgi-bin/qm/qr?k=e_aVG1Q7gi0PJUBkbrUGAgbeO3kUEInK&jump_from=webapi)   
[QQ5群(已满): 451925680](https://jq.qq.com/?_wv=1027&k=6VHhvxyf)   
[QQ6群: 784741035](https://jq.qq.com/?_wv=1027&k=BbLBIo9P)   
[钉钉1群（已满）: 21960511](https://qr.dingtalk.com/action/joingroup?code=v1,k1,cchz6k12ci9B08NNqhNRFGXocNVHrZtW0kaOtTKg/Rk=&_dt_no_comment=1&origin=11)  
[钉钉2群（已满）: 32796397](https://qr.dingtalk.com/action/joingroup?code=v1,k1,jyU9GtEuNU5S0QTyklqYcYJ8qDZtUuTPMM7uPZTS8Hs=&_dt_no_comment=1&origin=11)  
[钉钉3群（已满）: 33797247](https://qr.dingtalk.com/action/joingroup?code=v1,k1,3UGlEScTGQaHpW2cIRo+gkxJ9EVZ5fz26M6nW3uFP30=&_dt_no_comment=1&origin=11)  
[钉钉4群（已满）: 33491624](https://qr.dingtalk.com/action/joingroup?code=v1,k1,V14Pb65Too70rQkEaJ9ohb6lZBZbtp6jIL/q9EWh9vA=&_dt_no_comment=1&origin=11)  
[钉钉5群（已满）: 32134498](https://h5.dingtalk.com/circle/healthCheckin.html?dtaction=os&corpId=dingb9fa1325d9dccc3ecac589edd02f1650&5233a=71a83&cbdbhh=qwertyuiop)  
[钉钉6群（已满）: 34707941](https://h5.dingtalk.com/circle/healthCheckin.html?dtaction=os&corpId=dingcf68008a1d443ac012d5427bdb061b7a&6ae36c3d-0c80-4=22398493-6c2a-4&cbdbhh=qwertyuiop)  
[钉钉7群（已满）: 35235427](https://h5.dingtalk.com/circle/healthCheckin.html?dtaction=os&corpId=ding532b9018c06c7fc8660273c4b78e6440&167fb=ed003&cbdbhh=qwertyuiop)  
[钉钉8群（已满）: 44752220](https://h5.dingtalk.com/circle/healthCheckin.html?dtaction=os&corpId=dingea96808beee421693fd4ba7542d6e5da&0380092a-fa46=a6a40905-7951&cbdbhh=qwertyuiop)   
[钉钉9群（已满）: 11045002277](https://h5.dingtalk.com/circle/healthCheckin.html?dtaction=os&corpId=dinge338d2215891c0459c13cd6b2cb108a6&6972d=b92f9&cbdbhh=qwertyuiop)   
[钉钉10群: 27360019755](https://qr.dingtalk.com/action/joingroup?code=v1,k1,v25LHn2liWmrWUKlkhIzOTcK7s7onp/sZP8mO5oIYSs=&_dt_no_comment=1&origin=11)

# 维护者

姬朋飞（玉霄)、庄家钜、怀宇

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
