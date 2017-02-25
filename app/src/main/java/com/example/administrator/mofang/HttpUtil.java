package com.example.administrator.mofang;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Administrator on 2017/2/10.
 */

public class HttpUtil {

    private static ConnectivityManager con;

    public static void getInstance(Context context) {
        con = (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);

    }


    public static Boolean isNetWorkConnected() {
        boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        boolean internet = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        if (wifi | internet) {
            return true;
        } else {
            //未联网
            return false;
//            Toast.makeText(getApplicationContext(),
//                    "亲，网络连了么？", Toast.LENGTH_LONG)
//                    .show();
        }


//        另一种写法
//        NetworkInfo current = cm.getActiveNetworkInfo();
//        if (current == null) {
//            return false;
//        }
//        return (current.isAvailable());
    }

}
