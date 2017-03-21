package com.example.administrator.mofang;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.mofang.set.UPushEvent;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.IUmengCallback;
import com.umeng.message.PushAgent;

import butterknife.ButterKnife;
import cn.bmob.v3.update.BmobUpdateAgent;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;


public class MainActivity extends AppCompatActivity {

    private SharedPreferences.Editor editor;

    public static String APP_LANGUAGE = "";

    private MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        APP_LANGUAGE = getString(R.string.app_language);

        PushAgent.getInstance(this).onAppStart();

        isFirstRun();
        initData();
        initView();
        //发起自动更新
        BmobUpdateAgent.update(this);
        EventBus.getDefault().register(this);


    }


    private void initData() {

    }

    private void initView() {
        mainFragment=new MainFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_fl_container,mainFragment)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    public void isFirstRun() {
        //判断是否是第一次运行
        //保存相应的设置信息
        SharedPreferences sharedPreferences = this.getSharedPreferences("share", MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
        editor = sharedPreferences.edit();
        if (isFirstRun) {
            Log.d("debug", "第一次运行");
            editor.putBoolean("isFirstRun", false);
            editor.putBoolean("isPush", true);
            editor.commit();
            //默认接收推送通知
            PushAgent.getInstance(this).enable(new IUmengCallback() {
                @Override
                public void onSuccess() {
                    Log.d("test", "开启推送通知成功");
                }

                @Override
                public void onFailure(String s, String s1) {
                    Log.d("test", "开启推送通知失败");
                }
            });


            SharedPreferences sharedPreferences1 = getSharedPreferences("case", MODE_PRIVATE);
            SharedPreferences.Editor editor1 = sharedPreferences1.edit();
            editor1.putBoolean("CLL", false);
            editor1.putBoolean("EG", false);

            editor1.putBoolean("PLL", true);
            editor1.putBoolean("OLL", true);
            editor1.putBoolean("F2L", true);
            editor1.putBoolean("OLLCP", false);
            editor1.putBoolean("ZBLL", false);
            editor1.putBoolean("COLL", false);
            editor1.putBoolean("CMLL", false);

            editor1.commit();
        } else {
            Log.d("debug", "不是第一次运行");
        }


    }


    @Subscribe
    public void onEventMainThread(UPushEvent event) {
        boolean b = event.getPush();
        Log.d("test", b + "");
        if (b) {
            PushAgent.getInstance(this).enable(new IUmengCallback() {
                @Override
                public void onSuccess() {
                    Log.d("test", "开启推送通知成功");
//                    Toast.makeText(MainActivity.this, "开启推送通知成功", Toast.LENGTH_SHORT).show();
                    editor.putBoolean("isPush", true);
                    editor.commit();
                }

                @Override
                public void onFailure(String s, String s1) {
                    Log.d("test", "开启推送通知失败");
                    Toast.makeText(MainActivity.this, "开启推送通知失败", Toast.LENGTH_SHORT).show();
                    editor.putBoolean("isPush", false);
                    editor.commit();
                }
            });
        } else {
            PushAgent.getInstance(this).disable(new IUmengCallback() {
                @Override
                public void onSuccess() {
                    Log.d("test", "关闭推送通知成功");
                    Toast.makeText(MainActivity.this, "关闭推送通知成功", Toast.LENGTH_SHORT).show();
                    editor.putBoolean("isPush", false);
                    editor.commit();
                }

                @Override
                public void onFailure(String s, String s1) {
                    Log.d("test", "关闭推送通知失败");
                    Toast.makeText(MainActivity.this, "关闭推送通知失败", Toast.LENGTH_SHORT).show();
                    editor.putBoolean("isPush", true);
                    editor.commit();
                }
            });
        }

    }


    @Subscribe
    public void onEventMainThread(FragmentEvent event){
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_fl_container,event.getFragment())
                .hide(mainFragment)
                .addToBackStack(null)
                .commit();
    }

}
