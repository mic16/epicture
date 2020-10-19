package com.example.epicture.ui.slideshow;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class Image {
    private String genre, year;
    private Bitmap image;

    public Image() {
    }

    public Image(Bitmap image, String genre, String year) {
        this.image = image;
        this.genre = genre;
        this.year = year;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap img) {
        this.image = img;
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