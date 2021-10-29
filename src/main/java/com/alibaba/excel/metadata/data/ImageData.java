package com.alibaba.excel.metadata.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * image
 *
 * @author Jiaju Zhuang
 */
@Getter
@Setter
@EqualsAndHashCode
public class ImageData extends ClientAnchorData {

    /**
     * image
     */
    private byte[] image;

    /**
     * image type
     */
    private ImageType imageType;

    @Getter
    public enum ImageType {
        /**
         * Extended windows meta file
         */
        PICTURE_TYPE_EMF(2),
        /**
         * Windows Meta File
         */
        PICTURE_TYPE_WMF(3),
        /**
         * Mac PICT format
         */
        PICTURE_TYPE_PICT(4),
        /**
         * JPEG format
         */
        PICTURE_TYPE_JPEG(5),
        /**
         * PNG format
         */
        PICTURE_TYPE_PNG(6),
        /**
         * Device independent bitmap
         */
        PICTURE_TYPE_DIB(7),

        ;

        int value;

        ImageType(int value) {
            this.value = value;
        }
    }

}
