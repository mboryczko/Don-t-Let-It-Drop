package com.example.majkiel.jumpproject.Objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.example.majkiel.jumpproject.Game.GamePanel;

/**
 * Created by majkiel on 14.05.17.
 */

public class Score {


    Paint paint;
    private int score;
    private int presentScore;



    public Score(){
        paint = new Paint();
        paint.setColor(Color.WHITE);
        //100
        paint.setTextSize(GamePanel.HEIGHT/17);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

    }

    public void draw(Canvas canvas){
        //450
        //110
        float width;
        if(getScore() < 10)
            width = (GamePanel.WIDTH * 0.8f);
        else if(getScore() < 100)
            width = (GamePanel.WIDTH * 0.74f);
        else if(getScore() < 1000)
            width = (GamePanel.WIDTH * 0.68f);
        else if(getScore() < 10000)
            width = (GamePanel.WIDTH * 0.62f);
        else if(getScore() < 100000)
            width = (GamePanel.WIDTH * 0.56f);
        else
        width = (GamePanel.WIDTH * 0.5f);

        canvas.drawText(getScore() + "pts", width, GamePanel.HEIGHT/14 , paint);
    }

    public int getScore(){
        return this.score+presentScore;
    }

    public int getScoreWithoutPresents(){ return score;}

    public void incrementScore(){
        this.score++;
    }

    public void addBonus() {this.presentScore += 15;}

}
