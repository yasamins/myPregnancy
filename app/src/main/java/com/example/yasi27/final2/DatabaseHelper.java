package com.example.yasi27.final2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.PublicKey;

/**
 * Created by yasi27 on 27.9.2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "pregnant.db";
    public static final String TABLE_NAME = "pregnant_table";
    public static final String COL_1 = "USERNAME";
    public static final String COL_2 = "DUEDATE";
    public static final String COL_3 = "PREWEIGHT";
    public static final String COL_4 = "CURRENTWEIGHT";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
//        SQLiteDatabase db = this.getWritableDatabase();
        //this is going to create your database and tables
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("create table " + TABLE_NAME + " (USERNAME TEXT, DUEDATE TEXT, PREWEIGHT TEXT, CURRENTWEIGHT TEXT) ");
        //it execute whatever query you pass inside this method as an argument

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
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

    public boolean insertUserInfoData(String username, String duedate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //now we are going to use this contentvalue instance and put data in the columns. it takes 2 arg: column name which you want to inser data and the value
        contentValues.put(COL_1, username);
        contentValues.put(COL_2, duedate);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;

        }
    }

    public boolean insertWeightData(String preweight, String currentweight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //now we are going to use this contentvalue instance and put data in the columns. it takes 2 arg: column name which you want to inser data and the value
        contentValues.put(COL_3, preweight);
        contentValues.put(COL_4, currentweight);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;

        }
    }
}