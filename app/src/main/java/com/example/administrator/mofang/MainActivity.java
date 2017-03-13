package com.example.administrator.mofang;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.administrator.mofang.competitor.CompetitorFragment;
import com.example.administrator.mofang.drawer.DrawerFragment;
import com.example.administrator.mofang.drawer.SaveEvent;
import com.example.administrator.mofang.drawer.UPushEvent;
import com.example.administrator.mofang.match.MatchFragment;
import com.example.administrator.mofang.me.Case;
import com.example.administrator.mofang.rank.RankFragment;
import com.example.administrator.mofang.solution.GongShiFragment;
import com.example.administrator.mofang.time.Scrambler;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.IUmengCallback;
import com.umeng.message.PushAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.example.administrator.mofang.R.id.webView;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.main_tab_layout)
    CommonTabLayout mTabLayout;
    @BindView(R.id.main_drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.main_tool_bar)
    Toolbar mToolBar;

    private ArrayList<Fragment> mFragmentList;
    private ArrayList<CustomTabEntity> mTabEntityList;


    private SharedPreferences.Editor editor;

    public static String APP_LANGUAGE = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
Log.d("test",(mTabLayout==null)+"");

        APP_LANGUAGE = getString(R.string.app_language);

        PushAgent.getInstance(this).onAppStart();

        isFirstRun();
        initData();
        initView();
        //发起自动更新
        BmobUpdateAgent.update(this);
        EventBus.getDefault().register(this);


    }

    private void temp(){
        JsoupUtil.getInterFace().getCase()
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        List<Case> list = JsoupUtil.getCase(s);
                        for (int i = 0; i <list.size() ; i++) {
                            com.example.administrator.mofang.solution.Case bean=new com.example.administrator.mofang.solution.Case();
                            bean.setName("ZBF2L");
                            bean.setType("三阶");
                            bean.setPicture(new BmobFile(list.get(i).getName()+".jpg","",list.get(i).getBitmap()));
                            bean.setSolution(list.get(i).getResult());
                            bean.setCaseName(list.get(i).getName());
                            bean.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if(e==null){
                                        Log.d("test","添加数据成功，");
                                    }else{
                                        Log.d("test","创建数据失败：" + e.getMessage());
                                    }
                                }
                            });
                        }
                    }
                });

    }


    private void initData() {
        mFragmentList = new ArrayList<>();
//        mFragmentList.add(new TimeFragment());
        mFragmentList.add(new MatchFragment());
        mFragmentList.add(new RankFragment());
        mFragmentList.add(new CompetitorFragment());


        mFragmentList.add(new GongShiFragment());

        mTabEntityList = new ArrayList<>();
//        mTabEntityList.add(new TabEntity(getString(R.string.main_tab_time), R.drawable.time_selected, R.drawable.time_unselected));
        mTabEntityList.add(new TabEntity(getString(R.string.main_tab_match), R.drawable.macth_selected, R.drawable.match_unselected));
        mTabEntityList.add(new TabEntity(getString(R.string.main_tab_rank), R.drawable.rank_selected, R.drawable.rank_unselected));
        mTabEntityList.add(new TabEntity(getString(R.string.main_tab_competitor), R.drawable.competitor_selected, R.drawable.competitor_unselected));
        mTabEntityList.add(new TabEntity(getString(R.string.main_tab_case), R.drawable.case_selected, R.drawable.case_unselected));

    }

    private void initView() {
        mTabLayout.setTabData(mTabEntityList, this, R.id.main_fl_container, mFragmentList);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_drawer_container, new DrawerFragment())
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

    @OnClick(R.id.main_iv_drawer)
    public void onClick() {
        mDrawerLayout.openDrawer(Gravity.LEFT);
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


    //下载全部公式
    @Subscribe
    public void onEventMainThread(SaveEvent event) {
        mDrawerLayout.closeDrawers();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("下载全部公式");
        builder.setMessage("下载全部公式后，可以在无网络的状态下查看全部公式");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "功能还在开发中(目前看过的公式会自动缓存，" +
                        "所以以前看过的公式无网的情况下可以可以查看)", Toast.LENGTH_SHORT).show();

//                List<SpinnerBean> spinnerList = mCaseFragment.getSpinnerList();
//                for (int j = 0; j < spinnerList.size(); j++) {
//                    JsoupUtil.getInterFace().getCase(spinnerList.get(j).getName())
//                            .subscribeOn(Schedulers.io())
//                            .subscribe(new Subscriber<String>() {
//                                @Override
//                                public void onCompleted() {
//
//                                }
//
//                                @Override
//                                public void onError(Throwable throwable) {
//
//                                }
//
//                                @Override
//                                public void onNext(String s) {
////                                    List<Case> list = JsoupUtil.getCase(s);
////                                    for (int k = 0; k < list.size(); k++) {
////                                        Glide.with(MainActivity.this).load(list.get(k).getBitmap()).downloadOnly(100, 100);
////                                    }
//                                }
//                            });
//                }


            }
        });
        builder.create().show();
    }


//    @Subscribe
//    public void onEventMainThread(FragmentEvent event){
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.main_fl_container,event.getFragment())
//                .addToBackStack(null)
//                .commit();
//    }

}
