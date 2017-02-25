package com.example.administrator.mofang.match;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.administrator.mofang.JsoupUtil;
import com.example.administrator.mofang.MainActivity;
import com.example.administrator.mofang.R;
import com.example.administrator.mofang.common.RecycleViewDivider;
import com.umeng.analytics.MobclickAgent;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/2/6.
 * 赛事
 */

public class MatchFragment extends Fragment {

    View rootView;
    @BindView(R.id.match_spinner)
    Spinner mSpinner;
    @BindView(R.id.match_recycler_view)
    PullLoadMoreRecyclerView mRecyclerView;

    private List<Match> mMatchList;
    private MatchAdapter mAdapter;
    private int page = 1;
    private String year = "";//为空的时候默认是全部

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_match, container, false);
        ButterKnife.bind(this, rootView);

        initView();

        return rootView;
    }

    private void initView() {
        mMatchList = new ArrayList<>();
        mAdapter = new MatchAdapter(mMatchList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLinearLayout();
        //第一次进入页面就是刷新
        mRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getData(true, page);
            }

            @Override
            public void onLoadMore() {
                page++;
                getData(false, page);
            }
        });

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (position == 0) {
                    year = "";
                    mRecyclerView.setPushRefreshEnable(true);
                } else if (position == 1) {
                    year = "current";
                    mRecyclerView.setPushRefreshEnable(false);
                } else {
                    year = (String) mSpinner.getSelectedItem();
                    mRecyclerView.setPushRefreshEnable(false);
                }
                mMatchList.clear();
                mRecyclerView.refresh();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        mRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL,R.drawable.divide));
        mRecyclerView.refresh();

    }

    private void getData(final Boolean isRefresh, int page) {
        JsoupUtil.getInterFace().getMatch(year, page, MainActivity.APP_LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Func1<String, List<Match>>() {
                    @Override
                    public List<Match> call(String s) {
                        return JsoupUtil.getMatch(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Match>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Toast.makeText(getActivity(), "网络出错", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(List<Match> matches) {
                        if (isRefresh) {
                            mMatchList.clear();
                        }
                        mMatchList.addAll(matches);
                        mAdapter.notifyDataSetChanged();
                        mRecyclerView.setPullLoadMoreCompleted();
                    }
                });
    }



    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MainScreen"); //统计页面，"MainScreen"为页面名称，可自定义
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MainScreen");
    }
}
