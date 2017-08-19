package com.example.majkiel.jumpproject.Objects;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;

import com.example.majkiel.jumpproject.Colors.Colors;
import com.example.majkiel.jumpproject.Game.GamePanel;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by majkiel on 13.05.17.
 */

public class Trap extends GameObject{
    Paint paint;
    Paint textPaint;
    public int nr;
    private float xDistance;
    private float yDistance;
    Random rand;


    public Trap(Context context){
        //Log.e("begining of trap ", "constructor");
        rand = new Random();
        setX(drawRandomX());
        //setX();
        //Log.e("after setX", "in Trap constructor");
        setY(GamePanel.HEIGHT + GamePanel.PADDING_Y);
       // rand = new Random();
        //int width = rand.nextInt(300)+300;

        //12
        setHeight(GamePanel.HEIGHT/148);
        //300
        setWidth((int)(GamePanel.WIDTH/3.6));

        //60
        xDistance = GamePanel.WIDTH/18;
        //26
        yDistance = GamePanel.HEIGHT/68;

        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);

        SharedPreferences sharedPref = context.getSharedPreferences(Constants.MyPREFERENCES, Context.MODE_PRIVATE);
        String color = sharedPref.getString("Trap", "PINK");
        paint.setColor(Colors.getColor(color));


    }

    @Override
    public void draw(Canvas canvas) {
        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);

        //int bound = (int)(this.getWidth()/xDistance) + 1;
        for(int i=0 ; i<11; i++){
            if(i%2 == 0){
                path.lineTo(x + xDistance * i/(float)2.0 , y);
                //Log.e(Float.toString(x + xDistance * i/(float)2.0), Float.toString(y));
            }

            else{
                path.lineTo(x + xDistance * i/(float)2.0 ,y-yDistance);
                //Log.e(Float.toString(x + xDistance * i/(float)2.0), Float.toString(y-yDistance));
            }

        }
        path.lineTo(x,y);
        path.close();


        path.close();
        canvas.drawPath(path, paint);
        canvas.drawRect(getRectangle(), paint);
    }

    public void update(){
        this.y -= GamePanel.GAME_SPEED;
    }

    private int drawRandomX(){
        int xValue = rand.nextInt(GamePanel.WIDTH - GamePanel.PADDING_X*2 - 300) + GamePanel.PADDING_X*2;
        //Log.e("drawn X", Integer.toString(xValue));
        return xValue;
    }

}
