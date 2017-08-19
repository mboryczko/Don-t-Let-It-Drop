package com.example.majkiel.jumpproject.Activities;
import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.majkiel.database.AppDatabaseHelper;
import com.example.majkiel.jumpproject.Adapter.HighscoresAdapter;
import com.example.majkiel.jumpproject.Objects.Highscore;
import com.example.majkiel.jumpproject.R;

import java.util.ArrayList;
import java.util.List;


public class HighScoreActivity extends Activity {

    private SQLiteDatabase db;
    private Cursor cursor;
    private ListView highscoreListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        highscoreListView = (ListView) findViewById(R.id.highscoreListView);


        try{
            SQLiteOpenHelper appDatabaseHelper = new AppDatabaseHelper(this);
            db = appDatabaseHelper.getReadableDatabase();
            cursor = db.rawQuery("select NAME, POINTS, DATA_ from HIGHSCORE order by POINTS DESC", null);
            List<Highscore> highscores = new ArrayList<>();

            while (cursor.moveToNext()) {
                Highscore highscore = new Highscore();
                highscore.setName(cursor.getString(0));
                highscore.setScore(cursor.getInt(1));
                highscore.setDate(cursor.getString(2));

                highscores.add(highscore);
            }

            HighscoresAdapter highscoresAdapter = new HighscoresAdapter(HighScoreActivity.this, highscores);
            highscoreListView.setAdapter(highscoresAdapter);
        }catch(SQLiteException e){
            e.printStackTrace();
            Toast toast = Toast.makeText(this, "Database is inaccessible",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        cursor.close();
        db.close();
    }
}