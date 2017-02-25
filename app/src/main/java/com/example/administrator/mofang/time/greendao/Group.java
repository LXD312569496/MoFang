package com.example.administrator.mofang.time.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by Administrator on 2017/2/23.
 */

@Entity
public class Group {


    @Id(autoincrement = false)
    String groupName;

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Generated(hash = 1797409645)
    public Group(String groupName) {
        this.groupName = groupName;
    }

    @Generated(hash = 117982048)
    public Group() {
    }


}
