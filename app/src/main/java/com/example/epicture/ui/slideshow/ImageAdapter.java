package com.example.epicture.ui.slideshow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.epicture.R;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {

    private List<Image> imageList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView year, genre;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.imageViewScroll);
            genre = (TextView) view.findViewById(R.id.titleScroll);
            year = (TextView) view.findViewById(R.id.upVoteScroll);
        }
    }


    public ImageAdapter(List<Image> moviesList) {
        this.imageList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (position >= imageList.size()) {
            Glide
                    .with(holder.itemView.getContext())
                    .load(R.drawable.ic_baseline_refresh_24)
                    .into(holder.image);
        } else {
            Image image = imageList.get(position);
            Glide
                    .with(holder.itemView.getContext())
                    .load(image.getImage())
                    .into(holder.image);
            holder.genre.setText(image.getGenre());
            holder.year.setText(image.getYear());
        }
    }

    @Override
    public int getItemCount() { return (Math.max(imageList.size(), 50)); }
}