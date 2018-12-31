package test;

import javafx.scene.input.DataFormat;
import utils.Utils;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date: 2018/12/26 0026
 **/
public class TimeTest {

    public static void main(String[] args) {
        // timeTest();
      //  String scheduljudge = scheduljudge("2018年5月1日14:15", 120);
       // System.out.println(scheduljudge);
      //  System.out.println("2018年1月1月12时21分".compareTo("2018年1月1月14时21分"));
      //  System.out.println(Utils.getTime());
      timeTest();
    }

    public static void timeTest() {
        Date date1 = new Date();
        String s = new SimpleDateFormat("yyyy-MM-dd-HH:mm").format(date1);
        //加上五十分钟  =  1000*60*50
        long l = date1.getTime() + 1000000 * 60 * 50;
        Date date2 = new Date(l);
        String s3 = new SimpleDateFormat("yyyy-MM-dd-HH:mm").format(date2);
        //System.out.println(s);
        System.out.println(s3);


    }

    /**
     * @param srcDate  开始时间     2018年12月1日 14:20
     * @param duration 播放时长     150(分钟)
     */
    public static String scheduljudge(String srcDate, int duration) {
        String time = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
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
}
