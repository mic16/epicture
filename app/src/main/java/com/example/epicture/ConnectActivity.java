package com.example.epicture;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import net.azzerial.jmgur.api.JmgurBuilder;
import net.azzerial.jmgur.api.OAuth2;

public class ConnectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        Log.i(ApiData.TAG, "Opening to OAuth2 login");

        final FrameLayout root = new FrameLayout(this);
        final WebView webView = new WebView(this);
        final String oauthUrl = "https://api.imgur.com/oauth2/authorize?client_id=" + ApiData.CLIENT_ID + "&response_type=token";

        root.addView(webView);
        setContentView(root);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                final String url = request.getUrl().toString();

                Log.i(ApiData.TAG, "Redirected WebView on: " + url);

                try {
                    final OAuth2 oauth = OAuth2.fromUrl(url);

                    ApiData.api = JmgurBuilder
                            .of(ApiData.CLIENT_ID)
                            .setOAuth(oauth)
                            .build();
                    finish();
                } catch (Exception e) {
                    Log.i(ApiData.TAG, e.getMessage());
                    finish();
                    return false;
                }

                ApiData.api.ACCOUNT.getSelfAccount().queue(
                        account -> Log.i(ApiData.TAG, "Logged in account: " + account),
                        Throwable::printStackTrace
                );
                return true;
            }
        });

        webView.loadUrl(oauthUrl);
        Log.i(ApiData.TAG, "Opened WebView on: " + oauthUrl);
    }
}