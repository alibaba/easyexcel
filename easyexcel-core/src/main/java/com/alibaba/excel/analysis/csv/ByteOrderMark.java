package com.alibaba.excel.analysis.csv;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Byte Order Mark (BOM)
 * <br/>
 * User in {@link BomBufferedInputStream}
 *
 * @author supalle
 * @see <a href="http://unicode.org/faq/utf_bom.html#BOM">Byte Order Mark (BOM) FAQ</a>
 * @see <a href="https://commons.apache.org/proper/commons-io/apidocs/org/apache/commons/io/ByteOrderMark.html">Apache CommonsIO ByteOrderMark</a>
 */
public class ByteOrderMark implements Comparable<ByteOrderMark> {

    /**
     * UTF-8 BOM.
     */
    public static final ByteOrderMark UTF_8 = new ByteOrderMark(StandardCharsets.UTF_8, 0xEF, 0xBB, 0xBF);

    /**
     * UTF-16BE BOM (Big-Endian).
     */
    public static final ByteOrderMark UTF_16BE = new ByteOrderMark(StandardCharsets.UTF_16BE, 0xFE, 0xFF);

    /**
     * UTF-16LE BOM (Little-Endian).
     */
    public static final ByteOrderMark UTF_16LE = new ByteOrderMark(StandardCharsets.UTF_16LE, 0xFF, 0xFE);

    /**
     * UTF-32BE BOM (Big-Endian).
     *
     * @since 2.2
     */
    public static final ByteOrderMark UTF_32BE = new ByteOrderMark(Charset.forName("UTF-32BE"), 0x00, 0x00, 0xFE, 0xFF);

    /**
     * UTF-32LE BOM (Little-Endian).
     *
     * @since 2.2
     */
    public static final ByteOrderMark UTF_32LE = new ByteOrderMark(Charset.forName("UTF-32LE"), 0xFF, 0xFE, 0x00, 0x00);

    private final Charset charset;
    private final int[] bytes;

    public ByteOrderMark(final Charset charset, final int... bytes) {
        this.charset = Objects.requireNonNull(charset, "charset must be not null");
        if (bytes == null || bytes.length == 0) {
            throw new IllegalArgumentException("bytes must be not empty");
        }
        this.bytes = bytes;
    }

    public Charset getCharset() {
        return charset;
    }

    public int[] getBytes() {
        return bytes;
    }

    public int length() {
        return bytes.length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ByteOrderMark that = (ByteOrderMark) o;
        return Objects.equals(charset, that.charset) && Arrays.equals(bytes, that.bytes);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(charset);
        result = 31 * result + Arrays.hashCode(bytes);
        return result;
    }

    @Override
    public String toString() {
        return "ByteOrderMark{" +
            "charset=" + charset +
            ", bytes=["
            + Arrays.stream(bytes)
            .mapToObj(Integer::toHexString)
            .map(String::toUpperCase)
            .map("0x"::concat)
            .collect(Collectors.joining(",")) +
            "]}";
    }

    @Override
    public int compareTo(ByteOrderMark o) {
        return o.length() - length();
    }
}
