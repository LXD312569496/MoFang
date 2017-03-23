package com.example.administrator.mofang.solution;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.administrator.mofang.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by asus on 2017/3/16.
 */

public class SolutionActivity extends AppCompatActivity {

    @BindView(R.id.solution_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.solution_load_layout)
    LinearLayout mLoadLayout;
    @BindView(R.id.solution_tool_bar)
    Toolbar mToolBar;

    private String name = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution);
        ButterKnife.bind(this);

        initView();

    }


    public static void startActivity(Context context, String name) {
        Intent intent = new Intent(context, SolutionActivity.class);
        intent.putExtra("name", name);
        context.startActivity(intent);
    }


    private void initView() {
        name = getIntent().getStringExtra("name");
        //设置toolbar

        mToolBar.setTitle(name);
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationIcon(R.drawable.back);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_refresh:
                        getData();
                        break;
                }
                return true;
            }
        });

        getData();

    }


    /**
     * 创建菜单
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.solution_menu, menu);
        return true;
    }

    private void getData() {
        mLoadLayout.setVisibility(View.VISIBLE);
        BmobQuery<Case> query = new BmobQuery<>();
        query.addWhereEqualTo("name", name);
        query.order("caseName");
        query.setLimit(1000);

        //判断是否有缓存，该方法必须放在查询条件（如果有的话）都设置完之后再来调用才有效，就像这里一样。
//            boolean isCache = query.hasCachedResult(Case.class);
//            if(isCache){
//                query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);    // 如果有缓存的话，则设置策略为CACHE_ELSE_NETWORK
//            }else{
//                query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);    // 如果没有缓存的话，则设置策略为NETWORK_ELSE_CACHE
//            }

        query.findObjects(new FindListener<Case>() {
            @Override
            public void done(List<Case> list, BmobException e) {
                if (e != null) {
                    Log.d("test", e.getMessage());
                    return;
                }
                if (list == null || list.size() == 0) {
                    return;
                }
                CaseAdapter adapter = new CaseAdapter(list);
                mRecyclerView.setAdapter(adapter);

                LinearLayoutManager layoutManager = new LinearLayoutManager(SolutionActivity.this);
                layoutManager.setSmoothScrollbarEnabled(true);
                layoutManager.setAutoMeasureEnabled(true);
                mRecyclerView.setLayoutManager(layoutManager);
                mRecyclerView.setHasFixedSize(true);
                mRecyclerView.setNestedScrollingEnabled(false);

                mLoadLayout.setVisibility(View.GONE);
            }
        });
    }

}
