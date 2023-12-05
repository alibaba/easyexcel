package com.alibaba.excel.enums;

import java.nio.charset.Charset;
import java.util.Map;

import com.alibaba.excel.util.MapUtils;

import lombok.Getter;
import org.apache.commons.io.ByteOrderMark;

/**
 * byte order mark
 *
 * @author Jiaju Zhuang
 */
@Getter
public enum ByteOrderMarkEnum {

    /**
     * UTF_8
     */
    UTF_8(ByteOrderMark.UTF_8),
    /**
     * UTF_16BE
     */
    UTF_16BE(ByteOrderMark.UTF_16BE),
    /**
     * UTF_16LE
     */
    UTF_16LE(ByteOrderMark.UTF_16LE),
    /**
     * UTF_32BE
     */
    UTF_32BE(ByteOrderMark.UTF_32BE),
    /**
     * UTF_32LE
     */
    UTF_32LE(ByteOrderMark.UTF_32LE),

    ;

    final ByteOrderMark byteOrderMark;
    final String stringPrefix;

    ByteOrderMarkEnum(ByteOrderMark byteOrderMark) {
        this.byteOrderMark = byteOrderMark;
        Charset charset = Charset.forName(byteOrderMark.getCharsetName());
        this.stringPrefix = new String(byteOrderMark.getBytes(), charset);
    }

    /**
     * store character aliases corresponding to `ByteOrderMark` prefix
     */
    private static final Map<String, ByteOrderMarkEnum> CHARSET_BYTE_ORDER_MARK_MAP = MapUtils.newHashMap();

    static {
        for (ByteOrderMarkEnum value : ByteOrderMarkEnum.values()) {
            CHARSET_BYTE_ORDER_MARK_MAP.put(value.getByteOrderMark().getCharsetName(), value);
        }
    }

    public static ByteOrderMarkEnum valueOfByCharsetName(String charsetName) {
        return CHARSET_BYTE_ORDER_MARK_MAP.get(charsetName);
    }

}
