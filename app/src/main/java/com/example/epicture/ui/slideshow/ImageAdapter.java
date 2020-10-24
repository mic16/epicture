package com.example.epicture.ui.slideshow;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.epicture.GalleryManager;
import com.example.epicture.R;

import net.azzerial.jmgur.api.entities.GalleryElement;
import net.azzerial.jmgur.api.entities.GalleryImage;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {
    public GalleryManager manager = new GalleryManager();
    private Handler handler = new Handler();


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView view;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.imageViewScroll);
            this.view = (TextView) view.findViewById(R.id.viewScroll);
        }
    }


    public ImageAdapter() {
        manager = new GalleryManager();
        manager.setOnsyncNeeded(() -> {
            notifyDataSetChanged();
        });
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (position >= manager.getGallery().size()) {
            Glide
                    .with(holder.itemView.getContext())
                    .load(R.drawable.ic_baseline_refresh_24)
                    .into(holder.image);
        } else {
            GalleryElement element = manager.getGallery().get(position);
            List<GalleryImage> images = GalleryManager.getImagesFrom(element);
            GalleryImage image = images.get(0);
            Glide
                    .with(holder.itemView.getContext())
                    .load(image.getUrl())
                    .into(holder.image);
            holder.view.setText(Image.format(element.getViews()));
        }
    }

    @Override
    public int getItemCount() { return (manager.getGallery().size()); }
}