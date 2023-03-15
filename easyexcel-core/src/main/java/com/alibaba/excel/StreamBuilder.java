package com.alibaba.excel;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * build stream from generator
 */
public class StreamBuilder {
    public static <T> Stream<T> stream(Consumer<Consumer<T>> sequence) {
        Iterator<T> iterator = new Iterator<T>() {
            @Override
            public boolean hasNext() {
                throw new NoSuchElementException();
            }

            @Override
            public T next() {
                throw new NoSuchElementException();
            }

            @Override
            public void forEachRemaining(Consumer<? super T> action) {
                sequence.accept(action::accept);
            }
        };
        return StreamSupport.stream(
            Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED),
            false);
    }
}
