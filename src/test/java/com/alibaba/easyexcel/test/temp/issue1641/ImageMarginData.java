package com.alibaba.easyexcel.test.temp.issue1641;

import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import lombok.Data;


import java.io.File;


@Data
@ContentRowHeight(100)
@ColumnWidth(30)
public class ImageMarginData {
    private File file;

}
