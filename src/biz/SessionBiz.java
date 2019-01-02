package biz;


import entity.Session;
import entity.SessionPerfect;


import java.util.ArrayList;

public interface SessionBiz {
    //添加场次
    boolean addSession(Session session);

    //查询场次
    ArrayList<SessionPerfect> querySession();

    //修改场次
    boolean updateSession(Session session);

    //删除场次
    boolean deleteSession(int sessionId);

    //查询单个场次
    Session querySessionByID(int sessionId);

    //根据场厅查询场次
    ArrayList<SessionPerfect> queryByHid(int hallId);

    //查询场次
    SessionPerfect queryPerfectBySeesion(int sessionId);

    //根据电影和影院查询场次
    ArrayList<SessionPerfect> querySessionByCid(int cid, int mid);

    //修改场次的开始时间
    boolean updateSessionHasNoTime(Session session);


}
