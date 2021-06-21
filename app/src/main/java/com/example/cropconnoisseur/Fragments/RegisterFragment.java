package com.example.cropconnoisseur.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cropconnoisseur.HomeActivity;
import com.example.cropconnoisseur.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.FlowLayout;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterFragment extends Fragment {
    private static final String TAG = "RegisterFragment";

    private EditText editUsername, editEmail, editPass, editNumber;
    private Button btnRegister;
    private ProgressBar progressBar;
    private RelativeLayout parent;
    private ScrollView scrollView;

    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        View view = inflater.inflate(R.layout.fragment_register,container,false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Register");

        initializeViews(view);

        auth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(v -> {

            closeKeyboard();

            String username = editUsername.getText().toString();
            String email = editEmail.getText().toString();
            String password = editPass.getText().toString();
            String number = editNumber.getText().toString();

            progressBar.setVisibility(View.VISIBLE);

            if(TextUtils.isEmpty(username) ||TextUtils.isEmpty(email) ||
                 TextUtils.isEmpty(password)|| TextUtils.isEmpty(number)){
                Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
            }
            else if(password.length() < 6){
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Password must be of atleast 6 characters", Toast.LENGTH_LONG).show();
            }
            else{
                register(username, email,password,number);
            }
            progressBar.setVisibility(View.GONE);

        });

        return view;
    }



    private void register(String username, String email, String password, String number) {
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        FirebaseUser firebaseUser = auth.getCurrentUser();
                        assert firebaseUser != null;
                        String userId = firebaseUser.getUid();

                        reference = FirebaseDatabase.getInstance().
                                getReference("Users").child(userId);

                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("id",userId);
                        hashMap.put("username",username);
                        hashMap.put("number",number);
                        hashMap.put("imageURL","default");

                        reference.setValue(hashMap).addOnCompleteListener(task1 -> {
                            if(task1.isSuccessful()){
                                Intent intent = new Intent(getActivity(), HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
                    }
                    else{
                        Toast.makeText(getContext(), "You can't register with this email or password", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    public void closeKeyboard(){
        try {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(parent.getWindowToken(),0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initializeViews(View view) {
        parent = (RelativeLayout) view.findViewById(R.id.parent);
        editEmail = view.findViewById(R.id.editEmail);
        editUsername = view.findViewById(R.id.editUsername);
        editNumber = view.findViewById(R.id.editNumber);
        editPass = view.findViewById(R.id.editPass);
        btnRegister = view.findViewById(R.id.btnRegister);
        progressBar = view.findViewById(R.id.progress_circular);
        scrollView = view.findViewById(R.id.scrollView);
    }
}