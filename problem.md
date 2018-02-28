# 常见问题汇总

## 1、系统环境变量缺失或JDK版本不支持

```
java.lang.NullPointerException
	at sun.awt.FontConfiguration.getVersion(FontConfiguration.java:1264)
	at sun.awt.FontConfiguration.readFontConfigFile(FontConfiguration.java:219)
	at sun.awt.FontConfiguration.init(FontConfiguration.java:107)
	at sun.awt.X11FontManager.createFontConfiguration(X11FontManager.java:774)
	at sun.font.SunFontManager$2.run(SunFontManager.java:431)
	at java.security.AccessController.doPrivileged(Native Method)
	at sun.font.SunFontManager.<init>(SunFontManager.java:376)
	at sun.awt.FcFontManager.<init>(FcFontManager.java:35)
	at sun.awt.X11FontManager.<init>(X11FontManager.java:57)
	at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
	at sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)
	at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
	at java.lang.reflect.Constructor.newInstance(Constructor.java:423)
	at java.lang.Class.newInstance(Class.java:442)
	at sun.font.FontManagerFactory$1.run(FontManagerFactory.java:83)
	at java.security.AccessController.doPrivileged(Native Method)
	at sun.font.FontManagerFactory.getInstance(FontManagerFactory.java:74)
	at java.awt.Font.getFont2D(Font.java:495)
	at java.awt.Font.canDisplayUpTo(Font.java:2080)
	at java.awt.font.TextLayout.singleFont(TextLayout.java:470)
	at java.awt.font.TextLayout.<init>(TextLayout.java:531)
	at org.apache.poi.ss.util.SheetUtil.getDefaultCharWidth(SheetUtil.java:275)
	at org.apache.poi.xssf.streaming.AutoSizeColumnTracker.<init>(AutoSizeColumnTracker.java:117)
	at org.apache.poi.xssf.streaming.SXSSFSheet.<init>(SXSSFSheet.java:79)
	at org.apache.poi.xssf.streaming.SXSSFWorkbook.createAndRegisterSXSSFSheet(SXSSFWorkbook.java:656)
	at org.apache.poi.xssf.streaming.SXSSFWorkbook.createSheet(SXSSFWorkbook.java:677)
	at org.apache.poi.xssf.streaming.SXSSFWorkbook.createSheet(SXSSFWorkbook.java:83)
	at com.alibaba.excel.write.context.GenerateContextImpl.buildCurrentSheet(GenerateContextImpl.java:93)
	at com.alibaba.excel.write.ExcelBuilderImpl.addContent(ExcelBuilderImpl.java:53)
	at com.alibaba.excel.ExcelWriter.write(ExcelWriter.java:44)
```

### 解决方法

该异常由于自己的环境变量缺少swing需要的字体配置，检查自己应用是否有配置-Djava.awt.headless=true，如果没有请加上该系统参数，可以解决问题。如果仍旧不行，在dockerfile中增加字体安装命令：
![粘贴图片.png](http://ata2-img.cn-hangzhou.img-pub.aliyun-inc.com/a857edfbc8199db7bb35b9e99f1f57d5.png)
参考：
https://lark.alipay.com/aone355606/gfqllg/ulptif
https://stackoverflow.com/questions/30626136/cannot-load-font-in-jre-8 http://www.jianshu.com/p/c05b5fc71bd0
## 2、xls格式错用xlsx方式解析

```
org.apache.poi.openxml4j.exceptions.OLE2NotOfficeXmlFileException: The supplied data appears to be in the OLE2 Format. You are calling the part of POI that deals with OOXML (Office Open XML) Documents. You need to call a different part of POI to process this data (eg HSSF instead of XSSF)
	at org.apache.poi.openxml4j.opc.internal.ZipHelper.verifyZipHeader(ZipHelper.java:172)
	at org.apache.poi.openxml4j.opc.internal.ZipHelper.openZipStream(ZipHelper.java:229)
	at org.apache.poi.openxml4j.opc.ZipPackage.<init>(ZipPackage.java:97)
	at org.apache.poi.openxml4j.opc.OPCPackage.open(OPCPackage.java:342)
	at com.alibaba.excel.read.v07.XlsxSaxAnalyser.<init>(XlsxSaxAnalyser.java:46)
	at com.alibaba.excel.read.ExcelAnalyserImpl.getSaxAnalyser(ExcelAnalyserImpl.java:30)
	at com.alibaba.excel.read.ExcelAnalyserImpl.analysis(ExcelAnalyserImpl.java:51)
	at com.alibaba.excel.ExcelReader.read(ExcelReader.java:55)
	at read.v07.Read2007Xlsx.noModel(Read2007Xlsx.java:42)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:50)
	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
	at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:47)
	at mockit.integration.junit4.internal.BlockJUnit4ClassRunnerDecorator.executeTest(BlockJUnit4ClassRunnerDecorator.java:126)
	at mockit.integration.junit4.internal.BlockJUnit4ClassRunnerDecorator.invokeExplosively(BlockJUnit4ClassRunnerDecorator.java:104)
	at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java)
	at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
	at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:325)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:78)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:57)
	at org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)
	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)
	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
	at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)
	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)
	at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
	at org.junit.runner.JUnitCore.run(JUnitCore.java:137)
	at com.intellij.junit4.JUnit4IdeaTestRunner.startRunnerWithArgs(JUnit4IdeaTestRunner.java:68)
	at com.intellij.rt.execution.junit.IdeaTestRunner$Repeater.startRunnerWithArgs(IdeaTestRunner.java:51)
	at com.intellij.rt.execution.junit.JUnitStarter.prepareStreamsAndStart(JUnitStarter.java:237)
	at com.intellij.rt.execution.junit.JUnitStarter.main(JUnitStarter.java:70)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at com.intellij.rt.execution.application.AppMain.main(AppMain.java:147)
``` 

### 解决方法

该异常时由于03版的xls,文件用07版的方式做解析的报错，请检查excelType是否设置错误。或者是不是手动去修改了excel文件名后缀的xls为xlsx。

## 3、xlsx错用xls解析

``` 
org.apache.poi.poifs.filesystem.OfficeXmlFileException: The supplied data appears to be in the Office 2007+ XML. You are calling the part of POI that deals with OLE2 Office Documents. You need to call a different part of POI to process this data (eg XSSF instead of HSSF)
	at org.apache.poi.poifs.storage.HeaderBlock.<init>(HeaderBlock.java:152)
	at org.apache.poi.poifs.storage.HeaderBlock.<init>(HeaderBlock.java:140)
	at org.apache.poi.poifs.filesystem.NPOIFSFileSystem.<init>(NPOIFSFileSystem.java:302)
	at org.apache.poi.poifs.filesystem.POIFSFileSystem.<init>(POIFSFileSystem.java:87)
	at com.alibaba.excel.read.v03.XlsSaxAnalyser.<init>(XlsSaxAnalyser.java:55)
	at com.alibaba.excel.read.ExcelAnalyserImpl.getSaxAnalyser(ExcelAnalyserImpl.java:27)
	at com.alibaba.excel.read.ExcelAnalyserImpl.analysis(ExcelAnalyserImpl.java:51)
	at com.alibaba.excel.ExcelReader.read(ExcelReader.java:55)
	at read.v03.XLS2003FunctionTest.testExcel2003NoModel(XLS2003FunctionTest.java:31)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at mockit.integration.junit3.internal.JUnitTestCaseDecorator.runTest(JUnitTestCaseDecorator.java:142)
	at mockit.integration.junit3.internal.JUnitTestCaseDecorator.originalRunBare(JUnitTestCaseDecorator.java:102)
	at mockit.integration.junit3.internal.JUnitTestCaseDecorator.runBare(JUnitTestCaseDecorator.java:87)
``` 
原理和原因大致同上