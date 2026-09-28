package main.comparator;

import main.model.Movie;
import java.util.Comparator;

public class MovieDurationComparator implements Comparator<Movie> {
    @Override
    public int compare(Movie first, Movie second) {
        return Integer.compare(first.getDuration(), second.getDuration());
    }
}