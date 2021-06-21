package com.example.cropconnoisseur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    public static final int SPLASH = 3500;
    private FirebaseUser firebaseUser;

    Animation bottomAnim,topAnim;
    private ImageView logo;
    private TextView tagline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started");

        logo = findViewById(R.id.logo);
        tagline = findViewById(R.id.tagline);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_anim);
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_anim);

        logo.setAnimation(topAnim);
        tagline.setAnimation(bottomAnim);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(firebaseUser != null){
                    Log.d(TAG, "MainActivity: Home Activity");
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                else{
                    Log.d(TAG, "MainActivity: Login Activity");
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        },SPLASH);
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//
//        if(firebaseUser != null){
//            Log.d(TAG, "MainActivity: Home Activity");
//            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//        }
//    }

}