package com.libraray.spinnerview.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by KZY on 2016/4/17.
 */
public class UserAccountDb extends SQLiteOpenHelper {
    public UserAccountDb(Context context) {
        super(context, "useraccount.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table accounttb(_id integer primary key autoincrement,account text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table blacktb");

        onCreate(db);
    }
}
