package main.fill;

import main.model.Movie;
import main.validation.MovieValidator;
import main.validation.ValidationException;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

// Реализация DataSource, которая считывает данные о фильмах из текстового файла (UTF-8).
// Формат строки: Название;Жанр;Год выхода;Продолжительность (мин)
// Пустые строки и строки, начинающиеся с #, пропускаются.
// Некорректные строки тоже пропускаются с сообщением о номере строки и причине ошибки.
public class FileDataSource implements DataSource {

    private static final int FIELD_COUNT = 4;
    private final Path path;
    private final PrintStream out;

    public FileDataSource(Path path, PrintStream out) {
        if (path == null || out == null) {
            throw new IllegalArgumentException("Path и PrintStream не могут быть null");
        }
        this.path = path;
        this.out = out;
    }

    @Override
    public List<Movie> load(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Размер списка должен быть положительным");
        }

        List<String> lines;
        try {
            lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            out.println("Не удалось прочитать файл: " + path);
            return new ArrayList<>();
        }

        List<Movie> movies = new ArrayList<>(size);
        for (int i = 0; i < lines.size() && movies.size() < size; i++) {
            // Убираем пробелы и невидимый символ BOM, который иногда добавляет в начало файла Блокнот Windows.
            String line = lines.get(i).replace("﻿", "").trim();
            if (line.isEmpty() || line.startsWith("#")) {
                continue;
            }

            try {
                movies.add(parseLine(line));
            } catch (ValidationException e) {
                out.println("  Строка " + (i + 1) + " пропущена: " + e.getMessage());
            }
        }

        if (movies.size() < size) {
            out.println("В файле нашлось только " + movies.size() + " корректных фильмов из " + size);
        }
        return movies;
    }

    // Превращает строку вида "Матрица;Фантастика;1999;136" в объект Movie.
    private Movie parseLine(String line) throws ValidationException {
        String[] parts = line.split(";", -1);
        if (parts.length != FIELD_COUNT) {
            throw new ValidationException("должно быть " + FIELD_COUNT + " поля через ';'");
        }

        // Год и продолжительность нужно сначала разобрать из строки; название и жанр проверит build().
        return Movie.builder()
                .title(parts[0].trim())
                .genre(parts[1].trim())
                .releaseYear(MovieValidator.validateReleaseYear(parts[2]))
                .duration(MovieValidator.validateDuration(parts[3]))
                .build();
    }
}
