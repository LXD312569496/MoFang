package com.example.administrator.mofang.solution;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.mofang.R;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Created by Administrator on 2017/2/16.
 */

public class GongShiFragment extends Fragment {

    View rootView;
    @BindView(R.id.gongshi_tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.gongshi_view_pager)
    ViewPager mViewPager;

    private ArrayList<String> mTitleList;
    private ArrayList<Fragment> mFragmentList;
    private FragmentStatePagerAdapter mAdapter;

    private ArrayList<String> mTypeList;

    private SharedPreferences.Editor mEditor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_gongshi, container, false);
        ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);

        mTitleList = new ArrayList<>();
        mFragmentList = new ArrayList<>();

        mTypeList = new ArrayList<>();
        mTypeList.add("EG");
        mTypeList.add("CLL");

        mTypeList.add("OLL");
        mTypeList.add("PLL");
        mTypeList.add("F2L");
        mTypeList.add("OLLCP");
        mTypeList.add("ZBLL");
        mTypeList.add("COLL");
        mTypeList.add("CMLL");
        mTypeList.add("VLS");
        mTypeList.add("WVLS");

        mTypeList.add("OH OLL");
        mTypeList.add("OH PLL");

        mTypeList.add("Parity OLL");
        mTypeList.add("Last Two Centers");
        mTypeList.add("Last Two Edges");
        mTypeList.add("CLL/EG");
        mTypeList.add("Last 5 Centers");
        mTypeList.add("Corners Last Slot");
        mTypeList.add("ZBF2L");

        SharedPreferences share = getActivity().getSharedPreferences("case", Context.MODE_PRIVATE);
        mEditor = share.edit();
        for (int i = 0; i < mTypeList.size(); i++) {
            if (share.getBoolean(mTypeList.get(i), false)) {
                mTitleList.add(mTypeList.get(i));
                mFragmentList.add(SolutionFragment.newInstance(mTypeList.get(i)));
            }
        }
        if (mTitleList.size()==0){
            mTitleList.add("F2L");
            mFragmentList.add(SolutionFragment.newInstance("F2L"));
            mTitleList.add("OLL");
            mFragmentList.add(SolutionFragment.newInstance("OLL"));
            mTitleList.add("PLL");
            mFragmentList.add(SolutionFragment.newInstance("PLL"));
        }


        mAdapter = new FragmentStatePagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mTitleList.size();
            }
        };
        mViewPager.setAdapter(mAdapter);
//        mViewPager.setOffscreenPageLimit(mTitleList.size()-1);
        mTabLayout.setViewPager(mViewPager, mTitleList.toArray(new String[mTitleList.size()]));


        return rootView;
    }


    @OnClick(R.id.solution_bt_select)
    public void onClick() {
        SelectActivity.startActivity(getActivity(), mTitleList);
    }


    @Subscribe
    public void onEventMainThread(SelectEvent event) {
        if (!event.isChange()){
            return;
        }
        mTitleList.clear();
        mFragmentList.clear();

        mTitleList.addAll(event.getList());
        for (int i = 0; i < mTitleList.size(); i++) {
            mFragmentList.add(SolutionFragment.newInstance(mTitleList.get(i)));
        }

        /**
         * bug,解决方法FragmentStatePagerAdapter代替FragmentPagerAdapter
         */

        FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }

            @Override
            public int getItemPosition(Object object) {
                return PagerAdapter.POSITION_NONE;
            }
        };
//        mViewPager.setOffscreenPageLimit(mTitleList.size());
        mViewPager.setAdapter(adapter);
        mTabLayout.setViewPager(mViewPager, mTitleList.toArray(new String[mTitleList.size()]));


        for (int i = 0; i < mTypeList.size(); i++) {
            if (mTitleList.contains(mTypeList.get(i))) {
                mEditor.putBoolean(mTypeList.get(i), true);
            } else {
                mEditor.putBoolean(mTypeList.get(i), false);
            }
        }
        mEditor.apply();
    }

}
