package com.example.epicture;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserProfileViewModel extends ViewModel {
    final MutableLiveData<String> favourite;
    final MutableLiveData<String> userPosts;

    public UserProfileViewModel() {
        favourite = new MutableLiveData<>();
        favourite.setValue("Favourites");
        userPosts = new MutableLiveData<>();
        userPosts.setValue("Your Posts");
    }

    public LiveData<String> getTextFavourite() {
        return favourite;
    }
    public LiveData<String> getTextPopular() {
        return userPosts;
    }
}
