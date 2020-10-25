package com.example.epicture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.epicture.ui.slideshow.Image;

import java.util.ArrayList;
import java.util.List;

public class PostComments extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_comments);
        String hash = (String)getIntent().getSerializableExtra("hash");

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
}