package com.example.cropconnoisseur.Fragments;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.cropconnoisseur.Model.Crop;
import com.example.cropconnoisseur.R;
import com.example.cropconnoisseur.Utils.AddCrops;
import com.example.cropconnoisseur.Utils.CropCardAdapter;
import com.xwray.groupie.GroupAdapter;

import java.util.ArrayList;


public class ModelCropsFragment extends Fragment {
    private static final String TAG = "SelectCropFragment";

    private Toolbar toolbar;
    GroupAdapter adapter;
    private ArrayList<Crop> crops;
    private AddCrops addCrops;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_model_crops,container,false);


       recyclerView = view.findViewById(R.id.recView);
       progressBar = view.findViewById(R.id.progressBar);
       toolbar =  view.findViewById(R.id.toolbar);
       toolbar.setTitle("Select Crop");

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        adapter = new GroupAdapter();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2,RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(adapter);

        addCrops = new AddCrops();
        crops = AddCrops.getModelCrops();

        for(Crop c: crops){
            adapter.add(new CropCardAdapter(getActivity(),c,"ModelCrops"));
        }

        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

        return view;
    }


}