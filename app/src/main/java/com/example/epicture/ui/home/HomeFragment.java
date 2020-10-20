package com.example.epicture.ui.home;

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

import com.example.epicture.ApiData;
import com.example.epicture.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
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

            View galleryView = inflater.inflate(R.layout.gallery_item, gallery, false);

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

        return root;
    }
}