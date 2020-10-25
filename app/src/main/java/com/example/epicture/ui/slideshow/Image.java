package com.example.epicture.ui.slideshow;

import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.epicture.GalleryManager;

import net.azzerial.jmgur.api.entities.GalleryAlbum;
import net.azzerial.jmgur.api.entities.GalleryElement;
import net.azzerial.jmgur.api.entities.GalleryImage;
import net.azzerial.jmgur.api.entities.subentities.Vote;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import fr.shiranuit.ImgurRequest.ImgurRequest;

public class Image implements Serializable {
    private String title, imageUrl, hash, description, favoriteHash, author;
    private int upVote, downVote, like, nbView;
    private boolean isAlbum, isFavorite;
    private OffsetDateTime creationDate;

    private List<Image> listImage = new ArrayList<>();

    public Image() {
    }

    public Image(GalleryElement img) {
        List<GalleryImage> list = GalleryManager.getImagesFrom(img);
        isAlbum = false;

        GalleryImage galleryImage = list.get(0);

        if (img instanceof GalleryAlbum) {
            isAlbum = true;
        }
        this.isFavorite = img.isFavorite();
        this.favoriteHash = img.getHash();
        this.imageUrl = galleryImage.getUrl();
        this.title = img.getTitle();
        this.hash = galleryImage.getHash();
        this.nbView = img.getViews();
        this.description = galleryImage.getDescription();
        this.upVote = img.getUps();
        this.downVote = img.getDowns();
        this.like = img.getFavoriteCount();
        this.author = img.getAuthorName();
        this.creationDate = img.getCreationDate();
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
        this.author = img.getAuthorName();
        this.creationDate = img.getCreationDate();
    }
    
    static public String format(int nb) {
        String res = Integer.toString(nb);
        if (res.length() > 3) {
            return (res.substring(0, res.length() - 3) + "k");
        }
        return res;
    }

    public boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(Boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public boolean getIsAlbum() {
        return isAlbum;
    }

    public void setIsAlbum(Boolean isAlbum) {
        this.isAlbum = isAlbum;
    }

    public List<Image> getListImage() {
        return listImage;
    }

    public void setListImage(List<Image> listImage) {
        this.listImage = listImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFavoriteHash() {
        return favoriteHash;
    }

    public void setFavoriteHash(String favoriteHash) {
        this.favoriteHash = favoriteHash;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String img) {
        this.imageUrl = img;
    }

    public String getHash() {
        return hash;
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

    public String getAuthor() { return author; };

    public void setAuthor(String author) { this.author = author; };

    public String getCreationDate() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return fmt.format(creationDate);
    };

    public void setCreationDate(OffsetDateTime creationDate) { this.creationDate = creationDate; };
}