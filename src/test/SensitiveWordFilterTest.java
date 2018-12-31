package test;

/**
 * Date: 2018/12/27 0027
 **/
public class SensitiveWordFilterTest {
    public static void main(String[] args) {
        // “性”、“色情”、“爆炸”、“恐怖”、“枪”、“军火”

        String[] cd = {"性","色情","爆炸","恐怖","枪","军火","法轮功"};//屏蔽词词典

        String mess = "拉登色情狂，经常有性暴力，是一个非常可怕的恐怖分子，经常制造爆炸事件，走私军火，枪械，他还练法轮功。。。。。。";
        for (String str : cd) {
            //生成指定长度的*
            String x = "";
            for (int i = 0; i < str.length(); i++) {
                x+="*";
            }

            if(mess.contains(str)){
                mess = mess.replace(str, x);
            }
        }


        System.out.println(mess);

    }

}
