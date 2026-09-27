package main.fill;

import main.model.Movie;
import main.validation.ValidationException;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Реализация DataSource, которая придумывает фильмы случайным образом.
public class RandomDataSource implements DataSource {

    // Название собирается из двух слов: первое из FIRST_WORDS, второе из SECOND_WORDS.
    private static final String[] FIRST_WORDS = {
        "Тайна", "Легенда", "Возвращение", "Тень", "Хроники",
        "Путь", "Сердце", "Последний день", "Проклятие", "Дети"
    };
    private static final String[] SECOND_WORDS = {
        "океана", "дракона", "города", "времени", "звёзд",
        "пустыни", "короля", "леса", "империи", "призрака"
    };
    private static final String[] GENRES = {
        "Драма", "Комедия", "Фантастика", "Боевик", "Триллер",
        "Ужасы", "Мелодрама", "Приключения", "Детектив", "Мультфильм"
    };

    private static final int MIN_YEAR = 1950;
    private static final int MAX_YEAR = Year.now().getValue();
    private static final int MIN_DURATION = 60;
    private static final int MAX_DURATION = 200;

    // Генератор случайных чисел. Создаём один раз и используем много раз.
    private final Random random;

    // Обычный конструктор: фильмы каждый раз получаются разные.
    public RandomDataSource() {
        this(new Random());
    }

    // Конструктор, в который можно передать свой генератор случайных чисел кроме null.
    public RandomDataSource(Random random) {
        if (random == null) {
            throw new IllegalArgumentException("Random не может быть null");
        }
        this.random = random;
    }

    @Override
    public List<Movie> load(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Размер списка должен быть положительным");
        }

        List<Movie> movies = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            String title = randomElement(FIRST_WORDS) + " " + randomElement(SECOND_WORDS);
            String genre = randomElement(GENRES);
            int releaseYear = randomInRange(MIN_YEAR, MAX_YEAR);
            int duration = randomInRange(MIN_DURATION, MAX_DURATION);

            movies.add(buildMovie(title, genre, releaseYear, duration));
        }
        return movies;
    }

    // Все значения берутся из допустимых диапазонов, поэтому ValidationException здесь означает ошибку в программе.
    private Movie buildMovie(String title, String genre, int releaseYear, int duration) {
        try {
            return Movie.builder()
                    .title(title)
                    .genre(genre)
                    .releaseYear(releaseYear)
                    .duration(duration)
                    .build();
        } catch (ValidationException e) {
            throw new IllegalStateException("Сгенерирован некорректный фильм: " + e.getMessage(), e);
        }
    }

    private String randomElement(String[] values) {
        return values[random.nextInt(values.length)];
    }

    // Случайное число от min до max включительно.
    private int randomInRange(int min, int max) {
        return min + random.nextInt(max - min + 1);
    }
}
