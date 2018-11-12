package com.sjs.viewholder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {
    private  static  DBOpenHelper sInstance;

    private static final String dbName = "item.db";
    private static final int dbVersion = 1;
    private static final String SQL_CREATE_ENTRIES =
            String.format("CREATE TABLE %s " +
                            "(%S INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "%s INTEGER, %s TEXT, %s TEXT)",
                    ItemInfo.ItemEntry.TABLE_NAME,
                    ItemInfo.ItemEntry.COLUMN_NAME_ID,
                    ItemInfo.ItemEntry.COLUMN_NAME_COUNT,
                    ItemInfo.ItemEntry.COLUMN_NAME_TITLE,
                    ItemInfo.ItemEntry.COLUMN_NAME_DETAIL);
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ItemInfo.ItemEntry.TABLE_NAME;

    public  static DBOpenHelper getsInstance(Context context){
        if(sInstance == null){
            sInstance = new DBOpenHelper(context);
        }
        return sInstance;
    }

    private DBOpenHelper(Context context) {

        super(context, dbName, null, dbVersion);
    }

    @Override

    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

 }
