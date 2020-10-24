package com.example.epicture.ui.slideshow;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;

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
import net.azzerial.jmgur.api.entities.dto.GalleryDTO;
import net.azzerial.jmgur.api.entities.dto.GallerySearchDTO;
import net.azzerial.jmgur.api.entities.subentities.GallerySort;
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
    private Switch nsfwSwitch;
    private Spinner sortSpinner;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.my_recycler_view);
        nsfwSwitch = (Switch) root.findViewById(R.id.NSFWSwitch);
        sortSpinner = (Spinner) root.findViewById(R.id.spinnerSort);
        mAdapter = new ImageAdapter();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(root.getContext(), 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        mediaSearchInput = (TextInputLayout) root.findViewById(R.id.mediaSearch);
        ImageButton selectMedia = root.findViewById(R.id.mediaSearchButton);


        mediaSearchInput.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    selectMedia.performClick();
                    mediaSearchInput.getEditText().setText("");
                }
                return false;
            }
        });

        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchMedia();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        nsfwSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                searchMedia();
            }
        });

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
        mAdapter.manager.frontPage();
        return root;
    }

    private void searchMedia() {
        GalleryDTO gallery = GalleryDTO.create();

        switch (sortSpinner.getSelectedItemPosition()) {
            case 0:
                gallery.setSort(GallerySort.VIRAL);
                break;
            case 1:
                gallery.setSort(GallerySort.TOP);
                break;
            case 2:
                gallery.setSort(GallerySort.TIME);
                break;
            case 3:
                gallery.setSort(GallerySort.RISING);
                break;
        }
        gallery.showNSFW(nsfwSwitch.isEnabled());
        mAdapter.manager.updateDTO(gallery);
        String query = mediaSearchInput.getEditText().getText().toString();
        if (query.isEmpty()) {
            mAdapter.manager.frontPage();
        } else {
            mAdapter.manager.searchGallery(query);
        }
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