package com.example.androidtextdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

/**
 * @author glsite.com
 * @version $
 * @des
 * @updateAuthor $
 * @updateDes
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DBname = "history_db";
//    private static final String TABname="history_tab";
    private SQLiteDatabase history_DB;

    static final int version=1;
    public DBHelper(@Nullable Context context) {
        super(context, DBname, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.history_DB=db;
        history_DB.execSQL("create table history_tab (id integer primary key autoincrement, Date varchar(20),score integer,time varchr(40))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void insert(ContentValues values,String TABname){
        SQLiteDatabase database = getWritableDatabase();
        database.insert(TABname,null,values);
        database.close();
    }
    public void delete(int id,String TABname){
        if (history_DB==null){
        SQLiteDatabase database = getWritableDatabase();
        database.delete(TABname,"id=?",new String[]{String.valueOf(id)});
        database.close();
        }
    }
    public Cursor query(String name,String TABname){

            SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.query(TABname, new String[]{"id", "name", "score", "Date"}, "name=?", new String[]{name}, null, null, null);
        database.close();
        return cursor;



    }
    public void update(ContentValues values,String TABname){
        SQLiteDatabase database = getWritableDatabase();
        database.insert(TABname,null,values);
        database.close();
    }


}
