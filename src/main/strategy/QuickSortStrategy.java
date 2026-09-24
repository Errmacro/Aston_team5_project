package main.strategy;

import java.util.Comparator;
import java.util.List;

public class QuickSortStrategy <T> implements SortStrategy<T>{

    @Override
    public void sort(List<T> items, Comparator<T> comparator) {
        if (items == null || items.size() < 2) {
            return;
        }
        quickSort(items, 0, items.size() - 1, comparator);
    }

    private void quickSort(List<T> items, int low, int high, Comparator<T> comparator) {
        if (low < high) {
            int pivotIndex = partition(items, low, high, comparator);
            quickSort(items, low, pivotIndex - 1, comparator);
            quickSort(items, pivotIndex + 1, high, comparator);
        }
    }

    private int partition(List<T> items, int low, int high, Comparator<T> comparator) {
        T pivot = items.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (comparator.compare(items.get(j), pivot) <= 0) {
                i++;
                swap(items, i, j);
            }
        }
        swap(items, i + 1, high);
        return i + 1;
    }

    private void swap(List<T> items, int i, int j) {
        T temp = items.get(i);
        items.set(i, items.get(j));
        items.set(j, temp);
    }
}
