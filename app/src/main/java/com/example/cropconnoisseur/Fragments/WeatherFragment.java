package com.example.cropconnoisseur.Fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.cropconnoisseur.CommunityActivity;
import com.example.cropconnoisseur.ModelActivity;
import com.example.cropconnoisseur.Notifications.Token;
import com.example.cropconnoisseur.R;
import com.example.cropconnoisseur.Utils.Common;
import com.example.cropconnoisseur.Utils.ViewPagerAdapter;
import com.example.cropconnoisseur.databinding.FragmentWeatherBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;


public class WeatherFragment extends Fragment {
    private static final String TAG = "WeatherFragment";

    private FragmentWeatherBinding binding;
    private TabLayout tabLayout;
    private BottomNavigationView bottomNavigationView;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;

    private FirebaseUser firebaseUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        bottomNavigationView = view.findViewById(R.id.bottomNavView);

        initBottomNavigation();

        binding = FragmentWeatherBinding.bind(view);

        binding.progressBar.setVisibility(View.VISIBLE);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                String refreshToken = task.getResult();
                updateToken(refreshToken);
            }
        });

        Dexter.withContext(getActivity())
                .withPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
                            buildLocationRequest();
                            buildLocationCallBack();

                            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }

                            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
                            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        Snackbar.make(binding.getRoot(),"Permission Denied", Snackbar.LENGTH_LONG).show();
                    }
                }).check();

        return view;
    }

    private void buildLocationCallBack() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);

                Common.currentLocation = locationResult.getLastLocation();

                setUpViewPager(binding.viewPager);
                tabLayout = binding.tabs;
                tabLayout.setupWithViewPager(binding.viewPager);
                binding.progressBar.setVisibility(View.GONE);

                Log.d(TAG, "onLocationResult: " + locationResult.getLastLocation().getLatitude() + " ## " + locationResult.getLastLocation().getLongitude()  );
            }
        };
    }

    private void setUpViewPager(ViewPager viewPager) {
        if (!isAdded()) return;
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(TodayWeatherFragment.getInstance(),"Today");
        adapter.addFragment(ForecastFragment.getInstance(),"5 Days");
        viewPager.setAdapter(adapter);
    }

    private void buildLocationRequest() {

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setSmallestDisplacement(10.0f);

    }

    private void initBottomNavigation(){

        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {

            switch (menuItem.getItemId()){
                case R.id.detect_disease:
                    Intent intent = new Intent(getActivity(), ModelActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                case R.id.home:
                    break;
                case R.id.community:
                    Intent intent2 = new Intent(getActivity(), CommunityActivity.class);
                    intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent2);
                    break;
                default:
                    break;
            }
            return true;
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Weather");
    }

    private void updateToken(String token){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);
        reference.child(firebaseUser.getUid()).setValue(token1);
    }
}