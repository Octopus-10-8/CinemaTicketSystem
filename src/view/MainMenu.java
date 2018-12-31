package view;

import biz.UserBiz;
import bizImpl.UserBizImpl;

import java.util.Scanner;

/**
 * Date: 2018/12/25 0025
 **/
public class MainMenu {
    private UserBiz userBiz = new UserBizImpl();       //User逻辑层引用
    private Scanner scanner = new Scanner(System.in);  //控制台对象
    private AdminMenu adminMenu = new AdminMenu();      //管理员中心
    private UserManageMenu userManageMenu = new UserManageMenu();   //用户中心
    private String choice = null;

    public void mainMenu() {
        while (true) {
            System.out.println("==========欢迎进入万达网购票网站==========");
            System.out.println("1.登录");
            System.out.println("2.注册");
            System.out.println("3.后台");
            System.out.println("4.退出");
            choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    userManageMenu.userLoginMenu();
                    break;
                case "2":
                    userManageMenu.userRigistMenu();
                    break;
                case "3":
                    adminMenu.adminLogin();
                    break;
                default:
                    break;
            }
        }
    }


}
