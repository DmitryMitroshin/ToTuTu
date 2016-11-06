package com.devgmail.mitroshin.totutu.controllers;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.devgmail.mitroshin.totutu.R;
import com.devgmail.mitroshin.totutu.hosts.ListActivity;
import com.devgmail.mitroshin.totutu.util.DatabaseHelper;
import com.devgmail.mitroshin.totutu.util.StationCursorAdapter;

//Контроллер для представления fragment_list.xml

public class ListFragment extends Fragment {

    private ListView mListView;
    private DatabaseHelper mDatabaseHelper;
    private Cursor mCursor;
    private String mDirectionType;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabaseHelper = new DatabaseHelper(getActivity().getApplicationContext());
        mDatabaseHelper.open();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mListView = (ListView) view.findViewById(R.id.list_list_view);
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
                mDatabaseHelper.STATION_DIRECTION + " LIKE 'Both') AND (" + mDatabaseHelper.CITIES_TABLE + "." +
                mDatabaseHelper.CITY_CITY_ID + " = " + mDatabaseHelper.STATION_CITY_ID + ")", null);

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
}
