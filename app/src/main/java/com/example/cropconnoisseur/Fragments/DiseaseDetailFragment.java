package com.example.cropconnoisseur.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cropconnoisseur.Classifier.Classifier;
import com.example.cropconnoisseur.Model.CropDisease;
import com.example.cropconnoisseur.R;
import com.example.cropconnoisseur.databinding.FragmentDiseaseDetailBinding;


public class DiseaseDetailFragment extends Fragment {
    private static final String TAG = "DiseaseDetailFragment";

    private FragmentDiseaseDetailBinding binding;
    private CropDisease cropDisease;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_disease_detail, container, false);
        binding = FragmentDiseaseDetailBinding.bind(view);

        binding.toolbar.toolbar.setTitle("Disease Details");

        try{
            Bundle bundle = getArguments();
            cropDisease = bundle.getParcelable("cropDisease");

            binding.cropName.setText(cropDisease.getCropName());
            if(cropDisease.isVulnerable()){
                binding.harmful.setText("Yes");
            }
            else{
                binding.harmful.setText("No");
            }

            binding.cause.setText(cropDisease.getCause());
            binding.remedy.setText(cropDisease.getRemedy());
            binding.diseaseName.setText(cropDisease.getDiseaseName());

        }catch (Exception e){
            e.printStackTrace();
            Log.d(TAG, "onCreateView: Error " + e.toString());
        }

        return view;
    }
}