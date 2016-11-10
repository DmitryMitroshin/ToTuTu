package com.devgmail.mitroshin.totutu.util;

// Класс адаптер, преобразует данные запроса, хранящиеся в курсоре,
// к необходимому для отображения виду.
// Связывает данные с конкретными виджетами

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devgmail.mitroshin.totutu.R;

public class StationCursorAdapter extends CursorAdapter {

    private DatabaseHelper mDatabaseHelper;

    public StationCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

//    private FilterQueryProvider mFilterQueryProvider;

    private String mStringCountry;
    private String mStringStation;
    private String mStringCity;

    private TextView mTextCountry;
    private TextView mTextStation;
    private TextView mTextCity;

//    @SuppressWarnings("deprection")
//    public StationCursorAdapter(Context context, Cursor cursor) {
//        super(context, cursor);
//    }
//
//    public StationCursorAdapter(Context context, Cursor c, boolean autoRequery) {
//        super(context, c, autoRequery);
//    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item, null, true);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        mTextCountry = (TextView) view.findViewById(R.id.item_text_country);
        mTextStation = (TextView) view.findViewById(R.id.item_text_station);
        mTextCity = (TextView) view.findViewById(R.id.item_text_city);

        mStringCountry = cursor.getString(cursor.getColumnIndexOrThrow(mDatabaseHelper.COUNTRY_TITLE));
        mStringStation = cursor.getString(cursor.getColumnIndexOrThrow(mDatabaseHelper.STATION_TITLE));
        mStringCity = cursor.getString(cursor.getColumnIndexOrThrow(mDatabaseHelper.CITY_TITLE));

        mTextCountry.setText(mStringCountry);
        mTextStation.setText(mStringStation);
        mTextCity.setText(mStringCity);
    }

//    @Override
//    public Filter getFilter() {
//        return super.getFilter();
//    }
//
//    @Override
//    public FilterQueryProvider getFilterQueryProvider() {
//        return super.getFilterQueryProvider();
//    }
//
//    @Override
//    public void setFilterQueryProvider(FilterQueryProvider filterQueryProvider) {
//        super.setFilterQueryProvider(filterQueryProvider);
//    }
}
