package com.example.majkiel.jumpproject.Objects;

import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by majkiel on 13.05.17.
 */

public abstract class GameObject {
    protected int x;
    protected int y;
    protected int dy;
    protected int dx;
    protected int width;
    protected int height;

    public void setX(int x)
    {
        this.x = x;
    }
    public void setY(int y)
    {
        this.y = y;
    }
    public void setWidth(int w){this.width = w;}
    public void setHeight(int h){this.height = h;}
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    public int getHeight()
    {
        return height;
    }
    public int getWidth()
    {
        return width;
    }
    public Rect getRectangle()
    {
        return new Rect(x, y, x+width, y+height);
    }
    public abstract void draw(Canvas canvas);

}
