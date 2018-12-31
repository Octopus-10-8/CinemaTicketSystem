package dao;


import entity.Movie;

import java.util.ArrayList;

public interface MovieDao {

    void save(Movie movie);

    ArrayList<Movie>  queryMovie();

    void updateMovie(Movie movie);

    void deleteMovie(int movieId);

    Movie queryById(int movieId);


}
