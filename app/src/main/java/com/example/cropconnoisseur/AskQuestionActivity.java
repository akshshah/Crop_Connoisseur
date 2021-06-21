package com.example.cropconnoisseur;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cropconnoisseur.Model.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class AskQuestionActivity extends AppCompatActivity {
    private static final String TAG = "AskQuestionActivity";

    private Toolbar toolbar;
    private ImageView imageView;
    private Spinner topicSpinner;
    private EditText question;
    private Button btnCancel,btnPost;

    private String username = "";
    private DatabaseReference askedByRef,reference2;
    private ProgressDialog progressDialog;
    StorageReference storageReference;
    private Uri imageUri = null;
    private StorageTask uploadTask;

    private FirebaseUser firebaseUser;
    private User user;
    private String userId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ask_question);

        initViews();
        toolbar.setTitle("Ask a Question");

        askedByRef = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference2 = FirebaseDatabase.getInstance().getReference("Questions");
        askedByRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
                assert user != null;
                username = user.getUsername();
                userId = firebaseUser.getUid();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.topics));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        topicSpinner.setAdapter(adapter);

        imageView.setOnClickListener(v -> {
            closeKeyboard();
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,1);
        });

        btnCancel.setOnClickListener(v -> {
            closeKeyboard();
            finish();
        });

        btnPost.setOnClickListener(v -> {
            closeKeyboard();
            postQuestion();
        });

    }

    private void postQuestion() {

        String topic = topicSpinner.getSelectedItem().toString();
        String questionText = question.getText().toString().trim();
        if(questionText.isEmpty()){
            question.setError("Question Required");
        }
        else if(topic.equals("Select Topic")){
            question.setError(null);
            Toast.makeText(AskQuestionActivity.this, "Please select a topic", Toast.LENGTH_SHORT).show();
        }
        else if(imageUri == null){
            question.setError(null);
            uploadQuestionWithNoImage();
        }
        else if(imageUri != null){
            question.setError(null);
            uploadQuestionWithImage();
        }

    }

    private void uploadQuestionWithNoImage() {
        startProgressBar();
        String postId = reference2.push().getKey();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("postId",postId);
        hashMap.put("question",question.getText().toString().trim());
        hashMap.put("publisherId",userId);
        hashMap.put("topic",topicSpinner.getSelectedItem().toString());
        hashMap.put("publisherName", username);
        hashMap.put("date", getDate());
        hashMap.put("hasImage",false);

        reference2.child(postId).setValue(hashMap).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(AskQuestionActivity.this, "Question posted successfully", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                startActivity(new Intent(AskQuestionActivity.this,CommunityActivity.class));
                finish();
            }else {
                Toast.makeText(AskQuestionActivity.this, "Task Unsuccessful, some error occurred", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }

    private void uploadQuestionWithImage() {
        startProgressBar();

        if(imageUri != null){
            final StorageReference fileReference = storageReference.
                    child(System.currentTimeMillis() + "." + getFileExtension(imageUri));

            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask((Continuation) task -> {
                if(!task.isSuccessful()){
                    throw  task.getException();
                }
                return fileReference.getDownloadUrl();
            }).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Uri downloadUri = (Uri) task.getResult();
                    assert downloadUri != null;
                    String myUrl = downloadUri.toString();

                    String postId = reference2.push().getKey();

                    HashMap<String,Object> hashMap = new HashMap<>();
                    hashMap.put("postId",postId);
                    hashMap.put("question",question.getText().toString().trim());
                    hashMap.put("publisherId",userId);
                    hashMap.put("topic",topicSpinner.getSelectedItem().toString());
                    hashMap.put("publisherName", username);
                    hashMap.put("date", getDate());
                    hashMap.put("imageUrl",myUrl);
                    hashMap.put("hasImage",true);

                    reference2.child(postId).setValue(hashMap).addOnCompleteListener(task1 -> {
                        if(task1.isSuccessful()){
                            Toast.makeText(AskQuestionActivity.this, "Question posted successfully", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            startActivity(new Intent(AskQuestionActivity.this,CommunityActivity.class));
                            finish();
                        }else {
                            Toast.makeText(AskQuestionActivity.this, "Task Unsuccessful, some error occurred", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
                }
                else{
                    Toast.makeText(AskQuestionActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }).addOnFailureListener(e -> {
                Toast.makeText(AskQuestionActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            });

        }
        else{
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void startProgressBar(){
        progressDialog.setMessage("Posting your question");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = this.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private String getDate(){
        return new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    public void closeKeyboard(){
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getRootView().getWindowToken(),0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        imageView = findViewById(R.id.image);
        topicSpinner = findViewById(R.id.topicSpinner);
        question = findViewById(R.id.question);
        btnCancel = findViewById(R.id.btnCancel);
        btnPost = findViewById(R.id.btnPost);

        progressDialog = new ProgressDialog(this);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference("questions");

    }
}