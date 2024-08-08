package com.alibaba.excel.read.builder;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author yilai
 */
public class StreamingReaderBuilder<T> extends ExcelReaderBuilder {
    public Stream<T> toStream() {
        return toStream(ExcelReaderBuilder::sheet);
    }

    public Stream<T> toStream(Integer sheetNo) {
        return toStream(b -> b.sheet(sheetNo));
    }

    public Stream<T> toStream(String sheetName) {
        return toStream(b -> b.sheet(sheetName));
    }

    private Stream<T> toStream(Function<ExcelReaderBuilder, ExcelReaderSheetBuilder> toSheet) {
        return stream(c -> {
            List<ReadListener<?>> listeners = parameter().getCustomReadListenerList();
            if (listeners != null && !listeners.isEmpty()) {
                listeners.clear();
            }
            registerReadListener(new ReadListener<T>() {
                @Override
                public void invoke(T data, AnalysisContext context) {
                    c.accept(data);
                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext context) {}
            });
            toSheet.apply(this).doRead();
        });
    }

    public static <T> Stream<T> stream(Consumer<Consumer<T>> sequence) {
        Iterator<T> iterator = new Iterator<T>() {
            @Override
            public boolean hasNext() {
                throw new UnsupportedOperationException();
            }

            @Override
            public T next() {
                throw new UnsupportedOperationException();
            }

            @Override
            public void forEachRemaining(Consumer<? super T> action) {
                sequence.accept(action::accept);
            }
        };
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false);
    }
}
