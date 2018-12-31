package test;

import biz.KeyBiz;
import bizImpl.KeyBizImpl;
import entity.Key;
import utils.Utils;

import java.util.*;

/**
 * Date: 2018/12/31 0031
 **/
public class KeyTest {
    private static KeyBiz keyBiz = new KeyBizImpl();

    public static void main(String[] args) {
        ArrayList<Key> arrayList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 10; i++) {
            System.out.println("输入关键词");
            String keyWord = scanner.next();
            Key k = new Key(keyWord);
            keyBiz.addKey(k);
        }
        Utils.showList(keyBiz.queryKey(), "所有数据");
        PriorityQueue<Key> queue = new PriorityQueue<>(new Comparator<Key>() {
            @Override
            public int compare(Key o1, Key o2) {
                return o1.getCount() - o2.getCount();
            }
        });

        for (Key key : keyBiz.queryKey()) {
            if (queue.size() < 5) {
                queue.offer(key);
            } else if (key.getCount() > queue.peek().getCount()) {
                queue.poll();
                queue.offer(key);
            }
        }

    }


}
