package daoImpl;

import dao.KeyDao;

import entity.Key;

import java.io.File;
import java.util.ArrayList;

/**
 * Date: 2018/12/31 0031
 **/
public class KeyDaoImpl extends BaseDao implements KeyDao {
    public KeyDaoImpl() {
        super(new File("key.txt"));
    }

    /**
     * 查询关键字
     * @return
     */
    @Override
    public ArrayList<Key> queryKey() {
        ArrayList<Key> read = read();
        return read;
    }

    /**
     * 添加关键字
     * @param key
     */
    @Override
    public void addKey(Key key) {
        ArrayList<Key> read = read();
        if (read.contains(key)) {
            int i = read.indexOf(key);
            Key k = read.get(i);
            k.setCount(k.getCount() + 1);
            read.set(i, k);
        } else {
            read.add(key);
        }
        write(read);
        closeAll();
    }
}
