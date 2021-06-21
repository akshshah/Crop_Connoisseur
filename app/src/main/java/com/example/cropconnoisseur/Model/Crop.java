package com.example.cropconnoisseur.Model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import com.xwray.groupie.GroupieViewHolder;

public class Crop implements Parcelable {
    private String cropName;
    private int cropDrawable;
    private String introduction;
    private String climate;
    private String soil;
    private String fertilizer;
    private String irrigation;
    private String disease;

    public Crop() {
    }

    public Crop(String cropName, int cropDrawable, String introduction, String climate,
                String soil, String fertilizer, String irrigation, String disease) {
        this.cropName = cropName;
        this.cropDrawable = cropDrawable;
        this.introduction = introduction;
        this.climate = climate;
        this.soil = soil;
        this.fertilizer = fertilizer;
        this.irrigation = irrigation;
        this.disease = disease;
    }

    protected Crop(Parcel in) {
        cropName = in.readString();
        cropDrawable = in.readInt();
        introduction = in.readString();
        climate = in.readString();
        soil = in.readString();
        fertilizer = in.readString();
        irrigation = in.readString();
        disease = in.readString();
    }

    public static final Creator<Crop> CREATOR = new Creator<Crop>() {
        @Override
        public Crop createFromParcel(Parcel in) {
            return new Crop(in);
        }

        @Override
        public Crop[] newArray(int size) {
            return new Crop[size];
        }
    };

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public int getCropDrawable() {
        return cropDrawable;
    }

    public void setCropDrawable(int cropDrawable) {
        this.cropDrawable = cropDrawable;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getClimate() {
        return climate;
    }

    public void setClimate(String climate) {
        this.climate = climate;
    }

    public String getSoil() {
        return soil;
    }

    public void setSoil(String soil) {
        this.soil = soil;
    }

    public String getFertilizer() {
        return fertilizer;
    }

    public void setFertilizer(String fertilizer) {
        this.fertilizer = fertilizer;
    }

    public String getIrrigation() {
        return irrigation;
    }

    public void setIrrigation(String irrigation) {
        this.irrigation = irrigation;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cropName);
        dest.writeInt(cropDrawable);
        dest.writeString(introduction);
        dest.writeString(climate);
        dest.writeString(soil);
        dest.writeString(fertilizer);
        dest.writeString(irrigation);
        dest.writeString(disease);
    }
}
