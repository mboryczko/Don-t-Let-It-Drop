package com.example.majkiel.jumpproject.Activities;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.majkiel.database.AppDatabaseHelper;
import com.example.majkiel.database.DBFunctions;
import com.example.majkiel.jumpproject.Objects.Constants;
import com.example.majkiel.jumpproject.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button whiteButton, yellowButton, blueButton, greyButton, pinkButton, greenButton;

    private Spinner spinner;
    private SharedPreferences sharedPref;
    private TextView overallPoints;


    private String itemSelected;
    private int positionSelected;
    private int allPoints;
    private String color;

    private SQLiteDatabase db;
    private DBFunctions dbAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        whiteButton = (Button) findViewById(R.id.whiteButton);
        yellowButton = (Button) findViewById(R.id.yellowButton);
        blueButton = (Button) findViewById(R.id.blueButton);
        greyButton = (Button) findViewById(R.id.greyButton);
        pinkButton = (Button) findViewById(R.id.pinkButton);
        greenButton = (Button) findViewById(R.id.greenButton);
        spinner = (Spinner) findViewById(R.id.elementsColorSpinner);
        overallPoints = (TextView) findViewById(R.id.userScoreTextView);

        try{
            SQLiteOpenHelper appDatabaseHelper = new AppDatabaseHelper(this);
            db = appDatabaseHelper.getReadableDatabase();
            dbAccess = new DBFunctions(db);

            String points = dbAccess.getOverallPoints();
            allPoints = Integer.parseInt(points);
            overallPoints.setText(points);

        }catch(SQLiteException e){
            e.printStackTrace();
            Toast toast = Toast.makeText(this, "Database is inaccessible",
                    Toast.LENGTH_SHORT);
            toast.show();
        }


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.elements_array, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        sharedPref = getSharedPreferences(Constants.MyPREFERENCES, Context.MODE_PRIVATE);
        color = getColorForElement();
        setSpecificColor(color);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String selected = parent.getItemAtPosition(pos).toString();


        if(selected.equals("Trap")){
            if(allPoints < 2000){
                Toast.makeText(this, "Please get at least 2000 points", Toast.LENGTH_SHORT).show();
                spinner.setSelection(positionSelected);
                return;
            }
        }

        if(selected.equals("Step")){
            if(allPoints < 5000){
                Toast.makeText(this, "Please get at least 5000 points", Toast.LENGTH_SHORT).show();
                spinner.setSelection(positionSelected);
                return;
            }
        }

        if(selected.equals("Present")){
            if(allPoints < 10000){
                Toast.makeText(this, "Please get at least 10000 points", Toast.LENGTH_SHORT).show();
                spinner.setSelection(positionSelected);
                return;
            }
        }


        positionSelected = pos;
        itemSelected = parent.getItemAtPosition(pos).toString();
        color = getColorForElement();
        setSpecificColor(color);
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }

    public String getColorForElement(){

        if(itemSelected == null)
            return sharedPref.getString(itemSelected, "YELLOW");

        if(itemSelected.equals("Ball")){
            return sharedPref.getString(itemSelected, "YELLOW");
        }
        else if(itemSelected.equals("Trap")){
            return sharedPref.getString(itemSelected, "PINK");
        }
        else if(itemSelected.equals("Step")){
            return sharedPref.getString(itemSelected, "GREY");
        }
        else if(itemSelected.equals("Present")){
            return sharedPref.getString(itemSelected, "GREEN");
        }

        return sharedPref.getString(itemSelected, "YELLOW");
    }



    public void setSpecificColor(String colorName){
        if(colorName == null)
            yellowClicked(yellowButton);

        if(colorName.equals("WHITE")){
            whiteClicked(whiteButton);
        }
        else if(colorName.equals("YELLOW")){
            yellowClicked(yellowButton);
        }
        else if(colorName.equals("BLUE")){
            blueClicked(blueButton);
        }
        else if(colorName.equals("GREY")){
            greyClicked(greyButton);
        }
        else if(colorName.equals("PINK")){
            pinkClicked(pinkButton);
        }
        else if(colorName.equals("GREEN")){
            greenClicked(greenButton);
        }
    }

    public void whiteClicked(View v){

/*        if(allPoints < 1000){
            Toast.makeText(this, "Please get at least 1000 points", Toast.LENGTH_SHORT).show();
            return;
        }*/

        color = "WHITE";
        bigScale((Button)v);
        smallScale(Arrays.asList(yellowButton, blueButton, greyButton, pinkButton, greenButton));
        saveToPreferences();
    }
    public void yellowClicked(View v){

        bigScale((Button)v);

        color = "YELLOW";
        smallScale(Arrays.asList(whiteButton, blueButton, greyButton, pinkButton, greenButton));
        saveToPreferences();
    }
    public void blueClicked(View v){

/*        if(allPoints < 10000){
            Toast.makeText(this, "Please get at least 10000 points", Toast.LENGTH_SHORT).show();
            return;
        }*/
        bigScale((Button)v);

        color = "BLUE";
        smallScale(Arrays.asList(yellowButton, whiteButton, greyButton, pinkButton, greenButton));
        saveToPreferences();
    }
    public void greyClicked(View v){

/*        if(allPoints < 50000){
            Toast.makeText(this, "Please get at least 50000 points", Toast.LENGTH_SHORT).show();
            return;
        }*/
        color = "GREY";
        bigScale((Button)v);

        smallScale(Arrays.asList(yellowButton, blueButton, whiteButton, pinkButton, greenButton));
        saveToPreferences();
    }

    public void pinkClicked(View v){
        color = "PINK";
        bigScale((Button)v);

        smallScale(Arrays.asList(yellowButton, blueButton, whiteButton, greyButton, greenButton));
        saveToPreferences();
    }


    public void greenClicked(View v){
        color = "GREEN";
        bigScale((Button)v);

        smallScale(Arrays.asList(yellowButton, blueButton, whiteButton, pinkButton, greyButton));
        saveToPreferences();
    }

    public void bigScale(Button b){
        b.setScaleX(0.9f);
        b.setScaleY(0.9f);
        b.setTextSize(20);
        b.setText("•");
    }

    public void smallScale(List<Button> list){
        for(Button b : list){
            b.setScaleY(1.0f);
            b.setScaleX(1.0f);
            b.setText("");
        }
    }

    public void saveToPreferences(){

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(itemSelected);
        editor.putString(itemSelected, color);
        editor.commit();
    }



}
