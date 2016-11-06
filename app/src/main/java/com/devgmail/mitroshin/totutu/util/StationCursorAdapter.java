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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.devgmail.mitroshin.totutu.R;

public class StationCursorAdapter extends CursorAdapter implements View.OnClickListener{

    private DatabaseHelper mDatabaseHelper;

    public StationCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    private String mStringCountry;
    private String mStringStation;
    private String mStringCity;

    private TextView mTextCountry;
    private TextView mTextStation;
    private TextView mTextCity;

    private Button mButtonSet;
    private Button mButtonInfo;

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item, null, true);

        mButtonSet = (Button) view.findViewById(R.id.item_button_set);
        mButtonInfo = (Button) view.findViewById(R.id.item_button_info);

        mButtonSet.setOnClickListener(this);
        mButtonInfo.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_button_set:
                Toast.makeText(mContext, "Set ",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_button_info:
                Toast.makeText(mContext, "Info ",
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
