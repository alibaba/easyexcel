package com.alibaba.easyexcel.test.temp.issue1624;

import com.alibaba.excel.EasyExcelFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

/**
 * @author Shilong Li (Lori)
 * @project easyexcel
 * @filename Issue1624
 * @date 2021/3/11 01:08
 */
public class Issue1624 {
    final String fileName = "/src/test/java/com/alibaba/easyexcel/test/temp/issue1624/Issue1624.xlsx";
    InputStream inputStream;
    List<Issue1624Class> dataList;
    @Before
    public void init() throws FileNotFoundException {
        inputStream = new FileInputStream(fileName);
        dataList = EasyExcelFactory.read(inputStream).head(Issue1624Class.class).sheet(0).doReadSync();
    }

    @Test
    public void testColumn1() {
        Assert.assertEquals("1",dataList.get(0).getT1());
    }
    @Test
    public void testColumn2() {
        Assert.assertEquals("1 3/4",dataList.get(0).getT2());
    }
    @Test
    public void testColumn3() {
        Assert.assertEquals("4309401934184",dataList.get(0).getT3());
    }
    @Test
    public void testColumn4(){
        Assert.assertEquals("4309401934184 3/4",dataList.get(0).getT4());
    }
    @Test
    public void testColumn5(){
        Assert.assertEquals("4309401934184",dataList.get(0).getT5());
    }
    @Test
    public void testColumn6(){
        Assert.assertEquals("4309401934184.00",dataList.get(0).getT6());
    }
}
