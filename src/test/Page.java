package test;

import biz.MovieBiz;
import bizImpl.MovieBizImpl;
import entity.Movie;
import utils.Utils;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Date: 2018/12/29 0029
 **/
public class Page {
    private static MovieBiz movieBiz = new MovieBizImpl();

    public static void main(String[] args) {
        ArrayList<Movie> movies = movieBiz.queryMovie();
        pageSelect(movies);
    }

    public static <T> void pageSelect(ArrayList<T> list) {
        int start = 0;
        int limit = 3;
        page(start, limit, list);
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("1：上一页--2：下一页");
            int i = scanner.nextInt();
            if (i == 1) {
                if (start == 0) {  //已经第一页的话，显示首页
                    page(0, limit, list);
                } else {
                    page(start - limit, limit, list);
                    start -= limit;
                }
            } else if (i == 2) {
                page(start + limit, limit, list);
                start += limit;
            } else {
                page(start, limit, list);
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
