package com.example.epicture;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.net.URL;

public class Utils {

    static public Bitmap getBitmapImageFromUrl(String url) {
        try {
            URL imgUrl = new URL(url);
            Bitmap bmp = BitmapFactory.decodeStream(imgUrl.openConnection().getInputStream());
            return bmp;
        } catch (Exception e) {
            return null;
        }
    }
}
