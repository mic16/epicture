package com.example.epicture.ui.slideshow;

import android.content.Context;
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
import com.example.epicture.GalleryManager;
import com.example.epicture.ItemClickSupport;
import com.example.epicture.R;
import com.example.epicture.ui.imageOpen.ImageOpen;
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

    private RecyclerView recyclerView;
    private ImageAdapter mAdapter;
    private TextInputLayout mediaSearchInput;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.my_recycler_view);

        mAdapter = new ImageAdapter();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(root.getContext(), 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        mAdapter.manager.home();

        mediaSearchInput = (TextInputLayout) root.findViewById(R.id.mediaSearch);
        Button selectMedia = root.findViewById(R.id.mediaSearchButton);
        selectMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchMedia();
            }
        });

        ItemClickSupport.addTo(recyclerView, R.layout.fragment_slideshow)
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

    private void searchMedia() {
        String query = mediaSearchInput.getEditText().getText().toString();
        mAdapter.manager.searchGallery(query);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}