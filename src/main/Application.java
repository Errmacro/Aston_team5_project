package main;

import main.comparator.MovieDurationComparator;
import main.comparator.MovieGenreComparator;
import main.comparator.MovieReleaseYearComparator;
import main.comparator.MovieTitleComparator;
import main.comparator.ReverseComparator;
import main.fill.DataSource;
import main.fill.FileDataSource;
import main.fill.FileResultWriter;
import main.fill.ManualDataSource;
import main.fill.RandomDataSource;
import main.model.Movie;
import main.strategy.BubbleSortStrategy;
import main.strategy.MergeSortStrategy;
import main.strategy.QuickSortStrategy;
import main.strategy.SortStrategy;

import java.io.PrintStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

// Управляет основным сценарием работы приложения:
// отображает меню, загружает фильмы, выводит их
// и запускает сортировку.
public class Application {

    private static final int MAX_SIZE = 100;
    private static final String DEFAULT_RESULTS_FILE = "data/results.txt";

    private final InputReader input;
    private final PrintStream out;
    private final FileResultWriter resultWriter;

    public Application(Scanner scanner, PrintStream out) {
        this.input = new InputReader(scanner, out);
        this.out = out;
        this.resultWriter = new FileResultWriter(Paths.get(DEFAULT_RESULTS_FILE), out);
    }

    // Управляет основным сценарием работы приложения:
    // отображает меню, загружает фильмы, выводит их
    // и запускает сортировку.
    public void run() {
        List<Movie> movies = null;

        while (true) {
            showMenu();

            int choice = input.readInt(
                    "Ваш выбор: ",
                    0,
                    5
            );

            switch (choice) {
                case 1:
                    movies = loadMovies(movies);
                    break;

                case 2:
                    showMovies(movies);
                    break;

                case 3:
                    sortMovies(movies);
                    break;

                case 4:
                    saveMovies(movies);
                    break;

                case 5:
                    sortAndSave(movies);
                    break;

                case 0:
                    out.println("Работа программы завершена.");
                    return;
            }
        }
    }
    // Выводит в консоль главное меню приложения.
    private void showMenu() {
        out.println();
        out.println("========== МЕНЮ ==========");
        out.println("1 — Загрузить фильмы");
        out.println("2 — Показать фильмы");
        out.println("3 — Отсортировать фильмы");
        out.println("4 — Сохранить фильмы в файл");
        out.println("5 — Отсортировать и сохранить");
        out.println("0 — Выход");
        out.println("==========================");
    }
    // Загружает фильмы выбранным способом и добавляет их
    // в текущий список фильмов.
    // @param movies текущий список фильмов; может быть null,
    // если загрузка выполняется впервые
    // @return обновлённый список фильмов
    private List<Movie> loadMovies(List<Movie> movies) {
        DataSource dataSource = chooseDataSource();

        int size = input.readInt(
                "Сколько фильмов загрузить? ",
                1,
                MAX_SIZE
        );

        List<Movie> newMovies = dataSource.load(size);

        if (movies == null) {
            movies = new ArrayList<>();
        }

        movies.addAll(newMovies);

        out.println();
        out.println(
                "Добавлено фильмов: "
                        + newMovies.size()
        );

        out.println(
                "Всего фильмов: "
                        + movies.size()
        );

        return movies;
    }

    // Выводит загруженные фильмы в консоль.
    // Если фильмы ещё не загружены, сообщает об этом пользователю.
    private void showMovies(List<Movie> movies) {
        if (movies == null || movies.isEmpty()) {
            out.println("Сначала загрузите фильмы.");
            return;
        }

        out.println();
        out.println(
                "Фильмы (" + movies.size() + "):"
        );

        for (Movie movie : movies) {
            out.println(movie);
        }
    }

    // Запускает сортировку текущего списка фильмов.
    // Пользователь выбирает алгоритм сортировки, направление и поле,
    // по которому необходимо выполнить сортировку.
    private void sortMovies(List<Movie> movies) {
        if (movies == null || movies.isEmpty()) {
            out.println("Сначала загрузите фильмы.");
            return;
        }

        SortStrategy<Movie> strategy = chooseSortStrategy();
        Comparator<Movie> comparator = chooseComparator();
        comparator = chooseSortDirection(comparator);
        strategy.sort(movies, comparator);

        out.println("Фильмы отсортированы.");
    }

    // Предлагает пользователю выбрать алгоритм сортировки
    // и возвращает соответствующую стратегию.
    // @return выбранная стратегия сортировки
    private SortStrategy<Movie> chooseSortStrategy() {
        out.println();
        out.println("Выберите алгоритм сортировки:");
        out.println("  1 — Пузырьковая сортировка");
        out.println("  2 — Сортировка слиянием");
        out.println("  3 — Быстрая сортировка");

        int choice = input.readInt(
                "Ваш выбор: ",
                1,
                3
        );

        switch (choice) {
            case 1:
                return new BubbleSortStrategy<>();

            case 2:
                return new MergeSortStrategy<>();

            default:
                return new QuickSortStrategy<>();
        }
    }

    private void saveMovies(List<Movie> movies) {
        if (movies == null || movies.isEmpty()) {
            out.println("Сначала загрузите фильмы.");
            return;
        }

        resultWriter.writeMovies(movies, "Фильмы");
    }

    private void sortAndSave(List<Movie> movies) {
        if (movies == null || movies.isEmpty()) {
            out.println("Сначала загрузите фильмы.");
            return;
        }

        sortMovies(movies);
        resultWriter.writeMovies(movies, "Отсортированные фильмы");
    }

    // Выбираем направление сортировки: по возрастанию или по убыванию.
    // Для обратного порядка используем ReverseComparator.
    private Comparator<Movie> chooseSortDirection(Comparator<Movie> comparator) {
        out.println();
        out.println("Выберите направление сортировки:");
        out.println("  1 — По возрастанию");
        out.println("  2 — По убыванию");

        int choice = input.readInt("Ваш выбор: ", 1, 2);

        if (choice == 2) {
            return new ReverseComparator<>(comparator);
        }

        return comparator;
    }

    //Предлагает пользователю выбрать поле фильма,
    // по которому будет выполняться сортировка,
    // и возвращает соответствующий компаратор.
    // @return выбранный компаратор
    private Comparator<Movie> chooseComparator() {
        out.println();
        out.println("Выберите поле для сортировки:");
        out.println("  1 — Название");
        out.println("  2 — Год выпуска");
        out.println("  3 — Длительность");
        out.println("  4 — Жанр");

        int choice = input.readInt(
                "Ваш выбор: ",
                1,
                4
        );

        switch (choice) {
            case 1:
                return new MovieTitleComparator();

            case 2:
                return new MovieReleaseYearComparator();

            case 3:
                return new MovieDurationComparator();

            default:
                return new MovieGenreComparator();
        }
    }

    //Предлагает пользователю выбрать источник данных
    // и создаёт соответствующий DataSource.
    // @return выбранный источник данных
    private DataSource chooseDataSource() {
        out.println("Способ заполнения списка:");
        out.println("  1 — вручную");
        out.println("  2 — из файла");
        out.println("  3 — случайно");

        int choice = input.readInt(
                "Ваш выбор: ",
                1,
                3
        );

        switch (choice) {
            case 1:
                return new ManualDataSource(
                        input.getScanner(),
                        out
                );

            case 2:
                return new FileDataSource(
                        input.readFilePath(),
                        out
                );

            default:
                return new RandomDataSource();
        }
    }
}
