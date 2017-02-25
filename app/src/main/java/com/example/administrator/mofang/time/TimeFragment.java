package com.example.administrator.mofang.time;


import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.mofang.R;
import com.example.administrator.mofang.common.DateUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2017/2/13.
 */

public class TimeFragment extends Fragment {

    View rootView;
    @BindView(R.id.time_tv_time)
    TextView mTvTime;
    @BindView(R.id.time_layout)
    RelativeLayout mLayout;

    private int mBackGround;

    private int mClickNum = 0;


    private Subscription mSubscription = null;

    private int mDelayTime = 1;//间隔时间，单位毫秒,observable发送的速率
    private long mCurrentTime = 0;//当前时间



    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_time, container, false);
        ButterKnife.bind(this, rootView);


        initView();

        return rootView;
    }

    private void initView() {
        mLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == motionEvent.ACTION_UP) {
                    Log.d("test", "触摸");

                    if (mClickNum == 0) {
                        mClickNum++;
                        mSubscription = Observable.interval(0, mDelayTime, TimeUnit.MILLISECONDS)
                                .sample(20, TimeUnit.MILLISECONDS)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<Long>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.d("test", e.toString());
                                    }

                                    @Override
                                    public void onNext(Long aLong) {
                                        mCurrentTime = aLong;
                                        mTvTime.setText(DateUtil.formatTime(mCurrentTime));
//                                        Log.d("test", "------>along：" + aLong + " time:" + SystemClock.elapsedRealtime());

                                    }
                                });
                    } else if (mClickNum == 1) {
                        mClickNum = 0;
                        mSubscription.unsubscribe();
                        showDialog();
                    }
                }

                return true;
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public static TimeFragment newInstance() {
        TimeFragment fragment = new TimeFragment();
        return fragment;
    }







    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("时间: " + mTvTime.getText().toString());

        String items[] = new String[]{"无惩罚", "+2", "DNF", "取消"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        saveScore();
                        break;
                    case 1:
                        mCurrentTime=mCurrentTime+2000;
                        saveScore();
                        break;
                    case 2:
                        mCurrentTime=0;
                        saveScore();
                        break;
                    case 3:
                        dialogInterface.dismiss();
                        break;
                }
            }
        });

        builder.create().show();

    }


    //保存成绩到数据库
    private void saveScore() {
        Log.d("test",DateUtil.getCurrentTime());
        EventBus.getDefault().post(new SaveTimeEvent(mCurrentTime,DateUtil.getCurrentTime()));
    }


//    @OnClick(R.id.time_bt_set)
//    public void onClick() {
//        Intent intent = new Intent();
//                /* 开启Pictures画面Type设定为image */
//        intent.setType("image/*");
//                /* 使用Intent.ACTION_GET_CONTENT这个Action */
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//                /* 取得相片后返回本画面 */
//        startActivityForResult(intent, 1);
//    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Log.e("uri", uri.toString());
            ContentResolver cr = getActivity().getContentResolver();


            try {
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
//                ImageView imageView = (ImageView) findViewById(R.id.iv01);
//                /* 将Bitmap设定到ImageView */
//                imageView.setImageBitmap(bitmap);
                File f = new File(getRealPathFromURI(uri));
                Drawable d = Drawable.createFromPath(f.getAbsolutePath());
                mLayout.setBackground(d);
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(), e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private String getRealPathFromURI(Uri contentURI) {
        Cursor cursor = getActivity().getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }
}
