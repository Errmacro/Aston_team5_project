package test;

import main.strategy.BubbleSortStrategy;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BubbleSortStrategyTest {

    // Проверка обычной сортировки случайного списка чисел
    @Test
    void shouldSortIntegers() {
        List<Integer> numbers = new ArrayList<>(List.of(5, 2, 8, 1, 3));

        BubbleSortStrategy<Integer> strategy = new BubbleSortStrategy<>();

        strategy.sort(numbers, Comparator.naturalOrder());

        assertEquals(List.of(1, 2, 3, 5, 8), numbers);
    }

    // Проверка, что уже отсортированный список не ломается
    @Test
    void shouldKeepAlreadySortedList() {
        List<Integer> numbers = new ArrayList<>(List.of(1, 2, 3, 4, 5));

        BubbleSortStrategy<Integer> strategy = new BubbleSortStrategy<>();

        strategy.sort(numbers, Comparator.naturalOrder());

        assertEquals(List.of(1, 2, 3, 4, 5), numbers);
    }

    // Проверка сортировки списка, который изначально отсортирован наоборот
    @Test
    void shouldSortReverseOrder() {
        List<Integer> numbers = new ArrayList<>(List.of(5, 4, 3, 2, 1));

        BubbleSortStrategy<Integer> strategy = new BubbleSortStrategy<>();

        strategy.sort(numbers, Comparator.naturalOrder());

        assertEquals(List.of(1, 2, 3, 4, 5), numbers);
    }

    // Проверка случая, когда в списке только один элемент
    @Test
    void shouldHandleOneElement() {
        List<Integer> numbers = new ArrayList<>(List.of(5));

        BubbleSortStrategy<Integer> strategy = new BubbleSortStrategy<>();

        strategy.sort(numbers, Comparator.naturalOrder());

        assertEquals(List.of(5), numbers);
    }

    // Проверка случая с пустым списком
    @Test
    void shouldHandleEmptyList() {
        List<Integer> numbers = new ArrayList<>();

        BubbleSortStrategy<Integer> strategy = new BubbleSortStrategy<>();

        strategy.sort(numbers, Comparator.naturalOrder());

        assertEquals(List.of(), numbers);
    }
}