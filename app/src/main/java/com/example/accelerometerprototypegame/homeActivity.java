package com.example.accelerometerprototypegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

public class homeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
    public void start(View view)
    {
        Intent A2 = new Intent (view.getContext(), MainActivity.class);
        startActivity(A2);
    }

    public void startLeaderboard(View view)
    {
        Intent A2 = new Intent (view.getContext(), HighscoreActivity.class);
        startActivity(A2);
    }
}