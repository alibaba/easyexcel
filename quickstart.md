# easyexcel核心功能
## 目录
### 读
DEMO代码地址：[https://github.com/alibaba/easyexcel/blob/master/src/test/java/com/alibaba/easyexcel/demo/read/ReadTest.java](/src/test/java/com/alibaba/easyexcel/test/demo/read/ReadTest.java)
* [最简单的读](#simpleRead)

### 写

## *读任意大小的03、07版Excel不会OO]<br />
## *读Excel自动通过注解，把结果映射为java模型<br />
## *读Excel支持多sheet<br />
## *读Excel时候是否对Excel内容做trim()增加容错<br />
## *写小量数据的03版Excel（不要超过2000行）<br />
## *写任意大07版Excel不会OOM<br />
## *写Excel通过注解将表头自动写入Excel<br />
## *写Excel可以自定义Excel样式 如：字体，加粗，表头颜色，数据内容颜色<br />
## *写Excel到多个不同sheet<br />
## *写Excel时一个sheet可以写多个Table<br />
## *写Excel时候自定义是否需要写表头<br />


## 读excel样例
### 最简单的读<span id="simpleRead" />
#### excel示例<span id="simpleReadExcel" />
![img](img/readme/quickstart/read/demo.png)
#### 对象<span id="simpleReadObject" />
```java
@Data
public class DemoData {
    private String string;
    private Date date;
    private Double doubleData;
}
```
#### 监听器<span id="simpleReadListener" />
```java
public class DemoDataListener extends AnalysisEventListener<DemoData> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoDataListener.class);
    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;
    List<DemoData> list = new ArrayList<DemoData>();

    @Override
    public void invoke(DemoData data, AnalysisContext context) {
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(data));
        list.add(data);
        if (list.size() >= BATCH_COUNT) {
            saveData();
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
        LOGGER.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        LOGGER.info("{}条数据，开始存储数据库！", list.size());
        LOGGER.info("存储数据库成功！");
    }
}
```
#### 代码
```java
    /**
     * 最简单的读
     * <li>1. 创建excel对应的实体对象 参照{@link DemoData}
     * <li>2. 由于默认异步读取excel，所以需要创建excel一行一行的回调监听器，参照{@link DemoDataListener}
     * <li>3. 直接读即可
     */
    @Test
    public void simpleRead() {
        // 写法1：
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcelFactory.read(fileName, DemoData.class, new DemoDataListener()).sheet().doRead();

        // 写法2：
        fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        ExcelReader excelReader = EasyExcelFactory.read(fileName, DemoData.class, new DemoDataListener()).build();
        ReadSheet readSheet = EasyExcelFactory.readSheet(0).build();
        excelReader.read(readSheet);
        // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
        excelReader.finish();
    }
```

### 指定列的下标或者列名<span id="indexOrNameRead" />
#### excel示例
参照：[excel示例](#simpleReadExcel)
#### 对象
```java
@Data
public class IndexOrNameData {
    /**
     * 强制读取第三个 这里不建议 index 和 name 同时用，要么一个对象只用index，要么一个对象只用name去匹配
     */
    @ExcelProperty(index = 2)
    private Double doubleData;
    /**
     * 用名字去匹配，这里需要注意，如果名字重复，会导致只有一个字段读取到数据
     */
    @ExcelProperty("字符串标题")
    private String string;
    @ExcelProperty("日期标题")
    private Date date;
}
```
#### 监听器
参照：[监听器](#simpleReadListener) 只是泛型变了而已
#### 代码
```java
    /**
     * 指定列的下标或者列名
     *
     * <li>1. 创建excel对应的实体对象,并使用{@link ExcelProperty}注解. 参照{@link IndexOrNameData}
     * <li>2. 由于默认异步读取excel，所以需要创建excel一行一行的回调监听器，参照{@link IndexOrNameDataListener}
     * <li>3. 直接读即可
     */
    @Test
    public void indexOrNameRead() {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        // 这里默认读取第一个sheet
        EasyExcelFactory.read(fileName, IndexOrNameData.class, new IndexOrNameDataListener()).sheet().doRead();
    }
```

### 读多个sheet<span id="repeatedRead" />
#### excel示例
参照：[excel示例](#simpleReadExcel)
#### 对象
参照：[对象](#simpleReadObject)
#### 监听器
参照：[监听器](#simpleReadListener)
#### 代码
```java
    /**
     * 读多个sheet,这里注意一个sheet不能读取多次，一定要多次需要重新读取文件
     * <li>1. 创建excel对应的实体对象 参照{@link DemoData}
     * <li>2. 由于默认异步读取excel，所以需要创建excel一行一行的回调监听器，参照{@link DemoDataListener}
     * <li>3. 直接读即可
     */
    @Test
    public void repeatedRead() {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        ExcelReader excelReader = EasyExcelFactory.read(fileName, DemoData.class, new DemoDataListener()).build();
        ReadSheet readSheet1 = EasyExcelFactory.readSheet(0).build();
        ReadSheet readSheet2 = EasyExcelFactory.readSheet(1).build();
        excelReader.read(readSheet1);
        excelReader.read(readSheet2);
        // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
        excelReader.finish();
    }
```

### 日期、数字或者自定义格式转换<span id="converterRead" />
#### excel示例
参照：[excel示例](#simpleReadExcel)
#### 对象
```java
@Data
public class ConverterData {
    /**
     * 我自定义 转换器，不管数据库传过来什么 。我给他加上“自定义：”
     */
    @ExcelProperty(converter = CustomStringStringConverter.class)
    private String string;
    /**
     * 这里用string 去接日期才能格式化。我想接收年月日格式
     */
    @DateTimeFormat("yyyy年MM月dd日HH时mm分ss秒")
    private String date;
    /**
     * 我想接收百分比的数字
     */
    @NumberFormat("#.##%")
    private String doubleData;
}
```
#### 监听器
参照：[监听器](#simpleReadListener) 只是泛型变了
#### 自定义转换器
````java
public class CustomStringStringConverter implements Converter<String> {
    @Override
    public Class supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    /**
     * 这里读的时候会调用
     *
     * @param cellData
     *            NotNull
     * @param contentProperty
     *            Nullable
     * @param globalConfiguration
     *            NotNull
     * @return
     */
    @Override
    public String convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) {
        return "自定义：" + cellData.getStringValue();
    }

    /**
     * 这里是写的时候会调用 不用管
     *
     * @param value
     *            NotNull
     * @param contentProperty
     *            Nullable
     * @param globalConfiguration
     *            NotNull
     * @return
     */
    @Override
    public CellData convertToExcelData(String value, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) {
        return new CellData(value);
    }

}
````
#### 代码
```java
    /**
     * 日期、数字或者自定义格式转换
     * <p>
     * 默认读的转换器{@link DefaultConverterLoader#loadDefaultReadConverter()}
     * <li>1. 创建excel对应的实体对象 参照{@link ConverterData}.里面可以使用注解{@link DateTimeFormat}、{@link NumberFormat}或者自定义注解
     * <li>2. 由于默认异步读取excel，所以需要创建excel一行一行的回调监听器，参照{@link ConverterDataListener}
     * <li>3. 直接读即可
     */
    @Test
    public void converterRead() {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 然后千万别忘记 finish
        EasyExcelFactory.read(fileName, ConverterData.class, new ConverterDataListener())
            // 这里注意 我们也可以registerConverter来指定自定义转换器， 但是这个转换变成全局了， 所有java为string,excel为string的都会用这个转换器。
            // 如果就想单个字段使用请使用@ExcelProperty 指定converter
            // .registerConverter(new CustomStringStringConverter())
            // 读取sheet
            .sheet().doRead();
    }
```

### 多行头<span id="complexHeaderRead" />
#### excel示例
参照：[excel示例](#simpleReadExcel)
#### 对象
参照：[对象](#simpleReadObject)
#### 监听器
参照：[监听器](#simpleReadListener)
#### 代码
```java
    /**
     * 多行头
     *
     * <li>1. 创建excel对应的实体对象 参照{@link DemoData}
     * <li>2. 由于默认异步读取excel，所以需要创建excel一行一行的回调监听器，参照{@link DemoDataListener}
     * <li>3. 设置headRowNumber参数，然后读。 这里要注意headRowNumber如果不指定， 会根据你传入的class的{@link ExcelProperty#value()}里面的表头的数量来决定行数，
     * 如果不传入class则默认为1.当然你指定了headRowNumber不管是否传入class都是以你传入的为准。
     */
    @Test
    public void complexHeaderRead() {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 然后千万别忘记 finish
        EasyExcelFactory.read(fileName, DemoData.class, new DemoDataListener()).sheet()
            // 这里可以设置1，因为头就是一行。如果多行头，可以设置其他值。不传入也可以，因为默认会根据DemoData 来解析，他没有指定头，也就是默认1行
            .headRowNumber(1).doRead();
    }
```

### 同步的返回<span id="synchronousRead" />
#### excel示例
参照：[excel示例](#simpleReadExcel)
#### 对象
参照：[对象](#simpleReadObject)
#### 代码
```java
    /**
     * 同步的返回，不推荐使用，如果数据量大会把数据放到内存里面
     */
    @Test
    public void synchronousRead() {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 同步读取会自动finish
        List<Object> list = EasyExcelFactory.read(fileName).head(DemoData.class).sheet().doReadSync();
        for (Object obj : list) {
            DemoData data = (DemoData)obj;
            LOGGER.info("读取到数据:{}", JSON.toJSONString(data));
        }

        // 这里 也可以不指定class，返回一个list，然后读取第一个sheet 同步读取会自动finish
        list = EasyExcelFactory.read(fileName).sheet().doReadSync();
        for (Object obj : list) {
            // 返回每条数据的键值对 表示所在的列 和所在列的值
            Map<Integer, String> data = (Map<Integer, String>)obj;
            LOGGER.info("读取到数据:{}", JSON.toJSONString(data));
        }
    }
```

## 测试数据分析
![POI usermodel PK easyexcel(Excel 2003).png](http://ata2-img.cn-hangzhou.img-pub.aliyun-inc.com/02c4bfbbab99a649788523d04f84a42f.png)
![POI usermodel PK easyexcel(Excel 2007).png](http://ata2-img.cn-hangzhou.img-pub.aliyun-inc.com/f6a8a19ec959f0eb564e652de523fc9e.png)
![POI usermodel PK easyexcel(Excel 2003) (1).png](http://ata2-img.cn-hangzhou.img-pub.aliyun-inc.com/26888f7ea1cb8dc56db494926544edf7.png)
![POI usermodel PK easyexcel(Excel 2007) (1).png](http://ata2-img.cn-hangzhou.img-pub.aliyun-inc.com/4de1ac95bdfaa4b1870b224af4f4cb75.png)
从上面的性能测试可以看出easyexcel在解析耗时上比poiuserModel模式弱了一些。主要原因是我内部采用了反射做模型字段映射，中间我也加了cache，但感觉这点差距可以接受的。但在内存消耗上差别就比较明显了，easyexcel在后面文件再增大，内存消耗几乎不会增加了。但poi userModel就不一样了，简直就要爆掉了。想想一个excel解析200M，同时有20个人再用估计一台机器就挂了。

