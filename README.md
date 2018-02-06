# JAVA解析Excel工具easyexcel

Java解析、生成Excel比较有名的框架有Apache poi、jxl。但他们都存在一个严重的问题就是非常的耗内存，poi有一套SAX模式的API可以一定程度的解决一些内存溢出的问题，但POI还是有一些缺陷，比如07版Excel解压缩以及解压后存储都是在内存中完成的，内存消耗依然很大。easyexcel重写了poi对07版Excel的解析，能够原本一个3M的excel用POI sax依然需要100M左右内存降低到KB级别，并且再大的excel不会出现内存溢出，03版依赖POI的sax模式。在上层做了模型转换的封装，让使用者更加简单方便

# 相关文档

* [关于软件](/abouteasyexcel.md)
* [快速使用](/quickstart.md)
* [常见问题](/problem.md)
* [更新记事](/update.md)
* [English-README](/easyexcel_en.md)

# 二方包 

```
<dependency>
    <groupId>com.alibaba.shared</groupId>
    <artifactId>easyexcel</artifactId>
    <version>{latestVersion}</version>
</dependency>
```

# 最新版本
## VERSION : 1.2.16

# 维护者
姬朋飞（玉霄）

# 快速开始

## 读Excel

```
public void noModelMultipleSheet() {
        InputStream inputStream = getInputStream("2007NoModelMultipleSheet.xlsx");
        try {
            ExcelReader reader = new ExcelReader(inputStream, ExcelTypeEnum.XLSX, null,
                new AnalysisEventListener<List<String>>() {
                    @Override
                    public void invoke(List<String> object, AnalysisContext context) {
                        System.out.println(
                            "当前sheet:" + context.getCurrentSheet().getSheetNo() + " 当前行：" + context.getCurrentRowNum()
                                + " data:" + object);
                    }
                    @Override
                    public void doAfterAllAnalysed(AnalysisContext context) {

                    }
                });

            reader.read();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
```
## 写Excel

```
@Test
public void test1() throws FileNotFoundException {
        OutputStream out = new FileOutputStream("/Users/jipengfei/78.xlsx");
        try {
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            //写第一个sheet, sheet1  数据全是List<String> 无模型映射关系
            Sheet sheet1 = new Sheet(1, 0,ExcelPropertyIndexModel.class);
            writer.write(getData(), sheet1);
            writer.finish();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
```
# 联系我们

有问题阿里同事可以通过钉钉找到我，阿里外同学可以通过git留言。其他技术非技术相关的也欢迎一起探讨。