# 3.0.5
* 修复`ReadListener` 转换异常不抛出的问题

# 3.0.4
* 调整读写默认大小，防止大批量写的时候可能会full gc
* `fill`的情况新增 `afterRowDispose`事件

# 3.0.3
* 修复`HeadStyle`无效的bug

# 3.0.2
* 大幅提升读写性能
* 修复列宽注解没用的bug [Issue #2151](https://github.com/alibaba/easyexcel/issues/2151)
* 修复`CellData`接收失败的的bug [Issue #2147](https://github.com/alibaba/easyexcel/issues/2147)


# 3.0.1
* 升级到正式版
* 修复填充样式可能丢失的问题 [Issue #2124](https://github.com/alibaba/easyexcel/issues/2124)
* 修复填充数据为空 可能NPE的bug
* 修复填充样式可能不生效bug
* 修复样式可能超过最大限制的bug
* 修复写入过慢的bug


# 3.0.0-beta3

* 修复导出浮点型数据可能精度异常的bug

# 3.0.0-beta2

* 优化写入样式

# 3.0.0-beta1

* 升级jdk8 不再支持jdk6 jdk7
* 升级poi 到 4.1.2
* 升级cglib 到 3.3.0
* 升级ehcache 到 3.8.1
* 支持非驼峰的字段读写
* 支持csv的读&写
* 修复`CellData`可能不返回行列号 [Issue #1832](https://github.com/alibaba/easyexcel/issues/1832)
* 优化读取性能
* 修复部分情况下不抛出异常
* 07版在导出的时候会导出 行数 [Issue #1282](https://github.com/alibaba/easyexcel/issues/1282)
* 修复没有样式的情况下空指针异常 [Issue #1738](https://github.com/alibaba/easyexcel/issues/1738)
* 修改异常抛出逻辑 [Issue #1618](https://github.com/alibaba/easyexcel/issues/1618)
* 兼容一些非官方excel的情况 [Issue #1527](https://github.com/alibaba/easyexcel/issues/1527)
* 修改读的关闭流无效 [Issue #1840](https://github.com/alibaba/easyexcel/issues/1840)
* 写入支持Collection [Issue #1834](https://github.com/alibaba/easyexcel/issues/1834)
* `Converter`支持null转换 [Issue #1776](https://github.com/alibaba/easyexcel/issues/1776)
* cglib 新增命名策略，防止和`spring`的冲突  [Issue #2064](https://github.com/alibaba/easyexcel/issues/2064)
* 修改填充可能填充错误的bug  [Issue #2035](https://github.com/alibaba/easyexcel/issues/2035)
* 修复无对象读 返回map的size可能会头的size不一致 [Issue #2014](https://github.com/alibaba/easyexcel/issues/2014)
* 修复合并头可能异常的bug [Issue #1662](https://github.com/alibaba/easyexcel/issues/1662)
* 修复填充调用横向样式策略报错 [Issue #1651](https://github.com/alibaba/easyexcel/issues/1651)
* 修复不自动行高的问题 [Issue #1869](https://github.com/alibaba/easyexcel/issues/1869)
* 新增头的非空校验 [Issue #1765](https://github.com/alibaba/easyexcel/issues/1765)
* 修复某些特殊的excel读取失败的问题 [Issue #1595](https://github.com/alibaba/easyexcel/issues/1595)
* 修复不创建对象写入数据异常 [Issue #1702](https://github.com/alibaba/easyexcel/issues/1702)
* 修复头和数据对象不一致会覆盖的问题 [Issue #1870](https://github.com/alibaba/easyexcel/issues/1870)
* 修复忽略字段后可能排序不一致的问题
* 修改填充时，无法使用生成的模板 [Issue #1552](https://github.com/alibaba/easyexcel/issues/1552)
* 修改填充可以不自动继承样式 [Issue #1710](https://github.com/alibaba/easyexcel/issues/1710)
* 修复填充数据不能为空的问题 [Issue #1703](https://github.com/alibaba/easyexcel/issues/1703)
* 新增部分jdk8特性

# 2.2.11

* 修复有些xlsx解析失败的bug [Issue #1595](https://github.com/alibaba/easyexcel/issues/1595)

# 2.2.10

* 修复读取的时候用string接收数字 可能四舍五入不一致的bug

# 2.2.9

* 修复读取的时候用string接收数字 可能四舍五入不一致的bug

# 2.2.8

* 兼容07在特殊的excel的情况下，读取数据异常

# 2.2.7

* 修改07在特殊情况下用`String`接收数字会丢小数位的bug

# 2.2.6

* 修改跳着读取03版本空指针bug

# 2.2.5

* `ExcelProperty`新增`order` 用于排序
* 修复导出指定`index`会导致空行的bug

# 2.2.4

* 撤销删除`AbstractMergeStrategy`
* 修改默认用String读取数字不使用科学计数法 通过`useScientificFormat`修改
* 修复07版仅有样式的空行 默认不忽略的bug
* 写入`sheet`不设置`index`和`name`默认不为0的问题
* 修复多个`sheet`不按照顺序写入 会乱序的bug [Issue #1332](https://github.com/alibaba/easyexcel/issues/1332)
* 修改head是List时，内容单元格的样式不生效 [Issue #1339](https://github.com/alibaba/easyexcel/issues/1339)
* 修复xls仅公式行 不读取的bug [Issue #1324](https://github.com/alibaba/easyexcel/issues/1324)
* 修复xls直接读取第2页 `NPE` 的bug [Issue #1280](https://github.com/alibaba/easyexcel/issues/1280)
* 修复填充的时候，最后一行中间有空行会创建失败的bug
* 修复`includeColumnIndexes`不包含第列 会无法导出数据的bug [Issue #1346](https://github.com/alibaba/easyexcel/issues/1346)
* 修复`@NumberFormat`注解转换double时可能会丢失精度 [Issue #1306](https://github.com/alibaba/easyexcel/issues/1306)

# 2.2.3

* 修改填充数据空数据的bug  [Issue #1274](https://github.com/alibaba/easyexcel/issues/1274)
* 回退自定义转换器入参为空

# 2.2.2

* 修改`sheet`事件未调用的bug
* 修复复杂表头不是`index=0`开始 合并异常的bug [Issue #1322](https://github.com/alibaba/easyexcel/issues/1322)

# 2.2.1

* 发布正式版
* 修复第一行为空不会调用`invokeHeadMap`的bug [Issue #993](https://github.com/alibaba/easyexcel/issues/993)
* 当类的属性没有按照ExcelProperty的属性index顺序排序的时候，写数据出现错乱 [Issue #1046](https://github.com/alibaba/easyexcel/issues/1046)
* 新增支持自定义转换器 入参可以为空 实现`NullableObjectConverter` 即可  [Issue #1084](https://github.com/alibaba/easyexcel/issues/1084)
* 修复xls丢失结束标记的情况下 会漏读最后一行
* 修复填充的时候 多次`forceNewRow` 空指针的bug [Issue #1201](https://github.com/alibaba/easyexcel/issues/1201)
* 修复`table`、`sheet`中创建的拦截器不执行`workbook`事件的bug [Issue #1202](https://github.com/alibaba/easyexcel/issues/1202)

# 2.2.0-beta2

* 修复最长匹配策略不同表格会有影响的bug [Issue #1010](https://github.com/alibaba/easyexcel/issues/1010)
* `LinkedList`写入的性能问题 #1121
* 修复在某些情况下可能出现不必要的`warn`日志

# 2.2.0-beta1

* 重写主流程，代码更加优雅
* 修复用String接收日期、数字和excel显示不一致的bug(不是完美修复，但是大部分情况已经兼容)
* 降低Ehcache版本 3.7.1(jkd7) -> 3.4.0(jdk6)
* 修复xls 用Map接收时多次接收会是同一个对象的bug
* 修复浮点型数据导入到excel 会丢失精度的bug
* 新增支持读取批注、超链接、合并单元格
* 如果是`RuntimeException`则不再封装对象
* 新增`CellData`可以获取行列号
* 新增样式注解
* 新增合并单元格注解
* 提升合并策略效率
* 兼容部分比较特殊的excel
* 同时传入了`List<List<String>>`和`class`的head,会通过index去匹配注解
* 修复读取转换器的并发问题
* 填充支持多个List对象

# 2.1.7

* 修复使用1+版本的写法，第1条开始读修改为第0条开始读

# 2.1.6

* 修复写入只有`sheetName`会抛异常

# 2.1.5

* 修复部分xlsx没有行号读取异常
* 填充时候支持根据`sheetName`定位`sheet`

# 2.1.4

* 新增参数`useDefaultListener` 可以排除默认对象转换

# 2.1.3

* 每个java进程单独创建一个缓存目录 [Issue #813](https://github.com/alibaba/easyexcel/issues/813)
* 统一修改合并为unsafe，提高大量数据导出的合并的效率
* 修改merge返回参数`relativeRowIndex`为`Integer`
* 新增参数`automaticMergeHead` 可以设置不自动合并头 [Issue #822](https://github.com/alibaba/easyexcel/issues/822)
* 新增参数`xlsxSAXParserFactoryName` 可以指定`SAXParserFactory`
* 修复合并策略 空指针的问题
* `SimpleColumnWidthStyleStrategy` 新增 参数`columnIndex`  [Issue #806](https://github.com/alibaba/easyexcel/issues/806)

# 2.1.2

* 修复强制创建新行填充，只有一行数据会未填充的bug

# 2.1.1

* 发布正式版
* 修改map返回为LinkedHashMap
* 修改同步读取返回对象支持泛型
* 修复03版不能直接读取第二个sheet的bug [Issue #772](https://github.com/alibaba/easyexcel/issues/772)
* 新增支持图片导出用URL [Issue #774](https://github.com/alibaba/easyexcel/issues/774)
* 加入多次关闭判断，防止多次关闭异常
* 加入根据模板自动识别导出的excel类型
* 修改默认失败后，不再往文件流写入数据。通过参数`writeExcelOnException` 参数设置异常了也要写入前面的数据。
* 循环合并策略支持一次性合并多列
* `ExcelDataConvertException`返回新增具体报错的数据
* 加入解析class缓存
* 修复填充的时候行高不复制的Bug [Issue #780](https://github.com/alibaba/easyexcel/issues/780)
* 修复03版无法获取大概总行数的bug

# 2.1.0-beta4

* 修改最长匹配策略会空指针的bug [Issue #747](https://github.com/alibaba/easyexcel/issues/747)
* 修改afterRowDispose错误 [Issue #751](https://github.com/alibaba/easyexcel/issues/751)
* 修复多个头的情况下会读取数据为空

# 2.1.0-beta3

* 支持强行指定在内存处理，以支持备注、RichTextString等的写入
* 修复关闭流失败，可能会不删除临时文件的问题
* 支持根据参数自定义导出列
* 修改最长匹配策略的最大长度 [Issue #734](https://github.com/alibaba/easyexcel/issues/734)
* 修复策略头未生效的bug [Issue #735](https://github.com/alibaba/easyexcel/issues/735)
* 修复填充的时候有数字会异常

# 2.1.0-beta2

* 修改模板通过流创建报错的bug
* 修复空数据未替换掉的bug
* 修复空模板会空一行的bug

# 2.1.0-beta1

* 新增支持导入、导出支持公式
* 新增支持读取单元格类型、写入指定单元格类型
* 支持通过模板填充数据
* 新增写支持 禁用头样式 `useDefaultStyle`
* 用map读取数据 空的单元格也会有个 null的数据
* 转换报错 能获取到对应的行号和列号
* 优化读取全部sheet方案
* 新增注解`ExcelIgnoreUnannotated` 支持忽略未加`ExcelProperty`注解的字段
* 支持导出加密 [Issue #361](https://github.com/alibaba/easyexcel/issues/361)
* 支持导入加密 [Issue #295](https://github.com/alibaba/easyexcel/issues/295)

# 2.0.5

* 优化07版超大文件读取方案
* 支持自己设置超大文件读取参数
* 读取xlsx会改变修改时间的bug [Issue #574](https://github.com/alibaba/easyexcel/issues/574)
* 默认读取忽略空行 根据参数ignoreEmptyRow参数设置

# 2.0.4

* 修复07版整个excel仅存在数字时会出现的NPE
* 修复03版 用String接收电话会出现科学计数法的问题

# 2.0.3

* 修复重大bug 在07版读取文件的时候 小概率导致数字部分丢失

# 2.0.2

* 修复xls无法获取sheetList的bug [Issue #621](https://github.com/alibaba/easyexcel/issues/621)
* 修复监听器转换异常会重复提示的bug

# 2.0.1

* 降级poi为3.17 兼容jdk6

# 2.0.0

* 修复当cell为空可能会抛出空指针的bug
* 修复电话等长数字可能出现科学计数法的问题 [Issue #583](https://github.com/alibaba/easyexcel/issues/583)
* 升级为正式版

# 2.0.0-beta6

* 修复空行读取空指针异常
* 修复写入指定头为List<List<String>>,但是数据用List<Class>导致的空指针

# 2.0.0-beta5

* 修复在读取值的时候读取了额外值导致数据转换异常

# 2.0.0-beta4

* 修改在传入List<List<Object>>判断行数错误 [Issue #526](https://github.com/alibaba/easyexcel/issues/526)
* 修复在mac 2016 2017导出的excel 可能存在多余字段的问题
* 修复03版 读取无法指定sheet的问题 [Issue #533](https://github.com/alibaba/easyexcel/issues/533)

# 2.0.0-beta3

* 导出完成移除临时目录 [Issue #386](https://github.com/alibaba/easyexcel/issues/386)
* 新增读取返回头数据

# 2.0.0-beta2

* 加速gc回收 [Issue #511](https://github.com/alibaba/easyexcel/issues/511)
* 修改空字符串读取可能读取上个字段的数据的bug
* 修改换行数据无法读取的bug [Issue #521](https://github.com/alibaba/easyexcel/issues/521)
* 修复在空字符串的时候 格式转换异常 [Issue #520](https://github.com/alibaba/easyexcel/issues/520)

# 2.0.0-beta1

* 优化读写逻辑
* 优化读写对外接口
* 加入转换器，方便格式转换
* 极大优化读大文件的内存和效率
* sheetNo 改成0开始
* 读支持指定列名
* 升级poi 到4.0.1

# 1.2.4

修复read()方法存在的bug

# 1.2.1

修复POI在大并发情况下创建临时目录失败的bug

# 1.0.9

修复excel超过16列被覆盖的问题，修复数据只有一行时候无法透传的bug。

# 1.0.8

如果整行excel数据全部为空，则不解析返回。完善多sheet的解析。

# 1.0.6

增加@ExcelColumnNum,修复字符串前后空白，增加过滤功能。

# 1.0.5

优化类型转换的性能。

# 1.0.4

修复日期类型转换时候数字问题。基础模型支持字段类型int,long,double,boolean,date,string

# 1.0.3

修复无@ExcelProperty标注的多余字段时候报错。

# 1.0.2

修复拿到一行数据后，存到list中，但最后处理时候变为空的bug。

# 1.0.1

完善测试用例，防止歧义，模型字段映射不上时候有抛异常，改为提醒。