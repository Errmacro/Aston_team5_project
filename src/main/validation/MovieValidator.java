package main.validation;

import java.time.Year;

/**
 * Набор статических правил валидации для полей {@link main.model.Movie}.
 * Используется как при построении объекта через Builder, так и при разборе
 * "сырых" строковых значений (ручной ввод, чтение из файла).
 */
public final class MovieValidator {

    private static final int MAX_TEXT_LENGTH = 100;

    private static final int MIN_YEAR = 1888; // год выхода первого известного фильма
    private static final int MAX_YEAR = Year.now().getValue() + 1; // допускаем уже анонсированные фильмы

    private static final int MIN_DURATION = 1;     // минимальная продолжительность фильма, минут
    private static final int MAX_DURATION = 1200;  // максимальная продолжительность фильма, минут

    private MovieValidator() {
    }

    public static void validateTitle(String title) throws ValidationException {
        validateText(title, "Название фильма");
    }

    public static void validateGenre(String genre) throws ValidationException {
        validateText(genre, "Жанр");
    }

    public static void validateReleaseYear(int releaseYear) throws ValidationException {
        validateRange(releaseYear, MIN_YEAR, MAX_YEAR, "Год выпуска");
    }

    public static void validateDuration(int duration) throws ValidationException {
        validateRange(duration, MIN_DURATION, MAX_DURATION, "Продолжительность (мин)");
    }

    /** Разбирает "сырую" строку с годом, проверяет его и возвращает. */
    public static int validateReleaseYear(String rawValue) throws ValidationException {
        int releaseYear = parseInt(rawValue, "Год выпуска");
        validateReleaseYear(releaseYear);
        return releaseYear;
    }

    /** Разбирает "сырую" строку с продолжительностью, проверяет её и возвращает. */
    public static int validateDuration(String rawValue) throws ValidationException {
        int duration = parseInt(rawValue, "Продолжительность");
        validateDuration(duration);
        return duration;
    }

    private static void validateText(String value, String fieldName) throws ValidationException {
        if (value == null || value.isBlank()) {
            throw new ValidationException(fieldName + " не может быть пустым");
        }
        if (value.trim().length() > MAX_TEXT_LENGTH) {
            throw new ValidationException(fieldName + ": не больше " + MAX_TEXT_LENGTH + " символов");
        }
    }

    private static void validateRange(int value, int min, int max, String fieldName) throws ValidationException {
        if (value < min || value > max) {
            throw new ValidationException(
                    fieldName + ": допустимо от " + min + " до " + max + ", получено: " + value);
        }
    }

    private static int parseInt(String rawValue, String fieldName) throws ValidationException {
        try {
            return Integer.parseInt(rawValue.trim());
        } catch (NumberFormatException e) {
            throw new ValidationException(fieldName + " должен быть целым числом, получено: '" + rawValue + "'");
        }
    }
}
