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
* 降级poi为3.1.7 兼容jdk6

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