package test;

import java.util.ArrayList;

/**
 * 座位设计方案
 * Date: 2018/12/25 0025
 **/
public class SeatTest {

    public static void main(String[] args) {
        String[] date = {"1#3", "2#4", "3#6"};
        ArrayList<String> arrayList = new ArrayList<>();
        for (String s : date) {
            arrayList.add(s);
        }

        seat(95,arrayList);
    }

    public static void seat(int capacity, ArrayList<String> seatNum) {
        //默认为10行
        int col = 10 + 1;
        int row = capacity / 10;
        System.out.println(row);
        int free = capacity % row;
        row += 1;
        int[][] array = new int[row][col];
        if (free > 0) {
            row += 1;
            array = new int[row][col];
            for (int i = free + 1; i < col; i++) {
                array[row - 1][i] = -1;
            }
        }

        for (int i = 0; i < seatNum.size(); i++) {
          //  String[] split = date[i].split("#");
            String[] split = seatNum.get(i).split("#");
            int s1 = Integer.parseInt(split[0]);
            int s2 = Integer.parseInt(split[1]);
            array[s1][s2] = 1;
        }
        for (int i = 1; i < array.length; i++) {
            System.out.print("第" + i + "排=");
            for (int j = 1; j < col; j++) {

                if (array[i][j] == 0) {
                    System.out.print("[ ]\t");
                } else if (array[i][j] == 1) {
                    System.out.print("[*]\t");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();

        }


    }


}
