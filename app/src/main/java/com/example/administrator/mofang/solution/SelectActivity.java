package com.example.administrator.mofang.solution;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.administrator.mofang.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2017/2/17.
 */

public class SelectActivity extends Activity implements CompoundButton.OnCheckedChangeListener {


    @BindView(R.id.select_switch_oll)
    Switch mSwitchOll;
    @BindView(R.id.select_switch_pll)
    Switch mSwitchPll;
    @BindView(R.id.select_switch_cll)
    Switch mSwitchCll;
    @BindView(R.id.select_switch_eg)
    Switch mSwitchEg;
    @BindView(R.id.select_switch_f2l)
    Switch mSwitchF2l;
    @BindView(R.id.select_switch_ollcp)
    Switch mSwitchOllcp;
    @BindView(R.id.select_switch_zbll)
    Switch mSwitchZbll;
    @BindView(R.id.select_switch_coll)
    Switch mSwitchColl;
    @BindView(R.id.select_switch_single_oll)
    Switch mSwitchSingleOll;
    @BindView(R.id.select_switch_single_pll)
    Switch mSwitchSinglePll;
    @BindView(R.id.select_switch_cmll)
    Switch mSwitchCmll;
    @BindView(R.id.select_switch_wvls)
    Switch mSwitchWvls;
    @BindView(R.id.select_switch_vls)
    Switch mSwitchVls;

    private ArrayList<String> mSelectedList;

    private Boolean mIsChange = false;//默认没有进行修改，只要进行修改，就会变为true

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        ButterKnife.bind(this);

        mSelectedList = new ArrayList<>();
        mSelectedList = getIntent().getStringArrayListExtra("list");


        //初始化界面
        initView();


    }

    private void initView() {
        for (int i = 0; i < mSelectedList.size(); i++) {
            String name = mSelectedList.get(i);
            if (name.equals("OLL")) {
                mSwitchOll.setChecked(true);
            } else if (name.equals("PLL")) {
                mSwitchPll.setChecked(true);
            } else if (name.equals("EG")) {
                mSwitchEg.setChecked(true);
            } else if (name.equals("CLL")) {
                mSwitchCll.setChecked(true);
            } else if (name.equals("OLLCP")) {
                mSwitchOllcp.setChecked(true);
            } else if (name.equals("ZBLL")) {
                mSwitchZbll.setChecked(true);
            } else if (name.equals("COLL")) {
                mSwitchColl.setChecked(true);
            } else if (name.equals("F2L")) {
                mSwitchF2l.setChecked(true);
            } else if (name.equals("OH PLL")) {
                mSwitchSinglePll.setChecked(true);
            } else if (name.equals("OH OLL")) {
                mSwitchSingleOll.setChecked(true);
            }else if (name.equals("VLS")){
                mSwitchVls.setChecked(true);
            }else if (name.equals("WVLS")){
                mSwitchWvls.setChecked(true);
            }else if (name.equals("CMLL")){
                mSwitchCmll.setChecked(true);
            }


        }
        mSwitchCll.setOnCheckedChangeListener(this);
        mSwitchEg.setOnCheckedChangeListener(this);

        mSwitchF2l.setOnCheckedChangeListener(this);
        mSwitchOll.setOnCheckedChangeListener(this);
        mSwitchPll.setOnCheckedChangeListener(this);
        mSwitchColl.setOnCheckedChangeListener(this);
        mSwitchOllcp.setOnCheckedChangeListener(this);
        mSwitchZbll.setOnCheckedChangeListener(this);

        mSwitchSinglePll.setOnCheckedChangeListener(this);
        mSwitchSingleOll.setOnCheckedChangeListener(this);

        mSwitchCmll.setOnCheckedChangeListener(this);
        mSwitchVls.setOnCheckedChangeListener(this);
        mSwitchWvls.setOnCheckedChangeListener(this);
    }


    public static void startActivity(Context context, ArrayList<String> selectList) {
        Intent intent = new Intent(context, SelectActivity.class);
        intent.putStringArrayListExtra("list", selectList);
        context.startActivity(intent);
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        mIsChange = true;
        if (b) {
            mSelectedList.add(compoundButton.getText().toString());
        } else {
            mSelectedList.remove(compoundButton.getText().toString());
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().post(new SelectEvent(mSelectedList, mIsChange));
    }
}
