package bizImpl;

import biz.CommentsBiz;
import dao.CommentsDao;
import dao.MovieDao;
import dao.UserDao;
import daoImpl.CommentsDaoImpl;
import daoImpl.MovieDaoImpl;
import daoImpl.UserDaoImpl;
import entity.Comments;
import entity.CommentsPerfect;
import entity.Movie;
import entity.User;

import java.util.ArrayList;

/**
 * Date: 2018/12/27 0027
 **/
public class CommentsBizImpl implements CommentsBiz {

    private CommentsDao commentsDao = new CommentsDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    private MovieDao movieDao = new MovieDaoImpl();

    @Override
    public boolean addComments(Comments comments) {
        //1:评论不能为空
        if (comments == null) {
            return false;
        }
        //2：对应的用户不能为空
        User user = userDao.queryByUserID(comments.getUid());
        if (user == null) {
            return false;
        }
        //对应的电影不能为空
        Movie movie = movieDao.queryById(comments.getMovie_id());
        if (movie == null) {
            return false;
        }
        commentsDao.save(comments);
        return true;
    }

    @Override
    public ArrayList<CommentsPerfect> queryComments() {
        ArrayList<Comments> comments = commentsDao.queryComments();
        ArrayList<CommentsPerfect> commentsPerfects = new ArrayList<>();
        for (Comments comment : comments) {
            User user = userDao.queryByUserID(comment.getUid());
            Movie movie = movieDao.queryById(comment.getMovie_id());
            CommentsPerfect commentsPerfect = new CommentsPerfect(comment, user, movie);
            commentsPerfects.add(commentsPerfect);
        }
        return commentsPerfects;
    }

    @Override
    public boolean updateComments(Comments comments) {

        //1:评论不能为空
        if (comments == null) {
            return false;
        }
        //2：对应的用户不能为空
        User user = userDao.queryByUserID(comments.getUid());
        if (user == null) {
            return false;
        }
        //对应的电影不能为空
        Movie movie = movieDao.queryById(comments.getMovie_id());
        if (movie == null) {
            return false;
        }
        commentsDao.updateComments(comments);
        return true;
    }

    @Override
    public boolean deleteComments(int commentsId) {

        Comments comments = commentsDao.queryCommentsByID(commentsId);
        if (comments == null) {
            return false;
        }
        commentsDao.deleteComments(commentsId);
        return true;
    }

    //返回一个Comments对象
    @Override
    public Comments queryCommentsByID(int commentsId) {
        ArrayList<Comments> comments = commentsDao.queryComments();
        for (Comments c : comments) {
            if (c.getId() == commentsId) {
                return c;
            }
        }
        return null;
    }

    //返回一个多表联查对象
    @Override
    public CommentsPerfect queryCommentsPerfectById(int commentsId) {
        ArrayList<CommentsPerfect> commentsPerfects = queryComments();
        for (CommentsPerfect commentsPerfect : commentsPerfects) {
            if (commentsPerfect.getComments().getId() == commentsId) {
                return commentsPerfect;
            }
        }
        return null;
    }


    /**
     * 根据电影ID查询对应的评论
     *
     * @param movieID
     * @return
     */
    @Override
    public ArrayList<CommentsPerfect> queryCommentsForMovie(int movieID) {
        ArrayList<CommentsPerfect> commentsPerfects = queryComments();
        ArrayList<CommentsPerfect> commentsforMovie = new ArrayList<>();
        //查询所有评论，然后匹配对应电影ID
        for (CommentsPerfect commentsPerfect : commentsPerfects) {
            if (commentsPerfect.getMovie().getId() == movieID) {
                commentsforMovie.add(commentsPerfect);
            }
        }
        return commentsforMovie;
    }
}
