package com.example.administrator.mofang.query.rank;

/**
 * Created by Administrator on 2017/2/7.
 * 排名
 */

public class Rank {

    String url;//用于点击启动webview
    String name;//名字
    String countryPic;
    String country;//地区
    String score;//成绩

    public Rank(String url, String name, String countryPic, String country, String score) {
        this.url = url;
        this.name = name;
        this.countryPic = countryPic;
        this.country = country;
        this.score = score;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryPic() {
        return countryPic;
    }

    public void setCountryPic(String countryPic) {
        this.countryPic = countryPic;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
