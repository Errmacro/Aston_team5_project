package fill;

import main.fill.FileDataSource;
import main.model.Movie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileDataSourceTest {

    @TempDir
    Path tempDir;

    private final ByteArrayOutputStream output = new ByteArrayOutputStream();

    private FileDataSource sourceWithContent(String content) throws IOException {
        Path file = tempDir.resolve("movies.txt");
        Files.writeString(file, content, StandardCharsets.UTF_8);
        return new FileDataSource(file, new PrintStream(output, true, StandardCharsets.UTF_8));
    }

    private String printed() {
        return output.toString(StandardCharsets.UTF_8);
    }

    @Test
    void shouldLoadValidLines() throws Exception {
        FileDataSource source = sourceWithContent("Матрица;Фантастика;1999;136\nНачало;Фантастика;2010;148\n");

        List<Movie> movies = source.load(2);

        Movie expected = Movie.builder()
                .title("Матрица").genre("Фантастика").releaseYear(1999).duration(136).build();
        assertEquals(2, movies.size());
        assertEquals(expected, movies.get(0));
        assertEquals("Начало", movies.get(1).getTitle());
    }

    @Test
    void shouldLoadNoMoreThanSize() throws IOException {
        FileDataSource source = sourceWithContent("A;Драма;2000;100\nB;Драма;2001;100\nC;Драма;2002;100\n");

        assertEquals(2, source.load(2).size());
    }

    @Test
    void shouldSkipCommentsEmptyLinesAndBom() throws IOException {
        FileDataSource source = sourceWithContent("﻿# комментарий\n\n   \nМатрица;Фантастика;1999;136\n");

        List<Movie> movies = source.load(1);

        assertEquals(1, movies.size());
        assertEquals("Матрица", movies.get(0).getTitle());
    }

    @Test
    void shouldSkipInvalidLinesAndReportLineNumbers() throws IOException {
        FileDataSource source = sourceWithContent(
                "Матрица;Фантастика;1999;136\n"   // 1 — ок
                        + ";Драма;2000;100\n"          // 2 — пустое название
                        + "Аватар;Фантастика;abc;162\n" // 3 — год не число
                        + "Нетерпимость;Драма;1500;163\n" // 4 — год вне диапазона
                        + "Титаник;Драма;1997\n"       // 5 — не хватает поля
                        + "Джентльмены;;2019;113\n"    // 6 — пустой жанр
                        + "Звёздные войны;Фантастика;1977;121\n"); // 7 — ок

        List<Movie> movies = source.load(10);

        assertEquals(2, movies.size());
        assertEquals("Звёздные войны", movies.get(1).getTitle());
        for (int line = 2; line <= 6; line++) {
            assertTrue(printed().contains("Строка " + line + " пропущена"), "нет сообщения о строке " + line);
        }
        assertTrue(printed().contains("только 2 корректных фильмов из 10"));
    }

    @Test
    void shouldReturnEmptyListWhenFileMissing() {
        FileDataSource source = new FileDataSource(tempDir.resolve("missing.txt"),
                new PrintStream(output, true, StandardCharsets.UTF_8));

        assertTrue(source.load(5).isEmpty());
        assertTrue(printed().contains("Не удалось прочитать файл"));
    }

    @Test
    void shouldThrowOnNonPositiveSize() throws IOException {
        FileDataSource source = sourceWithContent("");

        assertThrows(IllegalArgumentException.class, () -> source.load(0));
    }
}
