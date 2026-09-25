package strategy;

import main.strategy.BubbleSortStrategy;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BubbleSortStrategyTest {
    @Test
    void shouldSortIntegers() {
        List<Integer> numbers = new ArrayList<>(List.of(5, 2, 8, 1, 3));

        BubbleSortStrategy<Integer> strategy = new BubbleSortStrategy<>();

        strategy.sort(numbers, Comparator.naturalOrder());

        assertEquals(List.of(1, 2, 3, 5, 8), numbers);
    }

}
