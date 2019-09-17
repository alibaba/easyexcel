package com.alibaba.excel.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * IO Utils
 *
 * @author Jiaju Zhuang
 */
public class IoUtils {
    public static final int EOF = -1;
    /**
     * The default buffer size ({@value}) to use for
     */
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

    private IoUtils() {}

    /**
     * Gets the contents of an InputStream as a byte[].
     *
     * @param input
     * @return
     * @throws IOException
     */
    public static byte[] toByteArray(final InputStream input) throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            copy(input, output);
            return output.toByteArray();
        } finally {
            output.toByteArray();
        }
    }

    /**
     * Gets the contents of an InputStream as a byte[].
     *
     * @param input
     * @param size
     * @return
     * @throws IOException
     */
    public static byte[] toByteArray(final InputStream input, final int size) throws IOException {
        if (size < 0) {
            throw new IllegalArgumentException("Size must be equal or greater than zero: " + size);
        }
        if (size == 0) {
            return new byte[0];
        }
        final byte[] data = new byte[size];
        int offset = 0;
        int read;
        while (offset < size && (read = input.read(data, offset, size - offset)) != EOF) {
            offset += read;
        }
        if (offset != size) {
            throw new IOException("Unexpected read size. current: " + offset + ", expected: " + size);
        }
        return data;
    }

    /**
     * Copies bytes
     *
     * @param input
     * @param output
     * @return
     * @throws IOException
     */
    public static int copy(final InputStream input, final OutputStream output) throws IOException {
        long count = 0;
        int n;
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        while (EOF != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        if (count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int)count;
    }
}
