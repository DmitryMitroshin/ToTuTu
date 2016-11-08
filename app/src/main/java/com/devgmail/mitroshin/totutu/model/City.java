package com.devgmail.mitroshin.totutu.model;

import android.database.Cursor;

import com.devgmail.mitroshin.totutu.util.DatabaseHelper;

public class City {

    private String mCity;
    private Long mId;
    private String mCountry;
    private String mRegion;
    private String mDistrict;
    private Double mLongitude;
    private Double mLatitude;

    public City(Cursor stationCursor) {

        DatabaseHelper mDatabaseHelper = null;

        this.mCity = stationCursor.getString(stationCursor.
                getColumnIndexOrThrow(mDatabaseHelper.CITY_TITLE));
        this.mId = stationCursor.getLong(stationCursor.
//                getColumnIndexOrThrow(mDatabaseHelper.CITIES_TABLE + "." + mDatabaseHelper.CITY_CITY_ID));
                getColumnIndexOrThrow(mDatabaseHelper.CITY_CITY_ID));
        this.mCountry = stationCursor.getString(stationCursor.
                getColumnIndexOrThrow(mDatabaseHelper.COUNTRY_TITLE));
        this.mRegion = stationCursor.getString(stationCursor.
                getColumnIndexOrThrow(mDatabaseHelper.REGION_TITLE));
        this.mDistrict = stationCursor.getString(stationCursor.
                getColumnIndexOrThrow(mDatabaseHelper.DISTRICT_TITLE));
        this.mLongitude = stationCursor.getDouble(stationCursor.
                getColumnIndexOrThrow(mDatabaseHelper.CITY_LONGITUDE));
        this.mLatitude = stationCursor.getDouble(stationCursor.
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
