package com.example.epicture;

import net.azzerial.jmgur.api.Jmgur;

import java.util.concurrent.CompletableFuture;

import fr.shiranuit.ImgurRequest.ImgurRequest;

public class ApiData {
    public static final String CLIENT_ID = "95106cda05f6acb";
    public static final String TAG = "Epicture";
    public static CompletableFuture<Jmgur> api = new CompletableFuture();

    public static Jmgur getApi() {
        try {
            return api.get();
        } catch (Exception e) {

        }
        return null;
    }

    public static ImgurRequest request = new ImgurRequest(api);
}