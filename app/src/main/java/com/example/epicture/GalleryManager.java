package com.example.epicture;


import net.azzerial.jmgur.api.Jmgur;
import net.azzerial.jmgur.api.entities.GalleryAlbum;
import net.azzerial.jmgur.api.entities.GalleryElement;
import net.azzerial.jmgur.api.entities.GalleryImage;
import net.azzerial.jmgur.api.requests.restaction.PagedRestAction;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Handler;

public class GalleryManager {

    private Jmgur jmgur = null;
    private PagedRestAction<List<GalleryElement>> pages;

    private AtomicBoolean loading = new AtomicBoolean(false);

    private CompletableFuture<Void> lastRequest;

    private List<GalleryElement> gallery = new ArrayList<>();

    private CompletableFuture<Void> onReady;

    private Runnable syncRunnable;
    private android.os.Handler handler = new android.os.Handler();

    public GalleryManager() {

    }

    public void home() {
        onReady = ApiData.api.thenRunAsync(() -> {
            jmgur = ApiData.getApi();
            pages = jmgur.GALLERY.getGallery();
            nextPage();
        });
    }

    public boolean isNextPageReady() {
        return lastRequest != null && lastRequest.isDone();
    }

    public CompletableFuture<Void> nextPage() {
        return CompletableFuture.runAsync(() -> {

            ApiData.api.join();
            onReady.join();

            if (!loading.get()) {
                loading.set(true);
                lastRequest = pages.next().submit().thenAcceptAsync((retrievedElements) -> {
                    gallery.addAll(retrievedElements);
                    loading.set(false);
                    sync();
                }).exceptionally(throwable -> {
                    gallery.clear();
                    loading.set(false);
                    sync();
                    return null;
                });
            }
            lastRequest.join();

        });
    }

    private void sync() {
        if (syncRunnable != null) {
            handler.post(syncRunnable);
        }
    }

    public void setOnsyncNeeded(Runnable runnable) {
        this.syncRunnable = runnable;
    }
    public CompletableFuture<Void> searchGallery(String text) {
        onReady = ApiData.api.thenRunAsync(() -> {
            gallery.clear();
            sync();
            jmgur = ApiData.getApi();
            pages = jmgur.GALLERY.searchGallery(text);
        });
        return nextPage();
    }

    public void favorites() {
        onReady = ApiData.api.thenRunAsync(() -> {
            jmgur = ApiData.getApi();
            pages = jmgur.ACCOUNT.getSelfFavorites();
            gallery.clear();
            nextPage();
        });
    }

    public void clear() {
        gallery.clear();
        sync();
        onReady = ApiData.api.thenRunAsync(() -> {
            jmgur = ApiData.getApi();
            pages = jmgur.GALLERY.getGallery();
        });
    }

    public List<GalleryElement> getGallery() {
        return this.gallery;
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