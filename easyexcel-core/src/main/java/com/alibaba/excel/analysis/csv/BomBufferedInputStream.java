package com.alibaba.excel.analysis.csv;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class is used to wrap a stream that includes an encoded {@link ByteOrderMark} as its first bytes.
 *
 * @author supalle
 * @see <a href="http://unicode.org/faq/utf_bom.html#BOM">Byte Order Mark (BOM) FAQ</a>
 * @see <a href="https://commons.apache.org/proper/commons-io/apidocs/org/apache/commons/io/ByteOrderMark.html">Apache CommonsIO BOMInputStream</a>
 */
public class BomBufferedInputStream extends BufferedInputStream {
    public final static List<ByteOrderMark> DEFAULT_BYTE_ORDER_MARKS = new ArrayList<>();

    static {
        DEFAULT_BYTE_ORDER_MARKS.add(ByteOrderMark.UTF_8);
        DEFAULT_BYTE_ORDER_MARKS.add(ByteOrderMark.UTF_16BE);
        DEFAULT_BYTE_ORDER_MARKS.add(ByteOrderMark.UTF_16LE);
        DEFAULT_BYTE_ORDER_MARKS.add(ByteOrderMark.UTF_32BE);
        DEFAULT_BYTE_ORDER_MARKS.add(ByteOrderMark.UTF_32LE);
    }

    private boolean initialized;
    private ByteOrderMark byteOrderMark;
    private final List<ByteOrderMark> byteOrderMarks;

    public BomBufferedInputStream(InputStream in, final ByteOrderMark... byteOrderMarks) {
        super(in);
        this.byteOrderMarks = applyByteOrderMarks(byteOrderMarks);
    }

    public BomBufferedInputStream(InputStream in, int size, final ByteOrderMark... byteOrderMarks) {
        super(in, size);
        this.byteOrderMarks = applyByteOrderMarks(byteOrderMarks);
    }

    private static List<ByteOrderMark> applyByteOrderMarks(ByteOrderMark[] byteOrderMarks) {
        return byteOrderMarks == null || byteOrderMarks.length == 0 ? DEFAULT_BYTE_ORDER_MARKS : Arrays.asList(byteOrderMarks);
    }

    public boolean hasByteOrderMark() throws IOException {
        return getByteOrderMark() != null;
    }

    public ByteOrderMark getByteOrderMark() throws IOException {
        if (initialized) {
            return byteOrderMark;
        }
        this.byteOrderMarks.sort(ByteOrderMark::compareTo);
        int maxBomLength = byteOrderMarks.get(0).length();
        mark(maxBomLength);
        int[] firstBytes = new int[maxBomLength];
        for (int i = 0; i < maxBomLength; i++) {
            firstBytes[i] = read();
            if (firstBytes[i] < 0) {
                break;
            }
        }
        byteOrderMark = matchByteOrderMark(this.byteOrderMarks, firstBytes);

        reset();
        if (byteOrderMark != null) {
            skip(byteOrderMark.length());
        }
        initialized = true;
        return byteOrderMark;
    }

    private ByteOrderMark matchByteOrderMark(final List<ByteOrderMark> byteOrderMarks, final int[] firstBytes) {
        loop:
        for (ByteOrderMark item : byteOrderMarks) {
            int[] bytes = item.getBytes();
            int length = bytes.length;
            for (int i = 0; i < length; i++) {
                if (firstBytes[i] != bytes[i]) {
                    continue loop;
                }
            }
            return item;
        }
        return null;
    }

}
