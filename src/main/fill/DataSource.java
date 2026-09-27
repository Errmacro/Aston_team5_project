package main.fill;
import main.model.Movie;
import java.util.List;

// Источник данных для заполнения списка фильмов (вручную, из файла, случайно).
public interface DataSource {
    // Возвращает список уже проверенных (валидных) фильмов.
    List<Movie> load(int size);
}
