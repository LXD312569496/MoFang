package com.example.administrator.mofang.common;

/**
 * Created by asus on 2017/3/8.
 * eventbus消息基类，我们发送的data是泛型结构，所以在指定位置进行强转就可获得指定对象了。
 */

public class EventCenter<T> {

    private int eventCode=-1;
    private T event;

    public EventCenter(int eventCode, T event) {
        this.eventCode = eventCode;
        this.event = event;
    }

    public int getEventCode() {
        return eventCode;
    }

    public void setEventCode(int eventCode) {
        this.eventCode = eventCode;
    }

    public T getEvent() {
        return event;
    }

    public void setEvent(T event) {
        this.event = event;
    }
}
