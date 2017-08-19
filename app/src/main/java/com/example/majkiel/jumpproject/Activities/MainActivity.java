package com.example.majkiel.jumpproject.Activities;


        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.Window;
        import android.view.WindowManager;

        import com.example.majkiel.jumpproject.Game.GamePanel;

public class MainActivity extends Activity {

    boolean isAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //turn title off
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //set to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(new GamePanel(this));
    }


    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }


}
