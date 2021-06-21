package com.example.cropconnoisseur.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cropconnoisseur.Model.MoistureData;
import com.example.cropconnoisseur.R;

import java.util.ArrayList;

public class TableRowAdapter extends RecyclerView.Adapter<TableRowAdapter.ViewHolder> {

    private ArrayList<MoistureData> moistureDataArrayList;
    private Context context;

    public TableRowAdapter(ArrayList<MoistureData> moistureData, Context context) {
        this.moistureDataArrayList = moistureData;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.table_row_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.date.setText(moistureDataArrayList.get(position).getDate());
        holder.moisture.setText(String.valueOf(moistureDataArrayList.get(position).getMoisture()));
        holder.comment.setText(moistureDataArrayList.get(position).getComment());
    }

    @Override
    public int getItemCount() {
        return moistureDataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView date,comment,moisture;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.date);
            comment = itemView.findViewById(R.id.comment);
            moisture = itemView.findViewById(R.id.moisture);
        }
    }

    public void setData(ArrayList<MoistureData> moistureDataArrayList) {
        this.moistureDataArrayList = moistureDataArrayList;
        notifyDataSetChanged();
    }
}
