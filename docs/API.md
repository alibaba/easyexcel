# 详细参数介绍
## 关于常见类解析
* EasyExcel 入口类，用于构建开始各种操作
* ExcelReaderBuilder ExcelWriterBuilder 构建出一个 ReadWorkbook WriteWorkbook，可以理解成一个excel对象，一个excel只要构建一个
* ExcelReaderSheetBuilder ExcelWriterSheetBuilder 构建出一个 ReadSheet WriteSheet对象，可以理解成excel里面的一页,每一页都要构建一个
* ReadListener 在每一行读取完毕后都会调用ReadListener来处理数据
* WriteHandler 在每一个操作包括创建单元格、创建表格等都会调用WriteHandler来处理数据
* 所有配置都是继承的，Workbook的配置会被Sheet继承，所以在用EasyExcel设置参数的时候，在EasyExcel...sheet()方法之前作用域是整个sheet,之后针对单个sheet
## 读
### 注解
* `ExcelProperty` 指定当前字段对应excel中的那一列。可以根据名字或者Index去匹配。当然也可以不写，默认第一个字段就是index=0，以此类推。千万注意，要么全部不写，要么全部用index，要么全部用名字去匹配。千万别三个混着用，除非你非常了解源代码中三个混着用怎么去排序的。
* `ExcelIgnore` 默认所有字段都会和excel去匹配，加了这个注解会忽略该字段
* `DateTimeFormat` 日期转换，用`String`去接收excel日期格式的数据会调用这个注解。里面的`value`参照`java.text.SimpleDateFormat`
* `NumberFormat` 数字转换，用`String`去接收excel数字格式的数据会调用这个注解。里面的`value`参照`java.text.DecimalFormat`
* `ExcelIgnoreUnannotated` 默认不加`ExcelProperty` 的注解的都会参与读写，加了不会参与
### 参数
#### 通用参数
`ReadWorkbook`,`ReadSheet` 都会有的参数，如果为空，默认使用上级。
* `converter` 转换器，默认加载了很多转换器。也可以自定义。
* `readListener` 监听器，在读取数据的过程中会不断的调用监听器。
* `headRowNumber` 需要读的表格有几行头数据。默认有一行头，也就是认为第二行开始起为数据。
* `head`  与`clazz`二选一。读取文件头对应的列表，会根据列表匹配数据，建议使用class。
* `clazz` 与`head`二选一。读取文件的头对应的class，也可以使用注解。如果两个都不指定，则会读取全部数据。
* `autoTrim` 字符串、表头等数据自动trim
* `password` 读的时候是否需要使用密码
#### ReadWorkbook（理解成excel对象）参数
* `excelType` 当前excel的类型 默认会自动判断
* `inputStream` 与`file`二选一。读取文件的流，如果接收到的是流就只用，不用流建议使用`file`参数。因为使用了`inputStream` easyexcel会帮忙创建临时文件，最终还是`file`
* `file` 与`inputStream`二选一。读取文件的文件。
* `autoCloseStream` 自动关闭流。
* `readCache` 默认小于5M用 内存，超过5M会使用 `EhCache`,这里不建议使用这个参数。
#### ReadSheet（就是excel的一个Sheet）参数
* `sheetNo` 需要读取Sheet的编码，建议使用这个来指定读取哪个Sheet
* `sheetName` 根据名字去匹配Sheet,excel 2003不支持根据名字去匹配
## 写
### 注解
* `ExcelProperty` index 指定写到第几列，默认根据成员变量排序。`value`指定写入的名称，默认成员变量的名字，多个`value`可以参照快速开始中的复杂头
* `ExcelIgnore` 默认所有字段都会写入excel，这个注解会忽略这个字段
* `DateTimeFormat` 日期转换，将`Date`写到excel会调用这个注解。里面的`value`参照`java.text.SimpleDateFormat`
* `NumberFormat` 数字转换，用`Number`写excel会调用这个注解。里面的`value`参照`java.text.DecimalFormat`
* `ExcelIgnoreUnannotated` 默认不加`ExcelProperty` 的注解的都会参与读写，加了不会参与
### 参数
#### 通用参数
`WriteWorkbook`,`WriteSheet` ,`WriteTable`都会有的参数，如果为空，默认使用上级。
* `converter` 转换器，默认加载了很多转换器。也可以自定义。
* `writeHandler` 写的处理器。可以实现`WorkbookWriteHandler`,`SheetWriteHandler`,`RowWriteHandler`,`CellWriteHandler`，在写入excel的不同阶段会调用
* `relativeHeadRowIndex` 距离多少行后开始。也就是开头空几行
* `needHead` 是否导出头
* `head`  与`clazz`二选一。写入文件的头列表，建议使用class。
* `clazz` 与`head`二选一。写入文件的头对应的class，也可以使用注解。
* `autoTrim` 字符串、表头等数据自动trim
#### WriteWorkbook（理解成excel对象）参数
* `excelType` 当前excel的类型 默认`xlsx`
* `outputStream` 与`file`二选一。写入文件的流
* `file` 与`outputStream`二选一。写入的文件
* `templateInputStream` 模板的文件流
* `templateFile` 模板文件
* `autoCloseStream` 自动关闭流。
* `password` 写的时候是否需要使用密码
* `useDefaultStyle` 写的时候是否是使用默认头
#### WriteSheet（就是excel的一个Sheet）参数
* `sheetNo` 需要写入的编码。默认0
* `sheetName` 需要些的Sheet名称，默认同`sheetNo`
#### WriteTable（就把excel的一个Sheet,一块区域看一个table）参数
* `tableNo` 需要写入的编码。默认0