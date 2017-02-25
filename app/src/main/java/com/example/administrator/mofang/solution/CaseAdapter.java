package com.example.administrator.mofang.solution;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.mofang.R;

import java.util.List;

/**
 * Created by Administrator on 2017/2/16.
 */

public class CaseAdapter extends RecyclerView.Adapter<CaseAdapter.CaseViewHolder> {

    private List<Case> mList;
    private Context mContext;

    public CaseAdapter(List<Case> mList) {
        this.mList = mList;
    }

    @Override
    public CaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new CaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_solution, parent, false));
    }

    @Override
    public void onBindViewHolder(CaseViewHolder holder, int position) {
        final Case bean = mList.get(position);
        final String name=bean.getCaseName();
        holder.tvName.setText(name);
        final String bitmap=bean.getPicture().getFileUrl();
        Glide.with(mContext).load(bitmap)
                .asBitmap().error(R.drawable.error).into(holder.ivPic);
        final String solution=bean.getSolution();

//        holder.tvResult.setText(solution.replace(";","\n"));
        holder.tvResult.setText(solution);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                EventBus.getDefault().post(new FragmentEvent(TimeFragment.newInstance(bean)));

            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class CaseViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPic;
        TextView tvName;
        TextView tvResult;

        public CaseViewHolder(View itemView) {
            super(itemView);
            ivPic = (ImageView) itemView.findViewById(R.id.solution_iv_pic);
            tvName = (TextView) itemView.findViewById(R.id.solution_tv_name);
            tvResult = (TextView) itemView.findViewById(R.id.solution_tv_result);
        }
    }
}
