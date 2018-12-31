package daoImpl;

import dao.SessionDao;
import entity.Session;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Date: 2018/12/26 0026
 **/
public class SessionDaoImpl extends BaseDao implements SessionDao {

    public SessionDaoImpl() {
        super(new File("Session.txt"));
    }

    @Override
    public void save(Session session) {
        ArrayList<Session> read = read();
        if (read.size() == 0) {
            session.setId(1);
        } else {
            session.setId(read.get(read.size() - 1).getId() + 1);
        }
        read.add(session);
        write(read);
        closeAll();
    }

    @Override
    public ArrayList<Session> querySession() {
        ArrayList<Session> read = read();
        return read;
    }

    @Override
    public void updateSession(Session session) {
        ArrayList<Session> read = read();
        for (int i = 0; i < read.size(); i++) {
            if (read.get(i).getId() == session.getId()) {
                read.set(i, session);
            }
        }
        write(read);
        closeAll();
    }

    @Override
    public void deleteSession(int sessionId) {
        ArrayList<Session> read = read();
        Iterator<Session> iterator = read.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId() == sessionId) {
                iterator.remove();
            }
        }
        write(read);
        closeAll();

    }

    @Override
    public Session querySessionByID(int sessionId) {
        ArrayList<Session> read = read();
        for (Session session : read) {
            if (session.getId() == sessionId) {
                return session;
            }
        }
        return null;
    }
}
