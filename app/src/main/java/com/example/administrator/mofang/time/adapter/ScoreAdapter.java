package com.example.administrator.mofang.time.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.mofang.R;
import com.example.administrator.mofang.common.DateUtil;
import com.example.administrator.mofang.common.DialogUtil;
import com.example.administrator.mofang.time.greendao.Score;

import java.util.List;

/**
 * Created by Administrator on 2017/2/22.
 */

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder> {

    private List<Score> mScoreList;
    private Context mContext;

    public ScoreAdapter(List<Score> mScoreList) {
        this.mScoreList = mScoreList;
    }

    @Override
    public ScoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new ScoreViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time, parent, false));
    }

    @Override
    public void onBindViewHolder(ScoreViewHolder holder, final int position) {
        final Score bean = mScoreList.get(position);

        long score=bean.getScore();
        if (score==0){
            holder.tvTime.setText("DNF");
        }else {
            holder.tvTime.setText(DateUtil.formatTime(score));
        }

        if (position<4){
            holder.tvAvg5.setText("N/A");
        }else {
            holder.tvAvg5.setText(DateUtil.formatTime(getAvg(position,4)));
        }

        if (position<11){
            holder.tvAvg12.setText("N/A");
        }else {
            holder.tvAvg12.setText(DateUtil.formatTime(getAvg(position,11)));
        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = DialogUtil.getBuilder(mContext);
                builder.setTitle("时间"+bean.getTime());
                builder.setMessage("成绩：" + DateUtil.formatTime(bean.getScore()));

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                            mDeleteListener.onDelete(position,bean);
                    }
                });
                builder.create().show();
            }
        });



    }

    private OnDeleteListener mDeleteListener;

    public void setOnDeleteListener(OnDeleteListener DeleteListener){
        this.mDeleteListener=DeleteListener;
    }

    public interface OnDeleteListener{
        void onDelete(int positon,Score bean);
    }

    @Override
    public int getItemCount() {
        return mScoreList.size();
    }

    public class ScoreViewHolder extends RecyclerView.ViewHolder {
        TextView tvTime;
        TextView tvAvg5;
        TextView tvAvg12;

        public ScoreViewHolder(View itemView) {
            super(itemView);
            tvTime = (TextView) itemView.findViewById(R.id.time_tv_time);
            tvAvg5 = (TextView) itemView.findViewById(R.id.time_tv_avg5);
            tvAvg12 = (TextView) itemView.findViewById(R.id.time_tv_avg12);
        }
    }



    private long getAvg(int position,int count){
        long sum=0;
        int num=0;//不是dnf的个数
        for (int i = 0; i < count; i++) {
            Score bean=mScoreList.get(position-count);
            if (bean.getScore()!=0){
                sum=sum+bean.getScore();
                num++;
            }
        }
        return (long) (sum*1.0/num);
    }


}
