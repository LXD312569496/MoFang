package com.example.administrator.mofang.time;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.administrator.mofang.FragmentEvent;
import com.example.administrator.mofang.R;
import com.example.administrator.mofang.common.DateUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Created by Administrator on 2017/2/13.
 */

public class TimeFragment extends Fragment {

    View rootView;
    @BindView(R.id.time_tv_time)
    TextView mTvTime;
    @BindView(R.id.time_layout)
    RelativeLayout mLayout;
    @BindView(R.id.time_spinner)
    Spinner mTypeSpinner;
    @BindView(R.id.time_tv_scramble)
    TextView mTvScramble;

    private int mBackGround;

    private int mClickNum = 0;
//    private int mClickColor=getResources().getColor(R.color.green);//点击改变颜色

    private Scrambler mScrambler = new Scrambler();
    private String mCurrentType = "3x3x3";//当前的魔方类型
    private String mCurrentScramble="";//当前的打乱

    private long mCurrentTime = 0;//当前时间

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_time, container, false);
        ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        initView();

        return rootView;
    }

    private void initView() {
        mTvTime.setTextColor(getResources().getColor(R.color.black));
        mLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction()==MotionEvent.ACTION_DOWN||motionEvent.getAction()==MotionEvent.ACTION_MOVE){
                    mTvTime.setTextColor(getResources().getColor(R.color.green));
                }
                else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    mTvTime.setTextColor(getResources().getColor(R.color.black));
                    EventBus.getDefault().post(new FragmentEvent(new TimeScreenFragment()));
                }
                return true;
            }
        });

        final String[] ITEMS = {"3x3x3", "2x2x2", "4x4x4", "5x5x5", "6x6x6", "7x7x7"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTypeSpinner.setAdapter(adapter);
        mTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mCurrentType=ITEMS[i];
                getScramble();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public static TimeFragment newInstance() {
        TimeFragment fragment = new TimeFragment();
        return fragment;
    }

    @Subscribe
    public void onEventMainThread(GetTimeEvent event){
        mCurrentTime=event.getTime();
        mTvTime.setText(DateUtil.formatTime(mCurrentTime));
        showDialog();
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("时间: " + mTvTime.getText().toString());

        String items[] = new String[]{"无惩罚", "+2", "DNF", "取消"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        saveScore();
                        break;
                    case 1:
                        mCurrentTime = mCurrentTime + 2000;
                        saveScore();
                        break;
                    case 2:
                        mCurrentTime = 0;
                        saveScore();
                        break;
                    case 3:
                        dialogInterface.dismiss();
                        break;
                }
            }
        });
        builder.create().show();
    }


    //保存成绩到数据库
    private void saveScore() {
        Log.d("test", DateUtil.getCurrentTime());
        EventBus.getDefault().post(new SaveTimeEvent(mCurrentTime, DateUtil.getCurrentTime(),mCurrentScramble));
        getScramble();
    }


    @OnClick(R.id.time_tv_scramble)
    public void onClick() {
        getScramble();
    }

    private void getScramble(){
        mCurrentScramble=mScrambler.getScramble(mCurrentType);
        mTvScramble.setText(mCurrentScramble);
    }
}
