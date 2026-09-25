package com.example.sorting.comparator;

import com.example.sorting.model.Movie;
import java.util.Comparator;

public class MovieTitleComparator implements Comparator<Movie> {
    @Override
    public int compare(Movie first, Movie second) {
        return first.getTitle().compareTo(second.getTitle());
    }
}