package com.devgmail.mitroshin.totutu.model;

import android.database.Cursor;

import com.devgmail.mitroshin.totutu.util.DatabaseHelper;

public class Station extends City {

    private String mStation;
    private Long mId;
    private Double mLongitude;
    private Double mLatitude;
//    private Long mCityId;

    public Station(Cursor stationCursor, Long stationId) {
        super(stationCursor);

        DatabaseHelper mDatabaseHelper = null;

        this.mStation = stationCursor.getString(stationCursor.
                getColumnIndexOrThrow(mDatabaseHelper.STATION_TITLE));
//        this.mId = stationCursor.getLong(stationCursor.
//                getColumnIndexOrThrow(mDatabaseHelper.STATIONS_TABLE + "." + mDatabaseHelper.STATION_ID));
////                getColumnIndexOrThrow(mDatabaseHelper.STATION_ID));
        this.mId = stationId;
        this.mLongitude = stationCursor.getDouble(stationCursor.
                getColumnIndexOrThrow(mDatabaseHelper.STATION_LONGITUDE));
        this.mLatitude = stationCursor.getDouble(stationCursor.
                getColumnIndexOrThrow(mDatabaseHelper.STATION_LATITUDE));

    }

    public String getStation() {
        return mStation;
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

//    public Long getCityId() {
//        return mCityId;
//    }

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
