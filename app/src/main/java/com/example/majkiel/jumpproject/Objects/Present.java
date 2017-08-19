package com.example.majkiel.jumpproject.Objects;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;

import com.example.majkiel.jumpproject.Colors.Colors;
import com.example.majkiel.jumpproject.Game.GamePanel;

import java.util.Random;

/**
 * Created by mjbor on 05.07.2017.
 */

public class Present extends GameObject {
    Paint paint;
    Random rand;
    private int presentSize;

    public Present(Context context){
        rand = new Random();
        this.paint = new Paint();
        //130
         presentSize= GamePanel.WIDTH/12;
        //pink
        // paint.setColor(Color.rgb(255, 0, 128));
        hide();

        setHeight(presentSize);
        setWidth(presentSize);
        preparePaint(context);
    }

    public void preparePaint(Context context){
        this.paint = new Paint();
        paint.setStyle(Paint.Style.FILL);

        SharedPreferences sharedPref = context.getSharedPreferences(Constants.MyPREFERENCES, Context.MODE_PRIVATE);
        String color = sharedPref.getString("Present", "GREEN");
        paint.setColor(Colors.getColor(color));
    }

    public void create(int x, int y, int stepWidth){
        //60
        setX(x + rand.nextInt(stepWidth - GamePanel.WIDTH/18 ) );

        //5
        setY(y - presentSize - GamePanel.WIDTH/355);
    }

    public void hide(){
        setX(-1000);
        setY(-1000);
    }

    @Override
    public void draw(Canvas canvas) {
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
}
