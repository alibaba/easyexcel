package com.alibaba.excel.util;

import java.util.ArrayList;
import java.util.List;

/**
 * List utils
 *
 * @author Jiaju Zhuang
 **/
public class ListUtils {
    private ListUtils() {}

    /**
     * Creates an {@code ArrayList} instance backed by an array with the specified initial size;
     * simply delegates to {@link ArrayList#ArrayList(int)}.
     *
     * <p><b>Note for Java 7 and later:</b> this method is now unnecessary and should be treated as
     * deprecated. Instead, use {@code new }{@link ArrayList#ArrayList(int) ArrayList}{@code <>(int)}
     * directly, taking advantage of the new <a href="http://goo.gl/iz2Wi">"diamond" syntax</a>.
     * (Unlike here, there is no risk of overload ambiguity, since the {@code ArrayList} constructors
     * very wisely did not accept varargs.)
     *
     * @param initialArraySize the exact size of the initial backing array for the returned array list
     *                         ({@code ArrayList} documentation calls this value the "capacity")
     * @return a new, empty {@code ArrayList} which is guaranteed not to resize itself unless its size
     * reaches {@code initialArraySize + 1}
     * @throws IllegalArgumentException if {@code initialArraySize} is negative
     */
    public static <E> ArrayList<E> newArrayListWithCapacity(int initialArraySize) {
        checkNonnegative(initialArraySize, "initialArraySize");
        return new ArrayList<>(initialArraySize);
    }

    /**
     * Creates an {@code ArrayList} instance to hold {@code estimatedSize} elements, <i>plus</i> an
     * unspecified amount of padding; you almost certainly mean to call {@link
     * #newArrayListWithCapacity} (see that method for further advice on usage).
     *
     * <p><b>Note:</b> This method will soon be deprecated. Even in the rare case that you do want
     * some amount of padding, it's best if you choose your desired amount explicitly.
     *
     * @param estimatedSize an estimate of the eventual {@link List#size()} of the new list
     * @return a new, empty {@code ArrayList}, sized appropriately to hold the estimated number of
     * elements
     * @throws IllegalArgumentException if {@code estimatedSize} is negative
     */
    public static <E> ArrayList<E> newArrayListWithExpectedSize(int estimatedSize) {
        return new ArrayList<>(computeArrayListCapacity(estimatedSize));
    }

    static int computeArrayListCapacity(int arraySize) {
        checkNonnegative(arraySize, "arraySize");
        return IntUtils.saturatedCast(5L + arraySize + (arraySize / 10));
    }

    static int checkNonnegative(int value, String name) {
        if (value < 0) {
            throw new IllegalArgumentException(name + " cannot be negative but was: " + value);
        }
        return value;
    }
}
