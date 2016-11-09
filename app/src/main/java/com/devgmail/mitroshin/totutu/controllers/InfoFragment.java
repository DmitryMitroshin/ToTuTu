package com.devgmail.mitroshin.totutu.controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devgmail.mitroshin.totutu.R;
import com.devgmail.mitroshin.totutu.hosts.InfoActivity;
import com.devgmail.mitroshin.totutu.model.Station;

//Контроллер для представления fragment_info.xml

public class InfoFragment extends Fragment {

    private TextView mStationTitle;
    private TextView mStationId;
    private TextView mCityTitle;
    private TextView mCityId;
    private TextView mCountry;
    private TextView mRegion;
    private TextView mDistrict;
    private TextView mStationLongitude;
    private TextView mStationLatitude;
    private TextView mCityLongitude;
    private TextView mCityLatitude;

    private Station mCurrentStation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_info, container, false);

        mStationTitle = (TextView) view.findViewById(R.id.info_text_station_title);
        mStationId = (TextView) view.findViewById(R.id.info_text_station_id);
        mCityTitle = (TextView) view.findViewById(R.id.info_text_city_title);
        mCityId = (TextView) view.findViewById(R.id.info_text_city_id);
        mCountry = (TextView) view.findViewById(R.id.info_text_country);
        mRegion = (TextView) view.findViewById(R.id.info_text_region);
        mDistrict = (TextView) view.findViewById(R.id.info_text_district);
        mStationLongitude = (TextView) view.findViewById(R.id.info_text_longitude_station);
        mStationLatitude = (TextView) view.findViewById(R.id.info_text_latitude_station);
        mCityLongitude = (TextView) view.findViewById(R.id.info_text_longitude_city);
        mCityLatitude = (TextView) view.findViewById(R.id.info_text_latitude_city);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

//          Спасибо мне из прошлого, что сделал удобный способ для передачи объекта,
//          а не через индекс и запрос в базу
        mCurrentStation = getActivity().getIntent().getParcelableExtra(InfoActivity.
                EXTRA_STATION_OBJECT_TO_INFO);

        System.out.println(mCurrentStation);

        mStationTitle.setText(mCurrentStation.getStation());
        mStationId.setText(mCurrentStation.getStationId().toString());
        mCityTitle.setText(mCurrentStation.getCity());
        mCityId.setText(mCurrentStation.getId().toString());
        mCountry.setText(mCurrentStation.getCountry());
        mRegion.setText(mCurrentStation.getRegion());
        mDistrict.setText(mCurrentStation.getDistrict());
        mStationLongitude.setText(mCurrentStation.getStationLongitude().toString());
        mStationLatitude.setText(mCurrentStation.getStationLatitude().toString());
        mCityLongitude.setText(mCurrentStation.getLongitude().toString());
        mCityLatitude.setText(mCurrentStation.getLatitude().toString());
    }
}
