package com.example.cropconnoisseur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.cropconnoisseur.Fragments.AllCropsFragment;
import com.example.cropconnoisseur.Fragments.MoistureFragment;
import com.example.cropconnoisseur.Fragments.NewsFragment;
import com.example.cropconnoisseur.Fragments.SchemesFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class SoilFertilityActivity extends YouTubeBaseActivity {

    private static final String TAG = "SoilFertilityActivity";
    private YouTubePlayerView youTubePlayerView;
    private Button btnPlay;
    private YouTubePlayer.OnInitializedListener onInitializedListener;
    private Toolbar toolbar;
    private TextView ph,nitrogen,phosphorus,potassium,txtCompare;
    private int year, month, day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soil_fertility);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Soil Fertility Check");


        youTubePlayerView = findViewById(R.id.youTubeView);
        ph = findViewById(R.id.ph);
        potassium = findViewById(R.id.potassium);
        nitrogen = findViewById(R.id.nitrogen);
        phosphorus = findViewById(R.id.phosphorus);
        btnPlay = findViewById(R.id.btnPlay);
//        txtCompare = findViewById(R.id.txtCompare);
//
//        Calendar calendar = Calendar.getInstance();
//        year = calendar.get(Calendar.YEAR);
//
//        month = calendar.get(Calendar.MONTH);
//        day = calendar.get(Calendar.DAY_OF_MONTH);

//        txtCompare.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DatePickerDialog datePickerDialog = new DatePickerDialog(SoilFertilityActivity.this,myDateListener, year,month,day);
//                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
//                datePickerDialog.show();
//            }
//        });

        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("qcH9aYIbgF4");

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d(TAG, "onInitializationFailure: Failed");
            }
        };

        btnPlay.setOnClickListener(v -> {
            Log.d(TAG, "onClick: Initializing Youtube player");
            youTubePlayerView.initialize(YouTubeConfig.getApiKey(), onInitializedListener);
        });

        potassium.setOnClickListener(v -> goToTest("potassium"));
        ph.setOnClickListener(v -> goToTest("ph"));
        nitrogen.setOnClickListener(v -> goToTest("nitrogen"));
        phosphorus.setOnClickListener(v -> goToTest("phosphorus"));


    }

//    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
//        @Override
//        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//            setDate(year,month+1,dayOfMonth);
//        }
//    };
//
//    private void setDate(int year, int month, int dayOfMonth) {
//        txtCompare.setText(new StringBuilder().append(dayOfMonth).append("/")
//                .append(month).append("/").append(year));
//    }


    private void goToTest(String testName) {
        Intent intent = new Intent(SoilFertilityActivity.this, TestResultsCompareActivity.class);
        intent.putExtra("testName",testName);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SoilFertilityActivity.this,HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}