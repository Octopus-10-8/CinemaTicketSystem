package utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Date: 2018/12/27 0027
 **/
public class MyMd5Utils {
    /**
     * Md5加密32位小写
     *
     * @param str
     * @return
     */
    public static String getMd5(String str) {
        String res = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            res = new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("密码存储失败");
        }
        return res;
    }

}
