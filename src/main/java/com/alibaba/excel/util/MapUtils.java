package com.alibaba.excel.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;

/**
 * Map utils
 *
 * @author Jiaju Zhuang
 **/
public class MapUtils {

    private MapUtils() {}


    /**
     * Creates a <i>mutable</i>, empty {@code HashMap} instance.
     *
     * <p><b>Note:</b> if mutability is not required, use {@link ImmutableMap#of()} instead.
     *
     * <p><b>Note:</b> if {@code K} is an {@code enum} type, use {@link #newEnumMap} instead.
     *
     * <p><b>Note for Java 7 and later:</b> this method is now unnecessary and should be treated as
     * deprecated. Instead, use the {@code HashMap} constructor directly, taking advantage of the new
     * <a href="http://goo.gl/iz2Wi">"diamond" syntax</a>.
     *
     * @return a new, empty {@code HashMap}
     */
    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<>(16);
    }

    /**
     * Creates a <i>mutable</i>, empty {@code TreeMap} instance using the natural ordering of its
     * elements.
     *
     * <p><b>Note:</b> if mutability is not required, use {@link ImmutableSortedMap#of()} instead.
     *
     * <p><b>Note for Java 7 and later:</b> this method is now unnecessary and should be treated as
     * deprecated. Instead, use the {@code TreeMap} constructor directly, taking advantage of the new
     * <a href="http://goo.gl/iz2Wi">"diamond" syntax</a>.
     *
     * @return a new, empty {@code TreeMap}
     */
    public static <K extends Comparable, V> TreeMap<K, V> newTreeMap() {
        return new TreeMap<>();
    }

    /**
     * Creates a {@code HashMap} instance, with a high enough "initial capacity" that it <i>should</i>
     * hold {@code expectedSize} elements without growth. This behavior cannot be broadly guaranteed,
     * but it is observed to be true for OpenJDK 1.7. It also can't be guaranteed that the method
     * isn't inadvertently <i>oversizing</i> the returned map.
     *
     * @param expectedSize the number of entries you expect to add to the returned map
     * @return a new, empty {@code HashMap} with enough capacity to hold {@code expectedSize} entries
     * without resizing
     * @throws IllegalArgumentException if {@code expectedSize} is negative
     */
    public static <K, V> HashMap<K, V> newHashMapWithExpectedSize(int expectedSize) {
        return new HashMap<>(capacity(expectedSize));
    }

    /**
     * Creates a <i>mutable</i>, empty, insertion-ordered {@code LinkedHashMap} instance.
     *
     * <p><b>Note:</b> if mutability is not required, use {@link ImmutableMap#of()} instead.
     *
     * <p><b>Note for Java 7 and later:</b> this method is now unnecessary and should be treated as
     * deprecated. Instead, use the {@code LinkedHashMap} constructor directly, taking advantage of
     * the new <a href="http://goo.gl/iz2Wi">"diamond" syntax</a>.
     *
     * @return a new, empty {@code LinkedHashMap}
     */
    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
        return new LinkedHashMap<>();
    }

    /**
     * Creates a {@code LinkedHashMap} instance, with a high enough "initial capacity" that it
     * <i>should</i> hold {@code expectedSize} elements without growth. This behavior cannot be
     * broadly guaranteed, but it is observed to be true for OpenJDK 1.7. It also can't be guaranteed
     * that the method isn't inadvertently <i>oversizing</i> the returned map.
     *
     * @param expectedSize the number of entries you expect to add to the returned map
     * @return a new, empty {@code LinkedHashMap} with enough capacity to hold {@code expectedSize}
     * entries without resizing
     * @throws IllegalArgumentException if {@code expectedSize} is negative
     * @since 19.0
     */
    public static <K, V> LinkedHashMap<K, V> newLinkedHashMapWithExpectedSize(int expectedSize) {
        return new LinkedHashMap<>(capacity(expectedSize));
    }

    /**
     * Returns a capacity that is sufficient to keep the map from being resized as long as it grows no
     * larger than expectedSize and the load factor is ≥ its default (0.75).
     */
    static int capacity(int expectedSize) {
        if (expectedSize < 3) {
            return expectedSize + 1;
        }
        if (expectedSize < IntUtils.MAX_POWER_OF_TWO) {
            // This is the calculation used in JDK8 to resize when a putAll
            // happens; it seems to be the most conservative calculation we
            // can make.  0.75 is the default load factor.
            return (int)((float)expectedSize / 0.75F + 1.0F);
        }
        return Integer.MAX_VALUE;
    }
}
