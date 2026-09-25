package com.example.sorting.strategy;
package main.strategy;

import java.util.Comparator;
import java.util.List;

/**
 * Интерфейс стратегии сортировки.
 * Принимает только список и компаратор.
 */
public interface SortStrategy<T> {
    void sort(List<T> items, Comparator<T> comparator);
}
public interface SortStrategy<T> {
    void sort(List<T> items, Comparator<T> comparator);
}
