package strategy;

import main.strategy.QuickSortStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuickSortStrategyTest {
    private QuickSortStrategy<Integer> sorter;

    @BeforeEach
    void setUp() {
        sorter = new QuickSortStrategy<>();
    }

    @Test
    void shouldSortRandomList() {
        List<Integer> list = new ArrayList<>(List.of(3, 1, 4, 1, 5, 9, 2, 6));
        sorter.sort(list, Comparator.naturalOrder());
        assertEquals(List.of(1, 1, 2, 3, 4, 5, 6, 9), list);
    }

    @Test
    void shouldHandleEmptyList() {
        List<Integer> list = new ArrayList<>();
        sorter.sort(list, Comparator.naturalOrder());
        assertTrue(list.isEmpty());
    }

    @Test
    void shouldHandleSingleElement() {
        List<Integer> list = new ArrayList<>(List.of(42));
        sorter.sort(list, Comparator.naturalOrder());
        assertEquals(List.of(42), list);
    }

    @Test
    void shouldHandleNullList() {
        assertDoesNotThrow(() -> sorter.sort(null, Comparator.naturalOrder()));
    }

    @Test
    void shouldThrowOnNullComparator() {
        List<Integer> list = new ArrayList<>(List.of(1, 2, 3));
        assertThrows(IllegalArgumentException.class,
                () -> sorter.sort(list, null));
    }

    @Test
    void shouldSortReversedList() {
        List<Integer> list = new ArrayList<>(List.of(5, 4, 3, 2, 1));
        sorter.sort(list, Comparator.naturalOrder());
        assertEquals(List.of(1, 2, 3, 4, 5), list);
    }

    @Test
    void shouldSortWithReverseComparator() {
        List<Integer> list = new ArrayList<>(List.of(3, 1, 4, 1, 5));
        sorter.sort(list, Comparator.reverseOrder());
        assertEquals(List.of(5, 4, 3, 1, 1), list);
    }

    @Test
    void shouldHandleDuplicates() {
        List<Integer> list = new ArrayList<>(List.of(3, 3, 1, 1, 2, 2));
        sorter.sort(list, Comparator.naturalOrder());
        assertEquals(List.of(1, 1, 2, 2, 3, 3), list);
    }

    @Test
    void shouldSortLargeList() {
        List<Integer> list = new ArrayList<>();
        for (int i = 1000; i > 0; i--) {
            list.add(i);
        }
        sorter.sort(list, Comparator.naturalOrder());
        for (int i = 0; i < 1000; i++) {
            assertEquals(i + 1, list.get(i));
        }
    }

    @Test
    void shouldNotChangeListSize() {
        List<Integer> list = new ArrayList<>(List.of(5, 2, 8, 1, 9));
        int sizeBefore = list.size();
        sorter.sort(list, Comparator.naturalOrder());
        assertEquals(sizeBefore, list.size());
    }
}
