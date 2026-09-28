package comparator;

import main.validation.ValidationException;
import main.comparator.MovieDurationComparator;
import main.comparator.MovieTitleComparator;
import main.comparator.MovieReleaseYearComparator;
import main.comparator.ReverseComparator;
import main.model.Movie;

/**
 * Ручные тесты компараторов.
 */
public class MovieComparatorManualTest {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) throws ValidationException {
        System.out.println("=== Тесты компараторов Movie ===");

        testTitleComparator();
        testYearComparator();
        testDurationComparator();
        testReverseComparator();

        System.out.println();
        System.out.println("Пройдено: " + passed + ", Провалено: " + failed);

        if (failed > 0) {
            System.exit(1);
        }
    }

    private static void testTitleComparator() throws ValidationException {
        Movie avatar = Movie.builder().title("Avatar").releaseYear(2009).genre("Fantasy").duration(162).build();
        Movie batman = Movie.builder().title("Batman").releaseYear(2022).genre("Action").duration(176).build();

        MovieTitleComparator comparator = new MovieTitleComparator();

        check(comparator.compare(avatar, batman) < 0, "Title: Avatar < Batman");
        check(comparator.compare(batman, avatar) > 0, "Title: Batman > Avatar");
        check(comparator.compare(avatar, avatar) == 0, "Title: одинаковые названия равны");
    }

    private static void testYearComparator() throws ValidationException {
        Movie old = Movie.builder().title("Old").releaseYear(1990).genre("Dramedy").duration(120).build();
        Movie recent = Movie.builder().title("Recent").releaseYear(2020).genre("Comedy").duration(130).build();

        MovieReleaseYearComparator comparator = new MovieReleaseYearComparator();

        check(comparator.compare(old, recent) < 0, "Year: 1990 < 2020");
        check(comparator.compare(recent, old) > 0, "Year: 2020 > 1990");
    }

    private static void testDurationComparator() throws ValidationException {
        Movie shortMovie = Movie.builder().title("Short").releaseYear(2020).genre("Dramedy").duration(90).build();
        Movie longMovie = Movie.builder().title("Long").releaseYear(2020).genre("Dramedy").duration(180).build();

        MovieDurationComparator comparator = new MovieDurationComparator();

        check(comparator.compare(shortMovie, longMovie) < 0, "Duration: 90 < 180");
        check(comparator.compare(longMovie, shortMovie) > 0, "Duration: 180 > 90");
    }

    private static void testReverseComparator() throws ValidationException {
        Movie m1 = Movie.builder().title("A").releaseYear(2000).genre("Comedy").duration(100).build();
        Movie m2 = Movie.builder().title("B").releaseYear(2010).genre("Comedy").duration(120).build();

        ReverseComparator<Movie> reverse = new ReverseComparator<>(new MovieReleaseYearComparator());
        check(reverse.compare(m1, m2) > 0, "Reverse: меняет порядок на обратный");
    }

    private static void check(boolean condition, String name) {
        if (condition) {
            System.out.println("PASS: " + name);
            passed++;
        } else {
            System.out.println("FAIL: " + name);
            failed++;
        }
    }
}