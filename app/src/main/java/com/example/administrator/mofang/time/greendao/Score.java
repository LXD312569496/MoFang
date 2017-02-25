package com.example.administrator.mofang.time.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by Administrator on 2017/2/22.
    成绩的实体类
 */

@Entity
public class Score {

    @Id(autoincrement = true)
    Long id;
    String name;//分组的名字
    long score;//计时的成绩
    String time;//计时的时间

    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public long getScore() {
        return this.score;
    }
    public void setScore(long score) {
        this.score = score;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1499519190)
    public Score(Long id, String name, long score, String time) {
        this.id = id;
        this.name = name;
        this.score = score;
        this.time = time;
    }
    @Generated(hash = 226049941)
    public Score() {
    }
  

}
