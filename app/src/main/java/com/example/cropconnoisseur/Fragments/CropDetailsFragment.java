package com.example.cropconnoisseur.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cropconnoisseur.Model.Crop;
import com.example.cropconnoisseur.R;

public class CropDetailsFragment extends Fragment {
    private static final String TAG = "CropDetailsFragment";

    private TextView cropName,introduction,climate,disease,irrigation,fertilizer,soil;
    private ImageView cropImage,btnIntroduction,btnClimate,btnFertilizer,btnSoil,btnDisease,btnIrrigation;
    private Crop crop;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {
            Bundle bundle = getArguments();
            crop = bundle.getParcelable("crop");
            Log.d(TAG, "onCreateView: " + crop.getCropName());
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        View view = inflater.inflate(R.layout.fragment_crop_details,container,false);

        initializeViews(view);

        cropName.setText(crop.getCropName());
        cropImage.setImageResource(crop.getCropDrawable());
        introduction.setText(Html.fromHtml(crop.getIntroduction()));
        climate.setText(Html.fromHtml(crop.getClimate()));
        fertilizer.setText(Html.fromHtml(crop.getFertilizer()));
        disease.setText(Html.fromHtml(crop.getDisease()));
        soil.setText(Html.fromHtml(crop.getSoil()));
        irrigation.setText(Html.fromHtml(crop.getIrrigation()));

        if(introduction.getVisibility() == View.GONE)
            btnIntroduction.setImageResource(R.drawable.ic_expand);
        else
            btnIntroduction.setImageResource(R.drawable.ic_collapse);

        if(climate.getVisibility() == View.GONE)
            btnClimate.setImageResource(R.drawable.ic_expand);
        else
            btnClimate.setImageResource(R.drawable.ic_collapse);

        if(fertilizer.getVisibility() == View.GONE)
            btnFertilizer.setImageResource(R.drawable.ic_expand);
        else
            btnFertilizer.setImageResource(R.drawable.ic_collapse);

        if(soil.getVisibility() == View.GONE)
            btnSoil.setImageResource(R.drawable.ic_expand);
        else
            btnSoil.setImageResource(R.drawable.ic_collapse);

        if(irrigation.getVisibility() == View.GONE)
            btnIrrigation.setImageResource(R.drawable.ic_expand);
        else
            btnIrrigation.setImageResource(R.drawable.ic_collapse);

        if(disease.getVisibility() == View.GONE)
            btnDisease.setImageResource(R.drawable.ic_expand);
        else
            btnDisease.setImageResource(R.drawable.ic_collapse);


        btnIntroduction.setOnClickListener(v -> {
            if(introduction.getVisibility() == View.GONE){
                introduction.setVisibility(View.VISIBLE);
                btnIntroduction.setImageResource(R.drawable.ic_collapse);
            }
            else{
                introduction.setVisibility(View.GONE);
                btnIntroduction.setImageResource(R.drawable.ic_expand);
            }
        });

        btnSoil.setOnClickListener(v -> {
            if(soil.getVisibility() == View.GONE){
                soil.setVisibility(View.VISIBLE);
                btnSoil.setImageResource(R.drawable.ic_collapse);
            }
            else{
                soil.setVisibility(View.GONE);
                btnSoil.setImageResource(R.drawable.ic_expand);
            }
        });

        btnIrrigation.setOnClickListener(v -> {
            if(irrigation.getVisibility() == View.GONE){
                irrigation.setVisibility(View.VISIBLE);
                btnIrrigation.setImageResource(R.drawable.ic_collapse);
            }
            else{
                irrigation.setVisibility(View.GONE);
                btnIrrigation.setImageResource(R.drawable.ic_expand);
            }
        });

        btnClimate.setOnClickListener(v -> {
            if(climate.getVisibility() == View.GONE){
                climate.setVisibility(View.VISIBLE);
                btnClimate.setImageResource(R.drawable.ic_collapse);
            }
            else{
                climate.setVisibility(View.GONE);
                btnClimate.setImageResource(R.drawable.ic_expand);
            }
        });

        btnFertilizer.setOnClickListener(v -> {
            if(fertilizer.getVisibility() == View.GONE){
                fertilizer.setVisibility(View.VISIBLE);
                btnFertilizer.setImageResource(R.drawable.ic_collapse);
            }
            else{
                fertilizer.setVisibility(View.GONE);
                btnFertilizer.setImageResource(R.drawable.ic_expand);
            }
        });

        btnDisease.setOnClickListener(v -> {
            if(disease.getVisibility() == View.GONE){
                disease.setVisibility(View.VISIBLE);
                btnDisease.setImageResource(R.drawable.ic_collapse);
            }
            else{
                disease.setVisibility(View.GONE);
                btnDisease.setImageResource(R.drawable.ic_expand);
            }
        });

        return view;
    }

    private void initializeViews(View view) {

        cropImage = view.findViewById(R.id.cropImage);
        cropName = view.findViewById(R.id.cropName);
        introduction = view.findViewById(R.id.introduction);
        fertilizer = view.findViewById(R.id.fertilizer);
        climate = view.findViewById(R.id.climate);
        irrigation = view.findViewById(R.id.irrigation);
        disease = view.findViewById(R.id.disease);
        soil = view.findViewById(R.id.soil);

        btnClimate = view.findViewById(R.id.btnClimate);
        btnIntroduction = view.findViewById(R.id.btnIntroduction);
        btnDisease = view.findViewById(R.id.btnDisease);
        btnFertilizer = view.findViewById(R.id.btnFertilizer);
        btnSoil = view.findViewById(R.id.btnSoil);
        btnIrrigation = view.findViewById(R.id.btnIrrigation);
    }
}