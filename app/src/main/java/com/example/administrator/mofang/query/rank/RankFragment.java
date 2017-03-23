package com.example.administrator.mofang.query.rank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.example.administrator.mofang.retrofit.JsoupUtil;
import com.example.administrator.mofang.MainActivity;
import com.example.administrator.mofang.R;
import com.example.administrator.mofang.query.competitor.SpinnerAdapter;
import com.example.administrator.mofang.query.competitor.SpinnerBean;
import com.umeng.analytics.MobclickAgent;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/2/6.
 */

public class RankFragment extends Fragment {

    View rootView;
    @BindView(R.id.rank_spinner_region)
    Spinner mSpinnerRegion;
    @BindView(R.id.rank_spinner_type)
    Spinner mSpinnerType;
    @BindView(R.id.rank_bt_single)
    Button mBtSingle;
    @BindView(R.id.rank_bt_average)
    Button mBtAverage;
    @BindView(R.id.rank_rv_result)
    PullLoadMoreRecyclerView mRvResult;

    private List<SpinnerBean> mRegionList;
    private SpinnerAdapter mRegionAdapter;

    private List<SpinnerBean> mTypeList;
    private SpinnerAdapter mTypeAdapter;

    private List<Rank> mRankList;
    private RankAdapter mRankAdapter;

    private String region = "World";
    private String event = "333";
    private String type = "single";
    private int page = 1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_rank, container, false);
        ButterKnife.bind(this, rootView);

        init();

        return rootView;
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MainScreen"); //统计页面，"MainScreen"为页面名称，可自定义
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MainScreen");
    }

    private void init() {
        mRegionList = new ArrayList<>();
        mRegionAdapter = new SpinnerAdapter(mRegionList);
        mSpinnerRegion.setAdapter(mRegionAdapter);

        mTypeList = new ArrayList<>();
        mTypeAdapter = new SpinnerAdapter(mTypeList);
        mSpinnerType.setAdapter(mTypeAdapter);

        mRankList = new ArrayList<>();
        mRankAdapter = new RankAdapter(mRankList);
        mRvResult.setAdapter(mRankAdapter);
        mRvResult.setLinearLayout();

        //初始化地区的下拉列表
        JsoupUtil.getInterFace().getRank(region, event, type, page, MainActivity.APP_LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Func1<String, List<SpinnerBean>>() {
                    @Override
                    public List<SpinnerBean> call(String s) {
                        return JsoupUtil.getRegion(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<SpinnerBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onNext(List<SpinnerBean> regions) {
                        mRegionList.addAll(regions);
                        mRegionAdapter.notifyDataSetChanged();
                    }
                });
        mSpinnerRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                region = mRegionList.get(i).getValue();
                getData(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //初始化魔方类型的下拉列表
        JsoupUtil.getInterFace().getRank(region, event, type, page,MainActivity.APP_LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Func1<String, List<SpinnerBean>>() {
                    @Override
                    public List<SpinnerBean> call(String s) {
                        return JsoupUtil.getType(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<SpinnerBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onNext(List<SpinnerBean> spinnerBeen) {
                        mTypeList.addAll(spinnerBeen);
                        mTypeAdapter.notifyDataSetChanged();
                    }
                });

        mSpinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                event = mTypeList.get(i).getValue();
                getData(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        mRvResult.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getData(true);
            }

            @Override
            public void onLoadMore() {
                page++;
                getData(false);
            }
        });
        mRvResult.refresh();

    }


    @OnClick({R.id.rank_bt_single, R.id.rank_bt_average})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rank_bt_single:
                type = "single";
                mBtSingle.setBackgroundResource(R.drawable.button_selected);
                mBtAverage.setBackgroundResource(R.drawable.button_unselected);
                break;
            case R.id.rank_bt_average:
                mBtAverage.setBackgroundResource(R.drawable.button_selected);
                mBtSingle.setBackgroundResource(R.drawable.button_unselected);
                type = "average";
                break;
        }
        mRankList.clear();
        mRvResult.refresh();
        mRvResult.scrollToTop();

    }


    private void getData(final Boolean isRefresh) {
        JsoupUtil.getInterFace().getRank(region, event, type, page,MainActivity.APP_LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Func1<String, List<Rank>>() {
                    @Override
                    public List<Rank> call(String s) {
                        return JsoupUtil.getRankList(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Rank>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onNext(List<Rank> ranks) {
                        if (isRefresh) {
                            mRankList.clear();
                        }
                        mRankList.addAll(ranks);
                        mRankAdapter.notifyDataSetChanged();
                        mRvResult.setPullLoadMoreCompleted();
                        if (ranks.size() == 100) {
                            mRvResult.setPushRefreshEnable(true);
                        } else {
                            mRvResult.setPushRefreshEnable(false);
                        }
                    }
                });
    }

}
