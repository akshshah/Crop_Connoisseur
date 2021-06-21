    package com.example.cropconnoisseur;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cropconnoisseur.Fragments.AllCropsFragment;
import com.example.cropconnoisseur.Fragments.MoistureFragment;
import com.example.cropconnoisseur.Fragments.NewsFragment;
import com.example.cropconnoisseur.Fragments.SchemesFragment;
import com.example.cropconnoisseur.Fragments.WeatherFragment;
import com.example.cropconnoisseur.Model.User;
import com.example.cropconnoisseur.Utils.AddMoistureCommentDialog;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity implements AddMoistureCommentDialog.AddComment, ChangeLanguageDialog.ChangeLanguage {
    private static final String TAG = "HomeActivity";

    private CircleImageView profilePic;
    private TextView name;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private FrameLayout contentFrame;

    private FirebaseUser firebaseUser;
    private DatabaseReference reference;

    private StorageReference storageReference;
    private static final int IMAGE_REQUEST = 1;
    private Uri imageUri;
    private StorageTask<UploadTask.TaskSnapshot> uploadTask;

    private ProgressDialog progressDialog;
    Context context;
    Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Log.d(TAG, "onCreate: started");

        contentFrame = findViewById(R.id.contentFrame);
        toolbar = findViewById(R.id.toolbar);
        //toolbar.setTitle("Crop Details");
        setSupportActionBar(toolbar);
        progressDialog = new ProgressDialog(HomeActivity.this);


        drawerLayout = findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        navigationView = findViewById(R.id.navigationDrawer);
        View headerView = navigationView.getHeaderView(0);
        name = headerView.findViewById(R.id.username);
        profilePic = headerView.findViewById(R.id.profilePic);

        storageReference = FirebaseStorage.getInstance().getReference("uploads");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "onDataChange: " + firebaseUser.getUid());
                User user = snapshot.getValue(User.class);
                if(user.getUsername() != null && user.getImageURL() != null){
                    name.setText(user.getUsername());
                    if(user.getImageURL().equals("default")){
                        profilePic.setImageResource(R.mipmap.ic_launcher);
                    }else{
                        Glide.with(getApplicationContext())
                                .load(user.getImageURL())
                                .into(profilePic);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        profilePic.setOnClickListener(v -> openImage());

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.contentFrame, new WeatherFragment());
        fragmentTransaction.commit();

        navigationView.setNavigationItemSelectedListener(item -> {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            removeColor(navigationView);
            item.setChecked(true);
            switch (item.getItemId()) {
                case R.id.cropDetail:
                    ft.replace(R.id.contentFrame, new AllCropsFragment());
                    ft.commit();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                case R.id.weather:
                    ft.replace(R.id.contentFrame, new WeatherFragment());
                    ft.commit();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                case R.id.moistureLevel:
                    ft.replace(R.id.contentFrame, new MoistureFragment());
                    ft.commit();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                case R.id.soilCheck:
                    Intent intent = new Intent(HomeActivity.this, SoilFertilityActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    return true;
                case R.id.agriSchemes:
                    ft.replace(R.id.contentFrame, new SchemesFragment());
                    ft.commit();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                case R.id.agriNews:
                    ft.replace(R.id.contentFrame, new NewsFragment());
                    ft.commit();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                case R.id.language:
                    ChangeLanguageDialog changeLanguageDialog = new ChangeLanguageDialog();
                    changeLanguageDialog.show(getSupportFragmentManager(),"All Languages");
                    return true;
                case R.id.logout:
                    FirebaseAuth.getInstance().signOut();
                    Intent intent2 = new Intent(HomeActivity.this, MainActivity.class);
                    intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent2);
                default:
                    break;
            }
            return false;
        });

    }

    private void removeColor(NavigationView navigationView) {
        for(int i=0; i< navigationView.getMenu().size(); i++){
            MenuItem item = navigationView.getMenu().getItem(i);
            item.setChecked(false);
        }
    }

    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = this.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading");
        progressDialog.show();

        if(imageUri != null){
            Log.d(TAG, "uploadImage: started");

            final StorageReference fileReference = storageReference.
                    child(System.currentTimeMillis() + "." + getFileExtension(imageUri));

            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask((Continuation<UploadTask.TaskSnapshot, Task<Uri>>) task -> {
                if(!task.isSuccessful()){
                    throw  task.getException();
                }
                return fileReference.getDownloadUrl();
            }).addOnCompleteListener((OnCompleteListener<Uri>) task -> {
                if(task.isSuccessful()){
                    Uri downloadUri = task.getResult();
                    assert downloadUri != null;
                    String mUri = downloadUri.toString();

                    reference = FirebaseDatabase.getInstance().getReference().child("Users")
                            .child(firebaseUser.getUid());

                    HashMap<String, Object> map = new HashMap<>();
                    map.put("imageURL", mUri);
                    reference.updateChildren(map);
                }
                else{
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }).addOnFailureListener((OnFailureListener) e -> {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            });
        }
        else{
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_REQUEST && data != null
                && data.getData() != null && resultCode == RESULT_OK){
            imageUri = data.getData();
            if(uploadTask != null && uploadTask.isInProgress()){
                Toast.makeText(this, "Upload in Progress", Toast.LENGTH_SHORT).show();
            }
            else{
                uploadImage();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            int count = getSupportFragmentManager().getBackStackEntryCount();
            if(count == 0){
                super.onBackPressed();
                finishAffinity();
                finish();
            }
            else {
                getSupportFragmentManager().popBackStack();
            }
        }
    }

    @Override
    public void onAddingComment(String comment, String moisture) {
        progressDialog.setMessage("Adding the record");
        progressDialog.setCancelable(false);
        progressDialog.show();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("MoistureData").child(firebaseUser.getUid());
        String moistureId = reference.push().getKey();

        HashMap<String,Object> hashMap = new HashMap<>();
        String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        hashMap.put("date",date);
        hashMap.put("comment",comment);
        hashMap.put("moisture",moisture);

        reference.child(moistureId).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(HomeActivity.this, "Record added successfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(HomeActivity.this, "Error adding the record ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onChangeLanguage(String language) {
        String code = "";
        if(language.equals("english")){
            code = "en";
        }
        else{
            code = "hi";
        }

//        context = LocaleHelper.setLocale(HomeActivity.this,code);
//        resources = context.getResources();

        Locale locale = new Locale(code);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration,resources.getDisplayMetrics());

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}