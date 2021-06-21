package com.example.cropconnoisseur.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cropconnoisseur.Classifier.Classifier;
import com.example.cropconnoisseur.Model.CropDisease;
import com.example.cropconnoisseur.Model.User;
import com.example.cropconnoisseur.Notifications.APIService;
import com.example.cropconnoisseur.Notifications.Client;
import com.example.cropconnoisseur.Notifications.Data;
import com.example.cropconnoisseur.Notifications.MyResponse;
import com.example.cropconnoisseur.Notifications.Sender;
import com.example.cropconnoisseur.Notifications.Token;
import com.example.cropconnoisseur.R;
import com.example.cropconnoisseur.Utils.AddCropDisease;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetectDiseaseFragment extends Fragment {

    private static final String TAG = "DetectDiseaseFragment";

    private Toolbar toolbar;
    private Button btnCamera,btnGallery;
    private ImageView imageView;
    private ListView listView;

    private Uri imageUri;

    private static final int GALLERY_CODE = 11;
    private static final int CAMERA_CODE = 22;

    private String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    int permissionCode = 100;

    private Classifier imageClassifier;
    private FirebaseUser firebaseUser;

    String cropName;

    APIService apiService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detect_disease, container, false);

        try{
            Bundle bundle = getArguments();
            cropName = bundle.getString("cropName");
            imageClassifier = new Classifier(getActivity(),cropName);
        }catch (Exception e){
            e.printStackTrace();
            Log.d(TAG, "onCreateView: Error " + e.toString());
        }

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        toolbar = view.findViewById(R.id.toolbar);
        btnCamera = view.findViewById(R.id.btnCamera);
        btnGallery = view.findViewById(R.id.btnGallery);
        imageView = view.findViewById(R.id.image);
        listView = view.findViewById(R.id.listView);

        toolbar.setTitle("Detect Disease");

        apiService = Client.getClient().create(APIService.class);

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if(checkPermission()){
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent,GALLERY_CODE);
                }
                else{
                    ActivityCompat.requestPermissions(getActivity(),permissions,permissionCode);
                }
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPermission()){
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, "New Picture");
                    imageUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent,CAMERA_CODE);
                }
                else{
                    ActivityCompat.requestPermissions(getActivity(),permissions,permissionCode);
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: " + parent.getItemAtPosition(position));

                String[] str = parent.getItemAtPosition(position).toString().split(":::::");

                Map<String, CropDisease> cropDiseaseMap = AddCropDisease.getDiseaseMap();
                CropDisease cropDisease = cropDiseaseMap.get(str[0].trim());

                Bundle bundle = new Bundle();
                bundle.putParcelable("cropDisease",cropDisease);

                Navigation.findNavController(view).navigate(R.id.action_detectDiseaseFragment_to_diseaseDetailFragment,bundle);
            }
        });

        return view;
    }

    private void sendNotification(String receiver,CropDisease cropDisease){
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");

        Query query = tokens.orderByKey().equalTo(receiver);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot2: snapshot.getChildren()){
                    Token token = snapshot2.getValue(Token.class);
                    Data data = new Data(firebaseUser.getUid(),R.mipmap.logo,"Crop name : " + cropDisease.getCropName() + "\nDisease Name : " + cropDisease.getDiseaseName() , "Harmful Disease Detected Near Your Area",
                            receiver);

                    Sender sender = new Sender(data, token.getToken());

                    apiService.sendNotification(sender)
                            .enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                    if(response.code() == 200){
                                        if(response.body().success != 1){
//                                            Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<MyResponse> call, Throwable t) {

                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE);
        int result2 = ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int result3 = ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CAMERA);

        if(result == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED && result3== PackageManager.PERMISSION_GRANTED ){
            return true;
        }else {
            return false;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && requestCode == 100) {
            boolean readAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            boolean writeAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
            boolean CameraAccepted = grantResults[2] == PackageManager.PERMISSION_GRANTED;

            if( !readAccepted ||  !writeAccepted || !CameraAccepted){
                Toast.makeText(getActivity(), "Please allow all permissions to continue", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_CODE && resultCode == Activity.RESULT_OK) {
            startCrop(data.getData());
        }
        else if(resultCode == Activity.RESULT_OK && requestCode == CAMERA_CODE){
            startCrop(imageUri);
        }

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            //imageView.setImageURI(result.getUri());
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),result.getUri());
                imageView.setImageBitmap(bitmap);

                List<Classifier.Recognition> predicitons = imageClassifier.recognizeImage(
                        bitmap, 0);
                // creating a list of string to display in list view
                final List<String> predicitonsList = new ArrayList<>();
                for (Classifier.Recognition recog : predicitons) {
                    predicitonsList.add(recog.getName() + "  :::::  " + recog.getConfidence());
                }
                // creating an array adapter to display the classification result in list view
                ArrayAdapter<String> predictionsAdapter = new ArrayAdapter<>(
                        getActivity(), R.layout.support_simple_spinner_dropdown_item, predicitonsList);

                listView.setAdapter(predictionsAdapter);

                String[] str = predictionsAdapter.getItem(0).split(":::::");

                double prediction = Double.parseDouble(str[1].trim());

                Map<String, CropDisease> cropDiseaseMap = AddCropDisease.getDiseaseMap();
                CropDisease cropDisease = cropDiseaseMap.get(str[0].trim());
//                Log.d(TAG, "onActivityResult: " + cropDiseaseMap.isEmpty());
//                Log.d(TAG, "onActivityResult: " + cropDiseaseMap.get(str[0].trim()).getRemedy());
                Log.d(TAG, "onActivityResult: " + str[0] + str[1]);

                if(prediction >= 0.8 && cropDisease.isVulnerable()){
                    prepareNotification(cropDisease);
                }
                else{
                    Toast.makeText(getActivity(), "No conclusive disease found", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    private void prepareNotification(CropDisease cropDisease) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1: snapshot.getChildren()){
                    User user = snapshot1.getValue(User.class);
                    sendNotification(user.getId(),cropDisease);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void startCrop(Uri imageUri) {

        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(getContext(),DetectDiseaseFragment.this);

    }


}