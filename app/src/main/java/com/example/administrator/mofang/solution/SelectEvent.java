package com.example.administrator.mofang.solution;

import java.util.List;

/**
 * Created by Administrator on 2017/2/17.
 */

public class SelectEvent {
//    Map<String,Boolean> map;
//
//
//    public SelectEvent(Map<String, Boolean> map) {
//        this.map = map;
//    }
//
//    public Map<String, Boolean> getMap() {
//        return map;
//    }
//
//    public void setMap(Map<String, Boolean> map) {
//        this.map = map;
//    }

//    String name;
//    Boolean isDelete;//是否删除已选择
//
//    public SelectEvent(String name, Boolean isDelete) {
//        this.name = name;
//        this.isDelete = isDelete;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Boolean getDelete() {
//        return isDelete;
//    }
//
//    public void setDelete(Boolean delete) {
//        isDelete = delete;
//    }


    List<String> list;
    boolean isChange;

    public SelectEvent(List<String> list, boolean isChange) {
        this.list = list;
        this.isChange = isChange;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public boolean isChange() {
        return isChange;
    }

    public void setChange(boolean change) {
        isChange = change;
    }
}
