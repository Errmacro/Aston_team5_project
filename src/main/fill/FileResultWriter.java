package main.fill;

import main.model.Movie;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Записывает результаты работы программы (отсортированные коллекции,
 * найденные значения) в файл в режиме добавления (APPEND).
 * Каждая запись сопровождается заголовком и датой.
 * Формат строки фильма тот же, что и при чтении: Название;Жанр;Год;Продолжительность.
 */
public class FileResultWriter {

    private static final String SEPARATOR = ";";
    private static final DateTimeFormatter TIMESTAMP_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final Path path;
    private final PrintStream out;

    public FileResultWriter(Path path, PrintStream out) {
        if (path == null || out == null) {
            throw new IllegalArgumentException("Path и PrintStream не могут быть null");
        }
        this.path = path;
        this.out = out;
    }

    /**
     * Дописывает в файл список фильмов с заголовком блока.
     */
    public void writeMovies(List<Movie> movies, String header) {
        if (movies == null || movies.isEmpty()) {
            out.println("Список пуст — записывать нечего.");
            return;
        }

        try (BufferedWriter writer = Files.newBufferedWriter(
                path,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE,
                StandardOpenOption.APPEND)) {

            writer.write("=== " + header + " (" + timestamp() + ") ===");
            writer.newLine();

            for (Movie movie : movies) {
                writer.write(toFileLine(movie));
                writer.newLine();
            }
            writer.newLine();

            out.println("Сохранено " + movies.size() + " фильмов в " + path);

        } catch (IOException e) {
            out.println("Не удалось записать в файл " + path + ": " + e.getMessage());
        }
    }

    /**
     * Дописывает произвольные значения (для будущих доп. заданий).
     */
    public void writeValues(String header, List<String> lines) {
        if (lines == null || lines.isEmpty()) {
            out.println("Нет значений для записи.");
            return;
        }

        try (BufferedWriter writer = Files.newBufferedWriter(
                path,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE,
                StandardOpenOption.APPEND)) {

            writer.write("=== " + header + " (" + timestamp() + ") ===");
            writer.newLine();

            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            writer.newLine();

            out.println("Сохранено " + lines.size() + " значений в " + path);

        } catch (IOException e) {
            out.println("Не удалось записать в файл " + path + ": " + e.getMessage());
        }
    }

    private String toFileLine(Movie movie) {
        return movie.getTitle() + SEPARATOR
                + movie.getGenre() + SEPARATOR
                + movie.getReleaseYear() + SEPARATOR
                + movie.getDuration();
    }

    private String timestamp() {
        return LocalDateTime.now().format(TIMESTAMP_FORMAT);
    }
}