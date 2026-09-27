package com.example.sorting.comparator;

import com.example.sorting.model.Movie;

/**
 * Ручные тесты компараторов.
 */
public class MovieComparatorManualTest {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
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

    private static void testTitleComparator() {
        Movie avatar = new Movie.Builder().title("Avatar").year(2009).duration(162).build();
        Movie batman = new Movie.Builder().title("Batman").year(2022).duration(176).build();

        MovieTitleComparator comparator = new MovieTitleComparator();

        check(comparator.compare(avatar, batman) < 0, "Title: Avatar < Batman");
        check(comparator.compare(batman, avatar) > 0, "Title: Batman > Avatar");
        check(comparator.compare(avatar, avatar) == 0, "Title: одинаковые названия равны");
    }

    private static void testYearComparator() {
        Movie old = new Movie.Builder().title("Old").year(1990).duration(120).build();
        Movie recent = new Movie.Builder().title("Recent").year(2020).duration(130).build();

        MovieYearComparator comparator = new MovieYearComparator();

        check(comparator.compare(old, recent) < 0, "Year: 1990 < 2020");
        check(comparator.compare(recent, old) > 0, "Year: 2020 > 1990");
    }

    private static void testDurationComparator() {
        Movie shortMovie = new Movie.Builder().title("Short").year(2020).duration(90).build();
        Movie longMovie = new Movie.Builder().title("Long").year(2020).duration(180).build();

        MovieDurationComparator comparator = new MovieDurationComparator();

        check(comparator.compare(shortMovie, longMovie) < 0, "Duration: 90 < 180");
        check(comparator.compare(longMovie, shortMovie) > 0, "Duration: 180 > 90");
    }

    private static void testReverseComparator() {
        Movie m1 = new Movie.Builder().title("A").year(2000).duration(100).build();
        Movie m2 = new Movie.Builder().title("B").year(2010).duration(120).build();

        ReverseComparator<Movie> reverse = new ReverseComparator<>(new MovieYearComparator());

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