package com.example.administrator.mofang.query;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.mofang.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by asus on 2017/3/16.
 */

public class QueryFragment extends Fragment {

    View rootView;
    @BindView(R.id.query_card_view_match)
    CardView mCardViewMatch;
    @BindView(R.id.query_card_view_rank)
    CardView mCardViewRank;
    @BindView(R.id.query_card_view_competitor)
    CardView mCardViewCompetitor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_query, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }


    @OnClick({ R.id.query_card_view_match, R.id.query_card_view_rank, R.id.query_card_view_competitor})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.query_card_view_match:
                QueryActivity.startActivity(getActivity(),QueryActivity.ACTION_MATCH);
                break;
            case R.id.query_card_view_rank:
                QueryActivity.startActivity(getActivity(),QueryActivity.ACTION_RANK);
                break;
            case R.id.query_card_view_competitor:
                QueryActivity.startActivity(getActivity(),QueryActivity.ACTION_COMPETITOR);
                break;
        }
    }
}
