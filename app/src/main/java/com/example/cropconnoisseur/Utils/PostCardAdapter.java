package com.example.cropconnoisseur.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.cropconnoisseur.CommentsActivity;
import com.example.cropconnoisseur.Model.Post;
import com.example.cropconnoisseur.Model.User;
import com.example.cropconnoisseur.R;
import com.example.cropconnoisseur.databinding.QuestionsPostedLayoutBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.xwray.groupie.GroupieViewHolder;
import com.xwray.groupie.Item;

public class PostCardAdapter extends Item<PostCardAdapter.ViewHolder> {
    private static final String TAG = "PostCardAdapter";

    private Context context;
    private Post post;
    private QuestionsPostedLayoutBinding binding;
    private FirebaseUser firebaseUser;

    public PostCardAdapter(Context context, Post post) {
        this.context = context;
        this.post = post;
    }

    public interface DeletePost{
        void onDeletePost(String postId);
    }

    private DeletePost deletePost;

    @NonNull
    @Override
    public ViewHolder createViewHolder(@NonNull View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {
        Log.d(TAG, "bind: started");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        View view = viewHolder.itemView;
        binding = QuestionsPostedLayoutBinding.bind(view);


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(post.getPublisherId());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if(user.getImageURL() != null){
                    if(user.getImageURL().equals("default")){
                        binding.profilePic.setImageResource(R.mipmap.ic_launcher);
                    }else{
                        Glide.with(context)
                                .load(user.getImageURL())
                                .into(binding.profilePic);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if(!post.isHasImage()){
            binding.questionImage.setVisibility(View.GONE);
        }
        else{
            binding.questionImage.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(post.getImageUrl())
                    .into(binding.questionImage);
        }

        binding.username.setText(post.getPublisherName());
        binding.questionText.setText(post.getQuestion());
        binding.topic.setText(post.getTopic());
        binding.datePosted.setText(post.getDate());

        if(post.getPublisherId().equals(firebaseUser.getUid())){
            binding.delete.setVisibility(View.VISIBLE);
        }
        else{
            binding.delete.setVisibility(View.GONE);
        }

        isLiked(post.getPostId());
        showLikesCount(post.getPostId());
        showCommentsCount(post.getPostId());

        binding.like.setOnClickListener(v -> {
            if(binding.like.getTag().equals("like")){
                FirebaseDatabase.getInstance().getReference().child("likes").child(post.getPostId()).child(firebaseUser.getUid()).setValue(true);
            }
            else{
                FirebaseDatabase.getInstance().getReference().child("likes").child(post.getPostId()).child(firebaseUser.getUid()).removeValue();
            }
        });

        binding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle("Delete post")
                        .setMessage("Are you sure you want to delete this post?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deletePost = (DeletePost) context;
                                deletePost.onDeletePost(post.getPostId());
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setCancelable(false);

                builder.create().show();
            }
        });

        binding.comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommentsActivity.class);
                intent.putExtra("postId",post.getPostId());
                intent.putExtra("publisherId",post.getPublisherId());
                context.startActivity(intent);
            }
        });

        binding.allComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommentsActivity.class);
                intent.putExtra("postId",post.getPostId());
                intent.putExtra("publisherId",post.getPublisherId());
                context.startActivity(intent);
            }
        });

        binding.questionImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ImageFullScreenActivity.class);
                intent.putExtra("imageUrl",post.getImageUrl());
                context.startActivity(intent);
            }
        });

    }

    private void showCommentsCount(String postId) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("comments").child(postId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int count = (int) snapshot.getChildrenCount();
                    binding.commentsCount.setText(String.valueOf(count));
                }
                else{
                    binding.commentsCount.setText(String.valueOf(0));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showLikesCount(String postId) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("likes").child(postId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int count = (int) snapshot.getChildrenCount();
                    binding.likesCount.setText(String.valueOf(count));
                }
                else{
                    binding.likesCount.setText(String.valueOf(0));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void isLiked(String postId){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("likes").child(postId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(firebaseUser.getUid()).exists()){
                    binding.like.setImageResource(R.drawable.ic_thumbs_up_filled);
                    binding.like.setTag("liked");
                }else{
                    binding.like.setImageResource(R.drawable.ic_thumbs_up_empty);
                    binding.like.setTag("like");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public int getLayout() {
        return R.layout.questions_posted_layout;
    }

    class ViewHolder extends GroupieViewHolder {

        public ViewHolder(@NonNull View rootView) {
            super(rootView);
        }
    }
}
