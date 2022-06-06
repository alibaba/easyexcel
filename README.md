EasyExcel
======================
[![Build Status](https://github.com/alibaba/easyexcel/actions/workflows/ci.yml/badge.svg?branch=master)](https://github.com/alibaba/easyexcel/actions/workflows/ci.yml?query=branch%3Amaster)
[![Maven central](https://maven-badges.herokuapp.com/maven-central/com.alibaba/easyexcel/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.alibaba/easyexcel)
[![License](http://img.shields.io/:license-apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

[QQ1 群（已满）: 662022184](https://jq.qq.com/?_wv=1027&k=1T21jJxh)  
[QQ2 群（已满）: 1097936804](https://jq.qq.com/?_wv=1027&k=j5zEy6Xl)  
[QQ3 群（已满）: 453928496](https://qm.qq.com/cgi-bin/qm/qr?k=e2ULsA5A0GldhV2CXJ8sIbAyu9I6qqs7&jump_from=webapi)  
[QQ4 群（已满）: 496594404](https://qm.qq.com/cgi-bin/qm/qr?k=e_aVG1Q7gi0PJUBkbrUGAgbeO3kUEInK&jump_from=webapi)   
[QQ5 群：203228638](https://jq.qq.com/?_wv=1027&k=m9kqpoV6)   
[钉钉 1 群（已满）: 21960511](https://qr.dingtalk.com/action/joingroup?code=v1,k1,cchz6k12ci9B08NNqhNRFGXocNVHrZtW0kaOtTKg/Rk=&_dt_no_comment=1&origin=11)  
[钉钉 2 群（已满）: 32796397](https://qr.dingtalk.com/action/joingroup?code=v1,k1,jyU9GtEuNU5S0QTyklqYcYJ8qDZtUuTPMM7uPZTS8Hs=&_dt_no_comment=1&origin=11)  
[钉钉 3 群（已满）: 33797247](https://qr.dingtalk.com/action/joingroup?code=v1,k1,3UGlEScTGQaHpW2cIRo+gkxJ9EVZ5fz26M6nW3uFP30=&_dt_no_comment=1&origin=11)  
[钉钉 4 群（已满）: 33491624](https://qr.dingtalk.com/action/joingroup?code=v1,k1,V14Pb65Too70rQkEaJ9ohb6lZBZbtp6jIL/q9EWh9vA=&_dt_no_comment=1&origin=11)  
[钉钉 5 群（已满）: 32134498](https://h5.dingtalk.com/circle/healthCheckin.html?dtaction=os&corpId=dingb9fa1325d9dccc3ecac589edd02f1650&5233a=71a83&cbdbhh=qwertyuiop)  
[钉钉 6 群（已满）: 34707941](https://h5.dingtalk.com/circle/healthCheckin.html?dtaction=os&corpId=dingcf68008a1d443ac012d5427bdb061b7a&6ae36c3d-0c80-4=22398493-6c2a-4&cbdbhh=qwertyuiop)  
[钉钉 7 群：35235427](https://h5.dingtalk.com/circle/healthCheckin.html?dtaction=os&corpId=ding532b9018c06c7fc8660273c4b78e6440&167fb=ed003&cbdbhh=qwertyuiop)  

[官方网站：https://easyexcel.opensource.alibaba.com/](https://easyexcel.opensource.alibaba.com/)  

[常见问题](https://easyexcel.opensource.alibaba.com/qa/)

#### 因为公司不方便用 QQ，所以建议加钉钉群

# JAVA 解析 Excel 工具 EasyExcel
Java 解析、生成 Excel 比较有名的框架有 Apache poi、jxl。但他们都存在一个严重的问题就是非常的耗内存，poi 有一套 SAX 模式的 API 可以一定程度的解决一些内存溢出的问题，但 POI 还是有一些缺陷，比如 07 版 Excel 解压缩以及解压后存储都是在内存中完成的，内存消耗依然很大。easyexcel 重写了 poi 对 07 版 Excel 的解析，一个 3M 的 excel 用 POI sax 解析依然需要 100M 左右内存，改用 easyexcel 可以降低到几 M，并且再大的 excel 也不会出现内存溢出；03 版依赖 POI 的 sax 模式，在上层做了模型转换的封装，让使用者更加简单方便

## 64M 内存 20 秒读取 75M（46W 行 25 列）的 Excel（3.0.2+版本）
当然还有极速模式能更快，但是内存占用会在 100M 多一点
![img](img/readme/large.png)

## 关于版本选择
如果项目中没有使用过 poi, 且 jdk 版本在 8-17 之间，直接使用最新版本，别犹豫。以下表格适用于不满足以上 2 个情况的。

| 版本                 | poi 依赖版本 （支持范围）        | jdk 版本支持范围    | 备注                                          |
|--------------------|-----------------------|--------------|---------------------------------------------|
| 3.1.0+             | 4.1.2 (4.1.2 - 5.2.2) | jkd8 - jdk17 | 推荐使用，会更新的版本                                 |
| 3.0.0-beta1 - 3.0.5 | 4.1.2 (4.1.2 - 5.2.2) | jkd8 - jdk11 | 不推荐项目新引入此版本，除非超级严重 bug, 否则不再更新                |
| 2.0.0-beta1-2.2.11 | 3.17 (3.17 - 4.1.2)   | jdk6 - jdk11 | 不推荐项目新引入此版本，除非是 jdk6 否则不推荐使用，除非超级严重 bug, 否则不再更新 |
| 1+版本               | 3.17 (3.17 - 4.1.2)   | jdk6 - jdk11 | 不推荐项目新引入此版本，超级严重 bug, 也不再更新                   |

注意： 3+版本的的 easyexcel，使用 poi 5+版本时，需要手动排除：poi-ooxml-schemas，例如：

```xml
 <dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>easyexcel</artifactId>
    <version>3.1.0</version>
    <exclusions>
        <exclusion>
            <artifactId>poi-ooxml-schemas</artifactId>
            <groupId>org.apache.poi</groupId>
        </exclusion>
    </exclusions>
</dependency>
```

### 关于版本升级
* 不建议跨大版本升级 尤其跨 2 个大版本
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
    <version>3.1.0</version>
</dependency>
```

### easyexcel 人员招募
由于工作较忙，有意愿做开源的同学可以报名，主要负责群里回答&issue 处理，当然也可以做一些 PR.   
由于开源没有任何物质回报，然后现在的维护同学也是课余时间维护的，所以想加入的同学需要持之以恒，而不是一时兴起。
要求如下：
* 有一定 java 编码能力 & 良好的编码习惯
* 了解 easyexcel 读&写的原理
* 热爱开源项目
* 能长期坚持的去做
* 相对工作没那么忙

## 相关文档
* [快速开始](https://www.yuque.com/easyexcel/doc/easyexcel)
* [关于软件](/abouteasyexcel.md)
* [更新记事](/update.md)
* [贡献代码](https://www.yuque.com/easyexcel/doc/contribute)

## 维护者
姬朋飞（玉霄）、庄家钜、怀宇
## 快速开始
### 读 Excel
DEMO 代码地址：[https://github.com/alibaba/easyexcel/blob/master/easyexcel-test/src/test/java/com/alibaba/easyexcel/test/demo/read/ReadTest.java](https://github.com/alibaba/easyexcel/blob/master/easyexcel-test/src/test/java/com/alibaba/easyexcel/test/demo/read/ReadTest.java)

```java
    /**
     * 最简单的读
     * <p>1. 创建 excel 对应的实体对象 参照{@link DemoData}
     * <p>2. 由于默认一行行的读取 excel，所以需要创建 excel 一行一行的回调监听器，参照{@link DemoDataListener}
     * <p>3. 直接读即可
     */
    @Test
    public void simpleRead() {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        // 这里 需要指定读用哪个 class 去读，然后读取第一个 sheet 文件流会自动关闭
        EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).sheet().doRead();
    }
```

### 写 Excel
DEMO 代码地址：[https://github.com/alibaba/easyexcel/blob/master/easyexcel-test/src/test/java/com/alibaba/easyexcel/test/demo/write/WriteTest.java](https://github.com/alibaba/easyexcel/blob/master/easyexcel-test/src/test/java/com/alibaba/easyexcel/test/demo/write/WriteTest.java)
```java
    /**
     * 最简单的写
     * <p>1. 创建 excel 对应的实体对象 参照{@link com.alibaba.easyexcel.test.demo.write.DemoData}
     * <p>2. 直接写即可
     */
    @Test
    public void simpleWrite() {
        String fileName = TestFileUtil.getPath() + "write" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个 class 去读，然后写到第一个 sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用 03 则 传入 excelType 参数即可
        EasyExcel.write(fileName, DemoData.class).sheet("模板").doWrite(data());
    }
```

### web 上传、下载
DEMO 代码地址：[https://github.com/alibaba/easyexcel/blob/master/easyexcel-test/src/test/java/com/alibaba/easyexcel/test/demo/web/WebTest.java](https://github.com/alibaba/easyexcel/blob/master/easyexcel-test/src/test/java/com/alibaba/easyexcel/test/demo/web/WebTest.java)
```java
   /**
     * 文件下载（失败了会返回一个有部分数据的 Excel）
     * <p>
     * 1. 创建 excel 对应的实体对象 参照{@link DownloadData}
     * <p>
     * 2. 设置返回的 参数
     * <p>
     * 3. 直接写，这里注意，finish 的时候会自动关闭 OutputStream, 当然你外面再关闭流问题不大
     */
    @GetMapping("download")
    public void download(HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用 swagger 会导致各种问题，请直接用浏览器或者用 postman
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里 URLEncoder.encode 可以防止中文乱码 当然和 easyexcel 没有关系
        String fileName = URLEncoder.encode("测试", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), DownloadData.class).sheet("模板").doWrite(data());
    }

    /**
     * 文件上传
     * <p>1. 创建 excel 对应的实体对象 参照{@link UploadData}
     * <p>2. 由于默认一行行的读取 excel，所以需要创建 excel 一行一行的回调监听器，参照{@link UploadDataListener}
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
有问题阿里同事可以通过钉钉找到我，阿里外同学可以通过 git 留言。其他技术非技术相关的也欢迎一起探讨。
