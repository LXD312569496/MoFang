package com.example.administrator.mofang;

import android.app.Application;
import android.util.Log;

import com.umeng.analytics.MobclickAgent;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import cn.bmob.v3.Bmob;


/**
 * Created by Administrator on 2017/2/7.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        HttpUtil.getInstance(this);
        JsoupUtil.getInstance(this);

        initBmob();
        initUMeng();
//        initRealm();

    }

//    private void initRealm(){
//        Realm.init(this);
//        RealmConfiguration config=new RealmConfiguration.Builder()
//                .name("score.realm")
//                .build();
//        Realm.setDefaultConfiguration(config);
//    }

    private void initBmob(){
        Bmob.initialize(this, "bd348eb6d1627be4c6a815d81e92c2e7");
    }

    private void initUMeng(){
        /** 设置是否对日志信息进行加密, 默认false(不加密). */
//        MobclickAgent.enableEncrypt(true);//6.0.0版本及以后
        MobclickAgent.openActivityDurationTrack(false);

        //设置分享
        Config.isJumptoAppStore = true;
        PlatformConfig.setWeixin("wxe078cc80136f5317", "bbeaa00792097b2037dae9a2dc31da89");
        UMShareAPI.get(this);


        //注册推送服务，每次调用register方法都会回调该接口
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //关闭日志输出
        mPushAgent.setDebugMode(false);
        //设置通知栏的最多个数
        mPushAgent.setDisplayNotificationNumber(2);
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                Log.d("test", deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.d("test", "失败");
            }
        });
    }


}
