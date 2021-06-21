package com.example.cropconnoisseur.Utils;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.cropconnoisseur.R;
import com.example.cropconnoisseur.ZoomImage;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageFullScreenActivity extends AppCompatActivity {

    private ZoomImage imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_full_screen);

        imageView = findViewById(R.id.image);
        Intent intent = getIntent();
        if(intent != null){
            String imageUrl = intent.getStringExtra("imageUrl");
            LoadImage loadImage = new LoadImage();
            loadImage.execute(imageUrl);

        }
    }

    public class LoadImage extends AsyncTask<String, String, Bitmap>{
        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                Bitmap bitmap = null;
                URL url = new URL(strings[0]);
                bitmap = BitmapFactory.decodeStream((InputStream)url.getContent());

                return bitmap;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
            super.onPostExecute(bitmap);
        }
    }


}