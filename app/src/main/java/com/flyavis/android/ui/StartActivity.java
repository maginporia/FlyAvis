package com.flyavis.android.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent activityIntent;
        activityIntent = new Intent(this, MainActivity.class);
        startActivity(activityIntent);
        finish();
    }
}
