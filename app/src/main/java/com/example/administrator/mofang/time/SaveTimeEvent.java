package com.example.administrator.mofang.time;

/**
 * Created by Administrator on 2017/2/23.
 */

public class SaveTimeEvent {
    long score;//成绩
    String time;//时间
    String scramble;//打乱

    public SaveTimeEvent(long score, String time,String scramble) {
        this.score = score;
        this.time = time;
        this.scramble=scramble;
    }

    public String getScramble() {
        return scramble;
    }

    public void setScramble(String scramble) {
        this.scramble = scramble;
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
