package com.example.epicture;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent2 = new Intent(this, User.class);
        startActivity(intent2);
        //Intent intent = new Intent(this, ConnectActivity.class);
        //startActivity(intent);
    }
}