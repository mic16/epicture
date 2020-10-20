package com.example.epicture.ui.slideshow;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.epicture.ApiData;
import com.example.epicture.ItemClickSupport;
import com.example.epicture.R;
import com.google.android.material.textfield.TextInputLayout;

import net.azzerial.jmgur.api.entities.GalleryAlbum;
import net.azzerial.jmgur.api.entities.GalleryElement;
import net.azzerial.jmgur.api.entities.GalleryImage;
import net.azzerial.jmgur.api.entities.dto.GallerySearchDTO;
import net.azzerial.jmgur.api.requests.restaction.PagedRestAction;
import net.azzerial.jmgur.api.requests.restaction.RestAction;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SlideshowFragment extends Fragment {

    private List<Image> imageList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ImageAdapter mAdapter;
    private TextInputLayout mediaSearchInput;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.my_recycler_view);

        mAdapter = new ImageAdapter(imageList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(root.getContext(), 1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        mediaSearchInput = (TextInputLayout) root.findViewById(R.id.mediaSearch);
        Button selectMedia = root.findViewById(R.id.mediaSearchButton);
        selectMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageList.clear();
                searchMedia();
            }
        });

        ItemClickSupport.addTo(recyclerView, R.layout.fragment_slideshow)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        RestAction<Boolean> res = ApiData.api.IMAGE.favoriteImage(imageList.get(position).getHash());
                        res.queue(bool ->{
                            System.out.println(bool);
                        });
                        Log.e("TAG", "Position : "+position + ": " +imageList.get(position).getHash() );
                    }
                });

        return root;
    }

    private void searchMedia() {
        String query = mediaSearchInput.getEditText().getText().toString();
        CompletableFuture.runAsync(() -> {
            ApiData.api.GALLERY.searchGallery(query).get().submit().thenAcceptAsync(
                    img -> {
                        for (int k = 0; k < img.size(); k++) {
                            if (img.get(k) instanceof GalleryAlbum) {
                                GalleryAlbum gAlbum = (GalleryAlbum) img.get(k);
                                List<GalleryImage> gImage = gAlbum.getImages();
                                for (int i = 0; i < gImage.size(); i++) {
                                    GalleryImage image = gImage.get(i);
                                    Image j = new Image(image.getUrl(), image.getTitle(), Integer.toString(image.getScore()), image.getHash());
                                    imageList.add(j);
                                }
                            } else if (img.get(k) instanceof GalleryImage) {
                                GalleryImage gImage = (GalleryImage) img.get(k);
                                GalleryImage image = gImage;
                                System.out.println(image.getTitle());
                                Image j = new Image(image.getUrl(), image.getTitle(), Integer.toString(image.getScore()), image.getHash());
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
    }
}