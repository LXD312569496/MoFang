package com.example.administrator.mofang.time.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by asus on 2017/3/13.
 * 主要是为了数据库升级而写的
 */

public class DBHelper extends DaoMaster.OpenHelper {

    public DBHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL("ALTER TABLE 'SCORE' ADD 'SCRAMBLE' TEXT;");
        }
    }
}
