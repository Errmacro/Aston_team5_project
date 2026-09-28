package main.comparator;

import main.model.Movie;
import java.util.Comparator;

public class MovieGenreComparator implements Comparator<Movie> {

    @Override
    public int compare(Movie first, Movie second) {
        return first.getGenre().compareTo(second.getGenre());
    }
}
