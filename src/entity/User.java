package entity;

import utils.Utils;

import java.io.Serializable;

/**
 * user	用户表：	用户ID	姓名	密码	等级	余额
 * id	name	passwd	level	balance
 **/
public class User implements Serializable {

    private int id;          //用户ID
    private String name;     //姓名
    private String passwd;   //密码
    private int level;       //等级
    private double balance;  //余额




    public User(String name, String passwd) {
        this.name = name;
        this.passwd = passwd;
    }

    @Override
    public String toString() {
        return "用户信息{\tID=[" + id +
                "]\t账号=[" + name +
                "]\t密码=[" + passwd+"]\t余额=[" + Utils.df.format(balance) +"]\t等级=["+(level==0?"普通用户":"VIP")+"]}";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public User(int id, String name, String passwd, int level, double balance) {
        this.id = id;
        this.name = name;
        this.passwd = passwd;
        this.level = level;
        this.balance = balance;
    }
}
