package com.example.administrator.mofang.time;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.administrator.mofang.R;
import com.example.administrator.mofang.common.DateUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Administrator on 2017/2/22.
 * time模块的Activity，包含三个fragment
 */

public class TimeScreenFragment extends Fragment {

    @BindView(R.id.time_tv_time)
    TextView mTvTime;
    @BindView(R.id.time_layout)
    FrameLayout mLayout;

    private Subscription mSubscription;

    private int mDelayTime = 1;//间隔时间，单位毫秒,observable发送的速率
    private long mCurrentTime = 0;//当前时间

    View rootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.activity_time,container,false);
        ButterKnife.bind(this,rootView);

        initView();

        return rootView;
    }

    private void initView() {
        mSubscription = Observable.interval(0, mDelayTime, TimeUnit.MILLISECONDS)
                .sample(20, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("test", e.toString());
                    }

                    @Override
                    public void onNext(Long aLong) {
                        mCurrentTime = aLong;
                        mTvTime.setText(DateUtil.formatTime(mCurrentTime));
                    }
                });

        mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mSubscription.unsubscribe();
        EventBus.getDefault().post(new GetTimeEvent(mCurrentTime));
    }

}
