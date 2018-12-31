package dao;

import entity.User;

import java.util.ArrayList;

/**
 * Date: 2018/12/25 0025
 **/
public interface UserDao {


    ArrayList<User> queryUsers();

    User queryByUserID(int userId);

    void addUser(User user);

    void updateUser(User user);




}
