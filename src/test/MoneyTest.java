package test;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Date: 2018/12/29 0029
 **/
public class MoneyTest {

    public static void main(String[] args) {
//        double d = 9.84;
//        double d2 = 1.22;
//        System.out.println(d * d2 * d2);
//        BigDecimal bigDecimal = new BigDecimal(d);
//        BigDecimal bigDecimal1 = new BigDecimal(d2);
//        BigDecimal multiply = bigDecimal.multiply(bigDecimal1);
//        Double mul = mul(d, d2);
//        Double mul1 = mul(mul, 1.86);
//        double v = Double.valueOf(mul1) * 1.86;
//        System.out.println(v);
//        System.out.println(mul1);
//        System.out.println(mul);

        boolean number = isNumber("121.12");
        System.out.println(number);

    }


    public static Double mul(Double value1, Double value2) {
        BigDecimal b1 = new BigDecimal(Double.toString(value1));
        BigDecimal b2 = new BigDecimal(Double.toString(value2));
        return b1.multiply(b2).doubleValue();
    }

    //金额验证
    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
        Matcher match = pattern.matcher(str);
        if (!match.matches() ) {
            return false;
        } else {
            return true;
        }
    }
}
