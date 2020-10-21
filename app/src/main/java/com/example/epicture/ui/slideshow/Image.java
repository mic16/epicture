package com.example.epicture.ui.slideshow;

import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.ImageView;

import net.azzerial.jmgur.api.entities.GalleryImage;

import java.io.Serializable;

public class Image implements Serializable {
    private String title, imageUrl, hash, description;
    private int upVote, downVote, like, nbView;

    public Image() {
    }

    public Image(GalleryImage img) {
        this.imageUrl = img.getUrl();
        this.title = img.getTitle();
        this.hash = img.getHash();
        this.nbView = img.getViews();
        this.description = img.getDescription();
        this.upVote = img.getUps();
        this.downVote = img.getDowns();
        this.like = img.getFavoriteCount();
    }

    public Image(String imageUrl, String title, String description ,String hash, int nbView, int upVote, int downVote, int like) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.hash = hash;
        this.nbView = nbView;
        this.description = description;
        this.upVote = upVote;
        this.downVote = downVote;
        this.like = like;
    }

    private String format(int nb) {
        String res = Integer.toString(nb);
        if (res.length() > 3) {
            return (res.substring(0, res.length() - 3) + "k");
        }
        return res;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String img) {
        this.imageUrl = img;
    }

    public String getHash() {
        return  hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUpVote() {
        return format(this.upVote);
    }

    public void setUpVote(int upVote) {
        this.upVote = upVote;
    }

    public String getDownVote() {
        return format(this.downVote);
    }

    public void setDownVote(int downVote) {
        this.downVote = downVote;
    }

    public String getLike() {
        return format(this.like);
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getNbView() {
        return format(this.nbView);
    }

    public void setNbView(int nbView) {
        this.nbView = nbView;
    }
}