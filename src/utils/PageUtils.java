package utils;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * 功能：显示分页
 * Date: 2018/12/31 0031
 **/
public class PageUtils {
    private static final int LIMIT = 3;
    public static <T> void pageSelect(ArrayList<T> list) {
        int start = 0;
        page(start, LIMIT, list);
        while (true) {
            int i = Utils.checkInput("1.[上一页]  2.[下一页]",1,100);
            if (i == 1) {
                if (start == 0) {  //已经第一页的话，显示首页
                    page(0, LIMIT, list);
                } else {
                    page(start - LIMIT, LIMIT, list);
                    start -= LIMIT;
                }
            } else if (i == 2) {
                page(start + LIMIT, LIMIT, list);
                start += LIMIT;
            } else {
                page(start, LIMIT, list);
            }
            if (!Utils.isGoOn()) {
                break;
            }
        }
    }

    private static <T> void page(int start, int limit, ArrayList<T> list) {
        if (start <= 0) {   //上一页
            start = 0;
        }
        if (start + limit >= list.size()) {  //下一页
            start = list.size() - limit;
        }
        for (int i = start; i < start + limit; i++) {
            System.out.println(list.get(i));
        }
    }

}
