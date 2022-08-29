package com.example.pdfviwer.stickynotes;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class SplaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread thread = new Thread(){
            @Override
            public void run() {
               try {
                   sleep(1500);
               }
               catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                   finally {
                   Intent intent = new Intent(SplaceActivity.this,Notes.class);
                   startActivity(intent);
                   finish();
               }

            }
        };thread.start();


    }
}