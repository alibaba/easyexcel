package com.alibaba.easyexcel.test.temp.issue1641;
import com.alibaba.easyexcel.test.demo.write.MultiImageData;
import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import lombok.Data;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Data
@ContentRowHeight(100)
@ColumnWidth(30)
public class ImageData {
    private File file;

}
