package com.devgmail.mitroshin.totutu.model;

import android.database.Cursor;

import com.devgmail.mitroshin.totutu.util.DatabaseHelper;

// Класс модели, описывающий объекты типа Станция

public class Station extends City {

    private String mStation;
    private Long mId;
    private Double mLongitude;
    private Double mLatitude;

    private Station mStationObject;

    public Station(Cursor stationCursor, Cursor cityCursor) {
        super(cityCursor);

        DatabaseHelper mDatabaseHelper = null;

        this.mStation = stationCursor.getString(stationCursor.
                getColumnIndexOrThrow(mDatabaseHelper.STATION_TITLE));
        this.mId = stationCursor.getLong(stationCursor.
                getColumnIndexOrThrow(mDatabaseHelper.STATION_ID));
        this.mLongitude = stationCursor.getDouble(stationCursor.
                getColumnIndexOrThrow(mDatabaseHelper.STATION_LONGITUDE));
        this.mLatitude = stationCursor.getDouble(stationCursor.
                getColumnIndexOrThrow(mDatabaseHelper.STATION_LATITUDE));

    }

    public Station getStationObject() {
        return mStationObject;
    }

    @Override
    public Long getId() {
        return mId;
    }

    @Override
    public Double getLongitude() {
        return mLongitude;
    }

    @Override
    public Double getLatitude() {
        return mLatitude;
    }

    @Override
    public String toString() {

        String strStations = " *** STATION *** \n" +
                "\nStation = " + mStation +
                "\nStation ID = " + mId +
                "\nLongitude = " + mLongitude +
                "\nLatitude = " + mLatitude + "\n";

        String strCity = super.toString();

        String strDivide = "\n-----------------------------------------------------------------\n";

        String strResult = strDivide + strCity + "\n" + strStations + strDivide;

        return strResult;
    }
}
