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

    /**
     * 添加电影
     * @param movie
     */
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

    /**
     * 查询电影
     * @return
     */
    @Override
    public ArrayList<Movie> queryMovie() {
        movieArrayList = read();

        return movieArrayList;
    }

    /**
     * 修改电影
     * @param movie
     */
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

    /**
     * 删除电影
     * @param movieId
     */
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

    /**
     * 通过ID查询电影
     * @param movieId
     * @return
     */
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
