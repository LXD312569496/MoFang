package com.example.administrator.mofang.solution;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.mofang.R;
import com.example.administrator.mofang.common.RecycleViewDivider;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2017/2/16.
 */

public class SolutionFragment extends Fragment {

    View rootView;
    @BindView(R.id.solution_recycler_view)
    PullLoadMoreRecyclerView mRecyclerView;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_solution, container, false);
        ButterKnife.bind(this, rootView);


        if (getArguments()!=null){
            mRecyclerView.setIsRefresh(true);
        getData();
        }
        mRecyclerView.setPullRefreshEnable(true);
        mRecyclerView.setPushRefreshEnable(false);
        mRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                mRecyclerView.setIsRefresh(true);
                getData();
            }

            @Override
            public void onLoadMore() {

            }
        });

        return rootView;
    }

    private void getData() {
        String name=getArguments().getString("name");
        BmobQuery<Case> query=new BmobQuery<>();
        query.addWhereEqualTo("name",name);
        query.order("caseName");
        query.setLimit(1000);
        //判断是否有缓存，该方法必须放在查询条件（如果有的话）都设置完之后再来调用才有效，就像这里一样。
//            boolean isCache = query.hasCachedResult(Case.class);
//            if(isCache){
//                query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);    // 如果有缓存的话，则设置策略为CACHE_ELSE_NETWORK
//            }else{
//                query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);    // 如果没有缓存的话，则设置策略为NETWORK_ELSE_CACHE
//            }

        query.findObjects(new FindListener<Case>() {
            @Override
            public void done(List<Case> list, BmobException e) {
                if (e!=null){
                    return;
                }
                if (list==null||list.size()==0){
                    return;
                }
                CaseAdapter adapter=new CaseAdapter(list);
                mRecyclerView.setAdapter(adapter);
                mRecyclerView.setLinearLayout();
                mRecyclerView.setIsRefresh(false);
                mRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL,R.drawable.divide));
            }
        });
    }

    //传递公式的名字，比如PLL，OLL
    public static SolutionFragment newInstance(String name) {
        SolutionFragment fragment = new SolutionFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        fragment.setArguments(bundle);
        return fragment;
    }

}
