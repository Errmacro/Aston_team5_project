package com.sortingapp.validation;

/**
 * Сигнализирует о том, что введённые пользователем (или прочитанные из файла)
 * данные не прошли проверку корректности.
 * <p>
 * Является checked-исключением намеренно: вызывающий код обязан явно
 * обработать некорректный ввод, а не позволить программе упасть.
 */
public class ValidationException extends Exception {

    public ValidationException(String message) {
        super(message);
    }
}
