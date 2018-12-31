package view;

import biz.*;
import bizImpl.*;
import dao.UserDao;
import entity.*;
import utils.*;

import javax.sound.midi.Soundbank;
import java.util.*;

/**
 * 用户管理中心
 **/
public class UserManageMenu {

    private UserBiz userBiz = new UserBizImpl();       //User逻辑层引用
    private Scanner scanner = new Scanner(System.in);  //控制台对象
    private String choice = null;
    private CinemaBiz cinemaBiz = new CinemaBizImpl();  //电影院对象
    private MovieBiz movieBiz = new MovieBizImpl();   //电影对象
    private HallBiz hallBiz = new HallBizImpl();   //场厅对象
    private SessionBiz sessionBiz = new SessionBizImpl(); //场次对象
    private TicketBiz ticketBiz = new TicketBizImpl(); //影票对象
    private SensitivewordFilter filter = new SensitivewordFilter();  //检查敏感词类
    private CommentsBiz commentsBiz = new CommentsBizImpl();  //评论对象
    private KeyBiz keyBiz = new KeyBizImpl();
    public User user = null;

    /**
     * 注册菜单
     */
    public void userRigistMenu() {
        while (true) {
            System.out.println("==========用户注册==========");
            System.out.println("请输入账号");
            String name = scanner.nextLine();
            System.out.println("请输入密码");
            String passwd = scanner.nextLine();
            System.out.println("请确认密码");
            String passwdConfim = scanner.nextLine();
            if (!passwd.equals(passwdConfim)) {
                System.out.println("密码不一致，注册失败");
                break;
            }
            //使用MD5加密保存进文件中
            String md5 = MyMd5Utils.getMd5(passwd);
            User u = new User(name, md5);
            boolean regist = userBiz.regist(u);
            if (regist) {
                System.out.println("注册成功");
            } else {
                System.out.println("注册已存在");
            }
            if (!Utils.isGoOn()) {
                break;
            }

        }
    }


    /**
     * 用户登陆
     */
    public void userLoginMenu() {

        while (true) {
            System.out.println("==========用户登陆==========");
            System.out.println("请输入账号");
            String name = scanner.nextLine();
            System.out.println("请输入密码");
            String passwd = scanner.nextLine();
            User u = new User(name, passwd);
            //登陆的时候在用md5加密的返回值去匹配
            boolean regist = userBiz.login(name, MyMd5Utils.getMd5(passwd));
            if (regist) {
                System.out.println("登陆成功, 欢迎您 [" + name + "]");
                user = userBiz.queryByName(name);
                userCenterMenu();
            } else {
                System.out.println("登陆失败");
            }
            if (!Utils.isGoOn()) {
                break;
            }

        }


    }

    /**
     * 用户中心
     */
    public void userCenterMenu() {
        while (true) {
            System.out.println("==========【万达购票网首页】==========");
            System.out.println("1.购票");
            System.out.println("2.电影查询");
            System.out.println("3.充值");
            System.out.println("4.我的电影票【评论】");
            System.out.println("5.个人信息");
            System.out.println("6.猜你喜欢");
            System.out.println("0.退出");
            choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    buyTicketMenu();
                    break;
                case "2":
                    movieQueryMenu();
                    break;
                case "3":
                    rechargeMenu();
                    break;
                case "4":
                    myTicketAndComments();
                    break;
                case "5":
                    personalProfileMenu();
                    break;
                case "6":
                    recommendedMenu();
                    break;
                default:
                    return;
            }
        }
    }


    /**
     * 购票  ->电影选择-影院选择->场次选择->座位选择
     *
     */

    /**
     * 购票流程：
     * 1.	用户注册登录，浏览电影列表，选择电影进入购票界面，购买影票，
     * 2.	浏览电影的界面应当包括电影的列表，电影相关的文字介绍，
     * 3.	购买影票界面应当提供对场次的搜索工具，搜索完成后显示相关的场次列表
     * 通过选择相关场次显示该场次的座位信息。
     */

    public void buyTicketMenu() {

        while (true) {
            //1：浏览电影，选择电影
            ArrayList<Movie> movies = movieBiz.queryMovie();
            Utils.showList(movies, "万达电影");
            int movieID = Utils.checkInput("请选择电影ID", 1, 10000);
            Movie movie = movieBiz.queryById(movieID);
            if (movie == null) {
                System.out.println("ID输入错误，正在返回...");
                break;
            }
            //2：选择电影院
            ArrayList<Cinema> cinemas = cinemaBiz.queryCinema();
            Utils.showList(cinemas, "电影院");
            int cid = Utils.checkInput("请选择电影院ID", 1, 1000);
            Cinema cinema = cinemaBiz.queryById(cid);
            if (cinema == null) {
                System.out.println("输入影院ID不存在");
                return;
            }//3：选择场次（这里的场次必须是包含于前面的电影院的而且电影ID必须对应）
            ArrayList<SessionPerfect> sessionPerfects = sessionBiz.querySessionByCid(cid, movieID);
            if (sessionPerfects.size() == 0) {
                System.out.println("该影院暂无上映场次，无法购票");
                break;
            }
            Utils.showList(sessionPerfects, "电影院场次");
            int sessionId = Utils.checkInput("请选择场次ID", 1, 1000);
            //获得场厅容量
            Session session = sessionBiz.querySessionByID(sessionId);
            if (session == null) {
                System.out.println("输入场次ID有误");
                break;
            }
            Hall hall = hallBiz.queryHallByID(session.getHid());
            //4：选择座位
            ArrayList<String> seatBySessionIdArraylist = ticketBiz.getSeatBySessionId(sessionId);
            Utils.seat(hall.getCapacity(), seatBySessionIdArraylist);
            StringBuffer stringBuffer = new StringBuffer();
            int x = Utils.checkInput("请选择排数", 1, 10);
            int y = Utils.checkInput("请选择座位数（从左到右）", 1, 10);
            stringBuffer.append(x);
            stringBuffer.append("#");
            stringBuffer.append(y);
            ArrayList<String> seatBySessionId = ticketBiz.getSeatBySessionId(sessionId);
            if (seatBySessionId.contains(stringBuffer.toString())) {
                System.out.println("不好意思，该座位已被预订");
                return;
            } else {
                System.out.println("选坐完成");
            }
            Session session1 = sessionBiz.querySessionByID(sessionId);
            System.out.println("请支付电影价格=[" + session1.getPrice() + "]");
            System.out.println("是否支付");
            System.out.println("请输入(是/否)");
            String confim = Utils.checkInputForStr(scanner.nextLine());
            if (confim.equals("是")) {
                //当用户点击时的时候，此时就需要判断是否是VIP了
                //判断用户余额
                //这里需要更新User对象
                user = userBiz.queryByName(user.getName());
                if (user.getBalance() >= session1.getPrice()) {
                    System.out.println("购买成功");
                    //5：支付，余额不足需充值
                    //6：生成订单，完成支付
                    Ticket ticket = new Ticket(this.user.getId(), sessionId, movieID, stringBuffer.toString());
                    boolean b = ticketBiz.addTickets(ticket);
                    if (b) {
                        //购买成功，更新用户余额
                        if (user.getLevel() > 0) {
                            //是Vip的话，则按八折算     //Utils.seatFormat(session1.getPrice() * 0.8)
                            user.setBalance(user.getBalance() - session1.getPrice() * 0.8);
                            System.out.println("[VIP]您是支付了[" + Utils.decimalForDouble(session1.getPrice() * 0.8) + "]元、优惠了[" + Utils.decimalForDouble(session1.getPrice() * 0.2) + "]元");
                        } else {
                            user.setBalance(user.getBalance() - session1.getPrice());
                            System.out.println("您支付了[" + Utils.decimalForDouble(session1.getPrice()) + "]元");
                        }
                        userBiz.updateUser(user);
                        //更新余票
                        System.out.println("余票=" + session1.getRemain());
                        session1.setRemain(session1.getRemain() - 1);
                        sessionBiz.updateSessionHasNoTime(session1);
                        Session session2 = sessionBiz.querySessionByID(session1.getId());
                        System.out.println(session2);
                    } else {
                        System.out.println("失败");
                    }
                } else {
                    System.out.println("余额不足，请即时充值");
                }

            }

            if (!Utils.isGoOn()) {
                break;
            }
        }
    }

    private void movieQueryMenu() {
        while (true) {
            System.out.println("请输入关键字[电影名、详情、类型]");
            String key = Utils.checkInputForStr(scanner.nextLine());
            ArrayList<Movie> movies = movieBiz.queryByKey(key);
            Utils.showList(movies, "[" + key + "]");
            keyBiz.addKey(new Key(key));
            if (!Utils.isGoOn()) {
                break;
            }
        }


    }

    /**
     * 充值
     */
    private void rechargeMenu() {
        while (true) {
            System.out.println("==========用户充值==========");
            System.out.println("充值[充值500以上成为VIP，可享受八折优惠]");
            double input = Utils.checkInputForDouble("请输入充值金额", 1, 10000);
            String validateCode = StringUtils.createValidateCode();
            System.out.println("验证码[" + validateCode + "]");
            String validate = Utils.checkInputForStr(scanner.nextLine());
            if (validateCode.equals(validate)) {
                System.out.println("验证通过");
                System.out.println("......充值完成");
                User userID = userBiz.queryByName(this.user.getName());
                userBiz.recharge(userID.getId(), input);

            } else {
                System.out.println("验证码输入错误");
                continue;
            }
            if (!Utils.isGoOn()) {
                break;
            }
        }

    }

    /**
     * 我的电影票和写评论
     */
    private void myTicketAndComments() {
        while (true) {
            System.out.println("==========我的电影票和评论信息");
            System.out.println("1.查看我购买的电影票");
            System.out.println("2.发表电影评论");
            System.out.println("3.查看某部电影的评论");
            System.out.println("4.查看所有评论");
            System.out.println("5.退票");
            String input = Utils.checkInputForStr(scanner.nextLine());
            switch (input) {
                case "1":
                    showMyTickets();
                    break;
                case "2":
                    releaseComment();
                    break;
                case "3":
                    showMovieComment();
                    break;
                case "4":
                    showAllComment();
                    break;
                case "5":
                    refund();
                    break;
                default:
                    return;
            }

        }
    }


    /**
     * 查询所有评论
     */
    private void showAllComment() {
        while (true) {
            ArrayList<CommentsPerfect> commentsPerfects = commentsBiz.queryComments();
            Utils.showList(commentsPerfects, "所有评论");
            if (!Utils.isGoOn()) {
                break;
            }
        }

    }

    /**
     * 退票
     */
    private void refund() {
        while (true) {
            ArrayList<TicketPerfect> ticketPerfectArrayList = ticketBiz.queryTicketByuserId(user.getId());
            Utils.showList(ticketPerfectArrayList, "用户[" + user.getName() + "]的购票信息");
            int tid = Utils.checkInput("请输入ID", 1, 1000);
            TicketPerfect ticketPerfect = ticketBiz.queryTicketPerfectById(tid);
            if (ticketPerfect == null) {
                System.out.println("输入的ID有误");
                break;
            }
            //检查用户选择的票的时候是否是当前时间2个小时后的


            System.out.println("检查退票信息=" + ticketPerfect);
            //更新用户余额
            double price = ticketPerfect.getSession().getPrice();
            User user = userBiz.queryByName(ticketPerfect.getUser().getName());
            user.setBalance(user.getBalance() + price);
            userBiz.updateUser(user);
            //删除票
            boolean b = ticketBiz.deleteTicket(ticketPerfect.getTicket().getId());

            //余票加一


            if (b) {
                System.out.println("退票成功");
            } else {
                System.out.println("退票失败");
            }


            if (!Utils.isGoOn()) {
                break;
            }
        }


    }


    private void showMyTickets() {
        while (true) {
            ArrayList<TicketPerfect> ticketPerfectArrayList = ticketBiz.queryTicketByuserId(user.getId());
            Utils.showList(ticketPerfectArrayList, "用户[" + user.getName() + "]的购票信息");
            if (!Utils.isGoOn()) {
                break;
            }
        }


    }

    private void releaseComment() {
        while (true) {
            ArrayList<TicketPerfect> ticketPerfectArrayList = ticketBiz.queryTicketByuserId(user.getId());
            Utils.showList(ticketPerfectArrayList, "用户[" + user.getName() + "]的购票信息");
            int movied = Utils.checkInput("请选择对应的电影ID", 1, 1000);
            if (movieBiz.queryById(movied) == null) {
                System.out.println("输入有误，该电影不存在");
                break;
            }
            //选择成功，下面开始进行评论
            System.out.println("请输入评论");
            String commentsUser = Utils.checkInputForStr(scanner.nextLine());
            //需要经过敏感词过滤
            String commentrHasFiltered = filter.replaceSensitiveWord(commentsUser, 1, "*");
            Comments comments = new Comments(user.getId(), movied, commentrHasFiltered, Utils.getTime());
            boolean b = commentsBiz.addComments(comments);
            if (b) {
                System.out.println("评论成功");
            } else {
                System.out.println("评论失败");
            }
            if (!Utils.isGoOn()) {
                break;
            }
        }

    }

    /**
     * 查看评论
     */
    private void showMovieComment() {
        while (true) {
            //显示所有的电影，让用户来选择
            ArrayList<Movie> movies = movieBiz.queryMovie();
            Utils.showList(movies, "全部电影");
            int movieID = Utils.checkInput("请输入电影ID查看评价", 1, 1000);
            Movie movie = movieBiz.queryById(movieID);
            if (movie == null) {
                System.out.println("输入有误，该电影不存在");
                break;
            }
            ArrayList<CommentsPerfect> commentsPerfects = commentsBiz.queryCommentsForMovie(movieID);
            Utils.showList(commentsPerfects, "电影[" + movie.getName() + "]的评价");
            if (!Utils.isGoOn()) {
                break;
            }
        }

    }

    private void personalProfileMenu() {
        while (true) {
            User user = userBiz.queryByName(this.user.getName());
            System.out.println(user);
            if (!Utils.isGoOn()) {
                break;
            }

        }
    }


    /**
     * 猜你喜欢
     */

    private void recommendedMenu() {
        while (true) {
            System.out.println("==========猜你喜欢============");
            System.out.println("1.热门电影");
            System.out.println("2.个人推荐");
            System.out.println("3.搜索热词");
            System.out.println("0.返回上一层");
            String input = Utils.checkInputForStr(scanner.nextLine());
            switch (input) {
                case "1":
                    hotMovie();
                    break;
                case "2":
                    perRecommendation();
                    break;

                case "3":
                    hotKey();
                    break;
                default:
                    return;
            }

        }


    }

    /**
     * 显示票房排名
     */
    private void hotMovie() {
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

    private void perRecommendation() {
        while (true) {
            Key[] keyWords = KeyWords.getKeyWords();
            int count = 0;
            for (int i = 0; i < keyWords.length; i++) {

                ArrayList<Movie> movies = movieBiz.queryByKey(keyWords[i].getKey());
                if (count > 4) {
                    break;
                }
                if (movies.size() == 0) {
                    continue;
                } else {
                    count += movies.size();
                    for (Movie movie : movies) {
                        System.out.println(movie);
                    }
                }

            }


            if (!Utils.isGoOn()) {
                break;
            }
        }

    }
    private void hotKey() {
        while (true) {
            Key[] keyWords = KeyWords.getKeyWords();
            System.out.println("============搜索热度榜=============");
            for (int i = 0; i <5; i++) {
                System.out.println("第"+(i+1)+"名 ["+keyWords[i].getKey()+"]" );
            }
            if (!Utils.isGoOn()) {
                break;
            }

        }
    }

}
