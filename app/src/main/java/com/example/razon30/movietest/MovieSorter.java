package com.example.razon30.movietest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by razon30 on 17-02-16.
 */
public class MovieSorter {

    public void sortMoviesByName(ArrayList<Movie> movies){

        Collections.sort(movies, new Comparator<Movie>() {
            @Override
            public int compare(Movie lhs, Movie rhs) {
                return lhs.getMovie_name().compareTo(rhs.getMovie_name());
            }
        });

    }

    public void sortMovieByDate(ArrayList<Movie> movies){

        Collections.sort(movies, new Comparator<Movie>() {
            @Override
            public int compare(Movie lhs, Movie rhs) {

                if(lhs.getMovie_release_date()!=null && rhs.getMovie_release_date()!=null)
                {
                    return rhs.getMovie_release_date().compareTo(lhs.getMovie_release_date());
                }
                else {
                    return 0;
                }
            }
        });

    }
    public void sortMoviesByRating(ArrayList<Movie> movies){
        Collections.sort(movies, new Comparator<Movie>() {
            @Override
            public int compare(Movie lhs, Movie rhs) {
                int ratingLhs = lhs.getMovie_audience_score();
                int ratingRhs = rhs.getMovie_audience_score();
                if (ratingLhs < ratingRhs) {
                    return 1;
                } else if (ratingLhs > ratingRhs) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
    }


}
