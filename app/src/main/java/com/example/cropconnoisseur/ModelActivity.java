package com.example.cropconnoisseur;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import com.example.cropconnoisseur.Model.CropDisease;
import com.example.cropconnoisseur.Utils.AddCropDisease;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Map;

public class ModelActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model);

        bottomNavigationView = findViewById(R.id.bottomNavView);

        initBottomNavigation();

        String disease = getIntent().getStringExtra("cropDisease");

//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if( disease != null){
            Map<String, CropDisease> cropDiseaseMap = AddCropDisease.getDiseaseMap();
            CropDisease cropDisease = cropDiseaseMap.get(disease);

            Bundle bundle = new Bundle();
            bundle.putParcelable("cropDisease",cropDisease);

            Navigation.findNavController(this,R.id.model_nav_host).navigate(R.id.action_selectCropFragment_to_diseaseDetailFragment,bundle);

        }

    }

    private void initBottomNavigation(){

        bottomNavigationView.setSelectedItemId(R.id.detect_disease);
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {

            switch (menuItem.getItemId()){
                case R.id.home:
                    Intent intent = new Intent(ModelActivity.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                case R.id.community:
                    Intent intent2 = new Intent(ModelActivity.this, CommunityActivity.class);
                    intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent2);
                    break;
                default:
                    break;
            }
            return true;
        });
    }

}