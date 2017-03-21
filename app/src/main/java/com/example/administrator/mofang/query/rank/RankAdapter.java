package com.example.administrator.mofang.query.rank;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.mofang.R;
import com.example.administrator.mofang.webview.WebViewActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/2/7.
 */

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.RankViewHolder> {

    private List<Rank> mRankList;
    private Context mContext;

    public RankAdapter(List<Rank> mRankList) {
        this.mRankList = mRankList;
    }

    @Override
    public RankViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new RankViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rank, parent, false));
    }

    @Override
    public void onBindViewHolder(RankViewHolder holder, int position) {
        final Rank bean = mRankList.get(position);
        holder.tvName.setText(bean.getName());
        holder.tvScore.setText(bean.getScore());
        holder.tvCountry.setText(bean.getCountry());
        holder.tvPosition.setText(String.valueOf(position + 1));
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebViewActivity.startActivity(mContext, bean.getUrl(),bean.getName()+"的信息");
            }
        });

        Glide.with(mContext).load(bean.getCountryPic()).error(R.mipmap.ic_launcher).into(holder.ivPic);
    }

    @Override
    public int getItemCount() {
        return mRankList.size();
    }

    public class RankViewHolder extends RecyclerView.ViewHolder {
        TextView tvPosition;
        TextView tvName;
        TextView tvCountry;
        TextView tvScore;
        ImageView ivPic;

        public RankViewHolder(View itemView) {
            super(itemView);
            tvPosition = (TextView) itemView.findViewById(R.id.rank_tv_position);
            tvName = (TextView) itemView.findViewById(R.id.rank_tv_name);
            tvCountry = (TextView) itemView.findViewById(R.id.rank_tv_country);
            tvScore = (TextView) itemView.findViewById(R.id.rank_tv_score);
            ivPic = (ImageView) itemView.findViewById(R.id.rank_iv_pic);
        }
    }

}
