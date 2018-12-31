package test;

import utils.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Date: 2018/12/25 0025
 **/
public class InputTestTwo {

    public final static String SEARCHKEYREGEX = "[\\s*$*$~!/@#$%^&*()\\-_=+\\|[{}];:\'\",<.>/?]+";

    //！@~#￥%……&*（）——+{}|：“《》？-=【】、；‘，。、
    public static void main(String[] a) {
        Matcher m = null;
        String searchKey = "";
        System.out.println(SEARCHKEYREGEX);
        Pattern p = Pattern.compile(SEARCHKEYREGEX);
        m = p.matcher(searchKey);
        System.out.println(m.find());
    }

}
