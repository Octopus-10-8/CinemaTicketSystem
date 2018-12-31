package biz;

import entity.Comments;
import entity.CommentsPerfect;

import java.util.ArrayList;

public interface CommentsBiz {

    boolean addComments(Comments comments);

    ArrayList<CommentsPerfect> queryComments();

    boolean updateComments(Comments comments);

    boolean deleteComments(int commentsId);

    Comments queryCommentsByID(int commentsId);

    CommentsPerfect queryCommentsPerfectById(int commentsId);

    ArrayList<CommentsPerfect>  queryCommentsForMovie(int  movieID);
}
