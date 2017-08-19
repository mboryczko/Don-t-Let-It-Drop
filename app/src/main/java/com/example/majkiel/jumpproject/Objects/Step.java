package com.example.majkiel.jumpproject.Objects;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.example.majkiel.jumpproject.Colors.Colors;
import com.example.majkiel.jumpproject.Game.GamePanel;

import java.util.Random;

/**
 * Created by majkiel on 13.05.17.
 */



public class Step extends GameObject{
    Paint paint;
    Random rand;
/*
    Paint textPaint;
    public int nr;*/

    //used as first few steps
    public Step(int j, Context context){
        rand = new Random();
        int width = drawRandomWidth();
        setX(drawRandomX(width));
        //Log.e("after setX step", "in step constructor");
        setY(GamePanel.HEIGHT/j + 50);
        //this.dy = speed;
        setHeight(calculateHeight());
        setWidth(width);

        preparePaint(context);
    }


    public Step(Present present, Context context){
        //Log.e("begining of step ", "constructor");
        rand = new Random();
        int width = drawRandomWidth();
        int mX = drawRandomX(width);
        int mY = GamePanel.HEIGHT + GamePanel.PADDING_Y;
        setX(mX);
        setY(mY);
        //Log.e("after setX step", "in step constructor");
        //this.dy = speed;
        setHeight(calculateHeight());
        setWidth(width);

        preparePaint(context);
        present.create(mX, mY, width);
    }

    public Step(Context context){
        //Log.e("begining of step ", "constructor");
        rand = new Random();
        int width = drawRandomWidth();
        int mX = drawRandomX(width);
        setX(mX);
        //Log.e("after setX step", "in step constructor");
        setY(GamePanel.HEIGHT + GamePanel.PADDING_Y);
        //this.dy = speed;
        setHeight(calculateHeight());
        setWidth(width);

        preparePaint(context);
    }

    public void preparePaint(Context context){
        this.paint = new Paint();
        paint.setStyle(Paint.Style.FILL);

        SharedPreferences sharedPref = context.getSharedPreferences(Constants.MyPREFERENCES, Context.MODE_PRIVATE);
        String color = sharedPref.getString("Step", "GREY");
        paint.setColor(Colors.getColor(color));
    }


    @Override
    public void draw(Canvas canvas) {
        //canvas.drawText(Integer.toString(nr), x, y-5, textPaint);
        //Log.e("in draw method", "in step");
        canvas.drawRect(getRectangle(), paint);
    }

    public void update(){

        this.y -= GamePanel.GAME_SPEED;
    }

    private int drawRandomX(int width){
        int xValue = rand.nextInt(GamePanel.WIDTH - GamePanel.PADDING_X*2 - width) + GamePanel.PADDING_X*2;
        Log.e("drawn X step", Integer.toString(xValue));
        return xValue;
    }

    private int drawRandomWidth(){
        int minWidth = GamePanel.WIDTH/5;
        return rand.nextInt(minWidth)+minWidth;
    }

    private int calculateHeight() {
        return GamePanel.HEIGHT / 120;
    }
}
