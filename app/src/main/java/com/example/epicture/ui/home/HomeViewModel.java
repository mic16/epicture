package com.example.epicture.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    final MutableLiveData<String> favourite;
    final MutableLiveData<String> popular;

    public HomeViewModel() {
        favourite = new MutableLiveData<>();
        favourite.setValue("Favourites");
        popular = new MutableLiveData<>();
        popular.setValue("Popular");
    }

    public LiveData<String> getTextFavourite() {
        return favourite;
    }
    public LiveData<String> getTextPopular() {
        return popular;
    }

}