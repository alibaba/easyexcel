# easyexcel核心功能
## 目录
### 读
DEMO代码地址：[https://github.com/alibaba/easyexcel/blob/master/src/test/java/com/alibaba/easyexcel/demo/read/ReadTest.java](/src/test/java/com/alibaba/easyexcel/test/demo/read/ReadTest.java)
* [最简单的读](#simpleRead)
* [指定列的下标或者列名](#indexOrNameRead)
* [读多个sheet](#repeatedRead)
* [日期、数字或者自定义格式转换](#converterRead)
* [多行头](#complexHeaderRead)
* [同步的返回](#synchronousRead)
* [web中的读](#webRead)
### 写
DEMO代码地址：[https://github.com/alibaba/easyexcel/blob/master/src/test/java/com/alibaba/easyexcel/test/demo/write/WriteTest.java](/src/test/java/com/alibaba/easyexcel/test/demo/write/WriteTest.java)
* [最简单的写](#simpleWrite)
* [指定写入的列](#indexWrite)
* [复杂头写入](#complexHeadWrite)
* [重复多次写入](#repeatedWrite)
* [日期、数字或者自定义格式转换](#converterWrite)
* [根据模板写入](#templateWrite)
* [列宽、行高](#widthAndHeightWrite)
* [自定义样式](#styleWrite)
* [合并单元格](#mergeWrite)
* [使用table去写入](#tableWrite)
* [web中的写](#webWrite)

## 读excel样例
### <span id="simpleRead" />最简单的读
##### <span id="simpleReadExcel" />excel示例
![img](img/readme/quickstart/read/demo.png)
##### <span id="simpleReadObject" />对象
```java
@Data
public class DemoData {
    private String string;
    private Date date;
    private Double doubleData;
}
```
##### <span id="simpleReadListener" />监听器
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
##### 代码
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

### <span id="indexOrNameRead" />指定列的下标或者列名
##### excel示例
参照：[excel示例](#simpleReadExcel)
##### 对象
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
##### 监听器
参照：[监听器](#simpleReadListener) 只是泛型变了而已
##### 代码
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

### <span id="repeatedRead" />读多个sheet
##### excel示例
参照：[excel示例](#simpleReadExcel)
##### 对象
参照：[对象](#simpleReadObject)
##### 监听器
参照：[监听器](#simpleReadListener)
##### 代码
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

### <span id="converterRead" />日期、数字或者自定义格式转换
##### excel示例
参照：[excel示例](#simpleReadExcel)
##### 对象
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
##### 监听器
参照：[监听器](#simpleReadListener) 只是泛型变了
##### 自定义转换器
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
##### 代码
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

### <span id="complexHeaderRead" />多行头
##### excel示例
参照：[excel示例](#simpleReadExcel)
##### 对象
参照：[对象](#simpleReadObject)
##### 监听器
参照：[监听器](#simpleReadListener)
##### 代码
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

### <span id="synchronousRead" />同步的返回
##### excel示例
参照：[excel示例](#simpleReadExcel)
##### 对象
参照：[对象](#simpleReadObject)
##### 代码
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

### <span id="webRead" />web中的读
##### 示例代码
DEMO代码地址：[https://github.com/alibaba/easyexcel/blob/master/src/test/java/com/alibaba/easyexcel/test/demo/web/WebTest.java](/src/test/java/com/alibaba/easyexcel/test/demo/web/WebTest.java)
##### excel示例
参照：[excel示例](#simpleReadExcel)
##### 对象
参照：[对象](#simpleReadObject) 只是名字变了
##### 监听器
参照：[监听器](#simpleReadListener) 只是泛型变了
##### 代码
```java
    /**
     * 文件上传
     * <li>1. 创建excel对应的实体对象 参照{@link UploadData}
     * <li>2. 由于默认异步读取excel，所以需要创建excel一行一行的回调监听器，参照{@link UploadDataListener}
     * <li>3. 直接读即可
     */
    @PostMapping("upload")
    @ResponseBody
    public String upload(MultipartFile file) throws IOException {
        EasyExcelFactory.read(file.getInputStream(), UploadData.class, new UploadDataListener()).sheet().doRead();
        return "success";
    }
```

## 写excel样例
### 通用数据生成 后面不会重复写
```java
    private List<DemoData> data() {
        List<DemoData> list = new ArrayList<DemoData>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }
```
### <span id="simpleWrite" />最简单的写
##### excel示例
![img](img/readme/quickstart/write/simpleWrite.png)
##### <span id="simpleWriteObject" />对象
```java
@Data
public class DemoData {
    @ExcelProperty("字符串标题")
    private String string;
    @ExcelProperty("日期标题")
    private Date date;
    @ExcelProperty("数字标题")
    private Double doubleData;
}
```
##### 代码
```java
    /**
     * 最简单的写
     * <li>1. 创建excel对应的实体对象 参照{@link DemoData}
     * <li>2. 直接写即可
     */
    @Test
    public void simpleWrite() {
        // 写法1
        String fileName = TestFileUtil.getPath() + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcelFactory.write(fileName, DemoData.class).sheet("模板").doWrite(data());

        // 写法2
        fileName = TestFileUtil.getPath() + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去读
        ExcelWriter excelWriter = EasyExcelFactory.write(fileName, DemoData.class).build();
        WriteSheet writeSheet = EasyExcelFactory.writerSheet("模板").build();
        excelWriter.write(data(), writeSheet);
        /// 千万别忘记finish 会帮忙关闭流
        excelWriter.finish();
    }
```

### <span id="indexWrite" />指定写入的列
##### excel示例
![img](img/readme/quickstart/write/indexWrite.png)
##### 对象
```java
@Data
public class IndexData {
    @ExcelProperty(value = "字符串标题", index = 0)
    private String string;
    @ExcelProperty(value = "日期标题", index = 1)
    private Date date;
    /**
     * 这里设置3 会导致第二列空的
     */
    @ExcelProperty(value = "数字标题", index = 3)
    private Double doubleData;
}
```
##### 代码
```java
    /**
     * 指定写入的列
     * <li>1. 创建excel对应的实体对象 参照{@link IndexData}
     * <li>2. 使用{@link ExcelProperty}注解指定写入的列
     * <li>3. 直接写即可
     */
    @Test
    public void indexWrite() {
        String fileName = TestFileUtil.getPath() + "indexWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcelFactory.write(fileName, IndexData.class).sheet("模板").doWrite(data());
    }
```

### <span id="complexHeadWrite" />复杂头写入
##### excel示例
![img](img/readme/quickstart/write/complexHeadWrite.png)
##### 对象
```java
@Data
public class ComplexHeadData {
    @ExcelProperty({"主标题", "字符串标题"})
    private String string;
    @ExcelProperty({"主标题", "日期标题"})
    private Date date;
    @ExcelProperty({"主标题", "数字标题"})
    private Double doubleData;
}
```
##### 代码
```java
    /**
     * 复杂头写入
     * <li>1. 创建excel对应的实体对象 参照{@link ComplexHeadData}
     * <li>2. 使用{@link ExcelProperty}注解指定复杂的头
     * <li>3. 直接写即可
     */
    @Test
    public void complexHeadWrite() {
        String fileName = TestFileUtil.getPath() + "complexHeadWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcelFactory.write(fileName, ComplexHeadData.class).sheet("模板").doWrite(data());
    }
```

### <span id="repeatedWrite" />重复多次写入
##### excel示例
![img](img/readme/quickstart/write/repeatedWrite.png)
##### 对象
参照：[对象](#simpleWriteObject)
##### 代码
```java
    /**
     * 重复多次写入
     * <li>1. 创建excel对应的实体对象 参照{@link ComplexHeadData}
     * <li>2. 使用{@link ExcelProperty}注解指定复杂的头
     * <li>3. 直接调用二次写入即可
     */
    @Test
    public void repeatedWrite() {
        String fileName = TestFileUtil.getPath() + "repeatedWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去读
        ExcelWriter excelWriter = EasyExcelFactory.write(fileName, DemoData.class).build();
        WriteSheet writeSheet = EasyExcelFactory.writerSheet("模板").build();
        // 第一次写入会创建头
        excelWriter.write(data(), writeSheet);
        // 第二次写入会在上一次写入的最后一行后面写入
        excelWriter.write(data(), writeSheet);
        /// 千万别忘记finish 会帮忙关闭流
        excelWriter.finish();
    }
```

### <span id="converterWrite" />日期、数字或者自定义格式转换
##### excel示例
![img](img/readme/quickstart/write/converterWrite.png)
##### 对象
```java
@Data
public class ConverterData {
    /**
     * 我想所有的 字符串起前面加上"自定义："三个字
     */
    @ExcelProperty(value = "字符串标题", converter = CustomStringStringConverter.class)
    private String string;
    /**
     * 我想写到excel 用年月日的格式
     */
    @DateTimeFormat("yyyy年MM月dd日HH时mm分ss秒")
    @ExcelProperty("日期标题")
    private Date date;
    /**
     * 我想写到excel 用百分比表示
     */
    @NumberFormat("#.##%")
    @ExcelProperty(value = "数字标题")
    private Double doubleData;
}
```
##### 代码
```java
    /**
     * 日期、数字或者自定义格式转换
     * <li>1. 创建excel对应的实体对象 参照{@link ConverterData}
     * <li>2. 使用{@link ExcelProperty}配合使用注解{@link DateTimeFormat}、{@link NumberFormat}或者自定义注解
     * <li>3. 直接写即可
     */
    @Test
    public void converterWrite() {
        String fileName = TestFileUtil.getPath() + "converterWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcelFactory.write(fileName, ConverterData.class).sheet("模板").doWrite(data());
    }
```

### <span id="templateWrite" />根据模板写入
##### 模板excel示例
参照：[模板excel示例](#simpleReadExcel)
##### excel示例
![img](img/readme/quickstart/write/templateWrite.png)
##### 对象
参照：[对象](#simpleWriteObject)
##### 代码
```java
    /**
     * 根据模板写入
     * <li>1. 创建excel对应的实体对象 参照{@link IndexData}
     * <li>2. 使用{@link ExcelProperty}注解指定写入的列
     * <li>3. 使用withTemplate 读取模板
     * <li>4. 直接写即可
     */
    @Test
    public void templateWrite() {
        String templateFileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        String fileName = TestFileUtil.getPath() + "templateWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcelFactory.write(fileName, DemoData.class).withTemplate(templateFileName).sheet().doWrite(data());
    }
```

### <span id="widthAndHeightWrite" />列宽、行高
##### excel示例
![img](img/readme/quickstart/write/widthAndHeightWrite.png)
##### 对象
````java
@Data
@ContentRowHeight(10)
@HeadRowHeight(20)
@ColumnWidth(25)
public class WidthAndHeightData {
    @ExcelProperty("字符串标题")
    private String string;
    @ExcelProperty("日期标题")
    private Date date;
    /**
     * 宽度为50
     */
    @ColumnWidth(50)
    @ExcelProperty("数字标题")
    private Double doubleData;
}
````
##### 代码
```java
    /**
     * 列宽、行高
     * <li>1. 创建excel对应的实体对象 参照{@link WidthAndHeightData}
     * <li>2. 使用注解{@link ColumnWidth}、{@link HeadRowHeight}、{@link ContentRowHeight}指定宽度或高度
     * <li>3. 直接写即可
     */
    @Test
    public void widthAndHeightWrite() {
        String fileName = TestFileUtil.getPath() + "widthAndHeightWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcelFactory.write(fileName, WidthAndHeightData.class).sheet("模板").doWrite(data());
    }
```

### <span id="styleWrite" />自定义样式
##### excel示例
![img](img/readme/quickstart/write/styleWrite.png)
##### 对象
参照：[对象](#simpleWriteObject)
##### 代码
```java
    /**
     * 自定义样式
     * <li>1. 创建excel对应的实体对象 参照{@link DemoData}
     * <li>2. 创建一个style策略 并注册
     * <li>3. 直接写即可
     */
    @Test
    public void styleWrite() {
        String fileName = TestFileUtil.getPath() + "styleWrite" + System.currentTimeMillis() + ".xlsx";
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景设置为红色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short)20);
        headWriteCellStyle.setWriteFont(headWriteFont);
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了 FillPatternType所以可以不指定
        contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        // 背景绿色
        contentWriteCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short)20);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
            new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);

        // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcelFactory.write(fileName, DemoData.class).registerWriteHandler(horizontalCellStyleStrategy).sheet("模板")
            .doWrite(data());
    }
```

### <span id="mergeWrite" />合并单元格
##### excel示例
![img](img/readme/quickstart/write/mergeWrite.png)
##### 对象
参照：[对象](#simpleWriteObject)
##### 代码
```java
   /**
     * 合并单元格
     * <li>1. 创建excel对应的实体对象 参照{@link DemoData}
     * <li>2. 创建一个merge策略 并注册
     * <li>3. 直接写即可
     */
    @Test
    public void mergeWrite() {
        String fileName = TestFileUtil.getPath() + "mergeWrite" + System.currentTimeMillis() + ".xlsx";
        // 每隔2行会合并 把eachColumn 设置成 3 也就是我们数据的长度，所以就第一列会合并。当然其他合并策略也可以自己写
        LoopMergeStrategy loopMergeStrategy = new LoopMergeStrategy(2, 0);
        // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcelFactory.write(fileName, DemoData.class).registerWriteHandler(loopMergeStrategy).sheet("模板")
            .doWrite(data());
    }
```

### <span id="tableWrite" />使用table去写入
##### excel示例
![img](img/readme/quickstart/write/tableWrite.png)
##### 对象
参照：[对象](#simpleWriteObject)
##### 代码
```java
   /**
     * 使用table去写入
     * <li>1. 创建excel对应的实体对象 参照{@link DemoData}
     * <li>2. 然后写入table即可
     */
    @Test
    public void tableWrite() {
        String fileName = TestFileUtil.getPath() + "tableWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里直接写多个table的案例了，如果只有一个 也可以直一行代码搞定，参照其他案例
        // 这里 需要指定写用哪个class去读
        ExcelWriter excelWriter = EasyExcelFactory.write(fileName, DemoData.class).build();
        // 把sheet设置为不需要头 不然会输出sheet的头 这样看起来第一个table 就有2个头了
        WriteSheet writeSheet = EasyExcelFactory.writerSheet("模板").needHead(Boolean.FALSE).build();
        // 这里必须指定需要头，table 会继承sheet的配置，sheet配置了不需要，table 默认也是不需要
        WriteTable writeTable0 = EasyExcelFactory.writerTable(0).needHead(Boolean.TRUE).build();
        WriteTable writeTable1 = EasyExcelFactory.writerTable(1).needHead(Boolean.TRUE).build();
        // 第一次写入会创建头
        excelWriter.write(data(), writeSheet, writeTable0);
        // 第二次写如也会创建头，然后在第一次的后面写入数据
        excelWriter.write(data(), writeSheet, writeTable1);
        /// 千万别忘记finish 会帮忙关闭流
        excelWriter.finish();
    }
```

### <span id="webWrite" />web中的写
##### 示例代码
DEMO代码地址：[https://github.com/alibaba/easyexcel/blob/master/src/test/java/com/alibaba/easyexcel/test/demo/web/WebTest.java](/src/test/java/com/alibaba/easyexcel/test/demo/web/WebTest.java)
##### 对象
参照：[对象](#simpleWriteObject) 就是名称变了下
##### 代码
```java
   /**
     * 文件下载
     * <li>1. 创建excel对应的实体对象 参照{@link DownloadData}
     * <li>2. 设置返回的 参数
     * <li>3. 直接写，这里注意，finish的时候会自动关闭OutputStream,当然你外面再关闭流问题不大
     */
    @GetMapping("download")
    public void download(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=demo.xlsx");
        EasyExcelFactory.write(response.getOutputStream(), DownloadData.class).sheet("模板").doWrite(data());
    }
```
## 测试数据分析
![POI usermodel PK easyexcel(Excel 2003).png](http://ata2-img.cn-hangzhou.img-pub.aliyun-inc.com/02c4bfbbab99a649788523d04f84a42f.png)
![POI usermodel PK easyexcel(Excel 2007).png](http://ata2-img.cn-hangzhou.img-pub.aliyun-inc.com/f6a8a19ec959f0eb564e652de523fc9e.png)
![POI usermodel PK easyexcel(Excel 2003) (1).png](http://ata2-img.cn-hangzhou.img-pub.aliyun-inc.com/26888f7ea1cb8dc56db494926544edf7.png)
![POI usermodel PK easyexcel(Excel 2007) (1).png](http://ata2-img.cn-hangzhou.img-pub.aliyun-inc.com/4de1ac95bdfaa4b1870b224af4f4cb75.png)
从上面的性能测试可以看出easyexcel在解析耗时上比poiuserModel模式弱了一些。主要原因是我内部采用了反射做模型字段映射，中间我也加了cache，但感觉这点差距可以接受的。但在内存消耗上差别就比较明显了，easyexcel在后面文件再增大，内存消耗几乎不会增加了。但poi userModel就不一样了，简直就要爆掉了。想想一个excel解析200M，同时有20个人再用估计一台机器就挂了。

