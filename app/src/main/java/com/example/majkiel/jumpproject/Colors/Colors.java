package com.example.majkiel.jumpproject.Colors;

import android.graphics.Color;

/**
 * Created by majkiel on 16.05.17.
 */

public class Colors {
    static int backgroundColor = Color.rgb(39, 39, 39);
    static int playerColor = Color.rgb(246, 223, 14);
    static int stepColor = Color.rgb(104, 104, 104);
    static int trapColor = Color.rgb(255,0,128);

    public static int getPresentColor() {
        return presentColor;
    }

    public static void setPresentColor(int presentColor) {
        Colors.presentColor = presentColor;
    }

    static int presentColor = Color.rgb(97, 198, 56);

    public Colors(){}

    public static int getTextColor() {
        return textColor;
    }

    public static void setTextColor(int textColor) {
        Colors.textColor = textColor;
    }

    static int textColor = Color.rgb(0,0,0);

    public static int getTrapColor() {
        return trapColor;
    }

    public static void setTrapColor(int trapColor) {
        Colors.trapColor = trapColor;
    }

    public static int getBackgroundColor() {
        return backgroundColor;
    }

    public static void setBackgroundColor(int backgroundColor) {
        Colors.backgroundColor = backgroundColor;
    }

    public static int getPlayerColor() {
        return playerColor;
    }

    public static void setPlayerColor(int playerColor) {
        Colors.playerColor = playerColor;
    }

    public static int getStepColor() {
        return stepColor;
    }

    public static void setStepColor(int stepColor) {
        Colors.stepColor = stepColor;
    }


    public static int getColor(String colorName){

        if(colorName == null)
            return Color.rgb(246, 223, 14);

        if(colorName.equals("WHITE")){
            return Color.rgb(255, 255, 255);
        }
        else if(colorName.equals("YELLOW")){
            return Color.rgb(246, 223, 14);
        }
        else if(colorName.equals("BLUE")){
            return Color.rgb(77, 219, 255);
        }
        else if(colorName.equals("GREY")){
            return Color.rgb(104, 104, 104);
        }

        else if(colorName.equals("PINK")){
            return Color.rgb(255,0,128);
        }

        else if(colorName.equals("GREEN")){
            return Color.rgb(97, 198, 56);
        }


        return Color.rgb(246, 223, 14);
    }





}
