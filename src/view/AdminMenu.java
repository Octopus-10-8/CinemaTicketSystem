package view;

import biz.*;
import bizImpl.*;
import entity.*;
import utils.MyMd5Utils;
import utils.PageUtils;
import utils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 管理员主类
 **/
public class AdminMenu {
    private UserBiz userBiz = new UserBizImpl();       //User逻辑层引用
    private Scanner scanner = new Scanner(System.in);  //控制台对象
    private CinemaBiz cinemaBiz = new CinemaBizImpl();  //电影院对象
    private MovieBiz movieBiz = new MovieBizImpl();   //电影对象
    private HallBiz hallBiz = new HallBizImpl();   //场厅对象
    private SessionBiz sessionBiz = new SessionBizImpl(); //场次对象
    private TicketBiz ticketBiz = new TicketBizImpl(); //影票对象

    /**
     * 管理员登陆，管理员账号写死
     */
    public void adminLogin() {
        while (true) {
            System.out.println("==========后台登陆==========");
            System.out.println("请输入账号");
            String name = Utils.checkInputForStr(scanner.nextLine());
            System.out.println("请输入密码");
            String passwd = Utils.checkInputForStr(scanner.nextLine());
            if (name.equals("123") && passwd.equals("123")) {
                System.out.println("登陆成功, 欢迎您[" + name + "]");
                adminManageMenu();
            } else {
                System.out.println("登陆失败");
            }
            if (!Utils.isGoOn()) {
                break;
            }
        }
    }


    /**
     * 管理员管理中心
     */
    private void adminManageMenu() {

        while (true) {
            System.out.println("==========万达电影后台中心==========");
            System.out.println("1.用户管理");
            System.out.println("2.电影管理");
            System.out.println("3.影院管理");
            System.out.println("4.场厅管理");
            System.out.println("5.场次管理");
            System.out.println("6.订单管理【影票】");
            System.out.println("7.查看效益");
            System.out.println("0.退出");
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    userMessageManage();
                    break;
                case "2":
                    movieManage();
                    break;
                case "3":
                    cinemaManage();
                    break;
                case "4":
                    hallManage();
                    break;
                case "5":
                    sessionManage();
                    break;
                case "6":
                    ticketManage();
                    break;
                case "7":
                    queryBenefitsManage();
                    break;
                default:
                    return;
            }

        }

    }

    /**
     * 用户管理菜单
     */
    private void userMessageManage() {
        while (true) {
            System.out.println("==========用户信息管理中心==========");
            System.out.println("1.查询所有用户信息");
            System.out.println("2.重置用户密码");
            System.out.println("3.查看VIP用户信息");
            System.out.println("0.退出");
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    queryAllUser();
                    break;
                case "2":
                    updateUserPasswd();
                    break;
                case "3":
                    queryUserOfVip();
                default:
                    return;
            }

        }


    }


    /**
     * 查询所有用户
     */
    private void queryAllUser() {
        while (true) {
            ArrayList<User> users = userBiz.queryUsers();
            Utils.showList(users, "用户信息");
            if (!Utils.isGoOn()) {
                break;
            }
        }
    }

    /**
     * 修改重置用户密码
     */
    private void updateUserPasswd() {
        while (true) {
            ArrayList<User> users = userBiz.queryUsers();
            Utils.showList(users, "用户信息");
            int userID = Utils.checkInput("请输入要修改的用户的ID", 1, 1000);
            User user = userBiz.queryByUserID(userID);
            if (user == null) {
                System.out.println("输入错误，该用户不存在");
                break;
            }
            System.out.println("请输入修改后的密码");
            String passwd = Utils.checkInputForStr(scanner.nextLine());
            System.out.println("请确认密码");
            String passwdConfim = Utils.checkInputForStr(scanner.nextLine());
            if (!passwd.equals(passwdConfim)) {
                System.out.println("俩次密码不一致，更新失败");
                break;
            }
            String md5 = MyMd5Utils.getMd5(passwd);
            user.setPasswd(md5);
            boolean b = userBiz.updateUser(user);
            if (b) {
                System.out.println("修改密码成功");
            } else {
                System.out.println("修改失败");
            }

            if (!Utils.isGoOn()) {
                break;
            }
        }
    }


    /**
     * 电影主菜单
     */
    private void movieManage() {
        while (true) {
            System.out.println("==========电影资源管理中心==========");
            System.out.println("1.添加电影");
            System.out.println("2.电影展示");
            System.out.println("3.修改电影信息");
            System.out.println("4.删除电影信息");
            System.out.println("5.搜索电影");
            System.out.println("0.退出");
            String input = Utils.checkInputForStr(scanner.nextLine());
            switch (input) {
                case "1":
                    addMovie();
                    break;
                case "2":
                    queryMovie();
                    break;
                case "3":
                    updateMovie();
                    break;
                case "4":
                    deleteMovie();
                    break;
                case "5":
                    searchMovie();
                    break;
                default:
                    return;
            }

        }


    }

    /**
     * 电影院功能方法
     */
    /**
     * 添加
     */
    private void addMovie() {
        while (true) {
            System.out.println("添加电影信息");
            System.out.println("请输入新电影名称");
            String name = Utils.checkInputForStr(scanner.nextLine());
            System.out.println("请输入电影详情");
            String detail = Utils.checkInputForStr(scanner.nextLine());
            int duration = Utils.checkInput("请输入电影时长[单位：分钟]", 50, 300);
            System.out.println("请输入电影类型");
            String type = Utils.checkInputForStr(scanner.nextLine());
            Movie movie = new Movie(name, detail, duration, type);
            boolean b = movieBiz.save(movie);
            if (b) {
                System.out.println("电影添加成功");
            } else {
                System.out.println("电影添加失败");
            }
            if (!Utils.isGoOn()) {
                break;
            }
        }

    }

    /**
     * 显示电影
     */
    private void queryMovie() {
        ArrayList<Movie> movies = movieBiz.queryMovie();
        if (movies.size() <= 3) {
            Utils.showList(movies, "电影展示");
        } else {
            PageUtils.pageSelect(movies);
        }
    }

    /**
     * 修改电影信息
     */
    private void updateMovie() {
        while (true) {
            ArrayList<Movie> movies = movieBiz.queryMovie();
            Utils.showList(movies, "万达网电影");
            int id = Utils.checkInput("请输入ID", 1, 1000);
            //查询该对象
            Movie m = movieBiz.queryById(id);
            if (m == null) {
                System.out.println("ID输入有误，该数据为空");
                break;
            }

            System.out.println("请输入修改后的名称");
            String name = Utils.checkInputForStr(scanner.nextLine());
            System.out.println("请输入修改后的电影详情");
            String detail = Utils.checkInputForStr(scanner.nextLine());
            int duration = Utils.checkInput("请输入电影时长[单位：分钟]", 50, 300);
            System.out.println("请输入修改后的类型");
            String type = Utils.checkInputForStr(scanner.nextLine());
            Movie movie = new Movie(id, name, detail, duration, type);
            boolean b = movieBiz.updateMovie(movie);
            if (b) {
                System.out.println("更新成功");
            } else {
                System.out.println("更新失败");
            }

            if (!Utils.isGoOn()) {
                break;
            }


        }
    }

    /**
     * 删除电影信息
     */
    private void deleteMovie() {
        ArrayList<Movie> movies = movieBiz.queryMovie();
        Utils.showList(movies, "万达影院电影展示");
        int id = Utils.checkInput("请输入要删除电影的ID", 1, 100);
        Movie movie = movieBiz.queryById(id);
        if (movie == null) {
            System.out.println("输入ID不存在");
            return;
        }
        boolean b = movieBiz.deleteMovie(id);
        if (b) {
            System.out.println("删除成功");
        } else {
            System.out.println("删除失败");
        }
    }

    /**
     * 查找电影
     */
    private void searchMovie() {
        while (true) {
            System.out.println("请输入关键字[名称、类型、描述]");
            String key = Utils.checkInputForStr(scanner.nextLine());
            ArrayList<Movie> movies = movieBiz.queryByKey(key);
            Utils.showList(movies, "共搜索到[" + movies.size() + "]条关于[" + key + "]的数据");
            if (!Utils.isGoOn()) {
                break;
            }

        }


    }


    /**
     * 电影院菜单
     */
    private void cinemaManage() {
        while (true) {
            System.out.println("==========电影院管理中心==========");
            System.out.println("1.添加电影院");
            System.out.println("2.查询某一个区域电影院信息");
            System.out.println("3.修改电影院信息");
            System.out.println("4.删除电影院信息");
            System.out.println("5.查询所有电影院信息");
            System.out.println("0.退出");
            String input = Utils.checkInputForStr(scanner.nextLine());
            switch (input) {
                case "1":
                    addCinema();
                    break;
                case "2":
                    queryByAddress();
                    break;
                case "3":
                    updateCinema();
                    break;
                case "4":
                    deleteCinema();
                    break;
                case "5":
                    queryCinema();
                    break;
                default:
                    return;
            }

        }


    }

    /**
     * 添加电影院
     */
    private void addCinema() {
        while (true) {
            System.out.println("添加电影院信息");
            System.out.println("请输入电影院名称");
            String name = Utils.checkInputForStr(scanner.nextLine());
            System.out.println("请输入电影院地址");
            String address = Utils.checkInputForStr(scanner.nextLine());
            Cinema cinema = new Cinema(name, address);
            boolean b = cinemaBiz.addCinema(cinema);
            if (b) {
                System.out.println("添加成功");
            } else {
                System.out.println("添加失败");
            }
            if (!Utils.isGoOn()) {
                break;
            }
        }


    }

    /**
     * 修改电影院
     */
    private void updateCinema() {
        while (true) {
            ArrayList<Cinema> cinemas = cinemaBiz.queryCinema();
            Utils.showList(cinemas, "请根据下面的信息选择对应的ID进行修改");
            int id = Utils.checkInput("请输入ID", 1, 1000);
            System.out.println("请输入修改后的名称");
            String name = Utils.checkInputForStr(scanner.nextLine());
            System.out.println("请输入修改后的地址");
            String address = Utils.checkInputForStr(scanner.nextLine());
            Cinema cinema = new Cinema(id, name, address);
            boolean b = cinemaBiz.updateCinema(cinema);
            if (b) {
                System.out.println("修改成功");
            } else {
                System.out.println("修改失败");
            }
            if (!Utils.isGoOn()) {
                break;
            }

        }
    }

    /**
     * 查询电影院信息
     */

    public void queryCinema() {
        ArrayList<Cinema> cinemas = cinemaBiz.queryCinema();
        if (cinemas.size() <= 3) {
            Utils.showList(cinemas, "影院展示");
        } else {
            PageUtils.pageSelect(cinemas);
        }
    }

    /**
     * 查询某个区域的电影院
     */
    private void queryByAddress() {
        while (true) {
            System.out.println("请输入地址关键字");
            String address = Utils.checkInputForStr(scanner.nextLine());
            ArrayList<Cinema> cinemas = cinemaBiz.queryCinemaByAddress(address);
            Utils.showList(cinemas, "根据[" + address + "]查询的结果如下");
            if (!Utils.isGoOn()) {
                break;
            }
        }
    }


    private void deleteCinema() {
        while (true) {
            ArrayList<Cinema> cinemas = cinemaBiz.queryCinema();
            Utils.showList(cinemas, "万达电影院信息");
            int delId = Utils.checkInput("请选择要删除的ID", 1, 1000);
            boolean b = cinemaBiz.deleteCinema(delId);
            if (b) {
                System.out.println("删除成功");
            } else {
                System.out.println("删除失败");
            }
            if (!Utils.isGoOn()) {
                break;
            }

        }


    }

    /**
     * 场厅管理中心
     */

    private void hallManage() {

        while (true) {
            System.out.println("==========场厅管理中心==========");
            System.out.println("1.添加场厅");
            System.out.println("2.查询所有场厅");
            System.out.println("3.修改场厅信息");
            System.out.println("4.删除场厅信息");
            System.out.println("0.退出");
            String input = Utils.checkInputForStr(scanner.nextLine());
            switch (input) {
                case "1":
                    addHall();
                    break;
                case "2":
                    queryAllHall();
                    break;
                case "3":
                    updateHall();
                    break;
                case "4":
                    deleteHall();
                    break;
                default:
                    return;
            }

        }
    }

    /**
     * 添加场厅
     */
    private void addHall() {
        while (true) {
            System.out.println("=========添加场厅信息=========");
            System.out.println("请输入场厅名称");
            String name = Utils.checkInputForStr(scanner.nextLine());
            ArrayList<Cinema> cinemas = cinemaBiz.queryCinema();
            Utils.showList(cinemas, "请根据下面的影院选择对应的ID");
            int cid = Utils.checkInput("请输入电影院ID", 1, 1000);
            Cinema cinema = cinemaBiz.queryById(cid);
            if (cinema == null) {
                System.out.println("ID输入有误");
                break;
            }
            boolean b = hallBiz.save(new Hall(cid, name, 100));  //场厅容量100固定一下
            if (b) {
                System.out.println("添加成功");
            } else {
                System.out.println("添加失败");
            }
            if (!Utils.isGoOn()) {
                break;
            }
        }


    }


    /**
     * 查询所有的场厅
     */
    private void queryAllHall() {
        ArrayList<HallPerfect> templateModels = hallBiz.queryHall();
        if (templateModels.size() <= 3) {
            Utils.showList(templateModels, "场厅展示");
        } else {
            PageUtils.pageSelect(templateModels);
        }
    }

    /**
     * 修改场厅信息
     * <p>
     * <p>
     * [场厅名称、影院ID、场厅容量]
     * <p>
     * 场厅名称：不需要关联，直接修改
     * <p>
     * 影院ID：即属于哪个电影院可以需要判断修改后的电影院是不是存在，然后直接修改
     * <p>
     * <p>
     * 场厅容量：固定的
     */

    private void updateHall() {
        while (true) {
            ArrayList<HallPerfect> templateModels = hallBiz.queryHall();
            Utils.showList(templateModels, "场厅信息");
            int hallId = Utils.checkInput("请根据下面的信息选择对应的ID", 1, 1000);
            Hall h = hallBiz.queryHallByID(hallId);
            if (h == null) {
                System.out.println("ID输入有误");
                break;
            }
            System.out.println("请输入修改后场厅名称");
            String name = Utils.checkInputForStr(scanner.nextLine());
            ArrayList<Cinema> cinemas = cinemaBiz.queryCinema();
            Utils.showList(cinemas, "请根据下面的影院选择修改后的对应的ID");
            int cid = Utils.checkInput("请输入电影院ID", 1, 1000);
            if (cinemaBiz.queryById(cid) == null) {
                System.out.println("电影院编号输入有误,请重新修改");
                break;

            }
            h.setCid(cid);
            h.setName(name);
            boolean b = hallBiz.updateHall(h);
            if (b) {
                System.out.println("修改成功");
            } else {
                System.out.println("修改失败");
            }
            if (!Utils.isGoOn()) {
                break;
            }
        }
    }


    /**
     * 删除场厅的信息
     */
    private void deleteHall() {
        while (true) {
            System.out.println("===============删除场厅信息===========");
            ArrayList<HallPerfect> templateModels = hallBiz.queryHall();
            Utils.showList(templateModels, "场厅信息");
            int hallId = Utils.checkInput("请根据下面的信息选择对应的ID", 1, 1000);
            if (hallBiz.queryHallByID(hallId) == null) {
                System.out.println("ID输入有误");
                break;
            }
            boolean b = hallBiz.deleteHall(hallId);
            if (b) {
                System.out.println("删除成功");
            } else {
                System.out.println("删除失败");
            }
            if (!Utils.isGoOn()) {
                break;
            }

        }


    }


    /**
     * 场次管理中心
     */
    private void sessionManage() {

        while (true) {
            System.out.println("==========场次管理中心==========");
            System.out.println("1.添加场次");
            System.out.println("2.查询所有场次");
            System.out.println("3.修改场次信息");
            System.out.println("4.删除场次信息");
            System.out.println("0.退出");
            String input = Utils.checkInputForStr(scanner.nextLine());
            switch (input) {
                case "1":
                    addSession();
                    break;
                case "2":
                    queryAllSession();
                    break;
                case "3":
                    updateSession();
                    break;
                case "4":
                    deleteSession();
                    break;
                default:
                    return;
            }

        }
    }

    /**
     * 添加场次
     */

    private void addSession() {
        while (true) {
            //遍历电影院 （XX） 这里不需要关联电影院，因为场厅已经关联了
            //影院的ID是可以通过场厅来获取的
            //遍历场厅
            ArrayList<HallPerfect> hallPerfects = hallBiz.queryHall();
            Utils.showList(hallPerfects, "场厅信息");
            int hallId = Utils.checkInput("请输入场厅ID", 1, 100);

            Hall hall = hallBiz.queryHallByID(hallId);
            if (hall == null) {
                System.out.println("场厅输入有误");
                break;
            }
            //获取电影院ID
            int cid = hall.getCid();
            //遍历电影
            ArrayList<Movie> movies = movieBiz.queryMovie();
            Utils.showList(movies, "电影信息");
            int movied = Utils.checkInput("请选择对应的ID", 1, 1000);
            //判断电影是否存在
            Movie movie = movieBiz.queryById(movied);
            if (movie == null) {
                System.out.println("电影输入有误");
                break;
            }
            System.out.println("请输入播放时间[格式：2000-12-01 12:05]");
            String time = Utils.checkInputForStr(scanner.nextLine());
            //利用异常来处理，如果用户输入的格式无法被格式解析的话那么就退出
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                Date parse = simpleDateFormat.parse(time);
            } catch (ParseException e) {
                System.out.println("格式错误");
                break;
            }
            //首先必须满足添加的班次必须是大于当前时间(时间的判断使用时间戳)
            if (time.compareTo(Utils.getTime()) < 0) {
                System.out.println("场次时间安培有误，请重新输入");
                break;
            }

            double price = Utils.checkInputForDouble("请输入价格", 1, 10000);
            //  int remian = Utils.checkInput("请输入剩余座位数", 1, 2000);
            Session session = new Session(hallId, cid, movied, time, price, 100);
            boolean b = sessionBiz.addSession(session);
            if (b) {
                System.out.println("添加成功");
            } else {
                System.out.println("添加失败");
            }
            if (!Utils.isGoOn()) {
                break;
            }
        }


    }

    /**
     * 查询所有的场次信息
     */
    private void queryAllSession() {
        ArrayList<SessionPerfect> sessionPerfects = sessionBiz.querySession();
        if (sessionPerfects.size() <= 3) {
            Utils.showList(sessionPerfects, "场次展示");
        } else {
            PageUtils.pageSelect(sessionPerfects);
        }

    }

    /**
     * 修改场次信息
     */
    private void updateSession() {
        while (true) {
            ArrayList<SessionPerfect> sessionPerfects = sessionBiz.querySession();
            Utils.showList(sessionPerfects, "场次信息展示");
            int id = Utils.checkInput("请选择对应的ID进行修改", 1, 1000);
            Session session = sessionBiz.querySessionByID(id);
            if (session == null) {
                System.out.println("输入场次错误");
                break;
            }
            //修改场厅
            ArrayList<HallPerfect> hallPerfects = hallBiz.queryHall();
            Utils.showList(hallPerfects, "场厅信息展示");
            int hid = Utils.checkInput("请输入场厅ID", 1, 1000);
            Hall hall = hallBiz.queryHallByID(hid);
            if (hall == null) {
                System.out.println("输入场厅错误");
                break;
            }
            //修改电影院
            ArrayList<Cinema> cinemas = cinemaBiz.queryCinema();
            Utils.showList(cinemas, "电影院信息展示");
            int cid = Utils.checkInput("请输入影院ID", 1, 1000);
            Cinema cinema = cinemaBiz.queryById(cid);
            if (cinema == null) {
                System.out.println("影院输入错误");
                break;
            }
            //修改电影
            ArrayList<Movie> movies = movieBiz.queryMovie();
            Utils.showList(movies, "电影信息展示");
            int mid = Utils.checkInput("请输入电影ID", 1, 1000);
            Movie m = movieBiz.queryById(mid);
            if (m == null) {
                System.out.println("电影输入错误");
                break;
            }
            //修改时间
            System.out.println("请输入播放时间[格式：2000-12-01 12:05]");
            String time = Utils.checkInputForStr(scanner.nextLine());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                Date parse = simpleDateFormat.parse(time);
            } catch (ParseException e) {
                System.out.println("格式错误");
                break;
            }
            //首先必须满足添加的班次必须是大于当前时间(时间的判断使用时间戳)
            if (time.compareTo(Utils.getTime()) < 0) {
                System.out.println("场次时间安培有误，请重新输入");
                break;
            }
            double price = Utils.checkInputForDouble("请输入价格", 1, 10000);
            //修改数据
            session.setPrice(price);
            session.setCid(cid);
            session.setHid(hid);
            session.setTime(time);
            session.setMmovie_id(mid);
            boolean b = sessionBiz.updateSession(session);
            if (b) {
                System.out.println("修改成功");
            } else {
                System.out.println("修改失败");
            }
            if (!Utils.isGoOn()) {
                break;
            }
        }


    }

    /**
     * 删除场次信息
     */

    private void deleteSession() {

        while (true) {

            ArrayList<SessionPerfect> sessionPerfects = sessionBiz.querySession();
            Utils.showList(sessionPerfects, "场次信息展示");
            int id = Utils.checkInput("请选择对应的ID进行删除", 1, 1000);
            Session session = sessionBiz.querySessionByID(id);
            if (session == null) {
                System.out.println("输入ID错误");
                break;
            }
            boolean b = sessionBiz.deleteSession(id);
            if (b) {
                System.out.println("删除成功");
            } else {
                System.out.println("删除失败");
            }


            if (!Utils.isGoOn()) {
                break;
            }
        }


    }


    /**
     * 影票中心
     */


    private void ticketManage() {
        while (true) {
            System.out.println("==========影票管理中心（查询）==========");
            System.out.println("1.查询所有订单");
            System.out.println("2.查询某个场次的订单");
            System.out.println("0.退出");
            String input = Utils.checkInputForStr(scanner.nextLine());
            switch (input) {
                case "1":
                    queryAllTickets();
                    break;
                case "2":
                    queryTicketsOfSession();
                    break;
                default:
                    return;
            }

        }
    }

    /**
     * 查询所有购票信息
     */
    private void queryAllTickets() {
        while (true) {
            ArrayList<TicketPerfect> ticketPerfectArrayList = ticketBiz.queryTicket();
            Utils.showList(ticketPerfectArrayList, "购票信息");
            if (!Utils.isGoOn()) {
                break;
            }
        }
    }

    /**
     * 查询某个场次的订单
     */
    private void queryTicketsOfSession() {
        while (true) {
            ArrayList<SessionPerfect> sessionPerfects = sessionBiz.querySession();
            Utils.showList(sessionPerfects, "场次信息");
            int sessionId = Utils.checkInput("请输入场次", 1, 10000);
            ArrayList<TicketPerfect> tickets = ticketBiz.showTicketBySession(sessionId);
            Utils.showList(tickets, "根据该场次查询");
            if (!Utils.isGoOn()) {
                break;
            }
        }


    }

    /**
     * 电影院效益信息中心
     */
    private void queryBenefitsManage() {

        while (true) {
            System.out.println("==========[影院效益查看]==========");
            System.out.println("1.查看电影的[票房]降序排序");
            System.out.println("2.查看影院的[盈利]降序排列");
            System.out.println("0.退出");
            String input = Utils.checkInputForStr(scanner.nextLine());
            switch (input) {
                case "1":
                    queryBoxOfficeOfMovie();
                    break;
                case "2":
                    queryBoxOfficeOfCinema();
                    break;
                default:
                    return;
            }


        }

    }

    /**
     * 查看电影的[票房]降序排序
     */
    private void queryBoxOfficeOfMovie() {
        while (true) {
            ArrayList<Map.Entry<Movie, Double>> entries = ticketBiz.queryBoxOfficeOrderDesc();
            int i = 1;
            for (Map.Entry<Movie, Double> entry : entries) {

                if (i >= 10) {
                    break;
                }
                System.out.println("第[" + i + "]名：\t电影名=[" + entry.getKey().getName() + "]" + "\t票房=[" + entry.getValue() + "]元");
                ++i;
            }

            if (!Utils.isGoOn()) {
                break;
            }
        }
    }

    /**
     * 查看影院的[盈利]降序排列
     */

    private void queryBoxOfficeOfCinema() {
        while (true) {
            ArrayList<Map.Entry<Cinema, Double>> entries = ticketBiz.queryBoxOfficeOfCinema();
            int i = 1;
            for (Map.Entry<Cinema, Double> entry : entries) {

                if (i >= 10) {
                    break;
                }
                System.out.println("第[" + i + "]名：\t影院名=[" + entry.getKey().getName() + "]" + "\t票房=[" + entry.getValue() + "]元");
                ++i;
            }
            if (!Utils.isGoOn()) {
                break;
            }
        }

    }


    /**
     * 查询所有的VIP用户
     */
    private void queryUserOfVip() {
        while (true) {
            ArrayList<User> users = userBiz.queryUserOfVip();
            Utils.showList(users, "所有VIP用户信息");
            if (!Utils.isGoOn()) {
                break;
            }
        }


    }

}
