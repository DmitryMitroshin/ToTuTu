package com.devgmail.mitroshin.totutu.controllers;

import android.app.Activity;
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
import com.devgmail.mitroshin.totutu.hosts.InfoActivity;
import com.devgmail.mitroshin.totutu.hosts.ListActivity;
import com.devgmail.mitroshin.totutu.model.Station;

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

    // Код запроса к активности списка
    private static final int REQUEST_STATION_OBJECT = 0;

    // Объекты модели, для полей From и To
    private Station mCurrentStationFrom = null;
    private Station mCurrentStationTo = null;

    // От дочерней активности так же будет поступать информация о направлении
    private String mResultDirectionType = null;

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
                if (mCurrentStationFrom == null) {
                    Toast.makeText(getActivity(), "Select the station",
                            Toast.LENGTH_SHORT).show();
                    break;
                }
                Intent intentInfoFrom = InfoActivity.newIntent(getActivity(), mCurrentStationFrom);
                startActivity(intentInfoFrom);
                break;
            case R.id.start_button_to_info:
                if (mCurrentStationTo == null) {
                    Toast.makeText(getActivity(), "Select the station",
                            Toast.LENGTH_SHORT).show();
                    break;
                }
                Intent intentInfoTo = InfoActivity.newIntent(getActivity(), mCurrentStationTo);
                startActivity(intentInfoTo);
                break;
            case R.id.start_button_from_set:
                Intent intentFrom = ListActivity.newIntent(getActivity(), "From");
                startActivityForResult(intentFrom, REQUEST_STATION_OBJECT);
                break;
            case R.id.start_button_to_set:
                Intent intentTo = ListActivity.newIntent(getActivity(), "To");
                startActivityForResult(intentTo, REQUEST_STATION_OBJECT);
                break;
            case R.id.start_button_date:
                Toast.makeText(getActivity(), "Отобразить диалоговое окно для выбора даты",
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//         Пользователь не выбрал станцию, а нажал на кнопку Back.
        if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }

//        Активность может вызывать несколько активностей, поэтому нужно убедиться,
//        что соответствующий результат пришел именно от соответствующей активности

        if (requestCode == REQUEST_STATION_OBJECT) {
            if (data == null) {
                return;
            }
            mResultDirectionType = ListFragment.resultDirectionType(data);

//            В зависимости от того в каком именно списке пользователь выбрал элемент,
//            нужно заполнить соответствующие информационные поля на родительской активности.
            updateUI(mResultDirectionType, data);
        }
    }

//    Метод должен заполнять поля From и To в соответствии с текущим значением объектов.
    private void updateUI(String directionType, Intent resultStationObject) {

        switch (directionType) {
            case "From":
                mCurrentStationFrom = ListFragment.resultStationObject(resultStationObject);
                fromStationTextView.setText(mCurrentStationFrom.getStation());
                fromCityTextView.setText(mCurrentStationFrom.getCity());
                break;
            case "To":
                mCurrentStationTo = ListFragment.resultStationObject(resultStationObject);
                toStationTextView.setText(mCurrentStationTo.getStation());
                toCityTextView.setText(mCurrentStationTo.getCity());
                break;
        }
    }
}
