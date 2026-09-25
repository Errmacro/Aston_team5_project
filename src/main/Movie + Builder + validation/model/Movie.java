package com.sortingapp.model;

import java.util.Objects;

/**
 * Неизменяемая модель фильма.
 * <p>
 * Экземпляры создаются исключительно через {@link Builder}, что гарантирует
 * невозможность получить объект с некорректными (невалидированными) полями.
 */
public final class Movie {

    private final String title;
    private final int releaseYear;
    private final String genre;

    private Movie(Builder builder) {
        this.title = builder.title;
        this.releaseYear = builder.releaseYear;
        this.genre = builder.genre;
    }

    public String getTitle() {
        return title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getGenre() {
        return genre;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Movie movie = (Movie) other;
        return releaseYear == movie.releaseYear
                && Objects.equals(title, movie.title)
                && Objects.equals(genre, movie.genre);
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + releaseYear;
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Movie{"
                + "title='" + title + '\''
                + ", releaseYear=" + releaseYear
                + ", genre='" + genre + '\''
                + '}';
    }

    /**
     * Builder для {@link Movie}. Валидацию значений выполняет
     * {@link com.sortingapp.validation.MovieValidator} на этапе {@link #build()},
     * поэтому построить некорректный объект невозможно.
     */
    public static final class Builder {

        private String title;
        private int releaseYear;
        private String genre;

        private Builder() {
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder releaseYear(int releaseYear) {
            this.releaseYear = releaseYear;
            return this;
        }

        public Builder genre(String genre) {
            this.genre = genre;
            return this;
        }

        /**
         * Проверяет накопленные значения и создаёт объект {@link Movie}.
         *
         * @throws com.sortingapp.validation.ValidationException если хотя бы одно
         *                                                        поле не прошло валидацию
         */
        public Movie build() throws com.sortingapp.validation.ValidationException {
            com.sortingapp.validation.MovieValidator.validateTitle(title);
            com.sortingapp.validation.MovieValidator.validateReleaseYear(releaseYear);
            com.sortingapp.validation.MovieValidator.validateGenre(genre);
            return new Movie(this);
        }
    }
}
