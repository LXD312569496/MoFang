package com.example.administrator.mofang.time;

import java.util.Random;

/**
 * Created by asus on 2017/3/13.
 */

public class Scrambler {

    private static String[] cube_2x2x2 = new String[]{"F", "B", "U", "D", "L", "R",};//2阶魔方
    private static String[] cube_3x3x3 = new String[]{"F", "B", "U", "D", "L", "R",};//3阶魔方
    private static String[] cube_4x4x4 = new String[]{"F", "B", "U", "D", "L", "R", "Fw", "Bw", "Uw", "Dw", "Lw", "Rw"};//4阶魔方
    private static String[] cube_5x5x5 = new String[]{"F", "B", "U", "D", "L", "R", "Fw", "Bw", "Uw", "Dw", "Lw", "Rw"};//5阶魔方
    private static String[] cube_6x6x6 = new String[]{"F", "B", "U", "D", "L", "R", "Fw", "Bw", "Uw", "Dw", "Lw", "Rw", "3Fw", "3Bw", "3Uw", "3Dw", "3Lw", "3Rw"};//6阶魔方
    private static String[] cube_7x7x7 = new String[]{"F", "B", "U", "D", "L", "R", "Fw", "Bw", "Uw", "Dw", "Lw", "Rw", "3Fw", "3Bw", "3Uw", "3Dw", "3Lw", "3Rw"};//7阶魔方

    //步数
    private static final int moves_2x2x2 = 10;
    private static final int moves_3x3x3 = 25;
    private static final int moves_4x4x4 = 40;
    private static final int moves_5x5x5 = 60;
    private static final int moves_6x6x6 = 80;
    private static final int moves_7x7x7 = 80;

    private static String[] direction = {"", "", "'", "'", "2"};//附加条件，表示方向：顺时针，逆时针，180度

    private int mOld = 0;//表示上一次所产生的随机数

    public String getScramble(String type) {
        StringBuilder scramble = new StringBuilder();
        int count = 0;
        String temp[] = new String[]{};
        switch (type) {
            case "2x2x2":
                count = moves_2x2x2;
                temp = cube_2x2x2;
                break;
            case "3x3x3":
                count = moves_3x3x3;
                temp = cube_3x3x3;
                break;
            case "4x4x4":
                count = moves_4x4x4;
                temp = cube_4x4x4;
                break;
            case "5x5x5":
                count = moves_5x5x5;
                temp = cube_5x5x5;
                break;
            case "6x6x6":
                count = moves_6x6x6;
                temp = cube_6x6x6;
                break;
            case "7x7x7":
                count = moves_7x7x7;
                temp = cube_7x7x7;
                break;
        }
        for (int i = 0; i < count; i++) {
            scramble.append(getRandomOption(temp) + getRandomDirection(direction) + " ");
        }
        return scramble.toString();
    }


    //随机返回一个操作动作
    private String getRandomOption(String[] str) {
        Random random = new Random();
        int newInt = random.nextInt(str.length);
        while (newInt == mOld) {
            newInt = random.nextInt(str.length);
        }
        mOld = newInt;
        return str[newInt];
    }

    //随机返回一个操作的方向
    private String getRandomDirection(String[] str) {
        Random random = new Random();
        return str[random.nextInt(str.length)];
    }


}
