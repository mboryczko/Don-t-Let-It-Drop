package com.example.majkiel.jumpproject.Objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.majkiel.jumpproject.Colors.Colors;

/**
 * Created by majkiel on 13.05.17.
 */

public class Background {

    private int width;
    private int height;
    Paint paint;

    public Background(int width, int height){
        this.width = width;
        this.height = height;
        this.paint = new Paint();
        paint.setColor(Colors.getBackgroundColor());
        paint.setStyle(Paint.Style.FILL);
    }

    public void draw(Canvas canvas){
        canvas.drawRect(0, 0, width, height, paint);
    }
}
