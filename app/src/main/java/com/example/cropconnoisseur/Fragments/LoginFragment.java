package com.example.cropconnoisseur.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cropconnoisseur.HomeActivity;
import com.example.cropconnoisseur.MainActivity;
import com.example.cropconnoisseur.R;
import com.example.cropconnoisseur.ResetPasswordActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class LoginFragment extends Fragment {
    private static final String TAG = "LoginFragment";

    private EditText editEmail,editPass;
    private ProgressBar progressBar;
    private Button btnLogin;
    private TextView txtRegister, forgotPass;
    private RelativeLayout relLayout1;
    private ScrollView parent;

    FirebaseAuth auth;
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: started" );

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser != null){
            Log.d(TAG, "MainActivity: Home Activity");
            Intent intent = new Intent(getActivity(), HomeActivity.class);
            startActivity(intent);
        }
        else {
            View view = inflater.inflate(R.layout.fragment_login,container,false);
            initializeViews(view);

            relLayout1.setVisibility(View.VISIBLE);

            auth = FirebaseAuth.getInstance();

            btnLogin.setOnClickListener(v -> {
                closeKeyboard();

                String email = editEmail.getText().toString();
                String password = editPass.getText().toString();

                progressBar.setVisibility(View.VISIBLE);

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                }
                else{
                    auth.signInWithEmailAndPassword(email,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Intent intent = new Intent(getActivity() , HomeActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                    else{
                                        Toast.makeText(getContext(), "Authentication Failed!", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                }
            });

            txtRegister.setOnClickListener(v -> Navigation.findNavController(v).
                    navigate(R.id.action_loginFragment_to_registerFragment));

            forgotPass.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), ResetPasswordActivity.class);
                startActivity(intent);
            });

            return view;
        }
        return null;
    }

    public void closeKeyboard(){
        try {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(parent.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initializeViews(View view) {
        parent =  view.findViewById(R.id.parent);
        btnLogin = view.findViewById(R.id.btnLogin);
        txtRegister = view.findViewById(R.id.txtRegister);
        editEmail = view.findViewById(R.id.editEmail);
        editPass = view.findViewById(R.id.editPass);
        forgotPass = view.findViewById(R.id.forgotPass);
        progressBar = view.findViewById(R.id.progress_circular);
        relLayout1 = view.findViewById(R.id.relLayout1);
    }
}