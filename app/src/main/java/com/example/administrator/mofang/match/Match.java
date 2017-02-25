package com.example.administrator.mofang.match;

/**
 * Created by Administrator on 2017/2/6.
 * 赛事bean
 */

public class Match {

    String url;//网址，用于启动webview
    String date;//日期
    String name;//名字
    Boolean isOut;//是否已经过期

    public Match(String url, String date, String name, Boolean isOut) {
        this.url = url;
        this.date = date;
        this.name = name;
        this.isOut = isOut;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getOut() {
        return isOut;
    }

    public void setOut(Boolean out) {
        isOut = out;
    }
}


