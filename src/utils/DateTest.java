package utils;

/**
 * 日期加七天
 * Date: 2018/12/22 0022
 **/

import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTest {
    public static void main(String[] args) throws ParseException {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 日期格式
//        Date date = dateFormat.parse("2015-07-31 12:12:12"); // 指定日期
//        Date newDate = addDate(date, 7); // 指定日期加上20天
//        System.out.println(dateFormat.format(date));// 输出格式化后的日期
//        System.out.println(dateFormat.format(newDate));

        System.out.println(Utils.getTime());
        System.out.println(Utils.scheduljudge("2018-12-28 17:02",50));

        if (Utils.getTime().compareTo(Utils.scheduljudge("2018-12-28 17:02",50))>0){

        }else {

        }

    }


    public static Date addDate(Date date, long day) throws ParseException {
        long time = date.getTime(); // 得到指定日期的毫秒数
        day = day * 24 * 60 * 60 * 1000; // 要加上的天数转换成毫秒数
        time += day; // 相加得到新的毫秒数
        return new Date(time); // 将毫秒数转换成日期
    }


    //时间测试比较大小
    public static void compareDate(String s1, String s2) {

        if (s1.compareTo(s2) > 0) {
            System.out.println("ddsd");
        }
    }






}
