package com.example.cropconnoisseur.Utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cropconnoisseur.Model.WeatherForecastResult;
import com.example.cropconnoisseur.R;
import com.example.cropconnoisseur.databinding.ForecastCardLayoutBinding;

public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.ViewHolder> {

    private static final String TAG = "WeatherForecastAdapter";

    Context context;
    WeatherForecastResult weatherForecastResult;
    public ForecastCardLayoutBinding binding;


    public WeatherForecastAdapter(Context context, WeatherForecastResult weatherForecastResult) {
        this.context = context;
        this.weatherForecastResult = weatherForecastResult;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.forecast_card_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String key =  weatherForecastResult.list.get(position).weather.get(0).getMain() + " " +
                weatherForecastResult.list.get(position).weather.get(0).getIcon();

        if(weatherForecastResult.list.get(position).weather.get(0).getMain().equals("Clouds")){
            key = weatherForecastResult.list.get(position).weather.get(0).getDescription() + " " +
                    weatherForecastResult.list.get(position).weather.get(0).getIcon();
        }

        Log.d(TAG, "key: " + key);
        Log.d(TAG, "key: " + Common.getIcons.get(key));

        Glide.with(context).load(Common.getIcons.get(key)).error(R.drawable.ic_error).into(holder.imageView);

        holder.dateTime.setText(Common.convertUnitToDate(weatherForecastResult.list.get(position).dt));

        holder.description.setText(weatherForecastResult.list.get(position).weather.get(0).getDescription().toUpperCase());

        holder.temperature.setText(new StringBuilder(String.valueOf(weatherForecastResult.list.get(position).main.getTemp()))
                .append("°C").toString());

    }

    @Override
    public int getItemCount() {
        return weatherForecastResult.list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView dateTime,description,temperature;
        public ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dateTime = itemView.findViewById(R.id.date);
            description = itemView.findViewById(R.id.description);
            temperature = itemView.findViewById(R.id.temperature);
            imageView = itemView.findViewById(R.id.imgWeather);
        }
    }
}
