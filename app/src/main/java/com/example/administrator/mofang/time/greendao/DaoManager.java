package com.example.administrator.mofang.time.greendao;

import android.content.Context;

import org.greenrobot.greendao.AbstractDao;

/**
 * Created by Administrator on 2017/2/22.
 */

public class DaoManager {

    private static DaoMaster daoMaster;
    private static DaoSession daoSession;
    private static ScoreDao scoreDao;
    private static GroupDao groupDao;

    public static void init(Context context){
//        DaoMaster.DevOpenHelper devOpenHelper=new DaoMaster.DevOpenHelper(context,"time.db",null);
       DBHelper helper=new DBHelper(context,"time.db");
        daoMaster=new DaoMaster(helper.getWritableDb());
        daoSession=daoMaster.newSession();
        scoreDao=daoSession.getScoreDao();
        groupDao=daoSession.getGroupDao();
    }

    public static DaoMaster getDaoMaster(){
        return daoMaster;
    }

    public static DaoSession getDaoSession(){
        return daoSession;
    }

    public static ScoreDao getScoreDao(){
        return scoreDao;
    }

    public static GroupDao getGroupDao(){return groupDao;}

    public static <T>AbstractDao getDao(Class<T> type){
        return daoSession.getDao(type);
    }

}
