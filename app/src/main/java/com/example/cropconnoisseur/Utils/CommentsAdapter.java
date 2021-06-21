package com.example.cropconnoisseur.Utils;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.cropconnoisseur.Model.Comment;
import com.example.cropconnoisseur.Model.User;
import com.example.cropconnoisseur.R;
import com.example.cropconnoisseur.databinding.CommentsLayoutBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.xwray.groupie.GroupieViewHolder;
import com.xwray.groupie.Item;

public class CommentsAdapter extends Item<CommentsAdapter.ViewHolder> {
    private static final String TAG = "CommentsAdapter";

    private Context context;
    private Comment comment;
    private CommentsLayoutBinding binding;
    private FirebaseUser firebaseUser;
    private String postId;

    public CommentsAdapter(Context context, Comment comment, String postId) {
        this.context = context;
        this.comment = comment;
        this.postId = postId;
    }

    @NonNull
    @Override
    public ViewHolder createViewHolder(@NonNull View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Log.d(TAG, "bind: started" );

        View view = viewHolder.itemView;
        binding = CommentsLayoutBinding.bind(view);

        setCommenterDetails(comment.getPublisherId());
        binding.commentText.setText(comment.getComment());
        binding.date.setText(comment.getDate());
    }

    private void setCommenterDetails(String publisherId) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(publisherId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                binding.commenterName.setText(user.getUsername());
                if(user.getImageURL() != null){
                    if(user.getImageURL().equals("default")){
                        binding.commProfilePic.setImageResource(R.mipmap.ic_launcher);
                    }else{
                        Glide.with(context)
                                .load(user.getImageURL())
                                .into(binding.commProfilePic);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public int getLayout() {
        return R.layout.comments_layout;
    }

    class ViewHolder extends GroupieViewHolder {

        public ViewHolder(@NonNull View rootView) {
            super(rootView);
        }
    }
}
