package biz;

import entity.User;

import java.util.ArrayList;

/**
 * Date: 2018/12/25 0025
 **/
public interface UserBiz {

    ArrayList<User> queryUsers();

    User queryByUserID(int userId);

    boolean regist(User user);

    boolean updateUser(User user);

    User queryByName(String name);

    boolean login(String name, String passwd);


    boolean recharge(int userId, double money);


    ArrayList<User> queryUserOfVip();


}
