package fill;

import main.fill.RandomDataSource;
import main.model.Movie;
import org.junit.jupiter.api.Test;

import java.time.Year;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class RandomDataSourceTest {

    @Test
    void shouldReturnRequestedNumberOfMovies() {
        RandomDataSource source = new RandomDataSource();

        List<Movie> movies = source.load(50);

        assertEquals(50, movies.size());
        assertFalse(movies.contains(null));
    }

    @Test
    void shouldGenerateValuesInRange() {
        RandomDataSource source = new RandomDataSource(new Random(42));
        int currentYear = Year.now().getValue();

        for (Movie movie : source.load(500)) {
            assertFalse(movie.getTitle().isBlank());
            assertFalse(movie.getGenre().isBlank());
            assertTrue(movie.getReleaseYear() >= 1950 && movie.getReleaseYear() <= currentYear);
            assertTrue(movie.getDuration() >= 60 && movie.getDuration() <= 200);
        }
    }

    @Test
    void shouldBeRepeatableWithSameSeed() {
        List<Movie> first = new RandomDataSource(new Random(7)).load(10);
        List<Movie> second = new RandomDataSource(new Random(7)).load(10);

        assertEquals(first, second);
    }

    @Test
    void shouldThrowOnNonPositiveSize() {
        RandomDataSource source = new RandomDataSource();

        assertThrows(IllegalArgumentException.class, () -> source.load(0));
    }
}
