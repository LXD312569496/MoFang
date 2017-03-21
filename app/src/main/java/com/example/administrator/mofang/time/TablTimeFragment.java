package com.example.administrator.mofang.time;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.mofang.R;
import com.example.administrator.mofang.TabEntity;
import com.example.administrator.mofang.time.greendao.DaoManager;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by asus on 2017/3/15.
 */

public class TablTimeFragment extends Fragment {

    View rootView;
    @BindView(R.id.tab_time_tab_layout)
    CommonTabLayout mTabLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_tab_time, container, false);
        ButterKnife.bind(this, rootView);

        DaoManager.init(getActivity());
        initView();

        return rootView;
    }

    private void initView() {
        ArrayList<CustomTabEntity> entities=new ArrayList<>();
        entities.add(new TabEntity("计时",R.drawable.ic_time_selected,R.drawable.ic_time_unselected));
        entities.add(new TabEntity("成绩",R.drawable.ic_score_selected,R.drawable.ic_score_unselected));
//        entities.add(new TabEntity("设置",R.mipmap.ic_launcher,R.mipmap.ic_launcher));

        final ArrayList<Fragment> fragmengList = new ArrayList<>();
        fragmengList.add(TimeFragment.newInstance());
        fragmengList.add(new ScoreFragment());

        mTabLayout.setTabData(entities,getActivity(),R.id.tab_time_fl_container,fragmengList);

    }

}
