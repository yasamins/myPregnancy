package com.example.yasi27.final2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yasi27 on 6.10.2016.
 */
public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Mothers.db";
    public static final String TABLE_NAME = "mothers_table";
    public static final String COL_1 = "USERNAME";
    public static final String COL_2 = "DUEDATE";
    public static final String COL_3 = "PREWEIGHT";
    public static final String COL_4 = "CURRENTWEIGHT";


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("create table " + TABLE_NAME + " (USERNAME TEXT, DUEDATE TEXT, PREWEIGHT TEXT, CURRENTWEIGHT TEXT, PRIMARY KEY(USERNAME) )");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public boolean insertData(String username, String duedate, String preweight, String currentweight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //now we are going to use this contentvalue instance and put data in the columns. it takes 2 arg: column name which you want to inser data and the value
        contentValues.put(COL_1, username);
        contentValues.put(COL_2, duedate);
        contentValues.put(COL_3, preweight);
        contentValues.put(COL_4, currentweight);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;

        }
    }

    void insertWeight(String username, String preweight, String currentweight) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_NAME + " SET PREWEIGHT='" + preweight + "',CURRENTWEIGHT='" + currentweight + "' WHERE USERNAME='" + username + "'; ");
    }

    public Cursor getAllData(String username) {
//         we will create the instance of database
        SQLiteDatabase db = this.getReadableDatabase();
        //we should create an instance of the cursor
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " WHERE USERNAME= '" + username + "'; ", null);
//        System.out.println(res);
        return res;


    }


}



