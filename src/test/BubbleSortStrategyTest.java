package test;

import main.strategy.BubbleSortStrategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BubbleSortStrategyTest {
    @Test
    void shouldSortIntegers() {
        List<Integer> numbers = new ArrayList<>(List.of(5, 2, 8, 1, 3));

        BubbleSortStrategy<Integer> strategy = new BubbleSortStrategy<>();

        strategy.sort(numbers, Comparator.naturalOrder());

        assertEquals(List.of(1, 2, 3, 5, 8), numbers);
    }

}
