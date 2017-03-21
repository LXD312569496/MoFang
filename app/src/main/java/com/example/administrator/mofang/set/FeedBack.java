package com.example.administrator.mofang.set;

import cn.bmob.v3.BmobObject;

/**
 * Created by asus on 2017/3/13.
 */

public class FeedBack extends BmobObject{
    String content;

    public FeedBack(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
