package com.example.birthday_v2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public final static String DB_NAME = "birthday";
    public static final int dbVersion = 2;
    final String LOG_TAG = "LogsDBHelper";


    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, DBHelper.DB_NAME, null, DBHelper.dbVersion);
        Log.d(LOG_TAG, "------ Constructor DBHelper ------");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "****--- onCreate database ---*****");
        // создаем таблицу с полями
        db.execSQL("create table person ("
                + "id integer primary key autoincrement,"
                + "surname text, name text, patronymic text, telephone text"
                + ");");
        db.execSQL("create table event ("
                + "id integer primary key autoincrement,"
                + "date text, event text, idperson integer"
                +");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(LOG_TAG, "-x-x-x-x-x drop table x-x-x-x-");
        db.execSQL("drop table if exists event");
        db.execSQL("drop table if exists person");

        onCreate(db);
    }
}
