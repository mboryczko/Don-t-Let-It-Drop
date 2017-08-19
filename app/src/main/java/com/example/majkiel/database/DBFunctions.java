package com.example.majkiel.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mjbor on 8/8/2017.
 */

public class DBFunctions {

    private SQLiteDatabase db;
    private Cursor cursor;


    public DBFunctions(SQLiteDatabase db){
        this.db = db;
    }

    public String getOverallPoints(){
        cursor = db.rawQuery("select POINTS from USER", null);
        cursor.moveToFirst();
        return cursor.getString(0);
    }





}

