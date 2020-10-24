package com.example.epicture.ui.home;

import android.content.Context;
import android.content.Intent;
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
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

<<<<<<< HEAD
import com.example.epicture.ApiData;
=======
import com.bumptech.glide.Glide;
import com.example.epicture.ApiData;
import com.example.epicture.GalleryManager;
import com.example.epicture.ItemClickSupport;
>>>>>>> cyril
import com.example.epicture.R;
import com.example.epicture.ui.imageOpen.ImageOpen;
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

<<<<<<< HEAD
    private HomeViewModel homeViewModel;
=======
    private RecyclerView recyclerView;
    private ImageAdapter mAdapter;
>>>>>>> cyril

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
<<<<<<< HEAD
        final TextView FavouriteTextView = root.findViewById(R.id.Favourites);
        homeViewModel.getTextFavourite().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                FavouriteTextView.setText(s);
            }
        });
        final TextView popularTextView = root.findViewById(R.id.Popular);
        homeViewModel.getTextPopular().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                popularTextView.setText(s);
            }
        });

        LinearLayout gallery = root.findViewById(R.id.FavouriteGallery);

        for (int i = 0; i < 6; i++) {
=======
>>>>>>> cyril

        recyclerView = (RecyclerView) root.findViewById(R.id.favoriteScroll);

<<<<<<< HEAD
            ImageView favoriteImage  = (ImageView)galleryView.findViewById(R.id.galleryItem);
            //ApiData.api.ACCOUNT.getSelfAccount().queue(account -> {
            //        Bitmap img = Utils.getBitmapImageFromUrl(ApiData.api.IMAGE.getImage(ApiData.api.ACCOUNT.getSelfFavorites().get().complete().get(0).getHash()).complete().getUrl());
            //        if (img != null) {
            //            userPictureView.setImageBitmap(img);
            //        }
            //    });
            ApiData.api.ACCOUNT.getSelfGalleryFavorites().get(0).queue(img -> {
                img.get(0);
            });
            //Glide.with(this).load(ApiData.api.IMAGE.getImage(ApiData.api.ACCOUNT.getSelfGalleryFavorites().get().complete().get(0).getHash())).into(favoriteImage);
            gallery.addView(galleryView);
        }
=======
        mAdapter = new ImageAdapter();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(root.getContext(), 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        mAdapter.manager.favorites();
        ItemClickSupport.addTo(recyclerView, R.layout.fragment_home)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Intent intent = new Intent(v.getContext(), ImageOpen.class);
                        Image img = new Image(mAdapter.manager.getGallery().get(position));
                        intent.putExtra("Image", img);
                        startActivity(intent);
                    }
                });
        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
>>>>>>> cyril

    @Override
    public void onDetach() {
        super.onDetach();
    }
}