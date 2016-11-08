package com.devgmail.mitroshin.totutu.controllers;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.devgmail.mitroshin.totutu.R;
import com.devgmail.mitroshin.totutu.hosts.ListActivity;
import com.devgmail.mitroshin.totutu.model.Station;
import com.devgmail.mitroshin.totutu.util.DatabaseHelper;
import com.devgmail.mitroshin.totutu.util.StationCursorAdapter;

import static android.R.attr.id;

//Контроллер для представления fragment_list.xml

public class ListFragment extends Fragment implements AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener{

    private ListView mListView;
    private DatabaseHelper mDatabaseHelper;
    private Cursor mCursor;
    private String mDirectionType;

    private Station mStation;
    private Cursor mStationCursor;

//    public String getDirectionType() {
//        return mDirectionType;
//    }

    public static final String EXTRA_STATION_ID = "com.devgmail.mitroshin.totutu.extra_station_id";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabaseHelper = new DatabaseHelper(getActivity().getApplicationContext());
        mDatabaseHelper.open();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);

        mListView = (ListView) view.findViewById(R.id.list_list_view);
        mListView.setOnItemClickListener(this);
        mListView.setOnItemLongClickListener(this);

        mDirectionType = (String) getActivity().getIntent().
                getSerializableExtra(ListActivity.EXTRA_DIRECTION_TYPE);


        // Нужно запросить из базы необходимые для отображения в элементе списка заголовки и
        // идентификатор, который будет передаваться для отображения подробной информации о
        // станции в активность Info.
        mCursor = mDatabaseHelper.database.rawQuery("SELECT " + mDatabaseHelper.COUNTRY_TITLE +
                ", " + mDatabaseHelper.CITY_TITLE + ", " + mDatabaseHelper.STATION_TITLE +
                ", " + mDatabaseHelper.STATIONS_TABLE + "." + mDatabaseHelper.STATION_ID + " FROM " +
                mDatabaseHelper.CITIES_TABLE + ", " + mDatabaseHelper.STATIONS_TABLE + " WHERE (" +
                mDatabaseHelper.CITY_DIRECTION + " LIKE '" + mDirectionType + "' OR " +
                mDatabaseHelper.CITY_DIRECTION + " LIKE 'Both') AND (" +
                mDatabaseHelper.STATION_DIRECTION + " LIKE '" + mDirectionType + "' OR " +
                mDatabaseHelper.STATION_DIRECTION + " LIKE 'Both') AND (" +
                mDatabaseHelper.CITIES_TABLE + "." + mDatabaseHelper.CITY_CITY_ID + " = " +
                mDatabaseHelper.STATION_CITY_ID + ")" +
                "ORDER BY " + mDatabaseHelper.COUNTRY_TITLE + ", " + mDatabaseHelper.CITY_TITLE, null);

        // Данные после получения результатов запроса нужно адаптировать.
        // Есть несколько дефолтных адаптеров, в данном случае реализован отдельный класс.
        StationCursorAdapter stationsCursorAdapter = new StationCursorAdapter(getActivity()
                .getApplicationContext(), mCursor);
        mListView.setAdapter(stationsCursorAdapter);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDatabaseHelper.database.close();
        mCursor.close();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long stationId) {

//        System.out.println("ID = " + id);

        mStationCursor = mDatabaseHelper.database.rawQuery("SELECT * FROM " +
                mDatabaseHelper.STATIONS_TABLE + ", " + mDatabaseHelper.CITIES_TABLE + " WHERE " +
                mDatabaseHelper.STATIONS_TABLE + "." + mDatabaseHelper.STATION_ID + " = '" + stationId +
                "' AND " + mDatabaseHelper.STATION_CITY_ID + " = " + mDatabaseHelper.CITIES_TABLE +
                "." + mDatabaseHelper.CITY_CITY_ID, null);

        mStationCursor.moveToFirst();

        mStation = new Station(mStationCursor, stationId);

//        Intent data = new Intent();
//        data.putExtra(EXTRA_STATION_ID, id);
//        getActivity().setResult(Activity.RESULT_CANCELED, data);
//        System.out.println("Result send");

        System.out.println(mStation);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }

    public static Long resultStationId(Intent result) {
        return result.getLongExtra(EXTRA_STATION_ID, 0L);
    }
}
