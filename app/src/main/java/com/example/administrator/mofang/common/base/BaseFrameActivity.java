package com.example.administrator.mofang.common.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by asus on 2017/3/8.
 */

public abstract class BaseFrameActivity extends Activity{

    private static final String TAG = "BaseActivity";
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, getClass().getName()+"   onCreate ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, getClass().getName()+"   onStart ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, getClass().getName()+"   onResume ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, getClass().getName()+"   onPause ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, getClass().getName()+"   onStop ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, getClass().getName()+"   onDestroy ");
    }

}
