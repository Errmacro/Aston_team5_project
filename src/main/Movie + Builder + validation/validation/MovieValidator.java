package com.sortingapp.validation;

import java.time.Year;
import java.util.regex.Pattern;

/**
 * Набор статических правил валидации для полей {@link com.sortingapp.model.Movie}.
 * <p>
 * Используется как при построении объекта через Builder, так и при разборе
 * "сырых" строковых значений (ручной ввод, чтение из файла).
 */
public final class MovieValidator {

    private static final int MIN_YEAR = 1888; // год выхода первого известного фильма
    private static final int MAX_YEAR = Year.now().getValue() + 1;

    private static final Pattern TEXT_PATTERN =
            Pattern.compile("^[A-Za-zА-Яа-яЁё0-9][A-Za-zА-Яа-яЁё0-9\\s\\-:.,!?'\"]{0,99}$");

    private MovieValidator() {
    }

    /**
     * Проверяет название фильма: не пусто, не длиннее 100 символов,
     * содержит только допустимые символы.
     */
    public static void validateTitle(String title) throws ValidationException {
        validateText(title, "Название фильма");
    }

    /**
     * Проверяет жанр фильма по тем же правилам, что и название.
     */
    public static void validateGenre(String genre) throws ValidationException {
        validateText(genre, "Жанр");
    }

    /**
     * Проверяет год выпуска: должен входить в диапазон [1888; текущий год + 1].
     */
    public static void validateReleaseYear(int releaseYear) throws ValidationException {
        if (releaseYear < MIN_YEAR || releaseYear > MAX_YEAR) {
            throw new ValidationException(
                    "Год выпуска должен быть в диапазоне от " + MIN_YEAR + " до " + MAX_YEAR
                            + ", получено: " + releaseYear);
        }
    }

    /**
     * Разбирает "сырую" строку с годом и проверяет её.
     *
     * @throws ValidationException если строка не является числом или год вне диапазона
     */
    public static int validateReleaseYear(String rawValue) throws ValidationException {
        if (rawValue == null || rawValue.trim().isEmpty()) {
            throw new ValidationException("Год выпуска не может быть пустым");
        }
        int year;
        try {
            year = Integer.parseInt(rawValue.trim());
        } catch (NumberFormatException e) {
            throw new ValidationException("Год выпуска должен быть целым числом, получено: '" + rawValue + "'");
        }
        validateReleaseYear(year);
        return year;
    }

    private static void validateText(String value, String fieldName) throws ValidationException {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException(fieldName + " не может быть пустым");
        }
        String trimmed = value.trim();
        if (!TEXT_PATTERN.matcher(trimmed).matches()) {
            throw new ValidationException(fieldName + " содержит недопустимые символы или длину: '" + trimmed + "'");
        }
    }
}
