package main;

import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

/** // Отвечает за чтение и проверку пользовательского ввода.
// Содержит методы для чтения чисел, строк и пути к файлу.*/
public class InputReader {

    private static final String DEFAULT_FILE = "data/movies.txt";

    private final Scanner scanner;
    private final PrintStream out;

   /** // Создаёт объект для чтения пользовательского ввода.
    // @param scanner объект для чтения данных из консоли
    // @param out поток для вывода сообщений пользователю*/
    public InputReader(Scanner scanner, PrintStream out) {
        this.scanner = scanner;
        this.out = out;
    }

    /**  // Читает целое число из консоли и проверяет,
    // что оно находится в заданном диапазоне.
    // Повторяет запрос, пока пользователь не введёт корректное значение.
    // @param prompt сообщение перед вводом
    // @param min минимально допустимое значение
    // @param max максимально допустимое значение
    // @return введённое корректное число*/
    public int readInt(
            String prompt,
            int min,
            int max
    ) {
        while (true) {
            String input = readLine(prompt).trim();

            try {
                int value = Integer.parseInt(input);

                if (value >= min && value <= max) {
                    return value;
                }

                out.println(
                        "Введите число от " + min + " до " + max
                );

            } catch (NumberFormatException e) {
                out.println("Введите целое число");
            }
        }
    }

    /** Выводит приглашение и считывает одну строку из консоли.
    // @param prompt сообщение перед вводом
    // @return введённая строка
    //  */
    public String readLine(String prompt) {
        out.print(prompt);
        return scanner.nextLine();
    }

    /**
    // * Читает путь к файлу и проверяет, существует ли указанный файл.
    // * Если пользователь нажимает Enter без ввода пути,
    // * используется путь к файлу по умолчанию.
    // *
    // * @return корректный путь к существующему файлу
    // */
    public Path readFilePath() {
        while (true) {
            String input = readLine(
                    "Путь к файлу (Enter — " + DEFAULT_FILE + "): "
            ).trim();

            Path path = Path.of(
                    input.isEmpty()
                            ? DEFAULT_FILE
                            : input
            );

            if (Files.isRegularFile(path)) {
                return path;
            }

            out.println(
                    "Файл не найден: "
                            + path.toAbsolutePath()
            );
        }
    }

    /**
     * Возвращает используемый Scanner.
     * Нужен для передачи одного общего Scanner в ManualDataSource.
     *
     * @return текущий Scanner
     */
    public Scanner getScanner() {
        return scanner;
    }
}
