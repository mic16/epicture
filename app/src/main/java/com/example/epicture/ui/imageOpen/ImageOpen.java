package com.example.epicture.ui.imageOpen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_open);
        image = (Image)getIntent().getSerializableExtra("Image");
        favorite = ApiData.getApi().ACCOUNT.getSelfFavorites().get();
        favView = (ImageView)findViewById(R.id.likeFullScreen);

        favorite.queue(fav ->{
            for (int i = 0; i < fav.size(); i++) {
                 if (fav.get(i).getHash().compareTo(image.getHash()) == 0) {
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
        description.setText(image.getDescription());

        TextView vu = (TextView)findViewById(R.id.nbViewFullScreen);
        vu.setText(image.getNbView());

        TextView up = (TextView)findViewById(R.id.nbUpFullscreen);
        up.setText(image.getUpVote());

        TextView down = (TextView)findViewById(R.id.nbDownFullscreen);
        down.setText(image.getDownVote());

        TextView like = (TextView)findViewById(R.id.nbLikeFullScreen);
        like.setText(image.getLike());
    }

    public void favImage(View view) {
        ApiData.getApi().IMAGE.favoriteImage(this.image.getHash());
        System.out.println("-------------------ok");
    }
}
