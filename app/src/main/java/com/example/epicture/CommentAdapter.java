package com.example.epicture;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private List<UserComment> commentDataset;

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        public TextView author;
        public TextView comment;

        public CommentViewHolder(View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.AuthorCommentName);
            comment = itemView.findViewById(R.id.Comment);
        }
    }

    public CommentAdapter(List<UserComment> commentDataset) {
        this.commentDataset = commentDataset;
    }

    @Override
    public CommentAdapter.CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_comment_item, parent, false);

        CommentViewHolder vh = new CommentViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        holder.author.setText(commentDataset.get(position).getAuthorName());
        holder.comment.setText(commentDataset.get(position).getComment());
    }

    @Override
    public int getItemCount() {
        return commentDataset.size();
    }
}