package com.example.epicture;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

public class User extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), PostMedia.class);
                startActivity(i);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
<<<<<<< HEAD
        View hView =  navigationView.getHeaderView(0);
        TextView userNameView = (TextView)hView.findViewById(R.id.userProfilName);
        TextView userPTSView = (TextView)hView.findViewById(R.id.userProfilPTS);
        ImageView userPictureView  = (ImageView)hView.findViewById(R.id.userProfilPicture);
        ApiData.api.ACCOUNT.getSelfAccount().queue(account -> {
            Bitmap img = Utils.getBitmapImageFromUrl(account.getAvatar().getUrl());
            if (img != null){
                userPictureView.setImageBitmap(img);
            }
            userNameView.setText(account.getName());
            userPTSView.setText("PTS : " + account.getReputationScore());
        });
        ApiData.api.ACCOUNT.getSelfAccount().queue(account -> {
            userNameView.setText(account.getName());
            userPTSView.setText("PTS : " + account.getReputationScore());
        });
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
=======
>>>>>>> cyril
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navigationView, navController);
        loadProfil();
    }

    public void loadProfil() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        TextView userNameView = (TextView)hView.findViewById(R.id.userProfilName);
        TextView userPTSView = (TextView)hView.findViewById(R.id.userProfilPTS);
        ImageView userPictureView  = (ImageView)hView.findViewById(R.id.userProfilPicture);
        ApiData.getApi().ACCOUNT.getSelfAccount().queue(account -> {
            //userNameView.setText(account.getName());
            //userPTSView.setText("PTS : " + account.getReputationScore());
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void userSettings(View view) {
<<<<<<< HEAD
        //Intent intent = new Intent(this, UserProfile.class);
        //startActivity(intent);
=======
        Intent intent = new Intent(this, UserSettings.class);
        startActivity(intent);
>>>>>>> cyril
    }
}