package test;

import entity.Key;
import org.junit.Test;
import utils.KeyWords;
import utils.Utils;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Date: 2018/12/25 0025
 **/
public class InputTest {
    public static void main(String[] args) {

        Key[] keyWords = KeyWords.getKeyWords();
     //   Utils.showList(keyWords,"关键词");
        for (Key keyWord : keyWords) {
            System.out.println(keyWord);
        }

    }


}
