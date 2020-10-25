package com.example.epicture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.epicture.ui.slideshow.Image;

import net.azzerial.jmgur.api.entities.subentities.Vote;

import java.util.ArrayList;
import java.util.List;

public class PostComments extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String hash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_comments);
        hash = (String)getIntent().getSerializableExtra("hash");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewPostComment);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ApiData.getApi().GALLERY.getGalleryPostComments(hash).queue(comments -> {
            ArrayList<UserComment> com = new ArrayList<UserComment>();

            for (int i = 0; i < comments.size(); i++) {
                com.add(new UserComment(comments.get(i)));
            }
            mAdapter = new CommentAdapter(com);
            recyclerView.setAdapter(mAdapter);
        });
    }

    public void postComment(View view) {
        Intent i = new Intent(this, PostUserComment.class);
        i.putExtra("hash", hash);
        startActivity(i);
    }
}