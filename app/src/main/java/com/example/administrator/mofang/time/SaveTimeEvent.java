package com.example.administrator.mofang.time;

/**
 * Created by Administrator on 2017/2/23.
 */

public class SaveTimeEvent {
    long score;//成绩
    String time;//时间

    public SaveTimeEvent(long score, String time) {
        this.score = score;
        this.time = time;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getScore() {
        return score;
    }

    public String getTime() {
        return time;
    }
}
