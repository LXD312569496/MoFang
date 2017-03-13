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
    @BindView(R.id.select_switch_parity_oll)
    Switch mSwitchParityOll;
    @BindView(R.id.select_switch_last_last_two_centers)
    Switch mSwitchLastLastTwoCenters;
    @BindView(R.id.select_switch_last_last_two_edges)
    Switch mSwitchLastLastTwoEdges;
    @BindView(R.id.select_switch_cll_eg)
    Switch mSwitchCllEg;
    @BindView(R.id.select_switch_last_5_centers)
    Switch mSwitchLast5Centers;
    @BindView(R.id.select_switch_corners_last_slot)
    Switch mSwitchCornersLastSlot;
    @BindView(R.id.select_switch_zbf2l)
    Switch mSwitchZbf2l;

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
            } else if (name.equals("VLS")) {
                mSwitchVls.setChecked(true);
            } else if (name.equals("WVLS")) {
                mSwitchWvls.setChecked(true);
            } else if (name.equals("CMLL")) {
                mSwitchCmll.setChecked(true);
            } else if (name.equals("Parity OLL")) {
                mSwitchParityOll.setChecked(true);
            } else if (name.equals("Last Two Centers")) {
                mSwitchLastLastTwoCenters.setChecked(true);
            } else if (name.equals("Last Two Edges")) {
                mSwitchLastLastTwoEdges.setChecked(true);
            } else if (name.equals("CLL/EG")) {
                mSwitchCllEg.setChecked(true);
            } else if (name.equals("Last 5 Centers")) {
                mSwitchLast5Centers.setChecked(true);
            } else if (name.equals("Corners Last Slot")) {
                mSwitchCornersLastSlot.setChecked(true);
            }else if (name.equals("ZBF2L")){
                mSwitchZbf2l.setChecked(true);
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

        mSwitchParityOll.setOnCheckedChangeListener(this);
        mSwitchLastLastTwoCenters.setOnCheckedChangeListener(this);
        mSwitchLastLastTwoEdges.setOnCheckedChangeListener(this);
        mSwitchCllEg.setOnCheckedChangeListener(this);
        mSwitchLast5Centers.setOnCheckedChangeListener(this);
        mSwitchCornersLastSlot.setOnCheckedChangeListener(this);
        mSwitchZbf2l.setOnCheckedChangeListener(this);
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
