package bizImpl;

import biz.UserBiz;
import dao.UserDao;
import daoImpl.UserDaoImpl;
import entity.User;

import java.util.ArrayList;

/**
 * Date: 2018/12/25 0025
 **/
public class UserBizImpl implements UserBiz {

    private UserDao userDao = new UserDaoImpl();  //dao层用户的对象

    @Override
    public ArrayList<User> queryUsers() {
        return userDao.queryUsers();
    }

    @Override
    public User queryByUserID(int userId) {
        return userDao.queryByUserID(userId);
    }

    @Override
    public boolean regist(User user) {
        ArrayList<User> users = queryUsers();
        for (User u : users) {
            if (u.getName().equals(user.getName())) {
                return false;  //账号唯一，如果账号重复则注册失败
            }
        }
        userDao.addUser(user);
        return true;
    }

    @Override
    public boolean updateUser(User user) {
        //如果用户不存在，则失败
        ArrayList<User> users = queryUsers();
        for (User u : users) {
            if (u.getId() == user.getId()) {
                userDao.updateUser(user);
                return true;
            }

        }
        return false;
    }

    @Override
    public User queryByName(String name) {
        ArrayList<User> users = queryUsers();
        for (User u : users) {
            if (u.getName().equals(name)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public boolean login(String name, String passwd) {
        ArrayList<User> users = queryUsers();
        for (User u : users) {
            if (u.getName().equals(name) && u.getPasswd().equals(passwd)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean recharge(int userId, double money) {
        User user = userDao.queryByUserID(userId);
        if (user == null) {
            return false;  //用户不存在，直接返回false
        }
        if (money >= 500) {
            //会员，需要提升等级
            user.setBalance(user.getBalance() + money);
            user.setLevel(user.getLevel() + 1);

        } else { //普通充值，不需要提升等级
            user.setBalance(user.getBalance() + money);
        }
        //更新数据
        userDao.updateUser(user);
        return true;
    }


    @Override
    public ArrayList<User> queryUserOfVip() {
        ArrayList<User> users = queryUsers();
        ArrayList<User> vipUser = new ArrayList<>();
        for (User user : users) {
            if (user.getLevel() > 0) {   //等级0为普通用户，1以上为VIP
                vipUser.add(user);
            }
        }
        return vipUser;
    }


}
