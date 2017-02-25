package com.example.administrator.mofang;

import com.flyco.tablayout.listener.CustomTabEntity;

/**
 * Created by Administrator on 2017/2/6.
 */

public class TabEntity implements CustomTabEntity{
    String title;
    int iconSelected;
    int iconUnSelected;

    public TabEntity(String title, int iconSelected, int iconUnSelected) {
        this.title = title;
        this.iconSelected = iconSelected;
        this.iconUnSelected = iconUnSelected;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return iconSelected;
    }

    @Override
    public int getTabUnselectedIcon() {
        return iconUnSelected;
    }
}
