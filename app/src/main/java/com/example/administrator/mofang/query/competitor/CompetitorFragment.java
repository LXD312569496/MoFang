package com.example.administrator.mofang.query.competitor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.mofang.JsoupUtil;
import com.example.administrator.mofang.MainActivity;
import com.example.administrator.mofang.R;
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
 */

public class CompetitorFragment extends Fragment {

    View rootView;
    @BindView(R.id.competitor_recycler_view)
    PullLoadMoreRecyclerView mRecyclerView;
    @BindView(R.id.competitor_et_input)
    EditText mEtInput;
    @BindView(R.id.competitor_spinner)
    Spinner mSpinner;

    private List<Competitor> mCompetitorList;
    private CompetitorAdapter mAdapter;

    private List<SpinnerBean> mRegionList;
    private SpinnerAdapter mSpinnerAdapter;

    private int page = 1;
    private String name = "";
    private String mCountry = "";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_competitor, container, false);
        ButterKnife.bind(this, rootView);

        initView();

        return rootView;
    }

    private void initView() {
        //回车键变为搜索
        mEtInput.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        mEtInput.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        mEtInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    //搜索
                    search();
                    return true;
                }
                return false;
            }
        });

        mCompetitorList = new ArrayList<>();
        mAdapter = new CompetitorAdapter(mCompetitorList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLinearLayout();

        mRegionList = new ArrayList<>();
        mSpinnerAdapter = new SpinnerAdapter(mRegionList);
        mSpinner.setAdapter(mSpinnerAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mCountry = mRegionList.get(i).getValue();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                page = 1;
                mCompetitorList.clear();
                getData(true);
            }

            @Override
            public void onLoadMore() {
                page++;
                getData(false);
            }
        });

        JsoupUtil.getInterFace().getPerson(mCountry, name, page, MainActivity.APP_LANGUAGE)
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
                        mSpinnerAdapter.notifyDataSetChanged();
                    }
                });

    }

    private void getData(final Boolean isRefresh) {
        JsoupUtil.getInterFace().getPerson(mCountry, name, page,MainActivity.APP_LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map(new Func1<String, List<Competitor>>() {
                    @Override
                    public List<Competitor> call(String s) {
                        return JsoupUtil.getCompetitor(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Competitor>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Toast.makeText(getActivity(), "搜索失败，不存在该选手", Toast.LENGTH_SHORT).show();
                        mRecyclerView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(List<Competitor> competitors) {
                        mRecyclerView.setVisibility(View.VISIBLE);
                        if (isRefresh) {
                            mCompetitorList.clear();
                        }
                        if (competitors.size() == 100) {
                            mRecyclerView.setPushRefreshEnable(true);
                        } else {
                            mRecyclerView.setPushRefreshEnable(false);
                        }
                        mCompetitorList.addAll(competitors);
                        mAdapter.notifyDataSetChanged();
                        mRecyclerView.setPullLoadMoreCompleted();
                    }
                });
    }

//    @OnClick(R.id.competitor_bt_search)
//    public void onClick() {
//        name = mEtInput.getText().toString();
//        getData(true);
//    }

    private void search() {
        name = mEtInput.getText().toString();
        getData(true);
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
