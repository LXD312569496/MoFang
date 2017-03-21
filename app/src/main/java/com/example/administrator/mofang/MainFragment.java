package com.example.administrator.mofang;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.mofang.set.SetFragment;
import com.example.administrator.mofang.query.QueryFragment;
import com.example.administrator.mofang.solution.TabSolutionFragment;
import com.example.administrator.mofang.time.TablTimeFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asus on 2017/3/16.
 */

public class MainFragment extends Fragment {
    @BindView(R.id.main_tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.main_view_pager)
    ViewPager mViewPager;

    private ArrayList<Fragment> mFragmentList;


    private SharedPreferences.Editor editor;

    public static String APP_LANGUAGE = "";

    public String[] mTitles = new String[]{"计时", "公式", "资讯", "我的"};


    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);

        APP_LANGUAGE = getString(R.string.app_language);

        initData();
        initView();

        return rootView;
    }

    private void initData() {
        mFragmentList = new ArrayList<>();
//        mFragmentList.add(new TimeFragment());
        mFragmentList.add(new TablTimeFragment());
        mFragmentList.add(new TabSolutionFragment());
        mFragmentList.add(new QueryFragment());
        mFragmentList.add(new SetFragment());

    }

    private void initView() {


        mViewPager.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles[position];
            }
        });
        mViewPager.setOffscreenPageLimit(3);

        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setText("").setIcon(R.drawable.time_selected);
        mTabLayout.getTabAt(1).setText("").setIcon(R.drawable.ic_solution_unselected);
        mTabLayout.getTabAt(2).setText("").setIcon(R.drawable.ic_search_unselected);
        mTabLayout.getTabAt(3).setText("").setIcon(R.drawable.ic_settings_unselected);

        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        mTabLayout.getTabAt(0).setText("").setIcon(R.drawable.ic_time_selected);
                        break;
                    case 1:
                        mTabLayout.getTabAt(1).setText("").setIcon(R.drawable.ic_solution_selected);
                        break;
                    case 2:
                        mTabLayout.getTabAt(2).setText("").setIcon(R.drawable.ic_search_selected);
                        break;
                    case 3:
                        mTabLayout.getTabAt(3).setText("").setIcon(R.drawable.ic_settings_selected);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        mTabLayout.getTabAt(0).setText("").setIcon(R.drawable.ic_time_unselected);
                        break;
                    case 1:
                        mTabLayout.getTabAt(1).setText("").setIcon(R.drawable.ic_solution_unselected);
                        break;
                    case 2:
                        mTabLayout.getTabAt(2).setText("").setIcon(R.drawable.ic_search_unselected);
                        break;
                    case 3:
                        mTabLayout.getTabAt(3).setText("").setIcon(R.drawable.ic_settings_unselected);
                        break;
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


}
