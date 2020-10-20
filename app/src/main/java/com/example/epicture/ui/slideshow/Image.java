package com.example.epicture.ui.slideshow;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class Image {
    private String genre, year, imageUrl, hash;

    public Image() {
    }

    public Image(String image, String genre, String year, String hash) {
        this.imageUrl = image;
        this.genre = genre;
        this.year = year;
        this.hash = hash;
    }

    public  String getHash() { return  hash;}
    public String getImage() {
        return imageUrl;
    }

    public void setImage(String img) {
        this.imageUrl = img;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}