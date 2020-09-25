package com.varlor.sqlitedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper
{
    public DBHelper(@Nullable Context context) {
        super(context,"sqlitedb",null,2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table if not exists person(personid integer primary key autoincrement,name varchar(30),age integer(3))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("升级啦","升级啦");
        db.execSQL("alter table person add account integer");
    }
}
