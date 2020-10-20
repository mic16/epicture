package com.example.epicture.ui.home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.epicture.ApiData;
import com.example.epicture.R;
import com.example.epicture.ui.slideshow.Image;
import com.example.epicture.ui.slideshow.ImageAdapter;

import net.azzerial.jmgur.api.entities.GalleryAlbum;
import net.azzerial.jmgur.api.entities.GalleryElement;
import net.azzerial.jmgur.api.entities.GalleryImage;
import net.azzerial.jmgur.api.requests.restaction.PagedRestAction;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class HomeFragment extends Fragment {

    private List<Image> imageList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ImageAdapter mAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.favoriteScroll);

        mAdapter = new ImageAdapter(imageList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(new GridLayoutManager(root.getContext(), 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        CompletableFuture.runAsync(() -> {
            ApiData.api.ACCOUNT.getSelfFavorites().get().submit().thenAcceptAsync(
                    img -> {
                        for (int k = 0; k < img.size(); k++) {
                            if (img.get(k) instanceof GalleryAlbum) {
                                GalleryAlbum gAlbum = (GalleryAlbum) img.get(k);
                                List<GalleryImage> gImage = gAlbum.getImages();
                                for (int i = 0; i < gImage.size(); i++) {
                                    GalleryImage image = gImage.get(i);
                                    Image j = new Image(image.getUrl(), image.getTitle(), Integer.toString(image.getScore()));
                                    imageList.add(j);
                                }
                            } else if (img.get(k) instanceof GalleryImage) {
                                GalleryImage gImage = (GalleryImage) img.get(k);
                                GalleryImage image = gImage;
                                System.out.println(image.getTitle());
                                Image j = new Image(image.getUrl(), image.getTitle(), Integer.toString(image.getScore()));
                                imageList.add(j);
                            }
                        }
                    }
            ).exceptionally(e -> {
                e.printStackTrace();
                return null;
            }).join();
        }).thenRunAsync(() -> {
            mAdapter.notifyDataSetChanged();
        });
        return root;
    }
}