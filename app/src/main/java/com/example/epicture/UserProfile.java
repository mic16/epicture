package com.example.epicture;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class UserProfile extends Fragment {


    private UserProfileViewModel userProfileViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        Log.d(ApiData.TAG, "onCreateView: 1");
        userProfileViewModel =
                ViewModelProviders.of(this).get(UserProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_user_profile, container, false);
        final TextView FavouriteTextView = root.findViewById(R.id.Favourites);
        userProfileViewModel.getTextFavourite().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                FavouriteTextView.setText(s);
            }
        });
        final TextView popularTextView = root.findViewById(R.id.UserPosts);
        userProfileViewModel.getTextPopular().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                popularTextView.setText(s);
            }
        });

        //LinearLayout gallery = root.findViewById(R.id.gallery);

        //for (int i = 0; i < 6; i++) {

        //View galleryView = inflater.inflate(R.layout.gallery_item, gallery, false);

        //ImageView favoriteImage  = (ImageView)galleryView.findViewById(R.id.galleryItem);
        //ApiData.api.ACCOUNT.getSelfAccount().queue(account -> {
        //        Bitmap img = Utils.getBitmapImageFromUrl(ApiData.api.IMAGE.getImage(ApiData.api.ACCOUNT.getSelfFavorites().get().complete().get(0).getHash()).complete().getUrl());
        //        if (img != null) {
        //            userPictureView.setImageBitmap(img);
        //        }
        //    });
        //ApiData.api.ACCOUNT.getSelfFavorites().get().complete().get(0).getHash()).complete().getUrl()
        //Log.d(ApiData.TAG, ApiData.api.ACCOUNT.getSelfFavorites().get().complete().get(0).getHash());
        //Glide.with(this).load(ApiData.api.IMAGE.getImage(ApiData.api.ACCOUNT.getSelfGalleryFavorites().get().complete().get(0).getHash())).into(favoriteImage);
        //gallery.addView(galleryView);
        //}

        return root;
    }
}