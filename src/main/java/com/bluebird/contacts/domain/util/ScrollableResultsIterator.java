package com.bluebird.contacts.domain.util;

import org.hibernate.ScrollableResults;

import java.util.Iterator;

public class ScrollableResultsIterator<T> implements Iterator<T> {

    private final ScrollableResults scrollableResults;

    public ScrollableResultsIterator(ScrollableResults scrollableResults) {
        this.scrollableResults = scrollableResults;
    }

    @Override
    public boolean hasNext() {
        return scrollableResults.next();
    }

    @Override
    @SuppressWarnings("unchecked")
    public T next() {
        return (T) scrollableResults.get(0);
    }
}
