package com.example.administrator.mofang;

import android.Manifest;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.example.administrator.mofang.set.UPushEvent;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.IUmengCallback;
import com.umeng.message.PushAgent;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.List;

import butterknife.ButterKnife;
import cn.bmob.v3.update.BmobUpdateAgent;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;


public class MainActivity extends AppCompatActivity {

    private SharedPreferences.Editor editor;

    public static final int REQUEST_CODE_SETTING=100;
    public static String APP_LANGUAGE = "";

    private MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        APP_LANGUAGE = getString(R.string.app_language);

        PushAgent.getInstance(this).onAppStart();


        initPermission();
        isFirstRun();
        initData();
        initView();
        //发起自动更新
        BmobUpdateAgent.update(this);
        EventBus.getDefault().register(this);




    }

    private void initPermission() {
        // 先判断是否有权限。
        if(AndPermission.hasPermission(this, Manifest.permission_group.STORAGE,Manifest.permission_group.PHONE,Manifest.permission_group.LOCATION)) {
            // 有权限，直接do anything.
        } else {
            // 申请权限。
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                AndPermission.with(this)
                        .requestCode(100)
                        .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE,Manifest.permission.ACCESS_FINE_LOCATION)
                        .send();
            }
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 只需要调用这一句，其它的交给AndPermission吧，最后一个参数是PermissionListener。
        AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, listener);
    }

    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。
            if(requestCode == 100) {
                // TODO 相应代码。
            } else if(requestCode == 101) {
                // TODO 相应代码。
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。

            // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
            if (AndPermission.hasAlwaysDeniedPermission(MainActivity.this, deniedPermissions)) {
                // 第一种：用默认的提示语。
                AndPermission.defaultSettingDialog(MainActivity.this, REQUEST_CODE_SETTING).show();

                // 第二种：用自定义的提示语。
                // AndPermission.defaultSettingDialog(this, REQUEST_CODE_SETTING)
                // .setTitle("权限申请失败")
                // .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
                // .setPositiveButton("好，去设置")
                // .show();

                // 第三种：自定义dialog样式。
                // SettingService settingService =
                //    AndPermission.defineSettingDialog(this, REQUEST_CODE_SETTING);
                // 你的dialog点击了确定调用：
                // settingService.execute();
                // 你的dialog点击了取消调用：
                // settingService.cancel();
            }
        }
    };
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

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //activity中调用 moveTaskToBack (boolean nonRoot)方法即可将activity 退到后台，
    // 而不用finish()退出。参数为false代表只有当前activity是task根，
    // 指应用启动的第一个activity时才有效;如果为true则忽略这个限制，
    // 任何activity都可以有效。
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);//true对任何Activity都适用
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
