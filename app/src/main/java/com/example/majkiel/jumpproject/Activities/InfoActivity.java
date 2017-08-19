package com.example.majkiel.jumpproject.Activities;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.majkiel.database.AppDatabaseHelper;
import com.example.majkiel.jumpproject.Colors.Colors;
import com.example.majkiel.jumpproject.R;

public class InfoActivity extends AppCompatActivity {

    private TextView overallPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);


        overallPoints = (TextView) findViewById(R.id.userScoreTextView);

    }

    public void clearHighScoreClicked(View v){


        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Do you really want to clear highscores?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        new ClearHighScoreTask().execute(new Integer(77));
                        Toast.makeText(InfoActivity.this, "Highscores cleared", Toast.LENGTH_LONG).show();
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void rateClicked(View v){
        //Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Uri uri = Uri.parse("market://details?id=com.mjbor.spanishwords");
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    //Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                    Uri.parse("play.google.com/store/apps/details?id=com.mjbor.spanishwords")));
        }
    }


    private class ClearHighScoreTask extends AsyncTask<Integer, Void, Boolean> {
        protected void onPreExecute(){
        }

        protected Boolean doInBackground(Integer ... params){
            SQLiteOpenHelper appDatabaseHelper = new AppDatabaseHelper(InfoActivity.this);

            try{
                SQLiteDatabase db = appDatabaseHelper.getWritableDatabase();
                db.execSQL("DELETE FROM HIGHSCORE");
                db.execSQL("update USER set POINTS = 0");
                Log.i("ok", "I guess I deleted everything");
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
