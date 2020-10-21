package com.example.epicture;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class UserSettings extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        TextView userNameView = findViewById(R.id.userProfilName);
        TextView userPTSView = findViewById(R.id.userProfilPTS);
        ImageView userPictureView  = findViewById(R.id.userProfilPicture);
        ApiData.getApi().ACCOUNT.getSelfAccount().queue(account -> {
            Bitmap img = Utils.getBitmapImageFromUrl(account.getAvatar().getUrl());
            if (img != null){
                userPictureView.setImageBitmap(img);
            }
            userNameView.setText(account.getName());
            userPTSView.setText("PTS : " + account.getReputationScore());
        });
        ApiData.getApi().ACCOUNT.getSelfAccount().queue(account -> {
            userNameView.setText(account.getName());
            userPTSView.setText("PTS : " + account.getReputationScore());
        });
        ApiData.getApi().ACCOUNT.getBlockedUserNames().queue(account -> {
            recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_blocked_users);

            //recyclerView.setHasFixedSize(true);

            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

            mAdapter = new MyAdapter(account);
            recyclerView.setAdapter(mAdapter);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mAdapter.notifyDataSetChanged();
    }
}