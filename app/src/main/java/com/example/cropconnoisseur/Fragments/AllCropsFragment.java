package com.example.cropconnoisseur.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cropconnoisseur.CommunityActivity;
import com.example.cropconnoisseur.HomeActivity;
import com.example.cropconnoisseur.Model.Crop;
import com.example.cropconnoisseur.ModelActivity;
import com.example.cropconnoisseur.Notifications.APIService;
import com.example.cropconnoisseur.Notifications.Client;
import com.example.cropconnoisseur.Notifications.Data;
import com.example.cropconnoisseur.Notifications.MyResponse;
import com.example.cropconnoisseur.Notifications.Sender;
import com.example.cropconnoisseur.Notifications.Token;
import com.example.cropconnoisseur.R;
import com.example.cropconnoisseur.Utils.AddCrops;
import com.example.cropconnoisseur.Utils.CropCardAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.xwray.groupie.GroupAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllCropsFragment extends Fragment {
    private RecyclerView recyclerView;

    GroupAdapter adapter;
    private ArrayList<Crop> crops;
    private ProgressBar progressBar;
    private AddCrops addCrops;
    private BottomNavigationView bottomNavigationView;

//    String refreshToken;
//    Token token;
//    APIService apiService;
//    FirebaseUser firebaseUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_crops,container,false);

        recyclerView = view.findViewById(R.id.recView);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        bottomNavigationView = view.findViewById(R.id.bottomNavView);
        recyclerView.setVisibility(View.GONE);
        initBottomNavigation();


        adapter = new GroupAdapter();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2,RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(adapter);

        addCrops = new AddCrops();
        crops = AddCrops.getAllCrops();

        for(Crop c: crops){
            adapter.add(new CropCardAdapter(getActivity(),c,"AllCrops"));
        }

        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
//        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//
//        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
//            @Override
//            public void onComplete(@NonNull Task<String> task) {
//                refreshToken = task.getResult();
//
//                if(firebaseUser != null){
//                    updateToken(refreshToken);
//                }
//
//                sendNotification();
//
//            }
//        });
//
//        apiService = Client.getClient().create(APIService.class);
        
        return view;
    }

//    private void updateToken(String refreshToken) {
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
//        token = new Token(refreshToken);
//        reference.child(firebaseUser.getUid()).setValue(token);
//    }

//    private void sendNotification() {
//        Data data = new Data(firebaseUser.getUid(),R.mipmap.logo,"Hello World","FCM",firebaseUser.getUid());
//
//        Sender sender = new Sender(data,token.getToken());
//
//        apiService.sendNotification(sender)
//                .enqueue(new Callback<MyResponse>() {
//                    @Override
//                    public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
//                        if(response.code() == 200){
//                            if(response.body().success != 1){
//                                Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<MyResponse> call, Throwable t) {
//
//                    }
//                });
//    }

    private void initBottomNavigation(){

        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {

            switch (menuItem.getItemId()){
                case R.id.detect_disease:
                    Intent intent = new Intent(getActivity(), ModelActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                case R.id.home:
                    Intent intent3 = new Intent(getActivity(), HomeActivity.class);
                    intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent3);
                    break;
                case R.id.community:
                    Intent intent2 = new Intent(getActivity(), CommunityActivity.class);
                    intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent2);
                    break;
                default:
                    break;
            }
            return true;
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Crop Details");
    }
}