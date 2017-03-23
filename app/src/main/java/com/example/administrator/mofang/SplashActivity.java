package com.example.administrator.mofang;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.LinearLayout;

import net.youmi.android.normal.spot.SplashViewSettings;
import net.youmi.android.normal.spot.SpotListener;
import net.youmi.android.normal.spot.SpotManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asus on 2017/3/21.
 */

public class SplashActivity extends Activity {

    @BindView(R.id.splash_layout)
    LinearLayout mSplashLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);


        SplashViewSettings splashViewSettings = new SplashViewSettings();
        splashViewSettings.setAutoJumpToTargetWhenShowFailed(true);
        splashViewSettings.setTargetClass(MainActivity.class);
        splashViewSettings.setSplashViewContainer(mSplashLayout);
        SpotManager.getInstance(this).showSplash(this, splashViewSettings, new SpotListener() {
            @Override
            public void onShowSuccess() {
                Log.d("test","onShowSuccess");
            }

            @Override
            public void onShowFailed(int i) {
                Log.d("test","onShowFailed");
            }

            @Override
            public void onSpotClosed() {
                Log.d("test","onSpotClosed");
            }

            @Override
            public void onSpotClicked(boolean b) {
                Log.d("test","onSpotClicked");
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SpotManager.getInstance(this).onDestroy();
    }
}
