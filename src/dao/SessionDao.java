package dao;

import entity.Hall;
import entity.Session;

import java.util.ArrayList;

/**
 * Date: 2018/12/26 0026
 * <p>
 * 电影场次
 **/
public interface SessionDao {


    void save(Session session);

    ArrayList<Session> querySession();

    void updateSession(Session session);

    void deleteSession(int sessionId);

    Session querySessionByID(int sessionId);


}
