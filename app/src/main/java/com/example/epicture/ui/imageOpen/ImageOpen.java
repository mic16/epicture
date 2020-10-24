package com.example.epicture.ui.imageOpen;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.epicture.ApiData;
import com.example.epicture.R;
import com.example.epicture.ui.slideshow.Image;

import net.azzerial.jmgur.api.entities.GalleryAlbum;
import net.azzerial.jmgur.api.entities.GalleryElement;
import net.azzerial.jmgur.api.entities.subentities.Vote;
import net.azzerial.jmgur.api.requests.restaction.RestAction;

import java.util.List;

import fr.shiranuit.ImgurRequest.ImgurRequest;

public class ImageOpen extends AppCompatActivity {

    private Image image;
    private  Vote vote;
    private RestAction<List<GalleryElement>> favorite;
    private ImageView favView, upVote, downVote;
    private TextView pageNb;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_open);
        image = (Image)getIntent().getSerializableExtra("Image");
        favView = (ImageView)findViewById(R.id.likeFullScreen);
        upVote = (ImageView)findViewById(R.id.upVoteDisplay);
        downVote = (ImageView)findViewById(R.id.downVoteDisplay);
        count = 0;

        if (image.getIsFavorite()) {
            favView.setImageResource(R.drawable.ic_baseline_favorite_24);
        }

        ImgurRequest.GALLERY.getGalleryAlbum(image.getFavoriteHash()).queue(album -> {
            this.vote = album.getVote();
            if (vote == Vote.UP) {
                upVote.setColorFilter(Color.GREEN);
            } else if(vote == Vote.DOWN) {
                downVote.setColorFilter(Color.RED);
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
        
        TextView author = (TextView)findViewById(R.id.authorName);
        author.setText(image.getAuthor());

        TextView creationDate = (TextView)findViewById(R.id.creationDate);
        creationDate.setText(image.getCreationDate());
    }

    public void upImage(View view) {
        if (vote == Vote.UP) {
            vote = Vote.NONE;
            upVote.setColorFilter(Color.GRAY);
        } else if(vote == Vote.DOWN) {
            vote = Vote.UP;
            downVote.setColorFilter(Color.GRAY);
            upVote.setColorFilter(Color.GREEN);
        } else {
            vote = Vote.UP;
            upVote.setColorFilter(Color.GREEN);
        }
        ApiData.getApi().GALLERY.updateGalleryPostVote(image.getFavoriteHash(), vote).queue();
    }

    public void downImage(View view) {
        if (vote == Vote.DOWN) {
            vote = Vote.NONE;
            downVote.setColorFilter(Color.GRAY);
        } else if(vote == Vote.UP) {
            vote = Vote.DOWN;
            upVote.setColorFilter(Color.GRAY);
            downVote.setColorFilter(Color.RED);
        } else {
            vote = Vote.DOWN;
            downVote.setColorFilter(Color.RED);
        }
        ApiData.getApi().GALLERY.updateGalleryPostVote(image.getFavoriteHash(), vote).queue();
    }

    public void favImage(View view) {
        if (this.image.getIsAlbum()) {
            ImgurRequest.ALBUM.favoriteAlbum(this.image.getFavoriteHash()).queue(bool -> {
                if (bool) {
                    favView.setImageResource(R.drawable.ic_baseline_favorite_24);
                } else {
                    favView.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                }
            });
        }
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
