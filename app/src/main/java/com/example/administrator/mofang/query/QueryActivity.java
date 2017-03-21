package com.example.administrator.mofang.query;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.administrator.mofang.R;
import com.example.administrator.mofang.query.competitor.CompetitorFragment;
import com.example.administrator.mofang.query.match.MatchFragment;
import com.example.administrator.mofang.query.rank.RankFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asus on 2017/3/16.
 */

public class QueryActivity extends AppCompatActivity {

    public static final int ACTION_MATCH = 0;
    public static final int ACTION_RANK = 1;
    public static final int ACTION_COMPETITOR = 2;
    @BindView(R.id.query_tool_bar)
    Toolbar mToolBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        ButterKnife.bind(this);

        int action = getIntent().getIntExtra("action", 0);
        switch (action) {
            case ACTION_MATCH:
                getSupportFragmentManager().beginTransaction().replace(R.id.query_fl_container, new MatchFragment()).commit();
                mToolBar.setTitle("查看赛事信息");
                break;
            case ACTION_RANK:
                getSupportFragmentManager().beginTransaction().replace(R.id.query_fl_container, new RankFragment()).commit();
                mToolBar.setTitle("查看排名信息");
                break;
            case ACTION_COMPETITOR:
                getSupportFragmentManager().beginTransaction().replace(R.id.query_fl_container, new CompetitorFragment()).commit();
                mToolBar.setTitle("查询选手信息");
                break;
        }

        setSupportActionBar(mToolBar);
        mToolBar.setNavigationIcon(R.drawable.back);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    public static void startActivity(Context context, int action) {
        Intent intent = new Intent(context, QueryActivity.class);
        intent.putExtra("action", action);
        context.startActivity(intent);
    }

}

