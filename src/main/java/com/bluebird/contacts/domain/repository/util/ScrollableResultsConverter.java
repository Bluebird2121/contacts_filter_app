package com.bluebird.contacts.domain.repository.util;

import com.bluebird.contacts.domain.util.ScrollableResultsIterator;
import org.hibernate.ScrollableResults;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ScrollableResultsConverter {

    public static <T> Stream<T> toStream(ScrollableResults queryResult, Class<T> streamtype) {
        ScrollableResultsIterator<T> iterator = new ScrollableResultsIterator<>( queryResult );
        Spliterator<T> spliterator = Spliterators.spliteratorUnknownSize( iterator, Spliterator.NONNULL );
        return StreamSupport.stream( spliterator, false );
    }

}
