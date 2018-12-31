package utils;

import biz.KeyBiz;
import bizImpl.KeyBizImpl;
import entity.Key;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Date: 2018/12/31 0031
 **/
public class KeyWords {
    private static KeyBiz keyBiz = new KeyBizImpl();

    public static  Key[]  getKeyWords() {
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
        Key[] keys = new Key[5];
        for (int i = keys.length - 1; i >= 0; i--) {

            keys[i] = queue.poll();
        }
        return keys;
    }
}
