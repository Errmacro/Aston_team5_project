package main.strategy;

import java.util.Comparator;
import java.util.List;

public class QuickSortStrategy <T> implements SortStrategy<T>{

    @Override
    public void sort(T[] array, Comparator<T> comparator) {
        if (array == null || array.length < 2) {
            return;
        }
        quickSort(array, 0, array.length - 1, comparator);
    }
    private void quickSort(T[] array, int low, int high, Comparator<T> comparator) {
        if (low < high) {
            int pivotIndex = partition(array, low, high, comparator);
            quickSort(array, low, pivotIndex - 1, comparator);
            quickSort(array, pivotIndex + 1, high, comparator);
        }
    }
    private int partition(T[] array, int low, int high, Comparator<T> comparator) {
        T pivot = array[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (comparator.compare(array[j], pivot) <= 0) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i + 1, high);
        return i + 1;
    }
    private void swap(T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    @Override
    public void sort(List<T> items, Comparator<T> comparator) {

    }
}
