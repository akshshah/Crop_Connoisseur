package com.example.cropconnoisseur.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cropconnoisseur.Model.WeatherResult;
import com.example.cropconnoisseur.R;
import com.example.cropconnoisseur.Retrofit.IOpenWeatherMap;
import com.example.cropconnoisseur.Retrofit.RetrofitClient;
import com.example.cropconnoisseur.Utils.Common;
import com.example.cropconnoisseur.databinding.FragmentTodayWeatherBinding;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


public class TodayWeatherFragment extends Fragment {
    private static final String TAG = "TodayWeatherFragment";

    static TodayWeatherFragment instance;

    private FragmentTodayWeatherBinding binding;
    private CompositeDisposable compositeDisposable;
    private IOpenWeatherMap mService;

    public static TodayWeatherFragment getInstance(){
        if(instance == null){
            instance = new TodayWeatherFragment();
        }
        return instance;
    }

    public TodayWeatherFragment() {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        mService = retrofit.create(IOpenWeatherMap.class);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_today_weather, container, false);

        binding = FragmentTodayWeatherBinding.bind(view);

        binding.progressBar.setVisibility(View.VISIBLE);
        binding.weatherPanel.setVisibility(View.GONE);
        getWeatherInfo();

        return view;
    }

    private void getWeatherInfo() {

        compositeDisposable.add(mService.getWeatherByLatLng(String.valueOf(Common.currentLocation.getLatitude()),
                String.valueOf(Common.currentLocation.getLongitude()),
                Common.APP_ID,
                "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherResult>() {
                    @Override
                    public void accept(WeatherResult weatherResult) throws Exception {

                        String key =  weatherResult.getWeather().get(0).getMain() + " " + weatherResult.getWeather().get(0).getIcon();

                        if(weatherResult.getWeather().get(0).getMain().equals("Clouds")){
                            key = weatherResult.getWeather().get(0).getDescription() + " " + weatherResult.getWeather().get(0).getIcon();
                        }

                        Log.d(TAG, "key: " + key);
                        Log.d(TAG, "key: " + Common.getIcons.get(key));

                        Glide.with(getContext()).load(Common.getIcons.get(key)).error(R.drawable.ic_error).into(binding.imgWeather);

                        binding.cityName.setText(weatherResult.getName());
                        binding.description.setText(weatherResult.getWeather().get(0).getDescription().toUpperCase() );

                        binding.temperature.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getTemp()))
                                .append("°C").toString());

                        binding.dateTime.setText(Common.convertUnitToDate(weatherResult.getDt()));

                        binding.pressure.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getPressure()))
                                .append(" hpa").toString());

                        binding.humidity.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getHumidity()))
                                .append(" %").toString());

                        binding.sunrise.setText(Common.convertUnitToHour(weatherResult.getSys().getSunrise()));
                        binding.sunset.setText(Common.convertUnitToHour(weatherResult.getSys().getSunset()));

                        binding.geoCoords.setText(new StringBuilder(weatherResult.getCoord().toString()));

                        binding.wind.setText(new StringBuilder( "Speed : ").append(weatherResult.getWind().getSpeed())
                                .append(" m/s"));

                        binding.weatherPanel.setVisibility(View.VISIBLE);
                        binding.progressBar.setVisibility(View.GONE);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })

        );
    }

    @Override
    public void onStop() {
        super.onStop();
        compositeDisposable.clear();
    }
}