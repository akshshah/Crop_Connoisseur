package com.example.cropconnoisseur;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cropconnoisseur.Classifier.Classifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TomatoActivity extends AppCompatActivity {

    private ImageView imageView;
    private ListView listView;
    private Button button;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1000;
    private static final int CAMERA_REQEUST_CODE = 10001;
    private Classifier imageClassifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tomato);

        try {
            initializeUIElements();
        } catch (IOException e) {
            e.printStackTrace();
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasPermission()) {
                    openCamera();
                } else {
                    requestPermission();
                }
            }
        });
    }

    private void initializeUIElements() throws IOException {
        imageView = findViewById(R.id.imageView);
        listView = findViewById(R.id.listView);
        button = findViewById(R.id.button);
        /*
         * Creating an instance of our tensor image classifier
         */
        try {
            imageClassifier = new Classifier(this,"tomato");
        } catch (IOException e) {
            Log.e("Image Classifier Error", "ERROR: " + e);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == CAMERA_REQEUST_CODE) {
            Bitmap photo = (Bitmap) Objects.requireNonNull(Objects.requireNonNull(data).getExtras()).get("data");
            imageView.setImageBitmap(photo);
            List<Classifier.Recognition> predicitons = imageClassifier.recognizeImage(
                    photo, 0);
            // creating a list of string to display in list view
            final List<String> predicitonsList = new ArrayList<>();
            for (Classifier.Recognition recog : predicitons) {
                predicitonsList.add(recog.getName() + "  ::::::::::  " + recog.getConfidence());
            }
            // creating an array adapter to display the classification result in list view
            ArrayAdapter<String> predictionsAdapter = new ArrayAdapter<>(
                    this, R.layout.support_simple_spinner_dropdown_item, predicitonsList);
            listView.setAdapter(predictionsAdapter);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (hasAllPermissions(grantResults)) {
                openCamera();
            } else {
                requestPermission();
            }
        }
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // whether permission can be requested or on not
            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                Toast.makeText(this, "Camera Permission Required", Toast.LENGTH_SHORT).show();
            }
            // request the camera permission permission
            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        }
    }


    private boolean hasAllPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED)
                return false;
        }
        return true;
    }

    private boolean hasPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQEUST_CODE);
    }
}