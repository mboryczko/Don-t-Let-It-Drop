package com.example.majkiel.jumpproject.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Toast;

import com.example.majkiel.jumpproject.R;
import com.imangazaliev.circlemenu.CircleMenu;
import com.imangazaliev.circlemenu.CircleMenuButton;

/**
 * Created by majkiel on 14.05.17.
 */

public class MenuActivity extends Activity {

    boolean doubleBackToExitPressedOnce = false;
    private CircleMenu circleMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        circleMenu = (CircleMenu) findViewById(R.id.circleMenu);

        circleMenu.setOnItemClickListener(new CircleMenu.OnItemClickListener() {
            @Override
            public void onItemClick(CircleMenuButton menuButton) {
                switch (menuButton.getId()) {
                    case R.id.playRoundButton:
                        playClicked();
                        break;
                    case R.id.highscoreRoundButton:
                        highscoreClicked();
                        break;
                    case R.id.settingsRoundButton:
                        settingsClicked();
                        break;
                    case R.id.infoRoundButton:
                        infoClicked();
                        break;

                }
            }
        });

        circleMenu.setStateUpdateListener(new CircleMenu.OnStateUpdateListener() {
            @Override
            public void onMenuExpanded() {

            }

            @Override
            public void onMenuCollapsed() {

            }
        });

    }

    public void playClicked(){
        Sleep( MainActivity.class);
    }


    public void highscoreClicked(){
        Sleep(HighScoreActivity.class);
    }

    public void settingsClicked(){
        Sleep(SettingsActivity.class);
    }

    public void infoClicked(){
        Sleep(InfoActivity.class);
    }

    public void aboutClicked(){
        Sleep(MainActivity.class);
    }

    public void Sleep(final Class <?> clazz ){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MenuActivity.this, clazz);
                startActivity(intent);
            }
        }, 800);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            this.finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


}
