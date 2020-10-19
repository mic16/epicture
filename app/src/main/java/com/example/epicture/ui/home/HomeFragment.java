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

import com.example.epicture.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    public String test = "test";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.Favourites);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        LinearLayout gallery = root.findViewById(R.id.gallery);

        for (int i = 0; i < 6; i++) {

            View galleryView = inflater.inflate(R.layout.gallery_item, gallery, false);

            ImageView imageView = galleryView.findViewById(R.id.imageView);
            //imageView.setImageResource(R.drawable.ic_launcher_background);

            gallery.addView(galleryView);
        }

        return root;
    }
}