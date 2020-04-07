package com.example.titanpjmm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class activity_second extends AppCompatActivity {

    public static final int BUTTON_REQUEST2 = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_second);

        Intent received_intent = getIntent();

        Button exit2 = findViewById(R.id.button);
        exit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent exit =
                        new Intent(getApplicationContext(),MainActivity.class);

                startActivityForResult(exit,BUTTON_REQUEST2);
                finish();

            }
        });


    }
}
