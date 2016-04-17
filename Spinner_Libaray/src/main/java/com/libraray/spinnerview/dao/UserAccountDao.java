package com.libraray.spinnerview.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.libraray.spinnerview.db.UserAccountDb;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KZY on 2016/4/17.
 */
public class UserAccountDao {

    private final UserAccountDb mDb;

    public UserAccountDao(Context context) {

        mDb = new UserAccountDb(context);

    }


    public void insert(String account) {

        delete(account);

        SQLiteDatabase writableDatabase = mDb.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("account", account);

        writableDatabase.insert("accounttb", null, values);

        writableDatabase.close();

    }


    public void delete(String account){

        SQLiteDatabase writableDatabase = mDb.getWritableDatabase();


        writableDatabase.delete("accounttb", "account=?", new String[]{account});


        writableDatabase.close();

    }


    public List<String> getAllAccount(){

        SQLiteDatabase readableDatabase = mDb.getReadableDatabase();

        Cursor cursor = readableDatabase.query("accounttb", new String[]{"account"}, null, null, null, null, "_id desc");

        List<String > list = new ArrayList<>();
        while (cursor.moveToNext()){

            String string = cursor.getString(0);

            list.add(string);
        }

        return list;
    }
}
