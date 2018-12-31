package daoImpl;

import dao.MovieDao;

import entity.Movie;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Date: 2018/12/25 0025
 **/
public class MovieDaoImpl extends BaseDao implements MovieDao {
    private ArrayList<Movie> movieArrayList = null;


    public MovieDaoImpl() {
        super(new File("movie.txt"));
    }

    @Override
    public void save(Movie movie) {
        movieArrayList = read();
        if (movieArrayList.size() == 0) {
            movie.setId(1);
        } else {
            movie.setId(movieArrayList.get(movieArrayList.size() - 1).getId() + 1);
        }
        movieArrayList.add(movie);
        write(movieArrayList);
        closeAll();
    }

    @Override
    public ArrayList<Movie> queryMovie() {
        movieArrayList = read();

        return movieArrayList;
    }

    @Override
    public void updateMovie(Movie movie) {
        movieArrayList = read();
        for (int i = 0; i < movieArrayList.size(); i++) {
            if (movieArrayList.get(i).getId() == movie.getId()) {
                movieArrayList.set(i, movie);
            }
        }
        write(movieArrayList);
        closeAll();
    }

    @Override
    public void deleteMovie(int movieId) {

        movieArrayList = read();
        Iterator<Movie> iterator = movieArrayList.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId() == movieId) {
                iterator.remove();
            }
        }
        write(movieArrayList);
        closeAll();


    }

    @Override
    public Movie queryById(int movieId) {
        movieArrayList = read();
        for (Movie movie : movieArrayList) {
            if (movie.getId() == movieId) {
                return movie;
            }
        }
        return null;
    }
}
