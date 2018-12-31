package biz;


import entity.Session;
import entity.SessionPerfect;


import java.util.ArrayList;

public interface SessionBiz {
    boolean addSession(Session session);

    ArrayList<SessionPerfect> querySession();

    boolean updateSession(Session session);

    boolean deleteSession(int sessionId);

    Session querySessionByID(int sessionId);

    ArrayList<SessionPerfect> queryByHid(int hallId);

    SessionPerfect queryPerfectBySeesion(int sessionId);

    ArrayList<SessionPerfect> querySessionByCid(int cid, int mid);

    boolean updateSessionHasNoTime(Session session);


}
