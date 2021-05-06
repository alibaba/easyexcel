package com.alibaba.excel.metadata.data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * wirte cell data
 *
 * @author Jiaju Zhuang
 */
@Data
@NoArgsConstructor
public class WriteCellData<T> extends CellData<T> {
    /**
     * Support only when writing.{@link CellDataTypeEnum#DATE}
     */
    private Date dateValue;
    /**
     * rich text.{@link CellDataTypeEnum#RICH_TEXT_STRING}
     */
    private RichTextStringData richTextStringDataValue;
    /**
     * image
     */
    private List<ImageData> imageDataList;
    /**
     * comment
     */
    private CommentData commentData;
    /**
     * hyper link
     */
    private HyperlinkData hyperlinkData;
    /**
     * sytle
     */
    private WriteCellStyle writeCellStyle;

    public WriteCellData(String stringValue) {
        this(CellDataTypeEnum.STRING, stringValue);
    }

    public WriteCellData(CellDataTypeEnum type) {
        super();
        setType(type);
    }

    public WriteCellData(CellDataTypeEnum type, String stringValue) {
        super();
        if (type != CellDataTypeEnum.STRING && type != CellDataTypeEnum.ERROR) {
            throw new IllegalArgumentException("Only support CellDataTypeEnum.STRING and  CellDataTypeEnum.ERROR");
        }
        if (stringValue == null) {
            throw new IllegalArgumentException("StringValue can not be null");
        }
        setType(type);
        setStringValue(stringValue);
    }

    public WriteCellData(BigDecimal numberValue) {
        super();
        if (numberValue == null) {
            throw new IllegalArgumentException("DoubleValue can not be null");
        }
        setType(CellDataTypeEnum.NUMBER);
        setNumberValue(numberValue);
    }

    public WriteCellData(Boolean booleanValue) {
        super();
        if (booleanValue == null) {
            throw new IllegalArgumentException("BooleanValue can not be null");
        }
        setType(CellDataTypeEnum.BOOLEAN);
        setBooleanValue(booleanValue);
    }

    public WriteCellData(Date dateValue) {
        super();
        if (dateValue == null) {
            throw new IllegalArgumentException("DateValue can not be null");
        }
        setType(CellDataTypeEnum.DATE);
        this.dateValue = dateValue;
    }

    public WriteCellData(byte[] image) {
        super();
        if (image == null) {
            throw new IllegalArgumentException("Image can not be null");
        }
        setType(CellDataTypeEnum.EMPTY);
        this.imageDataList = ListUtils.newArrayList();
        ImageData imageData = new ImageData();
        imageData.setImage(image);
        imageDataList.add(imageData);
    }

}
