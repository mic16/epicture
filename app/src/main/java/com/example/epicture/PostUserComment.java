package com.example.epicture;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;

public class PostUserComment extends AppCompatActivity {

    private String hash;
    private TextInputLayout comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_user_comment);
        hash = (String)getIntent().getSerializableExtra("hash");
        comment = (TextInputLayout) findViewById(R.id.CommentInput);
    }

    public void post(View view) {
        ApiData.getApi().GALLERY.postCommentOnGalleryPost(hash, comment.getEditText().getText().toString()).queue();
        finish();
    }
}