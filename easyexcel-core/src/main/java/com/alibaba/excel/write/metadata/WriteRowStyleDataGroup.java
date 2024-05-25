package com.alibaba.excel.write.metadata;

import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Styled data group
 * @author bigNoseCat
 */
@Data
public class WriteRowStyleDataGroup {

    List<Object> data = new ArrayList<>();

    WriteCellStyle writeCellStyle;


    public WriteRowStyleDataGroup(Collection<Object> data, WriteCellStyle writeCellStyle) {
        if (data == null) {
            return;
        }
        this.data.addAll(data);
        this.writeCellStyle = writeCellStyle;
    }
}


