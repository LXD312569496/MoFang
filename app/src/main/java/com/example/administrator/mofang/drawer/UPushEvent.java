package com.example.administrator.mofang.drawer;

/**
 * Created by Administrator on 2017/2/9.
 */

public class UPushEvent {
    Boolean isPush;//是否开启比赛推送

    public UPushEvent(Boolean isPush) {
        this.isPush = isPush;
    }

    public Boolean getPush() {
        return isPush;
    }

    public void setPush(Boolean push) {
        isPush = push;
    }
}
