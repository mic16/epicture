package com.example.epicture.ui.imageOpen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.epicture.ApiData;
import com.example.epicture.R;
import com.example.epicture.ui.slideshow.Image;

import net.azzerial.jmgur.api.entities.GalleryElement;
import net.azzerial.jmgur.api.requests.restaction.RestAction;

import java.util.List;

public class ImageOpen extends AppCompatActivity {

    private Image image;
    private RestAction<List<GalleryElement>> favorite;
    private ImageView favView;
    private TextView pageNb;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_open);
        image = (Image)getIntent().getSerializableExtra("Image");
        favorite = ApiData.getApi().ACCOUNT.getSelfFavorites().get();
        favView = (ImageView)findViewById(R.id.likeFullScreen);
        count = 0;
        favorite.queue(fav ->{
            for (int i = 0; i < fav.size(); i++) {
                 if (fav.get(i).getHash().compareTo(image.getFavoriteHash()) == 0) {
                    favView.setImageResource(R.drawable.ic_baseline_favorite_24);
                    break;
                }
            }
        });
        ImageView userPictureView = (ImageView)findViewById(R.id.imageViewFullScreen);
        Glide
                .with(this)
                .load(image.getImageUrl())
                .into(userPictureView);

        TextView title = (TextView)findViewById(R.id.titleFullScreen);
        title.setText(image.getTitle());

        TextView description = (TextView)findViewById(R.id.descriptionFullScreen);
        description.setMovementMethod(new ScrollingMovementMethod());
        description.setText(image.getDescription());

        TextView vu = (TextView)findViewById(R.id.nbViewFullScreen);
        vu.setText(image.getNbView());

        TextView up = (TextView)findViewById(R.id.nbUpFullscreen);
        up.setText(image.getUpVote());

        TextView down = (TextView)findViewById(R.id.nbDownFullscreen);
        down.setText(image.getDownVote());

        TextView like = (TextView)findViewById(R.id.nbLikeFullScreen);
        like.setText(image.getLike());

        pageNb = (TextView)findViewById(R.id.pageOnPage);
        pageNb.setText((count + 1) + "/" + image.getListImage().size());
    }

    public void favImage(View view) {
        ApiData.getApi().IMAGE.favoriteImage(this.image.getFavoriteHash()).queue(bool -> {
            if (bool) {
                favView.setImageResource(R.drawable.ic_baseline_favorite_24);
            } else {
                favView.setImageResource(R.drawable.ic_baseline_favorite_border_24);
            }
        });
    }

    public void nextFullScreen(View view) {
        if (count >= image.getListImage().size() - 1) {
            return;
        } else {
            ImageView userPictureView = (ImageView)findViewById(R.id.imageViewFullScreen);
            count++;
            Glide
                    .with(this)
                    .load(image.getListImage().get(count).getImageUrl())
                    .into(userPictureView);
        }
        pageNb.setText((count + 1) + "/" + image.getListImage().size());
    }

    public void backFullScreen(View view) {
        if (count <= 0) {
            return;
        } else {
            ImageView userPictureView = (ImageView)findViewById(R.id.imageViewFullScreen);
            count--;
            Glide
                    .with(this)
                    .load(image.getListImage().get(count).getImageUrl())
                    .into(userPictureView);
        }
        pageNb.setText((count + 1) + "/" + image.getListImage().size());
    }
}
