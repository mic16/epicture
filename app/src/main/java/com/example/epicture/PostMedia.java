package com.example.epicture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import net.azzerial.jmgur.api.entities.dto.ImageUploadDTO;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class PostMedia extends AppCompatActivity {


    private static int RESULT_LOAD_IMAGE = 1;
    private String path = "";

    private TextInputLayout mediaTitleInput;
    private TextInputLayout mediaDescriptionInput;

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_media);

        mediaTitleInput = (TextInputLayout) findViewById(R.id.mediaTitle);
        mediaDescriptionInput = (TextInputLayout) findViewById(R.id.mediaDescription);

        Button selectMedia = findViewById(R.id.selectMedia);
        selectMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
    }

    public String getPath() {
        return this.path;
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    private void uploadImage(String path, String title, String description)
    {
        final Bitmap bitmap = BitmapFactory.decodeFile(path);
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        final String base64 = Base64.getEncoder().encodeToString(baos.toByteArray());

        System.out.println(title + " : " + description + " : " + path);

        final ImageUploadDTO image = ImageUploadDTO.create()
                .base64(base64)
                .setTitle(title)
                .setDescription(description);

        ApiData.getApi().IMAGE.uploadImage(image).queue(
                System.out::println,
                Throwable::printStackTrace
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        verifyStoragePermissions(this);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            this.path = cursor.getString(columnIndex);
            cursor.close();
            ImageView imageView = (ImageView) findViewById(R.id.imageViewMedia);
            if (imageView != null)
                imageView.setImageBitmap(BitmapFactory.decodeFile(this.path));
        }
    }

    public void sendImage(View view) {
        String title = this.mediaTitleInput.getEditText().getText().toString();
        String description = this.mediaDescriptionInput.getEditText().getText().toString();
        if (!title.isEmpty() && !description.isEmpty() && !this.path.isEmpty()) {
            uploadImage(this.path, title, description);
            finish();
        }
    }
}