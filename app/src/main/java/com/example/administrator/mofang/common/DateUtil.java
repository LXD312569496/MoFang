package com.example.administrator.mofang.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/2/23.
 */

public class DateUtil {

    //毫秒转化为分秒
    public static String formatTime(long time) {
        StringBuilder result = new StringBuilder();

        long millSecond = time % 1000;
        long second = time / 1000 % 60;
        long min = time / 1000 / 60 % 60;
        long hour = time / 1000 / 60 / 60 % 24;

        if (hour != 0) {
            result.append(String.valueOf(hour) + ":");
        }
        if (min != 0) {
            if (min < 10) {
                result.append("0" + String.valueOf(min) + ":");
            } else {
                result.append(String.valueOf(min) + ":");
            }
        }

        result.append((second < 10 ? "0" + second : second) + "."
                + (millSecond < 100 ? "0" + millSecond : millSecond));
        return result.toString();
    }


    //获取时间年月日时分秒
    public static String getCurrentTime(){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        return format.format(date);
    }

    //获取时间年月日
    public static String getCurrentDate(){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        return format.format(date);
    }

}
