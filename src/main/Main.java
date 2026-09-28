package main;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.Scanner;

// Точка входа в программу.
// Создает объекты для ввода вывода и запускает приложеине.

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(
                System.in,
                StandardCharsets.UTF_8
        );

        PrintStream out = new PrintStream(
                System.out,
                true,
                StandardCharsets.UTF_8
        );

        try {
            new Application(scanner, out).run();
        } catch (NoSuchElementException e) {
            // Обрабатываем ситуацию, когда ввод пользователя неожиданно закончился.
            out.println();
            out.println("Ввод завершён. Работа программы прекращена.");
        }
    }
}
