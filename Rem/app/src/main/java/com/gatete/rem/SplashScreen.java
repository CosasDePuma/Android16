package com.gatete.rem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        nextActivity(VoiceRecognition.class);
    }

    private void nextActivity(Class newActivity)
    {
        Intent intent = new Intent(SplashScreen.this, newActivity);
        startActivity(intent);
        finish();
    }
}
