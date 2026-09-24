package main.strategy;

import java.util.Comparator;
import java.util.List;

// Реализация алгоритма пузырьковой сортировки.
// T — универсальный тип элементов списка.
public class BubbleSortStrategy<T> implements SortStrategy<T> {

    // Реализация пузырьковой сортировки для List<T>.
    @Override
    public void sort(List<T> list, Comparator<T> comparator) {

        for (int i = 0; i < list.size() - 1; i++) {

            for (int j = 0; j < list.size() - 1 - i; j++) {

                if (comparator.compare(list.get(j), list.get(j + 1)) > 0) {

                    T temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }

    // Метод-заготовка для дальнейшей поддержки сортировки массивов T[].
    @Override
    public void sort(T[] array, Comparator<T> comparator) {

    }

}
