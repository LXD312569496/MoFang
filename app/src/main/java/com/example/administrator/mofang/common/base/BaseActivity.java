package com.example.administrator.mofang.common.base;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.example.administrator.mofang.common.BitmapUtil;
import com.example.administrator.mofang.common.EventCenter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import de.greenrobot.event.EventBus;

/**
 * Created by asus on 2017/3/8.
 */

public abstract class BaseActivity extends BaseFrameActivity{

    //启动activity的请求码
    public static final int REQUEST_CODE_OPEN_PHONE_PICTURE =0;
    public static final int REQUEST_CODE_OPEN_PHONT_CAMERA=1;

    //事件的eventCode
    public static final int EVENT_CODE_URI=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(initContentView());
        initView();
        initData();
    }
    protected abstract int  initContentView();
    protected abstract void initView();
    protected abstract void initData();

    public LayoutInflater getLayoutInflater(){
            return LayoutInflater.from(this);
    }

    public void gotoActivity(Class<?> pClass){
        Intent intent=new Intent();
        intent.setClass(this,pClass);
        startActivity(intent);
    }

    public void gotoActivity(Intent intent){
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case REQUEST_CODE_OPEN_PHONT_CAMERA:
                    String name = new DateFormat().format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
                    Toast.makeText(this, name, Toast.LENGTH_LONG).show();
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式

                    FileOutputStream b = null;
                    //???????????????????????????????为什么不能直接保存在系统相册位置呢？？？？？？？？？？？？
                    File file = new File("/sdcard/myImage/");
                    file.mkdirs();// 创建文件夹
                    String fileName = "/sdcard/myImage/"+name;

                    try {
                        b = new FileOutputStream(fileName);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            b.flush();
                            b.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        EventCenter<String> event=new EventCenter<>(EVENT_CODE_URI,fileName);
                        EventBus.getDefault().post(event);
                    }
                    break;
                case REQUEST_CODE_OPEN_PHONE_PICTURE:
                    //发送的是手机图片的file地址
                    Uri uri = data.getData();
                    if (uri != null) {
                        EventCenter<String> event=new EventCenter<String>(EVENT_CODE_URI,BitmapUtil.getImageAbsolutePath(this,uri));
                        EventBus.getDefault().post(event);
                    }
                    break;
            }
        }
    }

    /**
     * Toast
     */
    public void showToastLong(int strId){
        Toast.makeText(this,strId,Toast.LENGTH_LONG).show();
    }

    public void showToastLong(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    public void showToastShort(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
    public void showToastShort(int strId){
        Toast.makeText(this,strId,Toast.LENGTH_SHORT).show();
    }


}
