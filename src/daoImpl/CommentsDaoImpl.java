package daoImpl;

import dao.CommentsDao;
import entity.Comments;
import entity.Session;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Date: 2018/12/27 0027
 **/
public class CommentsDaoImpl extends BaseDao implements CommentsDao {
    public CommentsDaoImpl() {
        super(new File("Comments.txt"));
    }

    /**
     * 添加评论
     * @param comments
     */
    @Override
    public void save(Comments comments) {
        ArrayList<Comments> read = read();
        if (read.size() == 0) {
            comments.setId(1);
        } else {
            comments.setId(read.get(read.size() - 1).getId() + 1);
        }
        read.add(comments);
        write(read);
        closeAll();
    }

    /**
     * 查询评论
     * @return
     */
    @Override
    public ArrayList<Comments> queryComments() {
        ArrayList<Comments> read = read();
        return read;
    }

    /**
     * 修改评论
     * @param comments
     */
    @Override
    public void updateComments(Comments comments) {
        ArrayList<Comments> read = read();
        for (int i = 0; i < read.size(); i++) {
            if (comments.getId() == read.get(i).getId()) {
                read.set(i, comments);
            }
        }
        write(read);
        closeAll();
    }

    /**
     * 删除评论
     * @param commentsId
     */
    @Override
    public void deleteComments(int commentsId) {
        ArrayList<Comments> read = read();
        Iterator<Comments> iterator = read.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId() == commentsId) {
                iterator.remove();
            }
        }
        write(read);
        closeAll();

    }

    @Override
    public Comments queryCommentsByID(int commentsId) {
        ArrayList<Comments> read = read();
        for (Comments comments : read) {
            if (comments.getId() == commentsId) {
                return comments;
            }
        }
        closeAll();
        return null;
    }
}
