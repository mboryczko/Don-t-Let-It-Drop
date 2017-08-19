package com.example.majkiel.jumpproject.Game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.lang.Math.*;

import com.example.majkiel.jumpproject.Activities.GameOverActivity;
import com.example.majkiel.jumpproject.Activities.MainActivity;
import com.example.majkiel.jumpproject.Objects.Background;
import com.example.majkiel.jumpproject.Objects.Player;
import com.example.majkiel.jumpproject.Objects.Present;
import com.example.majkiel.jumpproject.Objects.Score;
import com.example.majkiel.jumpproject.Objects.Step;
import com.example.majkiel.jumpproject.Objects.Trap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.Math.scalb;

/**
 * Created by majkiel on 13.05.17.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{
    private MainThread thread;
    public static int WIDTH;
    public static int HEIGHT;
    public static int PADDING_X;
    public static int PADDING_Y;
    public static int GAME_SPEED;

    Random rand;

    private Background bg;
    private ArrayList<Step> steps;
    private ArrayList<Trap> traps;
    private long stepStartTime;
    private long trapStartTime;
    private long presentStartTime;
    private Player player;
    private Score score;
    private Present present;
    private int presentScore;

    private int clickedX;
    private int clickedY;
    private boolean isOnFlag;
    private boolean wasOnFlag;
    public int i = 0;


    private Context mContext;
    private boolean gameOver;
    public GamePanel(Context context)
    {
        super(context);
        this.mContext = getContext();
        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);
        //make gamePanel focusable so it can handle events
        setScreenMetrics();
        PADDING_X = (int)(0.05 * WIDTH);
        PADDING_Y = (int) (0.2 * HEIGHT);

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        rand = new Random();

        bg = new Background(WIDTH, HEIGHT);
        stepStartTime = System.nanoTime();
        player = new Player(WIDTH, PADDING_Y, mContext);
        steps = new ArrayList<Step>();
        traps = new ArrayList<Trap>();
        score = new Score();
        present = new Present(mContext);
        GAME_SPEED = getGameSpeed();
        presentScore = 0;

        //create first 7 steps
        for(int j=1; j<5; j++){
            i++;
            steps.add(new Step(j, mContext));
        }

        thread = new MainThread(getHolder(), this);
        //we can safely start the game loop
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        int counter = 0;
        while(retry && counter<1000)
        {
            counter++;
            try{
                thread.setRunning(false);
                thread.join();
                retry = false;
                thread = null;

            }catch(InterruptedException e){e.printStackTrace();}

        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int actionPerformed = event.getAction();

        switch(actionPerformed){
            case MotionEvent.ACTION_DOWN:{
                this.clickedX = (int) event.getX();
                this.clickedY = (int) event.getY();

                if(!player.isPlayerPlaying()){
                    player.setPlayerPlaying(true);
                    player.setX(clickedX);
                    player.setY(clickedY);
                    isPlayerPlaying();
                    deleteOldSteps();
                }


                if(clickedX < WIDTH/2){
                    player.setLeft(true);
                    player.setRight(false);
                }else{
                    player.setLeft(false);
                    player.setRight(true);
                }
                return true;
            }

            case MotionEvent.ACTION_UP:{
                player.setLeft(false);
                player.setRight(false);
                return true;
            }

            case MotionEvent.ACTION_MOVE:{
                //Log.e("ACTION MOVEEEE", "oneoneone");
                this.clickedX = (int) event.getX();
                this.clickedY = (int) event.getY();

                if(clickedX < WIDTH/2){
                    player.setLeft(true);
                    player.setRight(false);
                }else{
                    player.setLeft(false);
                    player.setRight(true);
                }
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);

        if(canvas != null){
            bg.draw(canvas);
            for(Step s : steps){
                s.draw(canvas);
            }

            for(Trap t : traps){
                t.draw(canvas);
            }

            player.draw(canvas);
            score.draw(canvas);
            present.draw(canvas);
        }

    }


    public void update(){
        if(player.isPlayerPlaying()){
            GAME_SPEED = getGameSpeed();
            long stepElapsed = (System.nanoTime()-stepStartTime)/1000000;
            if(stepElapsed > (500 - score.getScore()/3)){
                //if(stepElapsed > (1000 - 110*4)){
                i++;
                if(i > 10 /*&& rand.nextInt(10) > 7*/) {
                    i = 0;
                    steps.add(new Step(present, mContext));
                }
                else
                    steps.add(new Step(mContext));
                score.incrementScore();
                //reset timer
                stepStartTime = System.nanoTime();
            }

            long trapElapsed = (System.nanoTime() - trapStartTime)/1000000;
            if(trapElapsed > (4000 - score.getScore()*6)){

                traps.add(new Trap(mContext));
                trapStartTime = System.nanoTime();
            }

            isOnFlag = false;
            //update steps
            int playerX = player.getX();
            int playerY = player.getPlayerFloor();

            for(Step s : steps){
                s.update();
                if( isPlayerYOnStep(s) && isPlayerXOnStep(s) ){
                    //if(wasOnFlag)
                    //    player.setY(s.getY() - (int)(0.5 * player.getRadius()));
                    player.setPlayerOnStep(true);
                    //Log.e("wtf", Integer.toString(s.nr));
                    isOnFlag = true;
                }


            }
            //jeśli tutaj isOnFlag == false
            //to przeszło przez wszystkie stepy i na żadnym sie nie zatrzymała
            //czyli player spadał
            wasOnFlag = isOnFlag;

            for(Trap t : traps){
                t.update();
                if(isPlayerXOnTrap(t) && isPlayerYOnTrap(t)){
                    player.setPlayerPlaying(false);
                    gameOver = true;
                }
            }

            if(  isPlayerXOnPresent() && isPlayerYOnPresent()   ){
                score.addBonus();
                present.hide();

            }


            present.update();
            player.update();
            //player.setPlayerOnStep(false);
            // Log.e("setStem", Boolean.toString(wasOnFlag));
            if(!isOnFlag){
                player.setPlayerOnStep(false);
            }
        }
        else
        {
            if(gameOver){
                Intent intent = new Intent(mContext, GameOverActivity.class);
                intent.putExtra("SCORE", score.getScore());
                mContext.startActivity(intent);
                gameOver = false;
            }

        }


    }

    private void setScreenMetrics(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        HEIGHT = displayMetrics.heightPixels;
        WIDTH = displayMetrics.widthPixels;
    }



    private boolean isPlayerXOnPresent(){
        if( (player.getX() + player.getRadius() > present.getX()) && ( player.getX() - player.getRadius() < present.getX() + present.getWidth() ) )
            return true;

        return false;
    }

    private boolean isPlayerYOnPresent(){
        if( (player.getY() + player.getRadius() > present.getY()) && ( player.getY() - player.getRadius() < present.getY() + present.getHeight() ) )
            return true;

        return false;
    }

    private boolean isPlayerYOnStep(Step s){
        //return (abs(player.getY() - s.getY()) < (player.dy+1) * 10);
        //boolean value = (abs(s.getY()-player.getPlayerFloor()) < 28 );
        boolean value = (abs(s.getY()-player.getPlayerFloor()) < player.getAcceleration() * 2 + getGameSpeed() );
        int difference = (abs(s.getY()-player.getPlayerFloor()) - player.getAcceleration() * 2 + getGameSpeed() );

        if(value){
            if(difference > 5 && player.getPlayerFloor() < s.getY())
                player.setevenPlayerOnStep(true);
            else
                player.setevenPlayerOnStep(false);
            //Log.e("step" + s.nr, "true " + s.getY() + " " + player.getPlayerFloor() + " abs: " + abs(s.getY()-player.getPlayerFloor()));
            return true;
        }
        return false;
        //return (abs(s.getY()-player.getY()) < 5 );
    }

    private boolean isPlayerXOnStep(Step s){
        if( ((player.getX() + player.getRadius()) < s.getX()) || ( (player.getX() - player.getRadius()) > (s.getX() + s.getWidth())) )
            return false;

        return true;

        //return !( (player.getX() < s.getX() ) || ((player.getX() + player.getWidth()) > (s.getX() + s.getWidth() )) );
    }

    private boolean isPlayerYOnTrap(Trap t){
        //return (abs(player.getY() - s.getY()) < (player.dy+1) * 10);
        //boolean value = (abs(s.getY()-player.getPlayerFloor()) < 28 );
        boolean value = (abs(t.getY()-player.getPlayerFloor()) < player.getAcceleration() * 2 + getGameSpeed() );

        if(value){
            return true;
        }

        return false;
    }

    private boolean isPlayerXOnTrap(Trap t){
        if( ((player.getX() + player.getRadius()) < t.getX()) || ( (player.getX() - player.getRadius()) > (t.getX() + t.getWidth())) )
            return false;

        return true;
    }

    private void deleteOldSteps(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Iterator<Step> iter = steps.iterator();
                while (iter.hasNext()) {
                    Step step = iter.next();
                    //String str = iter.next();

                    if (step.getY() < -100)
                        iter.remove();
                }

                Iterator<Trap> trapIterator = traps.iterator();
                while(trapIterator.hasNext()){
                    Trap trap = trapIterator.next();
                    if(trap.getY() < -200)
                        trapIterator.remove();
                }

                handler.postDelayed(this, 2200);
            }
        }, 1500);
    }


    private void isPlayerPlaying(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if(player.getY() > HEIGHT){
                    player.setPlayerPlaying(false);
                    gameOver = true;
                }

                if (player.getY() < 0){
                    player.setPlayerPlaying(false);
                    gameOver = true;
                }

                handler.postDelayed(this, 900);
            }
        }, 800);
    }


    private int getGameSpeed(){
        int mScore = score.getScoreWithoutPresents();
        int speed = HEIGHT/1500+1;
        if(mScore < 10){
            return HEIGHT/170;
        }

        return (mScore+speed)/4 +HEIGHT/170;
    }



}
