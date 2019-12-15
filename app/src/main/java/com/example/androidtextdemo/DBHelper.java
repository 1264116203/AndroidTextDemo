package com.example.androidtextdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * @author glsite.com
 * @version $
 * @des
 * @updateAuthor $
 * @updateDes
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DBname = "history_db";
    private static final String TABname = "Score_tab";
    private SQLiteDatabase history_DB;

    static final int version = 1;
    private Cursor cursor;

    public DBHelper(@Nullable Context context) {
        super(context, DBname, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.history_DB = db;
        history_DB.execSQL("create table Score_tab (_id integer primary key autoincrement,Score integer,Date varchr(40),time not null default current_timestamp )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(ContentValues values, String TABname) {
        SQLiteDatabase database = getWritableDatabase();
        database.insert(TABname, null, values);
        database.close();
    }

    public void delete(int id, String TABname) {
        if (history_DB == null) {
            SQLiteDatabase database = getWritableDatabase();
            database.delete(TABname, "_id=?", new String[]{String.valueOf(id)});
            database.close();
        }
    }

    public Cursor query(Context context) {
        //判断是否创建了表 没有则toast
        SQLiteDatabase database = getWritableDatabase();
        cursor = database.query(TABname, null, null,
                null, null, null, null);
//        cursor.close();
//        database.close();
        return cursor;
    }

}
