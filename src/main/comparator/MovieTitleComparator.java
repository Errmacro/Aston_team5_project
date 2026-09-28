package main.comparator;


import main.model.Movie;

import java.util.Comparator;

public class MovieTitleComparator implements Comparator<Movie> {
    @Override
    public int compare(Movie first, Movie second) {
        return first.getTitle().compareTo(second.getTitle());
    }
}