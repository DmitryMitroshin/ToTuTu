package com.devgmail.mitroshin.totutu.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.devgmail.mitroshin.totutu.R;
import com.devgmail.mitroshin.totutu.hosts.ListActivity;

//Контроллер для представления fragment_start.xml


public class StartFragment extends Fragment implements View.OnClickListener{

    // Ссылки на поля, описывающие станцию отправления
    private TextView fromStationTextView;
    private TextView fromCityTextView;

    // Ссылки на поля, описывающие станцию назначения
    private TextView toStationTextView;
    private TextView toCityTextView;

    // Ссылки на кнопки вызова списка
    private Button fromSetButton;
    private Button toSetButton;

    // Ссылки на кнопки для отображения информации
    private Button fromInfoButton;
    private Button toInfoButton;

    // Ссылка на кнопку вызова datepicker
    private Button datePickerButton;

    // Ключ кода запроса для дочерней активности со списком
    private static final int RESULT_SET_STATION = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Заполнение макета
        View view = inflater.inflate(R.layout.fragment_start, container, false);

        // Связывание ссылок на виджеты и самих виджетов
        fromSetButton = (Button) view.findViewById(R.id.start_button_from_set);
        toSetButton = (Button) view.findViewById(R.id.start_button_to_set);
        fromInfoButton = (Button) view.findViewById(R.id.start_button_from_info);
        toInfoButton = (Button) view.findViewById(R.id.start_button_to_info);
        datePickerButton = (Button) view.findViewById(R.id.start_button_date);
        fromStationTextView = (TextView) view.findViewById(R.id.start_text_from_station);
        fromCityTextView = (TextView) view.findViewById(R.id.start_text_from_city);
        toStationTextView = (TextView) view.findViewById(R.id.start_text_to_station);
        toCityTextView = (TextView) view.findViewById(R.id.start_text_to_city);

        // Назначение слушателя для кнопок
        fromSetButton.setOnClickListener(this);
        toSetButton.setOnClickListener(this);
        fromInfoButton.setOnClickListener(this);
        toInfoButton.setOnClickListener(this);
        datePickerButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_button_from_info:
                Toast.makeText(getActivity(), "Отобразить информацию о пункте отправления",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.start_button_to_info:
                Toast.makeText(getActivity(), "Отобразить информацию о пункте прибытия",
                        Toast.LENGTH_SHORT).show();
            break;
            case R.id.start_button_from_set:
                Intent intentFrom = ListActivity.newIntent(getActivity(), "From");
                startActivity(intentFrom);
                break;
            case R.id.start_button_to_set:
                Intent intentTo = ListActivity.newIntent(getActivity(), "To");
                startActivity(intentTo);
                break;
            case R.id.start_button_date:
                Toast.makeText(getActivity(), "Отобразить диалоговое окно для выбора даты",
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
