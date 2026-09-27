package main.fill;

import main.model.Movie;
import main.validation.MovieValidator;
import main.validation.ValidationException;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Реализация DataSource, которая считывает данные о фильмах из консоли (пользователь вводит их вручную с клавиатуры).
public class ManualDataSource implements DataSource {

    private final Scanner scanner;
    private final PrintStream out;

    public ManualDataSource(Scanner scanner, PrintStream out) {
        if (scanner == null || out == null) {
            throw new IllegalArgumentException("Scanner и PrintStream не могут быть null");
        }
        this.scanner = scanner;
        this.out = out;
    }

    @Override
    public List<Movie> load(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Размер списка должен быть положительным");
        }

        List<Movie> movies = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            out.println("Фильм " + (i + 1) + " из " + size + ":");
            String title = readField("  Название: ", raw -> {
                MovieValidator.validateTitle(raw);
                return raw.trim();
            });
            String genre = readField("  Жанр: ", raw -> {
                MovieValidator.validateGenre(raw);
                return raw.trim();
            });
            int releaseYear = readField("  Год выхода: ", MovieValidator::validateReleaseYear);
            int duration = readField("  Продолжительность (мин): ", MovieValidator::validateDuration);

            movies.add(buildMovie(title, genre, releaseYear, duration));
        }
        return movies;
    }

    // Все поля уже проверены при вводе, поэтому ValidationException здесь означает ошибку в программе.
    private Movie buildMovie(String title, String genre, int releaseYear, int duration) {
        try {
            return Movie.builder()
                    .title(title)
                    .genre(genre)
                    .releaseYear(releaseYear)
                    .duration(duration)
                    .build();
        } catch (ValidationException e) {
            throw new IllegalStateException("Не удалось создать фильм из проверенных данных: " + e.getMessage(), e);
        }
    }

    // Разбирает и проверяет введённую строку; может выбросить ValidationException.
    // Стандартный Function не подходит: он не позволяет бросать checked-исключения.
    @FunctionalInterface
    private interface FieldParser<T> {
        T parse(String raw) throws ValidationException;
    }

    // Считывает поле. При ошибке сообщает о ней и повторяет запрос только для этого поля.
    private <T> T readField(String prompt, FieldParser<T> parser) {
        while (true) {
            out.print(prompt);
            String raw = scanner.nextLine(); // NoSuchElementException, если ввод закончился
            try {
                return parser.parse(raw);
            } catch (ValidationException e) {
                out.println("  Ошибка: " + e.getMessage() + ". Повторите ввод.");
            }
        }
    }
}
