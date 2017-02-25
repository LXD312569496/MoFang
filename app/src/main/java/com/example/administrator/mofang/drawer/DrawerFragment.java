package com.example.administrator.mofang.drawer;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.administrator.mofang.R;
import com.example.administrator.mofang.time.TimeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;
import cn.bmob.v3.update.UpdateStatus;
import de.greenrobot.event.EventBus;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2017/2/7.
 */

public class DrawerFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    View rootView;
    @BindView(R.id.drawer_switch_push)
    Switch mSwitchPush;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_drawer, container, false);
        ButterKnife.bind(this, rootView);

        mSwitchPush.setOnCheckedChangeListener(this);

        init();
        return rootView;
    }

    private void init() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("share", MODE_PRIVATE);
        Boolean isPush = sharedPreferences.getBoolean("isPush", true);
        mSwitchPush.setChecked(isPush);

    }

    @OnClick({
//            R.id.drawer_ll_save,
            R.id.drawer_ll_update, R.id.drawer_ll_about, R.id.drawer_ll_exit, R.id.drawer_ll_time})
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.drawer_ll_use:
//                break;
            case R.id.drawer_ll_update:
                BmobUpdateAgent.setUpdateListener(new BmobUpdateListener() {

                    @Override
                    public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
                        // TODO Auto-generated method stub
                        if (updateStatus == UpdateStatus.Yes) {//版本有更新

                        } else if (updateStatus == UpdateStatus.No) {
                            Toast.makeText(getActivity(), "版本无更新", Toast.LENGTH_SHORT).show();
                        } else if (updateStatus == UpdateStatus.EmptyField) {//此提示只是提醒开发者关注那些必填项，测试成功后，无需对用户提示
                            Toast.makeText(getActivity(), "请检查你AppVersion表的必填项，1、target_size（文件大小）是否填写；2、path或者android_url两者必填其中一项。", Toast.LENGTH_SHORT).show();
                        } else if (updateStatus == UpdateStatus.IGNORED) {
                            Toast.makeText(getActivity(), "该版本已被忽略更新", Toast.LENGTH_SHORT).show();
                        } else if (updateStatus == UpdateStatus.ErrorSizeFormat) {
                            Toast.makeText(getActivity(), "请检查target_size填写的格式，请使用file.length()方法获取apk大小。", Toast.LENGTH_SHORT).show();
                        } else if (updateStatus == UpdateStatus.TimeOut) {
                            Toast.makeText(getActivity(), "查询出错或查询超时", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                BmobUpdateAgent.forceUpdate(getActivity());
                break;
            case R.id.drawer_ll_about:
                ShowAboutDialog();
                break;
            case R.id.drawer_ll_exit:
                getActivity().finish();
                break;
//            case R.id.drawer_ll_save:
//                EventBus.getDefault().post(new SaveEvent());
//                break;
            case R.id.drawer_ll_time:
                TimeActivity.startActivity(getActivity());
                break;
        }
    }


    private void ShowAboutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("关于作者");
        builder.setMessage("qq：312569496\n\nqq群 :74157725\n\n希望各位魔友多提提意见");
        builder.setPositiveButton("关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();

    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        EventBus.getDefault().post(new UPushEvent(b));
    }


}
