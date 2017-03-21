package com.example.administrator.mofang.query.match;

/**
 * Created by Administrator on 2017/2/6.
 * 赛事bean
 */

public class Match {

    String url;//网址，用于启动webview
    String date;//日期
    String name;//名字
    Boolean isOut;//是否已经过期
    String danger;//距离比赛报名截止还有多久

    public Match(String url, String date, String name, Boolean isOut, String danger) {
        this.url = url;
        this.date = date;
        this.name = name;
        this.isOut = isOut;
        this.danger = danger;
    }

    public String getDanger() {
        return danger;
    }

    public void setDanger(String danger) {
        this.danger = danger;
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


