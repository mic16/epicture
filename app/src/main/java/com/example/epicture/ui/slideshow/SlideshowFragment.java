package com.example.epicture.ui.slideshow;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.epicture.ApiData;
import com.example.epicture.R;

import net.azzerial.jmgur.api.entities.GalleryElement;
import net.azzerial.jmgur.api.requests.restaction.PagedRestAction;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SlideshowFragment extends Fragment {

    private List<Image> imageList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ImageAdapter mAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.my_recycler_view);


        mAdapter = new ImageAdapter(imageList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        test();
        return root;
    }

    private void test() {
        PagedRestAction<List<GalleryElement>> elem = ApiData.api.GALLERY.searchGallery("yolo");
        elem.get(0).queue(img -> {
            System.out.println( "--------------------> "+ img.get(0).getHash());
            System.out.println( "--------------------> " + img.get(0).getUrl());
            Bitmap bit = getBitmapImageFromUrl(img.get(0).getUrl());
            Image image = new Image(bit, "Action & Adventure", "2015");
            imageList.add(image);
        });
        //Image image = new Image("Mad Max: Fury Road", "Action & Adventure", "2015");
        //imageList.add(image);

        mAdapter.notifyDataSetChanged();
    }

    private Bitmap getBitmapImageFromUrl(String url) {
        try {
            URL imgUrl = new URL(url);
            Bitmap bmp = BitmapFactory.decodeStream(imgUrl.openConnection().getInputStream());
            return bmp;
        } catch (Exception e) {
            return null;
        }
    }
}