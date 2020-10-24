package com.example.epicture;

import android.os.Handler;
import android.util.Log;

import androidx.annotation.UiThread;

import com.example.epicture.ApiData;

import net.azzerial.jmgur.api.Jmgur;
import net.azzerial.jmgur.api.entities.GalleryAlbum;
import net.azzerial.jmgur.api.entities.GalleryElement;
import net.azzerial.jmgur.api.entities.GalleryImage;
import net.azzerial.jmgur.api.entities.GalleryProfile;

import net.azzerial.jmgur.api.entities.dto.GalleryDTO;
import net.azzerial.jmgur.api.entities.subentities.FavoriteSort;
import net.azzerial.jmgur.api.entities.subentities.GallerySort;

import net.azzerial.jmgur.api.requests.restaction.PagedRestAction;
import net.azzerial.jmgur.internal.entities.GalleryAlbumImpl;
import net.azzerial.jmgur.internal.entities.GalleryDTOImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

import fr.shiranuit.ImgurRequest.ImgurRequest;

public class GalleryManager {

    private Jmgur jmgur = null;
    private PagedRestAction<List<GalleryElement>> pages;

    private AtomicBoolean loading = new AtomicBoolean(false);

    private CompletableFuture<Void> lastRequest;

    private List<GalleryElement> gallery = new ArrayList<>();

    private CompletableFuture<Void> onReady;

    private Runnable syncRunnable;
    private Handler handler;
    private AtomicBoolean endOfPages = new AtomicBoolean(false);
    GalleryDTO dto;

    public GalleryManager() {
        handler = new Handler();
        dto = GalleryDTO.create();
        onReady = ApiData.api.thenRunAsync(() -> {
            jmgur = ApiData.getApi();
            pages = ImgurRequest.GALLERY.getGallery(dto);
            endOfPages.set(false);
        });
    }

    public CompletableFuture<Void> frontPage() {
        onReady = ApiData.api.thenRunAsync(() -> {
            gallery.clear();
            jmgur = ApiData.getApi();
            pages = ImgurRequest.GALLERY.getGallery(dto);
            endOfPages.set(false);
        });
        return nextPage();
    }

    public void setOnSyncNeeded(Runnable runnable) {
        syncRunnable = runnable;
    }

    public boolean isNextPageReady() {
        return lastRequest != null && lastRequest.isDone();
    }

    public CompletableFuture<Void> nextPage() {
        return CompletableFuture.runAsync(() -> {
            if (endOfPages.get()) {
                return;
            }
            ApiData.api.join();
            onReady.join();

            if (!loading.get()) {
                loading.set(true);
                lastRequest = pages.next().submit().thenAcceptAsync((retrievedElements) -> {
                    gallery.addAll(retrievedElements);
                    loading.set(false);
                    sync();
                    if (retrievedElements.size() == 0) {
                        endOfPages.set(true);
                    }
                }).exceptionally(throwable -> {
                    gallery.clear();
                    loading.set(false);
                    sync();
                    endOfPages.set(true);
                    throwable.printStackTrace();
                    return null;
                });
            }
            lastRequest.join();
        });
    }

    public CompletableFuture<Void> searchGallery(String text) {
        onReady = ApiData.api.thenRunAsync(() -> {
            gallery.clear();
            sync();
            jmgur = ApiData.getApi();
            pages = jmgur.GALLERY.searchGallery(text);
            endOfPages.set(false);
        });
        return nextPage();
    }

    public void clear() {
        gallery.clear();
        sync();
        onReady = ApiData.api.thenRunAsync(() -> {
            jmgur = ApiData.getApi();
            pages = jmgur.GALLERY.getGallery(dto);
            endOfPages.set(false);
        });
    }

    private void sync() {
        if (syncRunnable != null) {
            handler.post(syncRunnable);
        }
    }

    public List<GalleryElement> getGallery() {
        return this.gallery;
    }

    public CompletableFuture<Void> loadSelfFavorites(FavoriteSort sort) {
        onReady = ApiData.api.thenRunAsync(() -> {
            gallery.clear();
            sync();
            jmgur = ApiData.getApi();
            pages = jmgur.ACCOUNT.getSelfGalleryFavorites(sort);
            endOfPages.set(false);
        });
        return nextPage();
    }

    public CompletableFuture<Void> loadSelfAccount() {
        onReady = ApiData.api.thenRunAsync(() -> {
            gallery.clear();
            sync();
            jmgur = ApiData.getApi();
            pages = jmgur.ACCOUNT.getUserSubmissions();
            endOfPages.set(false);
        });
        return nextPage();
    }

    public CompletableFuture<Void> loadSelfFavorites() {
        return loadSelfFavorites(FavoriteSort.NEWEST);
    }

    public void updateDTO(GalleryDTO dto) {
        if (dto == null) {
            this.dto = GalleryDTO.create();
        }
        this.dto = dto;
        frontPage();
    }

    public static List<GalleryImage> getImagesFrom(GalleryElement element) {
        if  (element instanceof GalleryImage) {
            GalleryImage image = (GalleryImage)element;
            ArrayList<GalleryImage> images = new ArrayList<>();
            images.add(image);
            return images;
        }

        if  (element instanceof GalleryAlbum) {
            GalleryAlbum image = (GalleryAlbum)element;
            return image.getImages();
        }

        return null;
    }

}