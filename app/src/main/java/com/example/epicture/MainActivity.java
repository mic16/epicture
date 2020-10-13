package com.example.epicture;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import net.azzerial.jmgur.api.Jmgur;
import net.azzerial.jmgur.api.JmgurBuilder;
import net.azzerial.jmgur.api.OAuth2;

public class MainActivity extends AppCompatActivity {

    private static final String CLIENT_ID = "95106cda05f6acb";
    private static final String TAG = "Epicture";

    public static Jmgur api = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "Opening to OAuth2 login");

        final FrameLayout root = new FrameLayout(this);
        final WebView webView = new WebView(this);
        final String oauthUrl = "https://api.imgur.com/oauth2/authorize?client_id=" + CLIENT_ID + "&response_type=token";

        root.addView(webView);
        setContentView(root);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                final String url = request.getUrl().toString();

                Log.i(TAG, "Redirected WebView on: " + url);

                try {
                    final OAuth2 oauth = OAuth2.fromUrl(url);

                    api = JmgurBuilder
                            .of(CLIENT_ID)
                            .setOAuth(oauth)
                            .build();
                } catch (Exception e) {
                    Log.i(TAG, e.getMessage());
                    return false;
                }

                api.ACCOUNT.getSelfAccount().queue(
                        account -> Log.i(TAG, "Logged in account: " + account),
                        Throwable::printStackTrace
                );
                return true;
            }
        });

        webView.loadUrl(oauthUrl);

        Log.i(TAG, "Opened WebView on: " + oauthUrl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}