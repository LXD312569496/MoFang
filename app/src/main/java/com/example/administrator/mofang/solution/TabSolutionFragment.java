package com.example.administrator.mofang.solution;

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

public class TabSolutionFragment extends Fragment {

    View rootView;
    @BindView(R.id.solution_card_view_cll)
    CardView mCardViewCll;
    @BindView(R.id.solution_card_view_eg)
    CardView mCardViewEg;
    @BindView(R.id.solution_card_view_oll)
    CardView mCardViewOll;
    @BindView(R.id.solution_card_view_pll)
    CardView mCardViewPll;
    @BindView(R.id.solution_card_view_f2l)
    CardView mCardViewF2l;
    @BindView(R.id.solution_card_view_ollcp)
    CardView mCardViewOllcp;
    @BindView(R.id.solution_card_view_zbll)
    CardView mCardViewZbll;
    @BindView(R.id.solution_card_view_coll)
    CardView mCardViewColl;
    @BindView(R.id.solution_card_view_cmll)
    CardView mCardViewCmll;
    @BindView(R.id.solution_card_view_wvls)
    CardView mCardViewWvls;
    @BindView(R.id.solution_card_view_vls)
    CardView mCardViewVls;
    @BindView(R.id.solution_card_view_zbf2l)
    CardView mCardViewZbf2l;
    @BindView(R.id.solution_card_view_oh_oll)
    CardView mCardViewOhOll;
    @BindView(R.id.solution_card_view_oh_pll)
    CardView mCardViewOhPll;
    @BindView(R.id.solution_card_view_parity_oll)
    CardView mCardViewParityOll;
    @BindView(R.id.solution_card_view_last_two_centers)
    CardView mCardViewLastTwoCenters;
    @BindView(R.id.solution_card_view_last_two_edges)
    CardView mCardViewLastTwoEdges;
    @BindView(R.id.solution_card_view_cll_eg)
    CardView mCardViewCllEg;
    @BindView(R.id.solution_card_view_last_5_centers)
    CardView mCardViewLast5Centers;
    @BindView(R.id.solution_card_view_corners_last_slot)
    CardView mCardViewCornersLastSlot;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_tab_solution, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @OnClick({R.id.solution_card_view_cll, R.id.solution_card_view_eg, R.id.solution_card_view_oll, R.id.solution_card_view_pll, R.id.solution_card_view_f2l, R.id.solution_card_view_ollcp, R.id.solution_card_view_zbll, R.id.solution_card_view_coll, R.id.solution_card_view_cmll, R.id.solution_card_view_wvls, R.id.solution_card_view_vls, R.id.solution_card_view_zbf2l, R.id.solution_card_view_oh_oll, R.id.solution_card_view_oh_pll, R.id.solution_card_view_parity_oll, R.id.solution_card_view_last_two_centers, R.id.solution_card_view_last_two_edges, R.id.solution_card_view_cll_eg, R.id.solution_card_view_last_5_centers, R.id.solution_card_view_corners_last_slot})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.solution_card_view_cll:
                SolutionActivity.startActivity(getActivity(),"CLL");
                break;
            case R.id.solution_card_view_eg:
                SolutionActivity.startActivity(getActivity(),"EG");
                break;
            case R.id.solution_card_view_oll:
                SolutionActivity.startActivity(getActivity(),"OLL");
                break;
            case R.id.solution_card_view_pll:
                SolutionActivity.startActivity(getActivity(),"PLL");
                break;
            case R.id.solution_card_view_f2l:
                SolutionActivity.startActivity(getActivity(),"F2L");
                break;
            case R.id.solution_card_view_ollcp:
                SolutionActivity.startActivity(getActivity(),"OLLCP");
                break;
            case R.id.solution_card_view_zbll:
                SolutionActivity.startActivity(getActivity(),"ZBLL");
                break;
            case R.id.solution_card_view_coll:
                SolutionActivity.startActivity(getActivity(),"COLL");
                break;
            case R.id.solution_card_view_cmll:
                SolutionActivity.startActivity(getActivity(),"CMLL");
                break;
            case R.id.solution_card_view_wvls:
                SolutionActivity.startActivity(getActivity(),"WVLS");
                break;
            case R.id.solution_card_view_vls:
                SolutionActivity.startActivity(getActivity(),"VLS");
                break;
            case R.id.solution_card_view_zbf2l:
                SolutionActivity.startActivity(getActivity(),"ZBF2L");
                break;
            case R.id.solution_card_view_oh_oll:
                SolutionActivity.startActivity(getActivity(),"OH OLL");
                break;
            case R.id.solution_card_view_oh_pll:
                SolutionActivity.startActivity(getActivity(),"OH PLL");
                break;
            case R.id.solution_card_view_parity_oll:
                SolutionActivity.startActivity(getActivity(),"Parity OLL");
                break;
            case R.id.solution_card_view_last_two_centers:
                SolutionActivity.startActivity(getActivity(),"Last Two Centers");
                break;
            case R.id.solution_card_view_last_two_edges:
                SolutionActivity.startActivity(getActivity(),"Last Two Edges");
                break;
            case R.id.solution_card_view_cll_eg:
                SolutionActivity.startActivity(getActivity(),"CLL/EG");
                break;
            case R.id.solution_card_view_last_5_centers:
                SolutionActivity.startActivity(getActivity(),"Last 5 Centers");
                break;
            case R.id.solution_card_view_corners_last_slot:
                SolutionActivity.startActivity(getActivity(),"Corners Last Slot");
                break;
        }
    }
}
