package dao;

import entity.Comments;
import entity.Session;

import java.util.ArrayList;

/**
 * Date: 2018/12/27 0027
 **/
public interface CommentsDao {

    void save(Comments comments);

    ArrayList<Comments> queryComments();

    void updateComments(Comments comments);

    void deleteComments(int commentsId);

    Comments queryCommentsByID(int commentsId);
}
