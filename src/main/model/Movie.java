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
    private final int duration;

    private Movie(Builder builder) {
        this.title = builder.title;
        this.releaseYear = builder.releaseYear;
        this.genre = builder.genre;
        this.duration = builder.duration;
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

    public int getDuration() {
        return duration;
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
                && duration == movie.duration
                && Objects.equals(title, movie.title)
                && Objects.equals(genre, movie.genre);
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + releaseYear;
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        result = 31 * result + duration;
        return result;
    }

    @Override
    public String toString() {
        return "Movie{"
                + "title='" + title + '\''
                + ", releaseYear=" + releaseYear
                + ", genre='" + genre + '\''
                + ", duration=" + duration
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
        private int duration;

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

        public Builder duration(int duration) {
            this.duration = duration;
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
            com.sortingapp.validation.MovieValidator.validateDuration(duration);
            return new Movie(this);
        }
    }
}
