package com.example.administrator.mofang.me;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/2/8.
 * 公式
 */

public class Case extends BmobObject{
    String url;//用于启动webview
    String name;//公式的名字
    String bitmap;//魔方的情况图片
    String result;//公式


    public Case(String url, String name, String bitmap, String result) {
        this.url = url;
        this.name = name;
        this.bitmap = bitmap;
        this.result = result;
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getBitmap() {
        return bitmap;
    }

    public void setBitmap(String bitmap) {
        this.bitmap = bitmap;
    }
}
