package com.example.administrator.mofang.query.competitor;

/**
 * Created by Administrator on 2017/2/7.
  地区
 */

public class SpinnerBean {
    String value;//传给url的一个参数
    String name;//名字

    public SpinnerBean(String value, String name) {
        this.value = value;
        this.name = name;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
