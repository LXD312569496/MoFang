package com.example.administrator.mofang.competitor;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.mofang.R;

import java.util.List;

/**
 * Created by Administrator on 2017/2/7.
 */

public class SpinnerAdapter extends BaseAdapter {

    private List<SpinnerBean> list;

    public SpinnerAdapter(List<SpinnerBean> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View converview, ViewGroup viewGroup) {
        SpinnerBean bean = list.get(i);

        if (converview == null) {
            converview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_spinner, viewGroup, false);
        }
        TextView tv = (TextView) converview.findViewById(R.id.spinner_tv_name);

        if (bean.getName().equals("洲") || bean.getName().equals("地区")) {
            tv.setText(bean.getName());
            tv.setTextColor(viewGroup.getContext().getResources().getColor(R.color.colorPrimaryDark));
            tv.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
            converview.setClickable(true);
        } else {
            tv.setText(bean.getName());
            tv.setTextColor(viewGroup.getContext().getResources().getColor(R.color.color_text));
            tv.setGravity(Gravity.CENTER);
            converview.setClickable(false);
        }

        return converview;
    }


}