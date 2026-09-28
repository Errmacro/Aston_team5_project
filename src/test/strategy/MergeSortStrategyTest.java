package strategy;

import main.model.Movie;
import main.strategy.MergeSortStrategy;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MergeSortStrategyTest {
    // Проверяем сортировку фильмов по названию
    @Test
    void shouldSortMoviesByTitle() throws Exception {
        List<Movie> movies = new ArrayList<>(List.of(
                Movie.builder()
                        .title("Interstellar")
                        .releaseYear(2014)
                        .genre("Sci-Fi")
                        .duration(169)
                        .build(),

                Movie.builder()
                        .title("Avatar")
                        .releaseYear(2009)
                        .genre("Fantasy")
                        .duration(162)
                        .build(),

                Movie.builder()
                        .title("Avengers")
                        .releaseYear(2012)
                        .genre("Action")
                        .duration(143)
                        .build()
        ));

        MergeSortStrategy<Movie> strategy = new MergeSortStrategy<>();

        strategy.sort(movies, Comparator.comparing(Movie::getTitle));

        assertEquals(List.of("Avatar", "Avengers", "Interstellar"),movies.stream().map(Movie::getTitle).toList());
    }

    // Проверяем сортировку фильмов по году выпуска
    @Test
    void shouldSortMoviesByReleaseYear() throws Exception {
        List<Movie> movies = new ArrayList<>(List.of(
                Movie.builder()
                        .title("Interstellar")
                        .releaseYear(2014)
                        .genre("Sci-Fi")
                        .duration(169)
                        .build(),

                Movie.builder()
                        .title("Avatar")
                        .releaseYear(2009)
                        .genre("Fantasy")
                        .duration(162)
                        .build(),

                Movie.builder()
                        .title("Avengers")
                        .releaseYear(2012)
                        .genre("Action")
                        .duration(143)
                        .build()
        ));

        MergeSortStrategy<Movie> strategy = new MergeSortStrategy<>();
        strategy.sort(movies, Comparator.comparingInt(Movie::getReleaseYear));
        assertEquals(List.of(2009, 2012, 2014), movies.stream().map(Movie::getReleaseYear).toList());
    }

    // Проверяем сортировку фильмов по длительности
    @Test
    void shouldSortMoviesByDuration() throws Exception {
        List<Movie> movies = new ArrayList<>(List.of(
                Movie.builder()
                        .title("Interstellar")
                        .releaseYear(2014)
                        .genre("Sci-Fi")
                        .duration(169)
                        .build(),

                Movie.builder()
                        .title("Avatar")
                        .releaseYear(2009)
                        .genre("Fantasy")
                        .duration(162)
                        .build(),

                Movie.builder()
                        .title("Avengers")
                        .releaseYear(2012)
                        .genre("Action")
                        .duration(143)
                        .build()
        ));

        MergeSortStrategy<Movie> strategy = new MergeSortStrategy<>();
        strategy.sort(movies, Comparator.comparingInt(Movie::getDuration));
        assertEquals(List.of(143, 162, 169), movies.stream().map(Movie::getDuration).toList());
    }

    // Проверяем случай, когда в списке только один фильм
    @Test
    void shouldHandleOneMovie() throws Exception {
        Movie movie = Movie.builder()
                .title("Interstellar")
                .releaseYear(2014)
                .genre("Sci-Fi")
                .duration(169)
                .build();

        List<Movie> movies = new ArrayList<>(List.of(movie));
        MergeSortStrategy<Movie> strategy = new MergeSortStrategy<>();
        strategy.sort(movies, Comparator.comparing(Movie::getTitle));
        assertEquals(List.of(movie), movies);
    }

    // Проверяем случай с пустым списком фильмов
    @Test
    void shouldHandleEmptyMovieList() {
        List<Movie> movies = new ArrayList<>();
        MergeSortStrategy<Movie> strategy = new MergeSortStrategy<>();
        strategy.sort(movies, Comparator.comparing(Movie::getTitle));
        assertEquals(List.of(), movies);
    }
}
