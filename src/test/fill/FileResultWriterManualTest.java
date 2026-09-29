package main.fill;

import main.model.Movie;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileResultWriterManualTest {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) throws Exception {
        System.out.println("=== Тесты FileResultWriter (APPEND) ===");

        Path testFile = Paths.get("test_results.txt");
        Files.deleteIfExists(testFile);

        testAppendMode(testFile);
        testFileFormat(testFile);
        testEmptyListIgnored(testFile);

        Files.deleteIfExists(testFile);

        System.out.println();
        System.out.println("Пройдено: " + passed + ", Провалено: " + failed);
        if (failed > 0) System.exit(1);
    }

    private static void testAppendMode(Path testFile) throws Exception {
        PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        FileResultWriter writer = new FileResultWriter(testFile, out);

        List<Movie> batch1 = List.of(
                Movie.builder().title("Матрица").genre("Фантастика").releaseYear(1999).duration(136).build()
        );
        List<Movie> batch2 = List.of(
                Movie.builder().title("Интерстеллар").genre("Фантастика").releaseYear(2014).duration(169).build()
        );

        writer.writeMovies(batch1, "Блок 1");
        writer.writeMovies(batch2, "Блок 2");

        String content = Files.readString(testFile, StandardCharsets.UTF_8);

        check(content.contains("Блок 1"), "Файл содержит блок 1");
        check(content.contains("Блок 2"), "Файл содержит блок 2 (APPEND сработал)");
        check(content.contains("Матрица") && content.contains("Интерстеллар"),
                "Оба фильма сохранены");
    }

    private static void testFileFormat(Path testFile) throws Exception {
        Files.deleteIfExists(testFile);
        PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        FileResultWriter writer = new FileResultWriter(testFile, out);

        writer.writeMovies(List.of(
                Movie.builder().title("Тест").genre("Драма").releaseYear(2020).duration(120).build()
        ), "Проверка формата");

        List<String> lines = Files.readAllLines(testFile, StandardCharsets.UTF_8);
        String movieLine = lines.stream()
                .filter(l -> !l.isEmpty() && !l.startsWith("==="))
                .findFirst().orElse("");

        check(movieLine.equals("Тест;Драма;2020;120"),
                "Формат: 'Название;Жанр;Год;Продолжительность'");
    }

    private static void testEmptyListIgnored(Path testFile) throws Exception {
        Files.deleteIfExists(testFile);
        PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        FileResultWriter writer = new FileResultWriter(testFile, out);

        writer.writeMovies(new ArrayList<>(), "Пусто");

        check(!Files.exists(testFile) || Files.readString(testFile).isEmpty(),
                "Пустой список не создаёт файл");
    }

    private static void check(boolean ok, String name) {
        if (ok) { System.out.println("PASS: " + name); passed++; }
        else    { System.out.println("FAIL: " + name); failed++; }
    }
}