package com.example.majkiel.jumpproject.Objects;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.majkiel.jumpproject.Colors.Colors;
import com.example.majkiel.jumpproject.Game.GamePanel;

/**
 * Created by majkiel on 13.05.17.
 */

public class Player extends GameObject {

    private long startTime;
    private int radius;
    private Paint paint;
    private boolean isPlayerOnStep;
    private boolean evenPlayerOnStep;
    private boolean isPlayerPlaying;
    private boolean left;
    private boolean right;
    private int playerSpeed;
    private int maxGravity;
    private SharedPreferences sharedPref;

    public Player(int x, int y, Context context){
        radius = GamePanel.WIDTH / 20;
        this.x = x;
        this.y = y;
        maxGravity = GamePanel.HEIGHT/125;
        dy = 0;
        playerSpeed = GamePanel.WIDTH/36;   //30 for 1080

        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);

        SharedPreferences sharedPref = context.getSharedPreferences(Constants.MyPREFERENCES, Context.MODE_PRIVATE);
        String color = sharedPref.getString("Ball", "YELLOW");
        paint.setColor(Colors.getColor(color));
    }



    @Override
    public void draw(Canvas canvas) {
        if(isPlayerPlaying())
            canvas.drawCircle(x, y, radius, paint);
    }

    public void update(){
        if(left){
            this.x -= playerSpeed;
            if(this.x < 0)
                this.x = 0;

        }

        if(right){
            this.x += playerSpeed;
            if(this.x > GamePanel.WIDTH)
                this.x = GamePanel.WIDTH;
        }

        //vertical movement
        if(isPlayerOnStep){
            if(evenPlayerOnStep)
                this.y -= GamePanel.GAME_SPEED - 1;
            else
                this.y -= GamePanel.GAME_SPEED;
            dy = 0;
        }else {
            dy += 1;
            if(dy > maxGravity ) dy = maxGravity;
            this.y += dy * 2;
        }
    }


    public int getPlayerFloor(){return this.y + radius;}
    public void setPlayerOnStep(boolean b){ this.isPlayerOnStep = b;}
    public boolean getPlayerOnStep(){return this.isPlayerOnStep;}
    public void setPlayerPlaying(boolean b){this.isPlayerPlaying = b;}
    public boolean isPlayerPlaying(){return this.isPlayerPlaying;}
    public void setLeft(boolean b){this.left = b;}
    public void setRight(boolean b){this.right = b;}
    public int getRadius(){return this.radius;}
    public int getAcceleration() {return this.dy;}
    public void setevenPlayerOnStep(boolean b){this.evenPlayerOnStep = b;}
    
}
