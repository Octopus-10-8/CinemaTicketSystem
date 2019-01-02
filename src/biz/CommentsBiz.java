package biz;

import entity.Comments;
import entity.CommentsPerfect;

import java.util.ArrayList;

public interface CommentsBiz {
    //添加评论
    boolean addComments(Comments comments);

    //查询评论
    ArrayList<CommentsPerfect> queryComments();

    //修改评论
    boolean updateComments(Comments comments);

    //删除评论
    boolean deleteComments(int commentsId);

    //根据ID查询评论
    Comments queryCommentsByID(int commentsId);

    CommentsPerfect queryCommentsPerfectById(int commentsId);

    //查询某部电影的评论
    ArrayList<CommentsPerfect> queryCommentsForMovie(int movieID);
}
