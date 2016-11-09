package com.devgmail.mitroshin.totutu.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.devgmail.mitroshin.totutu.util.DatabaseHelper;

// Класс модели, описывающий объекты типа Город

public class City implements Parcelable{

    private String mCity;
    private Long mId;
    private String mCountry;
    private String mRegion;
    private String mDistrict;
    private Double mLongitude;
    private Double mLatitude;

    public City(Cursor cityCursor) {

        System.out.println(" *** City cursor constructor *** ");

        DatabaseHelper mDatabaseHelper = null;

        this.mCity = cityCursor.getString(cityCursor.
                getColumnIndexOrThrow(mDatabaseHelper.CITY_TITLE));
        this.mId = cityCursor.getLong(cityCursor.
                getColumnIndexOrThrow(mDatabaseHelper.CITY_CITY_ID));
        this.mCountry = cityCursor.getString(cityCursor.
                getColumnIndexOrThrow(mDatabaseHelper.COUNTRY_TITLE));
        this.mRegion = cityCursor.getString(cityCursor.
                getColumnIndexOrThrow(mDatabaseHelper.REGION_TITLE));
        this.mDistrict = cityCursor.getString(cityCursor.
                getColumnIndexOrThrow(mDatabaseHelper.DISTRICT_TITLE));
        this.mLongitude = cityCursor.getDouble(cityCursor.
                getColumnIndexOrThrow(mDatabaseHelper.CITY_LONGITUDE));
        this.mLatitude = cityCursor.getDouble(cityCursor.
                getColumnIndexOrThrow(mDatabaseHelper.CITY_LATITUDE));
    }

    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };

    public City(Parcel parcel) {

        System.out.println(" *** City parcel constructor *** ");

        mCity = parcel.readString();
        mId = parcel.readLong();
        mCountry = parcel.readString();
        mRegion = parcel.readString();
        mDistrict = parcel.readString();
        mLongitude = parcel.readDouble();
        mLatitude = parcel.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {

        System.out.println(" *** City write to parcel *** ");

        parcel.writeString(mCity);
        parcel.writeLong(mId);
        parcel.writeString(mCountry);
        parcel.writeString(mRegion);
        parcel.writeString(mDistrict);
        parcel.writeDouble(mLongitude);
        parcel.writeDouble(mLatitude);
    }

    public String getCity() {
        return mCity;
    }

    public Long getId() {
        return mId;
    }

    public String getCountry() {
        return mCountry;
    }

    public String getRegion() {
        return mRegion;
    }

    public String getDistrict() {
        return mDistrict;
    }

    public Double getLongitude() {
        return mLongitude;
    }

    public Double getLatitude() {
        return mLatitude;
    }

    @Override
    public String toString() {

        String str = " *** City *** \n" +
                "\nCity = " + mCity +
                "\nCity ID = " + mId +
                "\nLongitude = " + mLongitude +
                "\nLatitude = " + mLatitude +
                "\nCountry = " + mCountry +
                "\nRegion = " + mRegion +
                "\nDistrict = " + mDistrict + "\n";
        return str;
    }
}
