package main.strategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public interface SortStrategy<T> {
    void sort(List<T> items, Comparator<T> comparator);

    default void sortEven(List<T> items, Comparator<T> comparator, Function<T, Integer> fieldExtractor) {
        if (items == null || items.size() < 2) return;
        if (comparator == null || fieldExtractor == null) {
            throw new IllegalArgumentException("Comparator и fieldExtractor не могут быть null");
        }
        List<T> evenElements = new ArrayList<>();
        for (T item : items) {
            if (fieldExtractor.apply(item) % 2 == 0) evenElements.add(item);
        }
        sort(evenElements, comparator);
        int evenIndex = 0;
        for (int i = 0; i < items.size(); i++) {
            if (fieldExtractor.apply(items.get(i)) % 2 == 0) {
                items.set(i, evenElements.get(evenIndex));
                evenIndex++;
            }
        }
    }
}
