package strategy;

import main.strategy.QuickSortStrategy;
import main.strategy.SortStrategy;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SortEvenTest {
    @Test
    void shouldSortOnlyEvenValues() {
        SortStrategy<Integer> sorter = new QuickSortStrategy<>();
        List<Integer> list = new ArrayList<>(List.of(1, 4, 3, 2, 5, 6, 7, 8));

        sorter.sortEven(list, Comparator.naturalOrder(), i -> i);

        assertEquals(List.of(1, 2, 3, 4, 5, 6, 7, 8), list);
    }
}
