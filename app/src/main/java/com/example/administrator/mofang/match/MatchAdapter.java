package com.example.administrator.mofang.match;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.mofang.R;
import com.example.administrator.mofang.webview.WebViewActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/2/6.
 */

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MatchViewHolder> {

    private List<Match> mMatchList;
    private Context mContext;

    public MatchAdapter(List<Match> mMatchList) {
        this.mMatchList = mMatchList;
    }

    @Override
    public MatchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new MatchViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_match, parent, false));
    }

    @Override
    public void onBindViewHolder(final MatchViewHolder holder, int position) {
        final Match match = mMatchList.get(position);
        holder.tvName.setText(match.getName());
        holder.tvDate.setText(match.getDate());
        if (match.getName().contains("WCA")) {
            holder.ivWCA.setBackgroundResource(R.drawable.wca);
        } else {
            holder.ivWCA.setBackground(null);
        }

        if (!match.getOut()) {
            holder.layout.setBackgroundColor(mContext.getResources().getColor(R.color.match_item));
        } else {
            holder.layout.setBackground(null);
        }

        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebViewActivity.startActivity(mContext, match.getUrl(), match.getName());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mMatchList.size();
    }

    public static class MatchViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate;
        TextView tvName;
        ImageView ivWCA;
        LinearLayout layout;

        public MatchViewHolder(View itemView) {
            super(itemView);
            tvDate = (TextView) itemView.findViewById(R.id.match_tv_date);
            tvName = (TextView) itemView.findViewById(R.id.match_tv_name);
            ivWCA = (ImageView) itemView.findViewById(R.id.match_iv_wca);
            layout = (LinearLayout) itemView.findViewById(R.id.match_layout);
        }
    }

}
