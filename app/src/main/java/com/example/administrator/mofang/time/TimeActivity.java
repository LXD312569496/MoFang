package com.example.administrator.mofang.time;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.mofang.R;
import com.example.administrator.mofang.TabEntity;
import com.example.administrator.mofang.time.greendao.DaoManager;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/22.
 * time模块的Activity，包含三个fragment
 */

public class TimeActivity extends AppCompatActivity {


    @BindView(R.id.show_tab_layout)
    CommonTabLayout mTabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        ButterKnife.bind(this);


        DaoManager.init(this);
        initView();

    }

    public static void startActivity(Context context){
        Intent intent=new Intent(context,TimeActivity.class);
        context.startActivity(intent);
    }

    private void initView() {
        ArrayList<CustomTabEntity> entities=new ArrayList<>();
        entities.add(new TabEntity("计时",R.drawable.time_selected,R.drawable.time_unselected));
        entities.add(new TabEntity("成绩",R.drawable.score_selected,R.drawable.score_unselected));
//        entities.add(new TabEntity("设置",R.mipmap.ic_launcher,R.mipmap.ic_launcher));


        ArrayList<Fragment> fragmengList=new ArrayList<>();
        fragmengList.add(TimeFragment.newInstance());
        fragmengList.add(new ScoreFragment());
//        fragmengList.add(new ScoreFragment());

        mTabLayout.setTabData(entities,this,R.id.show_fl_container,fragmengList);
    }



}
