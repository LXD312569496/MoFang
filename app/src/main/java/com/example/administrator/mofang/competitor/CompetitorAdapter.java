package com.example.administrator.mofang.competitor;

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

public class CompetitorAdapter extends RecyclerView.Adapter<CompetitorAdapter.CompetitorViewHolder> {

    private List<Competitor> mList;
    private Context mContext;

    public CompetitorAdapter(List<Competitor> mList) {
        this.mList = mList;
    }

    @Override
    public CompetitorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new CompetitorViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_competitor, parent, false));
    }

    @Override
    public void onBindViewHolder(final CompetitorViewHolder holder, int position) {
        final Competitor bean = mList.get(position);
        holder.tvName.setText(bean.getName());
        holder.tvWcaId.setText(bean.getWcaId());
        holder.tvCountry.setText(bean.getCountry());
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebViewActivity.startActivity(mContext, bean.getUrl(), "魔方选手" + bean.getName() + "的信息");
            }
        });
        Glide.with(mContext).load(bean.getCountryPic()).into(holder.ivPic);


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class CompetitorViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvWcaId;
        TextView tvCountry;
        ImageView ivPic;

        public CompetitorViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.competitor_tv_name);
            tvWcaId = (TextView) itemView.findViewById(R.id.competitor_tv_wcaid);
            tvCountry = (TextView) itemView.findViewById(R.id.competitor_tv_country);
            ivPic = (ImageView) itemView.findViewById(R.id.competitor_iv_pic);
        }
    }
}
