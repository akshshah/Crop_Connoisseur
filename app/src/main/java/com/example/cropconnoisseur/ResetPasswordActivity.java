package com.example.cropconnoisseur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText send_email;
    private Button btnReset;
    private ProgressBar progressBar;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        send_email = findViewById(R.id.send_email);
        btnReset = findViewById(R.id.btnReset);
        progressBar = findViewById(R.id.progressBar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Reset Password");

        firebaseAuth = FirebaseAuth.getInstance();

        btnReset.setOnClickListener(v -> {

            progressBar.setVisibility(View.VISIBLE);
            String email = send_email.getText().toString();

            if(email.equals("")){
                progressBar.setVisibility(View.GONE);
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            }
            else{
                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ResetPasswordActivity.this, "Email has been sent", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ResetPasswordActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            String error = task.getException().getMessage();
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(ResetPasswordActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}