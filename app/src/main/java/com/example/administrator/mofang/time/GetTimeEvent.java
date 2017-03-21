package com.example.administrator.mofang.time;

/**
 * Created by asus on 2017/3/16.
 */

public class GetTimeEvent {
    long time;

    public GetTimeEvent(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
