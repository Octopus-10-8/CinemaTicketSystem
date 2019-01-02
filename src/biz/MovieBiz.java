package biz;

import entity.Movie;
import entity.Session;

import java.util.ArrayList;

/**
 * Date: 2018/12/25 0025
 **/
public interface MovieBiz {
    //添加电影
    boolean save(Movie movie);

    //查询电影
    ArrayList<Movie> queryMovie();

    //修改电影
    boolean updateMovie(Movie movie);

    //删除电影
    boolean deleteMovie(int movieId);

    //查询单个电影
    Movie queryById(int movieId);

    //模糊查询电影
    ArrayList<Movie> queryByKey(String key);

    //根据电影查询对应的场次
    ArrayList<Session> querySessionBymid(int movieId);
}
