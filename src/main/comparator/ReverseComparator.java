package com.example.sorting.comparator;

import java.util.Comparator;

public class ReverseComparator<T> implements Comparator<T> {
    private final Comparator<T> delegate;

    public ReverseComparator(Comparator<T> delegate) {
        this.delegate = delegate;
    }

    @Override
    public int compare(T first, T second) {
        return delegate.compare(second, first);
    }
}