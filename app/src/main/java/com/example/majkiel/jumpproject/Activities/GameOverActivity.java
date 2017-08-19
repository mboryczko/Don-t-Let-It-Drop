package com.example.majkiel.jumpproject.Activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.majkiel.database.AppDatabaseHelper;
import com.example.majkiel.jumpproject.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GameOverActivity extends Activity {

    int highScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Intent intent = getIntent();
        highScore = intent.getIntExtra("SCORE", -1);
        TextView textView = (TextView)findViewById(R.id.score);
        textView.setText(Integer.toString(highScore));



    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void saveHighScoreOnClick(View v){

        new InsertHighScoreTask().execute(new Integer(77));

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private class InsertHighScoreTask extends AsyncTask<Integer, Void, Boolean> {
        ContentValues highScoreValues;

        protected void onPreExecute(){
            TextView nameEntered = (TextView)findViewById(R.id.enteredName);
            String name = nameEntered.getText().toString();
            highScoreValues = new ContentValues();
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String dateStr = sdf.format(date);

            highScoreValues.put("NAME", name);
            highScoreValues.put("POINTS", highScore);
            highScoreValues.put("DATA_", dateStr);
        }

        protected Boolean doInBackground(Integer ... params){
            SQLiteOpenHelper fastLogicDatabaseHelper = new AppDatabaseHelper(GameOverActivity.this);

            try{
                SQLiteDatabase db = fastLogicDatabaseHelper.getWritableDatabase();

                db.insert("HIGHSCORE", null, highScoreValues);
                String sql = "update USER set POINTS = POINTS + " + highScore;
                db.execSQL(sql);
                Log.i("ok", "I guess I inserted something");
                db.close();
                return true;
            }
            catch(SQLiteException e){
                return false;
            }
        }

        protected void onPostExecute(Boolean success){
            if(!success){
                Log.e("error", "Cannot connect to db");
            }
        }
    }


}
