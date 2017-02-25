package com.example.administrator.mofang;

import android.support.v4.app.Fragment;

/**
 * Created by Administrator on 2017/2/17.
 */

public class FragmentEvent {

    Fragment fragment;

    public FragmentEvent(Fragment fragment) {
        this.fragment = fragment;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
