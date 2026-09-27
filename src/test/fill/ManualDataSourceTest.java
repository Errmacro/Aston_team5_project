package fill;

import main.fill.ManualDataSource;
import main.model.Movie;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class ManualDataSourceTest {

    private final ByteArrayOutputStream output = new ByteArrayOutputStream();

    private ManualDataSource sourceWithInput(String input) {
        Scanner scanner = new Scanner(input);
        PrintStream out = new PrintStream(output, true, StandardCharsets.UTF_8);
        return new ManualDataSource(scanner, out);
    }

    private String printed() {
        return output.toString(StandardCharsets.UTF_8);
    }

    @Test
    void shouldLoadValidMovie() throws Exception {
        ManualDataSource source = sourceWithInput("Inception\nSci-Fi\n2010\n148\n");

        List<Movie> movies = source.load(1);

        Movie expected = Movie.builder()
                .title("Inception").genre("Sci-Fi").releaseYear(2010).duration(148).build();
        assertEquals(List.of(expected), movies);
    }

    @Test
    void shouldLoadSeveralMovies() {
        ManualDataSource source = sourceWithInput(
                "Avatar\nFantasy\n2009\n162\n"
                        + "Брат\nДрама\n1997\n100\n");

        List<Movie> movies = source.load(2);

        assertEquals(2, movies.size());
        assertEquals("Avatar", movies.get(0).getTitle());
        assertEquals("Брат", movies.get(1).getTitle());
        assertEquals(1997, movies.get(1).getReleaseYear());
    }

    @Test
    void shouldTrimTextFields() {
        ManualDataSource source = sourceWithInput("  Inception  \n  Sci-Fi \n 2010 \n 148 \n");

        Movie movie = source.load(1).get(0);

        assertEquals("Inception", movie.getTitle());
        assertEquals("Sci-Fi", movie.getGenre());
        assertEquals(2010, movie.getReleaseYear());
        assertEquals(148, movie.getDuration());
    }

    @Test
    void shouldRepeatOnlyInvalidField() {
        // Год: сначала не число, потом вне диапазона, потом корректный.
        ManualDataSource source = sourceWithInput("Inception\nSci-Fi\nabc\n1500\n2010\n148\n");

        Movie movie = source.load(1).get(0);

        assertEquals("Inception", movie.getTitle());
        assertEquals(2010, movie.getReleaseYear());
        assertEquals(2, printed().split("Ошибка", -1).length - 1);
    }

    @Test
    void shouldRepeatOnEmptyTitle() {
        ManualDataSource source = sourceWithInput("\n   \nInception\nSci-Fi\n2010\n148\n");

        Movie movie = source.load(1).get(0);

        assertEquals("Inception", movie.getTitle());
        assertTrue(printed().contains("Ошибка"));
    }

    @Test
    void shouldRepeatOnInvalidDuration() {
        ManualDataSource source = sourceWithInput("Inception\nSci-Fi\n2010\n0\n-5\n148\n");

        assertEquals(148, source.load(1).get(0).getDuration());
    }

    @Test
    void shouldThrowOnNonPositiveSize() {
        ManualDataSource source = sourceWithInput("");

        assertThrows(IllegalArgumentException.class, () -> source.load(0));
        assertThrows(IllegalArgumentException.class, () -> source.load(-1));
    }

    @Test
    void shouldThrowWhenInputEndsEarly() {
        ManualDataSource source = sourceWithInput("Inception\nSci-Fi\n");

        assertThrows(NoSuchElementException.class, () -> source.load(1));
    }
}
