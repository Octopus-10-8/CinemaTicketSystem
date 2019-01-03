package utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Date: 2018/12/20 0020
 **/
public class Utils {
    public final static String SEARCHKEYREGEX = "[*$*$~!/@#$%^&*()\\_=+\\|[{}];\'\",<.>/?]+";  //判断是否含有非法字符
    private static Scanner scanner = new Scanner(System.in);
    private static String chioce;
    public static DecimalFormat df = new DecimalFormat("#.00");   //保留小数2位

    public static boolean isGoOn() {
        System.out.println("是否继续、输入n/N退出");
        chioce = scanner.next();
        if (chioce.equalsIgnoreCase("n")) {
            return false;
        }
        return true;
    }


    /**
     * 提供用户输入的检查
     *
     * @param name 显示会话
     * @param min  输入的最小值
     * @param max  输入的最大值
     * @return 整型的值
     */
    public static int checkInput(String name, int min, int max) {
        int res = 0;
        while (true) {
            System.out.println(name);
            scanner.useDelimiter("\n");
            String input = scanner.next();
            try {
                res = Integer.parseInt(input);
                if (res >= min && res <= max) {
                    break;
                } else {
                    System.out.println("请输入" + min + " ~" + max + "之间的值");
                }
            } catch (NumberFormatException e) {
                System.out.println("输入不合法");
            }

        }
        return res;
    }

    /**
     * 字符串输入检测(不能输入非法字符)
     * <p>
     * name = scanner.nextLine();
     * scanner.nextLine();  注意这里的使用，scanner.nextLine()后面不能接收ln
     */
    public static String checkInputForStr(String name) {
        String res = null;
        while (true) {
            Pattern p = Pattern.compile(SEARCHKEYREGEX);
            Matcher m = p.matcher(name);
            boolean b = m.find();
            if (b) {
                System.out.println("不能输入非法字符");
                System.out.println("请重新输入");
                name = scanner.nextLine();
                scanner.nextLine();
            } else {
                res = name;
                break;
            }
        }
        return res;
    }


    /**
     * 集合打印输出工具
     *
     * @param al  需要打印的集合
     * @param str 菜单显示
     * @param <T>
     */
    public static <T> void showList(ArrayList<T> al, String str) {
        System.out.println("================" + str + "===========共计[" + al.size() + "]条数据=====");
        if (al.size() == 0) {
            System.out.println("没有数据");
        } else {
            for (T t : al) {
                System.out.println(t);
            }
        }
    }

    /***
     * 获取当前时间
     * @return
     */
    public static String getTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(date);
        return time;
    }


    /**
     * 将给定的时间推后某个时间段
     *
     * @param nowTime 需要修改的原时间
     * @param day     延迟的天数
     * @return
     */
    public static String updateDate(String nowTime, int day) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 日期格式

        try {
            Date date = dateFormat.parse(nowTime); // 指定日期
            Date newDate = null; // 指定日期加上20天
            newDate = addDate(date, day);
            return dateFormat.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        System.out.println(dateFormat.format(date));// 输出格式化后的日期
//        System.out.println(dateFormat.format(newDate));
        return null;
    }

    private static Date addDate(Date date, long day) throws ParseException {
        long time = date.getTime(); // 得到指定日期的毫秒数
        day = day * 24 * 60 * 60 * 1000; // 要加上的天数转换成毫秒数
        time += day; // 相加得到新的毫秒数
        return new Date(time); // 将毫秒数转换成日期
    }

    /**
     * @param srcDate  开始时间     2018年12月1日 14:20
     * @param duration 播放时长     150(分钟)
     */
    public static String scheduljudge(String srcDate, int duration) {
        String time = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date parse = simpleDateFormat.parse(srcDate);
            long t1 = parse.getTime();
            //解析分钟，限制输入为int型
            long t2 = t1 + duration * 60 * 1000;
            Date res = new Date(t2);
            time = simpleDateFormat.format(res);
        } catch (ParseException e) {
            System.out.println("格式错误");
        }
        return time;
    }

    /**
     * 座位排列
     *
     * @param capacity
     * @param seatNum
     */
    public static void seat(int capacity, ArrayList<String> seatNum) {
        //默认为10列
        int col = 10 + 1;
        int row = capacity / 10;

        int free = capacity % row;
        row += 1;
        int[][] array = new int[row][col];
        if (free > 0) {
            row += 1;
            array = new int[row][col];
            for (int i = free + 1; i < col; i++) {
                array[row - 1][i] = -1;
            }
        }

        for (int i = 0; i < seatNum.size(); i++) {
            //  String[] split = date[i].split("#");
            String[] split = seatNum.get(i).split("#");
            int s1 = Integer.parseInt(split[0]);
            int s2 = Integer.parseInt(split[1]);
            array[s1][s2] = 1;
        }
        for (int i = 1; i < array.length; i++) {
            if (i == 10) {
                System.out.print("第" + i + "排=");
            } else {
                System.out.print("第" + i + "排= ");
            }

            for (int j = 1; j < col; j++) {

                if (array[i][j] == 0) {
                    System.out.print("[ ]\t");
                } else if (array[i][j] == 1) {
                    System.out.print("[X]\t");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();

        }


    }

    /**
     * @param seat 输入为 7#5式的字符串
     * @return 输出为  7排5座格式的字符串
     */
    public static String seatFormat(String seat) {
        String[] split = seat.split("#");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(split[0] + "排" + split[1] + "座");
        return stringBuffer.toString();
    }


    /**
     * double类型的输入检查
     *
     * @param name 输入的字符串
     * @param min  最小值
     * @param max  最大值
     * @return
     */
    public static double checkInputForDouble(String name, double min, double max) {
        double res = 0;
        while (true) {
            System.out.println(name);
            scanner.useDelimiter("\n");
            String input = scanner.next();
            try {
                res = Double.parseDouble(input);
                if (!isNumber(input)) {
                    System.out.println("至多输入俩位小数");
                    continue;
                }
                if (res >= min && res <= max) {
                    break;
                } else {
                    System.out.println("请输入" + min + " ~" + max + "之间的值");
                }
            } catch (NumberFormatException e) {
                System.out.println("输入不合法");
            }
        }
        return res;
    }

    /**
     * 判断是否为俩位n小数的数值（可用于金额判断）
     *
     * @param str
     * @return
     */

    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
        Matcher match = pattern.matcher(str);
        if (!match.matches()) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * Double类型保留小数
     */

    public static double decimalForDouble(double d) {
        BigDecimal b = new BigDecimal(d);
        double df = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return df;
    }
}

