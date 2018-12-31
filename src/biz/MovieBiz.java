package biz;

import entity.Movie;
import entity.Session;

import java.util.ArrayList;

/**
 * Date: 2018/12/25 0025
 **/
public interface MovieBiz {
    boolean save(Movie movie);

    ArrayList<Movie> queryMovie();

    boolean updateMovie(Movie movie);

    boolean deleteMovie(int movieId);

    Movie queryById(int movieId);

    ArrayList<Movie> queryByKey(String key);

    ArrayList<Session> querySessionBymid(int movieId);
}
