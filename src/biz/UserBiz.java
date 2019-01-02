package biz;

import entity.User;

import java.util.ArrayList;

/**
 * Date: 2018/12/25 0025
 **/
public interface UserBiz {

    //查询用户信息
    ArrayList<User> queryUsers();

    //查询单个用户
    User queryByUserID(int userId);

    //注册
    boolean regist(User user);

    //修改用户信息
    boolean updateUser(User user);

    //根据账号名查询
    User queryByName(String name);

    //登陆
    boolean login(String name, String passwd);


    boolean recharge(int userId, double money);


    ArrayList<User> queryUserOfVip();


}
