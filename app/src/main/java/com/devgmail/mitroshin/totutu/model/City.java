package com.devgmail.mitroshin.totutu.model;

import android.database.Cursor;

import com.devgmail.mitroshin.totutu.util.DatabaseHelper;

// Класс модели, описывающий объекты типа Город

public class City {

    private String mCity;
    private Long mId;
    private String mCountry;
    private String mRegion;
    private String mDistrict;
    private Double mLongitude;
    private Double mLatitude;

    public City(Cursor cityCursor) {

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
