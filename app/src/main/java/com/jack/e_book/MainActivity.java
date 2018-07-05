package com.jack.e_book;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.jack.e_book.ui.BookHomeActivity;

public class MainActivity extends AppCompatActivity {
    //设置延迟时间
    private final int SKIP_DELAY_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

       /* Timer time = new Timer();
        TimerTask task = new TimerTask(){
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this,BookHomeActivity.class));
            }
        };
        time.schedule(task, SKIP_DELAY_TIME);*/

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this,BookHomeActivity.class));
                finish();
            }
        },SKIP_DELAY_TIME);
    }


}
