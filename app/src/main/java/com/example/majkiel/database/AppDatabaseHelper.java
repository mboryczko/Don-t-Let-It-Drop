package com.example.majkiel.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mjbor on 01.05.2017.
 */

public class AppDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "fastLogicDB";
    private static final int DB_VERSION = 1;

    public AppDatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        updateMyDatabese(db, 0, DB_VERSION);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        updateMyDatabese(db, oldVersion, newVersion);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        updateMyDatabese(db, oldVersion, newVersion);
    }

    private void updateMyDatabese(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion < 1){
            String tableCreation = "CREATE TABLE HIGHSCORE( " +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "NAME TEXT," +
                    "POINTS INTEGER," +
                    "DATA_ TEXT"+
                    ");";

            String userCreation = "CREATE TABLE USER( " +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "POINTS INTEGER" +
                    ");";



            try{
                db.execSQL(tableCreation);
                db.execSQL(userCreation);
                db.execSQL("insert into USER(points) values('0') ");
            }
            catch(SQLiteException e){
                e.printStackTrace();
            }

        }

    }
}