package com.example.cropconnoisseur.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class CropDisease implements Parcelable {

    private String cropName;
    private String diseaseName;
    private boolean isVulnerable;
    private String cause;
    private String remedy;

    public CropDisease(String cropName, String diseaseName, boolean isVulnerable, String cause, String remedy) {
        this.cropName = cropName;
        this.diseaseName = diseaseName;
        this.isVulnerable = isVulnerable;
        this.cause = cause;
        this.remedy = remedy;
    }

    public CropDisease() {
    }

    protected CropDisease(Parcel in) {
        cropName = in.readString();
        diseaseName = in.readString();
        isVulnerable = in.readByte() != 0;
        cause = in.readString();
        remedy = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cropName);
        dest.writeString(diseaseName);
        dest.writeByte((byte) (isVulnerable ? 1 : 0));
        dest.writeString(cause);
        dest.writeString(remedy);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CropDisease> CREATOR = new Creator<CropDisease>() {
        @Override
        public CropDisease createFromParcel(Parcel in) {
            return new CropDisease(in);
        }

        @Override
        public CropDisease[] newArray(int size) {
            return new CropDisease[size];
        }
    };

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public boolean isVulnerable() {
        return isVulnerable;
    }

    public void setVulnerable(boolean vulnerable) {
        isVulnerable = vulnerable;
    }

    public String getRemedy() {
        return remedy;
    }

    public void setRemedy(String remedy) {
        this.remedy = remedy;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}

