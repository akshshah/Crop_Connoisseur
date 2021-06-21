package com.example.cropconnoisseur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cropconnoisseur.Model.Comment;
import com.example.cropconnoisseur.Model.Post;
import com.example.cropconnoisseur.Model.User;
import com.example.cropconnoisseur.Utils.CommentsAdapter;
import com.example.cropconnoisseur.Utils.PostCardAdapter;
import com.example.cropconnoisseur.databinding.ActivityCommentsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.xwray.groupie.GroupAdapter;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class CommentsActivity extends AppCompatActivity {
    private static final String TAG = "CommentsActivity";

    private ActivityCommentsBinding binding;
    private ProgressDialog progressDialog;
    private String postId,publisherId;
    private FirebaseUser firebaseUser;
    GroupAdapter commentsAdapter;

    private ArrayList<Comment> comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCommentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbar.toolbar.setTitle("Comments");

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        progressDialog = new ProgressDialog(CommentsActivity.this);

        try{
            Intent intent = getIntent();
            postId = intent.getStringExtra("postId");
            publisherId = intent.getStringExtra("publisherId");
        }catch (Exception e){
            e.printStackTrace();
        }


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        binding.noResults.setVisibility(View.GONE);
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.commentsRecView.setVisibility(View.GONE);

        binding.commentsRecView.setLayoutManager(layoutManager);
        binding.commentsRecView.setHasFixedSize(true);
        commentsAdapter = new GroupAdapter();
        binding.commentsRecView.setAdapter(commentsAdapter);

        fetchComments();

        setImage();

        binding.btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
                String comment = binding.textComment.getText().toString().trim();
                Log.d(TAG, "onClick: " + comment);
                if(!comment.equals("")){
                    addComment();
                }else {
                    Toast.makeText(CommentsActivity.this, "Please type something", Toast.LENGTH_SHORT).show();
                }
                binding.textComment.setText("");
            }
        });

    }

    public void closeKeyboard(){
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            View currentFocusedView = CommentsActivity.this.getCurrentFocus();
            if(currentFocusedView != null){
                imm.hideSoftInputFromWindow(currentFocusedView.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void fetchComments() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("comments").child(postId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                comments = new ArrayList<>();
                commentsAdapter.clear();
                for(DataSnapshot snapshot1: snapshot.getChildren()){
                    Comment comment = snapshot1.getValue(Comment.class);
                    comments.add(comment);
                }

                if(comments.size() > 0){
                    for(Comment c:comments){
                        commentsAdapter.add(new CommentsAdapter(CommentsActivity.this,c,postId));
                    }
                    binding.progressBar.setVisibility(View.GONE);
                    binding.commentsRecView.setVisibility(View.VISIBLE);
                    binding.noResults.setVisibility(View.GONE);
                }
                else{
                    binding.progressBar.setVisibility(View.GONE);
                    binding.commentsRecView.setVisibility(View.GONE);
                    binding.noResults.setVisibility(View.VISIBLE);
                }
                commentsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CommentsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setImage() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if(user.getImageURL() != null){
                    if(user.getImageURL().equals("default")){
                        binding.profilePic.setImageResource(R.mipmap.ic_launcher);
                    }else{
                        Glide.with(CommentsActivity.this)
                                .load(user.getImageURL())
                                .into(binding.profilePic);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addComment() {
        progressDialog.setMessage("Adding a comment");
        progressDialog.setCancelable(false);
        progressDialog.show();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("comments").child(postId);
        String commentId = reference.push().getKey();
        String date = DateFormat.getDateInstance().format(new Date());

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("comment",binding.textComment.getText().toString());
        hashMap.put("publisherId", firebaseUser.getUid());
        hashMap.put("commentId",commentId);
        hashMap.put("postId",postId);
        hashMap.put("date",date);

        reference.child(commentId).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(CommentsActivity.this, "Comment added successfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(CommentsActivity.this, "Error posting a comment", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}