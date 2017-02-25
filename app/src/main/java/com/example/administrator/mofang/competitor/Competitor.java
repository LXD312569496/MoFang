package com.example.administrator.mofang.competitor;

/**
 * Created by Administrator on 2017/2/7.
 */

public class Competitor {

    String url;//用于启动webview
    String name;
    String wcaId;
    String country;//国家或者地区
    String countryPic;

    public Competitor(String url, String name, String wcaId, String country, String countryPic) {
        this.url = url;
        this.name = name;
        this.wcaId = wcaId;
        this.country = country;
        this.countryPic = countryPic;
    }

    public String getCountryPic() {
        return countryPic;
    }

    public void setCountryPic(String countryPic) {
        this.countryPic = countryPic;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getWcaId() {
        return wcaId;
    }

    public void setWcaId(String wcaId) {
        this.wcaId = wcaId;
    }
}
