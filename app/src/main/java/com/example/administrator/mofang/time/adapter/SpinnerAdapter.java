package com.example.administrator.mofang.time.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.mofang.R;

import java.util.List;

/**
 * Created by Administrator on 2017/2/22.
 */

public class SpinnerAdapter extends BaseAdapter {

    private List<String> mStringList;

    public SpinnerAdapter(List<String> mStringList) {
        this.mStringList = mStringList;
    }

    @Override
    public int getCount() {
        return mStringList.size();
    }

    @Override
    public Object getItem(int i) {
        return mStringList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View converView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (converView == null) {
            converView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_spinner_time, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.tv = (TextView) converView.findViewById(R.id.time_tv_name);
            converView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) converView.getTag();
        }
        viewHolder.tv.setText(mStringList.get(i));

        return converView;
    }


    class ViewHolder {
        TextView tv;
    }

}
