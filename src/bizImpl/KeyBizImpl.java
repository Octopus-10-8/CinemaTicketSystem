package bizImpl;

import biz.KeyBiz;
import dao.KeyDao;
import daoImpl.KeyDaoImpl;
import entity.Key;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Date: 2018/12/31 0031
 **/
public class KeyBizImpl implements KeyBiz {

    private KeyDao keyDao = new KeyDaoImpl();

    @Override
    public ArrayList<Key> queryKey() {
        ArrayList<Key> arrayList = keyDao.queryKey();
        //倒序输出
        Collections.sort(arrayList, new Comparator<Key>() {
            @Override
            public int compare(Key o1, Key o2) {
                return  o2.getCount()-o1.getCount();
            }
        });
        return arrayList;
    }

    @Override
    public boolean addKey(Key key) {
        if (key == null) {
            return false;
        }
        keyDao.addKey(key);
        return true;
    }
}
