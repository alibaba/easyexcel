EasyExcel
======================
[![Build Status](https://github.com/alibaba/easyexcel/actions/workflows/ci.yml/badge.svg?branch=master)](https://github.com/alibaba/easyexcel/actions/workflows/ci.yml?query=branch%3Amaster)
[![Maven central](https://maven-badges.herokuapp.com/maven-central/com.alibaba/easyexcel/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.alibaba/easyexcel)
[![License](http://img.shields.io/:license-apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![](https://img.shields.io/badge/EasyExcel-Check%20Your%20Contribution-orange)](https://opensource.alibaba.com/contribution_leaderboard/details?projectValue=easyexcel)

[Communication Group 1 in QQ(Full): 662022184](https://jq.qq.com/?_wv=1027&k=1T21jJxh)  
[Communication Group 2 in QQ(Full): 1097936804](https://jq.qq.com/?_wv=1027&k=j5zEy6Xl)  
[Communication Group 3 in QQ(Full): 453928496](https://qm.qq.com/cgi-bin/qm/qr?k=e2ULsA5A0GldhV2CXJ8sIbAyu9I6qqs7&jump_from=webapi)  
[Communication Group 4 in QQ: 496594404](https://qm.qq.com/cgi-bin/qm/qr?k=e_aVG1Q7gi0PJUBkbrUGAgbeO3kUEInK&jump_from=webapi)   
[Communication Group 1 in DingTalk（Full）: 21960511](https://qr.dingtalk.com/action/joingroup?code=v1,k1,cchz6k12ci9B08NNqhNRFGXocNVHrZtW0kaOtTKg/Rk=&_dt_no_comment=1&origin=11)  
[Communication Group 2 in DingTalk（Full）: 32796397](https://qr.dingtalk.com/action/joingroup?code=v1,k1,jyU9GtEuNU5S0QTyklqYcYJ8qDZtUuTPMM7uPZTS8Hs=&_dt_no_comment=1&origin=11)  
[Communication Group 3 in DingTalk（Full）: 33797247](https://qr.dingtalk.com/action/joingroup?code=v1,k1,3UGlEScTGQaHpW2cIRo+gkxJ9EVZ5fz26M6nW3uFP30=&_dt_no_comment=1&origin=11)  
[Communication Group 4 in DingTalk（Full）: 33491624](https://qr.dingtalk.com/action/joingroup?code=v1,k1,V14Pb65Too70rQkEaJ9ohb6lZBZbtp6jIL/q9EWh9vA=&_dt_no_comment=1&origin=11)  
[Communication Group 5 in DingTalk: 32134498](https://h5.dingtalk.com/circle/healthCheckin.html?dtaction=os&corpId=dingb9fa1325d9dccc3ecac589edd02f1650&5233a=71a83&cbdbhh=qwertyuiop)  
[Official Website: https://yuque.com/easyexcel](https://www.yuque.com/easyexcel/doc/easyexcel)  

[FAQ](https://www.yuque.com/easyexcel/faq)
#### It is recommended to join a DingTalk group

# EasyExcel, a java toolkit for parsing Excel easily
There are several java frameworks or toolkit which can parse and generate Excel, such as Apache POI or jxl. But they all have some difficulties to handle problems like excessive memory usage. Apache POI framework has a set of SAX mode API can fix some memory overflow problems at some extent, but it still has some flaws. For example, the unzipping and the storage of the unzipping of Excel file in version 07 are done in memory, so the memory consumption is still very high. The EasyExcel toolkit rewrites the logic of POI for parsing Excel version 07. One 3 megabytes Excel file parsed with POI still requires about 100M memory, which can be reduced to a few megabyte by using EasyExcel instead. And yes, there is no memory overflow for even larger excel with EasyExcel. EasyExcel version 03 depends on POI SAX model and does model transformation/encapsulation in the upper layer to make it simpler and more convenient for users.

## Using EasyExcel version 3.0.2+, a machine with 64M RAM can read a 75 megabyte Excel file containing 460,000 rows and 25 columns in 20 seconds
Of course, there is also a very fast mode can be faster, but the memory consumption will be a little more than 100M
![img](img/readme/large.png)

## Version support
* EasyExcel version 2+ works on Java7 or Java6
* EasyExcel version 3+ works on Java8 or java8+
### About version upgrade
* It is not recommended upgrading across major versions, especially across 2 major versions.
* There are some incompatibilities in upgrading from version 2+ to version 3+.
  * Using a custom interceptor to modify the style can cause problems, even if it does not compile with errors.
  * When reading the Excel file, the `invoke` function will throw an exception, there will not be an additional layer of `ExcelAnalysisException` wrapped here, and it will not compile with errors.
  * Style and other annotations involving `boolean` or some enumeration values have been changed, adding the default value. The compiler will report an error, just change the annotation.
* It is recommended to re-test the relevant functions after upgrading across major versions.

### Latest Version
```xml
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>easyexcel</artifactId>
            <version>3.0.2</version>
        </dependency>
```

## Advertising space
### Alibaba New Retail Business Department Recruitment
Alibaba New Retail Business Department sincerely recruit JAVA senior development, technical experts. If you are interested, you can contact us by WeChat, or send your Resume to my email jipengfei.jpf@alibaba-inc.com.
### EasyExcel personnel recruitment
Anyone who wants to participate in this project can apply, mainly responsible for answering questions in the communication group and dealing with the issues. Of course, you can also do some PR.
Since there is no material reward for participating in the open source project, and the current maintainers are also maintaining the project in their spare time. So people who want to join this project need to be persistent, not just on a whim.   
The requirements are as follows:
* There are certain java coding skills & good coding habits
* Understand the read&write principles of EasyExcel
* Has passion for open source projects
* Be able to do things consistently for a long time
* Your job is not so busy

## Related Documents
* [Quick Start](https://www.yuque.com/easyexcel/doc/easyexcel)
* [About Us](/abouteasyexcel.md)
* [Update Notes](/update.md)
* [Code Contribution](https://www.yuque.com/easyexcel/doc/contribute)

## Maintainers
姬朋飞（玉霄)、庄家钜、怀宇
## Quick Start
### Read Excel File
DEMO：[https://github.com/alibaba/easyexcel/blob/master/src/test/java/com/alibaba/easyexcel/demo/read/ReadTest.java](/src/test/java/com/alibaba/easyexcel/test/demo/read/ReadTest.java)

```java
    /**
     * The easiest way to read Excel file using EasyExcel toolkit
     *
     * <p>
     *     1. Create an entity object, such as {@link DemoData}, each property of the entity object corresponds to a specific field in any row of Excel.
     *     2. When reading each row of an Excel file, create a callback listener for the corresponding row. Refer to{@link DemoDataListener}
     *     3. Invoke the read function
     * </p>
     */
    @Test
    public void simpleRead() {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        // Specify which entity object class to use to read the Excel content. The file stream will close automatically after reading the first sheet of Excel.
        EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).sheet().doRead();
    }
```

### Write Excel File
DEMO：[https://github.com/alibaba/easyexcel/blob/master/src/test/java/com/alibaba/easyexcel/test/demo/write/WriteTest.java](/src/test/java/com/alibaba/easyexcel/test/demo/write/WriteTest.java)
```java
    /**
     * The easiest way to write Excel file using EasyExcel toolkit
     *
     * <p>
     *     1. Create an entity object, refer to{@link com.alibaba.easyexcel.test.demo.write.DemoData}. 
     *        Each property of the entity object corresponds to a specific field of Excel
     *     2. Invoke write function
     * </p>
     */
    @Test
    public void simpleWrite() {
        String fileName = TestFileUtil.getPath() + "write" + System.currentTimeMillis() + ".xlsx";
        // Specify which entity object class to use to write Excel, it will write to the first sheet of Excel with the name template. Then the file stream will be closed automatically.
        // With version 03, just pass in the excelType parameter
        EasyExcel.write(fileName, DemoData.class).sheet("template").doWrite(data());
    }
```

### File Uploading&Downloading
DEMO：[https://github.com/alibaba/easyexcel/blob/master/src/test/java/com/alibaba/easyexcel/test/demo/web/WebTest.java](/src/test/java/com/alibaba/easyexcel/test/demo/web/WebTest.java)
```java
   /**
     * File downloading
     *
     * Note: returns an Excel with partial data if it fails
     *
     * <p>
     *  1. Create an entity object, refer to{@link DownloadData}. 
     *     Each property of the entity object corresponds to a specific field of Excel
     *  2. Specify the returned properties
     *  3. Invoke write function, then the OutputStream is automatically closed when it ends.
     * </p>
     */
    @GetMapping("download")
    public void download(HttpServletResponse response) throws IOException {
        // Using swagger may cause some problems, please use your browser directly or use postman to invoke this
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // URLEncoder.encode function can prevent Chinese garbled code 
        String fileName = URLEncoder.encode("test", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), DownloadData.class).sheet("template").doWrite(data());
    }

    /**
     * File uploading
     *
     * <p>
     *     1. Create an entity object, refer to{@link UploadData}
     *        Each property of the entity object corresponds to a specific field of Excel
     *     2. When reading each row of an Excel file, create a callback listener for the corresponding row. Refer to{@link UploadDataListener}
     *     3. Invoke read function
     * </p>
     */
    @PostMapping("upload")
    @ResponseBody
    public String upload(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), UploadData.class, new UploadDataListener(uploadDAO)).sheet().doRead();
        return "success";
    }
```
### Contact Us
If you have any questions, Alibaba colleagues can find me in DingTalk, and others can leave messages here. All related questions are well welcomed.
