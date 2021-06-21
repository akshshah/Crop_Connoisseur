package com.example.cropconnoisseur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cropconnoisseur.Model.Post;
import com.example.cropconnoisseur.Utils.PostCardAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.xwray.groupie.GroupAdapter;

import java.util.ArrayList;

public class CommunityActivity extends AppCompatActivity implements ShowAllTopicsDialog.SelectTopic,PostCardAdapter.DeletePost  {

    private ExtendedFloatingActionButton askQuestion;
    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private TextView noResults;

    private ProgressBar progressBar;
    private RecyclerView recView;

    private ArrayList<Post> posts;
    GroupAdapter postAdapter;

    private FirebaseUser firebaseUser;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.community_menu,menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        
        initViews();
        toolbar.setTitle("Community");
        setSupportActionBar(toolbar);
        initBottomNavigation();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        recView.setHasFixedSize(true);
        recView.setLayoutManager(layoutManager);
        postAdapter = new GroupAdapter();
        recView.setAdapter(postAdapter);
        progressBar.setVisibility(View.VISIBLE);
        recView.setVisibility(View.INVISIBLE);
        noResults.setVisibility(View.INVISIBLE);

        fetchPosts();

        recView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if(!recyclerView.canScrollVertically(-1) && newState == RecyclerView.SCROLL_STATE_IDLE){
                    askQuestion.show();
                    askQuestion.shrink();
                }
                else if(!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE){
                    askQuestion.hide();
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy > 0){
                    askQuestion.show();
                    askQuestion.shrink();
                }
                else{
                    askQuestion.show();
                    askQuestion.extend();
                }
            }
        });

    }

    private void fetchPosts() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Questions");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                posts = new ArrayList<>();
                for(DataSnapshot snapshot1: snapshot.getChildren()){
                    Post post = snapshot1.getValue(Post.class);
                    posts.add(post);
                }

                if(posts.size() > 0){
                    for(Post post:posts){
                        postAdapter.add(new PostCardAdapter(CommunityActivity.this,post));
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                    recView.setVisibility(View.VISIBLE);
                    noResults.setVisibility(View.INVISIBLE);

                }
                else{
                    progressBar.setVisibility(View.INVISIBLE);
                    recView.setVisibility(View.INVISIBLE);
                    noResults.setVisibility(View.VISIBLE);
                }
                postAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CommunityActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
                recView.setVisibility(View.INVISIBLE);
                noResults.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void fetchPosts(String topic) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Questions");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                posts = new ArrayList<>();
                for(DataSnapshot snapshot1: snapshot.getChildren()){
                    Post post = snapshot1.getValue(Post.class);
                    if(post.getTopic().equals(topic))
                        posts.add(post);
                }

                if(posts.size() > 0){
                    for(Post post:posts){
                        postAdapter.add(new PostCardAdapter(CommunityActivity.this,post));
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                    recView.setVisibility(View.VISIBLE);
                    noResults.setVisibility(View.INVISIBLE);
                }
                else{
                    progressBar.setVisibility(View.INVISIBLE);
                    recView.setVisibility(View.INVISIBLE);
                    noResults.setVisibility(View.VISIBLE);
                }
                postAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CommunityActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
                recView.setVisibility(View.INVISIBLE);
                noResults.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void fetchUserPosts(String userId) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Questions");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                posts = new ArrayList<>();
                for(DataSnapshot snapshot1: snapshot.getChildren()){
                    Post post = snapshot1.getValue(Post.class);
                    if(post.getPublisherId().equals(userId))
                        posts.add(post);
                }

                if(posts.size() > 0){
                    for(Post post:posts){
                        postAdapter.add(new PostCardAdapter(CommunityActivity.this,post));
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                    recView.setVisibility(View.VISIBLE);
                    noResults.setVisibility(View.INVISIBLE);
                }
                else{
                    progressBar.setVisibility(View.INVISIBLE);
                    recView.setVisibility(View.INVISIBLE);
                    noResults.setVisibility(View.VISIBLE);
                }
                postAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CommunityActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
                recView.setVisibility(View.INVISIBLE);
                noResults.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void deletePost(String postId) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Questions").child(postId);

        reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CommunityActivity.this, "Post Deleted Successfully", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(CommunityActivity.this, "Error in deleting the post", Toast.LENGTH_SHORT).show();
                }
            }
        });

        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("comments").child(postId);

        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    reference2.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference reference3 = FirebaseDatabase.getInstance().getReference("likes").child(postId);
        reference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    reference3.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        posts.clear();
        postAdapter.clear();
        fetchPosts();

    }

    private void initBottomNavigation(){

        bottomNavigationView.setSelectedItemId(R.id.community);
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {

            switch (menuItem.getItemId()){
                case R.id.detect_disease:
                    Intent intent = new Intent(CommunityActivity.this,ModelActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                case R.id.home:
                    Intent intent2 = new Intent(CommunityActivity.this, HomeActivity.class);
                    intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent2);
                    break;
                case R.id.community:
                    break;
                default:
                    break;
            }
            return true;
        });

        askQuestion.setOnClickListener(v -> {
            Intent intent = new Intent(CommunityActivity.this,AskQuestionActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.filterTopics:
                ShowAllTopicsDialog showAllTopicsDialog = new ShowAllTopicsDialog();
                showAllTopicsDialog.show(getSupportFragmentManager(),"All Topics");
                break;
            case R.id.myQues:
                posts.clear();
                postAdapter.clear();
                fetchUserPosts(firebaseUser.getUid());
                break;
            default:
                break;
        }
        return false;
    }

    private void initViews() {
        askQuestion = findViewById(R.id.askQuestion);
        toolbar = findViewById(R.id.toolbar);
        bottomNavigationView = findViewById(R.id.bottomNavView);
        recView = findViewById(R.id.recView);
        progressBar = findViewById(R.id.progressBar);
        noResults = findViewById(R.id.noResults);
    }

    @Override
    public void onSelectTopic(String topic) {
        //Toast.makeText(this, topic + " Selected", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.VISIBLE);
        recView.setVisibility(View.INVISIBLE);
        posts.clear();
        postAdapter.clear();
        if(topic.equals("All"))
            fetchPosts();
        else
            fetchPosts(topic);

    }

    @Override
    public void onDeletePost(String postId) {
        progressBar.setVisibility(View.VISIBLE);
        recView.setVisibility(View.INVISIBLE);
        deletePost(postId);
    }

}