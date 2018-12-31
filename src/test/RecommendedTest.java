package test;

import biz.UserBiz;
import bizImpl.UserBizImpl;
import dao.UserDao;
import entity.User;

import java.util.ArrayList;

/**
 * 个性化推荐
 * Date: 2018/12/29 0029
 **/
public class RecommendedTest {

    private static UserBiz userBiz = new UserBizImpl();

    public static void main(String[] args) {
        ArrayList<User> users = userBiz.queryUsers();
        User user1 = userBiz.queryByName("123");
        int max = 1000;
        User u=null;
        for (User user : users) {
            int i = EditDistance(user.toString(), user1.toString());
            System.out.println(i);
            if (i < max) {
                max = i;
                u=user;
            }
        }
        System.out.println(u+"  "+max);
    }

    private static int EditDistance(String source, String target) {
        char[] sources = source.toCharArray();
        char[] targets = target.toCharArray();
        int sourceLen = sources.length;
        int targetLen = targets.length;
        int[][] d = new int[sourceLen + 1][targetLen + 1];
        for (int i = 0; i <= sourceLen; i++) {
            d[i][0] = i;
        }
        for (int i = 0; i <= targetLen; i++) {
            d[0][i] = i;
        }

        for (int i = 1; i <= sourceLen; i++) {
            for (int j = 1; j <= targetLen; j++) {
                if (sources[i - 1] == targets[j - 1]) {
                    d[i][j] = d[i - 1][j - 1];
                } else {
                    //插入
                    int insert = d[i][j - 1] + 1;
                    //删除
                    int delete = d[i - 1][j] + 1;
                    //替换
                    int replace = d[i - 1][j - 1] + 1;
                    d[i][j] = Math.min(insert, delete) > Math.min(delete, replace) ? Math.min(delete, replace) :
                            Math.min(insert, delete);
                }
            }
        }
        return d[sourceLen][targetLen];
    }


}

class Stu {
    private String address;  //地址
    private String gender;
    private String hobby;
    private int age;

}


