package com.example.cropconnoisseur.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cropconnoisseur.Model.WeatherForecastResult;
import com.example.cropconnoisseur.R;
import com.example.cropconnoisseur.Retrofit.IOpenWeatherMap;
import com.example.cropconnoisseur.Retrofit.RetrofitClient;
import com.example.cropconnoisseur.Utils.Common;
import com.example.cropconnoisseur.Utils.WeatherForecastAdapter;
import com.example.cropconnoisseur.databinding.FragmentForecastBinding;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ForecastFragment extends Fragment {

    private static final String TAG = "ForecastFragment";

    private FragmentForecastBinding binding;

    static ForecastFragment mInstance;

    public static ForecastFragment getInstance(){
        if(mInstance == null){
            mInstance = new ForecastFragment();
        }
        return mInstance;
    }

    private CompositeDisposable compositeDisposable;
    IOpenWeatherMap mService;

    public ForecastFragment(){
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        mService = retrofit.create(IOpenWeatherMap.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);

        binding = FragmentForecastBinding.bind(view);

        binding.recView.setHasFixedSize(true);
        binding.recView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
//        binding.recView.

        getForecastInfo();

        return view;
    }

    private void getForecastInfo() {
        compositeDisposable.add(mService.getWeatherForecastByLatLng(String.valueOf(Common.currentLocation.getLatitude()),
                String.valueOf(Common.currentLocation.getLongitude()),
                Common.APP_ID,
                "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherForecastResult>() {
                    @Override
                    public void accept(WeatherForecastResult weatherForecastResult) throws Exception {
                        displayWeatherForecast(weatherForecastResult);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "accept: " + throwable.getMessage());

                    }
                })
        );
    }

    private void displayWeatherForecast(WeatherForecastResult weatherForecastResult) {
        binding.cityName.setText(String.valueOf(weatherForecastResult.city.name));
        binding.geoCoords.setText(weatherForecastResult.city.coord.toString());

        WeatherForecastAdapter adapter = new WeatherForecastAdapter(getContext(),weatherForecastResult);
        binding.recView.setAdapter(adapter);
    }

    @Override
    public void onStop() {
        super.onStop();
        compositeDisposable.clear();
    }
}