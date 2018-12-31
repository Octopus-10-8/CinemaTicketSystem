package daoImpl;

import dao.UserDao;
import entity.User;

import java.io.File;
import java.util.ArrayList;

/**
 * Date: 2018/12/25 0025
 **/
public class UserDaoImpl extends BaseDao implements UserDao {

    public UserDaoImpl() {
        super(new File("users.txt"));
    }

    private ArrayList<User> users;    //用户对象集合

    @Override
    public ArrayList<User> queryUsers() {
        users = read();
        return users;
    }

    @Override
    public User queryByUserID(int userId) {
        users = read();
        for (User user : users) {
            if (user.getId() == userId) {
                return user;
            }
        }
        closeAll();
        return null;
    }

    @Override
    public void addUser(User user) {
        users = read();
        if (users.size() == 0) {
            user.setId(1);
        } else {
            user.setId(users.get(users.size() - 1).getId() + 1);
        }
        users.add(user);
        write(users);
        closeAll();

    }

    @Override
    public void updateUser(User user) {
        users = read();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == user.getId()) {
                users.set(i, user);
            }
        }
        write(users);
        closeAll();
    }
}
