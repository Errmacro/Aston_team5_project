package main;

import main.fill.DataSource;
import main.fill.FileDataSource;
import main.fill.ManualDataSource;
import main.fill.RandomDataSource;
import main.model.Movie;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

// Точка входа в программу: выбор способа заполнения списка фильмов и вывод результата.
public class Main {

    private static final int MAX_SIZE = 100;
    private static final String DEFAULT_FILE = "data/movies.txt";

    public static void main(String[] args) {
        // Один Scanner на всю программу: второй Scanner на System.in может "съесть" часть ввода.
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        try {
            DataSource dataSource = chooseDataSource(scanner, out);

            int size = readInt(scanner, out, "Сколько фильмов загрузить? ", 1, MAX_SIZE);
            List<Movie> movies = dataSource.load(size);

            out.println();
            out.println("Загруженные фильмы (" + movies.size() + "):");
            for (Movie movie : movies) {
                out.println(movie);
            }
        } catch (NoSuchElementException e) {
            // Scanner бросает его, когда ввод закончился (Ctrl+D / Ctrl+Z или конец перенаправленного файла).
            out.println();
            out.println("Ввод завершён. Работа программы прекращена.");
        }
    }

    // Спрашивает способ заполнения; дальше программа работает с любым источником через DataSource.
    private static DataSource chooseDataSource(Scanner scanner, PrintStream out) {
        out.println("Способ заполнения списка:");
        out.println("  1 — вручную");
        out.println("  2 — из файла");
        out.println("  3 — случайно");
        int choice = readInt(scanner, out, "Ваш выбор: ", 1, 3);

        switch (choice) {
            case 1:
                return new ManualDataSource(scanner, out);
            case 2:
                return new FileDataSource(readFilePath(scanner, out), out);
            default:
                return new RandomDataSource();
        }
    }

    // Спрашивает путь к файлу, пока пользователь не укажет существующий файл.
    // Если просто нажать Enter, берётся файл по умолчанию.
    private static Path readFilePath(Scanner scanner, PrintStream out) {
        while (true) {
            String input = readLine(scanner, out, "Путь к файлу (Enter — " + DEFAULT_FILE + "): ").trim();
            Path path = Path.of(input.isEmpty() ? DEFAULT_FILE : input);
            if (Files.isRegularFile(path)) {
                return path;
            }
            out.println("Файл не найден: " + path.toAbsolutePath());
        }
    }

    // Считывает целое число в диапазоне [min, max] включительно, иначе повторяет запрос.
    private static int readInt(Scanner scanner, PrintStream out, String prompt, int min, int max) {
        while (true) {
            String input = readLine(scanner, out, prompt).trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
                out.println("Введите число от " + min + " до " + max);
            } catch (NumberFormatException e) {
                out.println("Введите целое число");
            }
        }
    }

    // Выводит подсказку и считывает одну строку ввода.
    private static String readLine(Scanner scanner, PrintStream out, String prompt) {
        out.print(prompt);
        return scanner.nextLine();
    }
}
