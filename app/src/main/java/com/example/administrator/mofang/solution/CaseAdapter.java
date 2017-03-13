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

import static android.R.attr.type;

/**
 * Created by Administrator on 2017/2/16.
 */

public class CaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Case> mList;
    private Context mContext;

    public static final int TYPE_NORMAL=0;//普通的魔方
    public static final int TYPE_Skewb=1;//斜转魔方

    public CaseAdapter(List<Case> mList) {
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        if (viewType==TYPE_Skewb){
            return new SkewbViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_solution_skewb, parent, false));
        }else {
            return new CaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_solution, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Case bean = mList.get(position);
        final String name=bean.getCaseName();
        final String bitmap=bean.getPicture().getFileUrl();
        final String solution=bean.getSolution();

        if (getItemViewType(position)==TYPE_Skewb){
            SkewbViewHolder viewHolder= (SkewbViewHolder) holder;
            Glide.with(mContext).load(bitmap)
                    .asBitmap().error(R.drawable.error).into(viewHolder.ivPic);
            viewHolder.tvResult.setText(solution);
        }else {
            CaseViewHolder viewHolder= (CaseViewHolder) holder;
            Glide.with(mContext).load(bitmap)
                    .asBitmap().error(R.drawable.error).into(viewHolder.ivPic);
            viewHolder.tvName.setText(name);
            viewHolder.tvResult.setText(solution);
        }




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

    @Override
    public int getItemViewType(int position) {
        if (mList.get(position).getType().equals("Skewb")){
            return TYPE_Skewb;
        }else {
            return TYPE_NORMAL;
        }
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


    public class SkewbViewHolder extends RecyclerView.ViewHolder{
        ImageView ivPic;
        TextView tvResult;
        public SkewbViewHolder(View itemView) {
            super(itemView);
            ivPic = (ImageView) itemView.findViewById(R.id.solution_iv_pic);
            tvResult = (TextView) itemView.findViewById(R.id.solution_tv_result);
        }
    }
}
