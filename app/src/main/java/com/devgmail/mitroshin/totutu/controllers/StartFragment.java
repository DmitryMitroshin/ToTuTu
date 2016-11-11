package com.devgmail.mitroshin.totutu.controllers;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.devgmail.mitroshin.totutu.R;
import com.devgmail.mitroshin.totutu.hosts.InfoActivity;
import com.devgmail.mitroshin.totutu.hosts.ListActivity;
import com.devgmail.mitroshin.totutu.model.Station;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

//Контроллер для представления fragment_start.xml


public class StartFragment extends Fragment implements View.OnClickListener {

//    Ключи для сохранения соответствующих объектов станций при изменении конфигурации
    private static final String SAVE_STATION_FROM = "save_station_from";
    private static final String SAVE_STATION_TO = "save_station_to";

    // Ссылки на поля, описывающие станцию отправления
    private static TextView fromStationTextView;
    private static TextView fromCityTextView;

    // Ссылки на поля, описывающие станцию назначения
    private TextView toStationTextView;
    private TextView toCityTextView;

    // Ссылки на кнопки вызова списка
    private static Button fromSetButton;
    private static Button toSetButton;

    // Ссылки на кнопки для отображения информации
    private static Button fromInfoButton;
    private Button toInfoButton;

    // Ссылка на кнопку вызова datepicker
    private static Button datePickerButton;

    // Код запроса к активности списка
    private static final int REQUEST_STATION_OBJECT = 0;

    // Объекты модели, для полей From и To
    private Station mCurrentStationFrom = null;
    private Station mCurrentStationTo = null;

    // От дочерней активности так же будет поступать информация о направлении
    private String mResultDirectionType = null;

    private DatePickerDialog mDatePickerDialog;
    private SimpleDateFormat mSimpleDateFormat;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//         Заполнение макета
        View view = inflater.inflate(R.layout.fragment_start, container, false);

        mSimpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

//         Связывание ссылок на виджеты и самих виджетов
        findAllViewById(view);

        datePickerButton.setInputType(InputType.TYPE_NULL);
        setDateField();

//         Назначение слушателя для кнопок
        setAllClickListener();

//        После смены конфигурации нужно проверить содержимое сохраненных ссылок.
        if (savedInstanceState != null ) {
            if (savedInstanceState.getParcelable(SAVE_STATION_FROM) != null) {
                mCurrentStationFrom = savedInstanceState.getParcelable(SAVE_STATION_FROM);
                updateStationUI(mCurrentStationFrom, "From");
            }

            if (savedInstanceState.getParcelable(SAVE_STATION_TO) != null) {
                mCurrentStationTo = savedInstanceState.getParcelable(SAVE_STATION_TO);
                updateStationUI(mCurrentStationTo, "To");
            }
        }

        return view;
    }

    private void updateStationUI(Station currentStation, String direction) {
        switch (direction) {
            case "From":
                fromStationTextView.setText(currentStation.getStation());
                fromCityTextView.setText(currentStation.getCity());
                break;
            case "To":
                toStationTextView.setText(currentStation.getStation());
                toCityTextView.setText(currentStation.getCity());
                break;
        }
    }

    private void setDateField() {

        Calendar myCalendar = Calendar.getInstance();

        mDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar myDate = Calendar.getInstance();
                myDate.set(year, month, dayOfMonth);
                datePickerButton.setText(mSimpleDateFormat.format(myDate.getTime()));
            }
        }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
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
                mDatePickerDialog.show();
                break;
        }
    }

//    Отрабатывает, когда на конкретный запрос активности пришел результат
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

    // Сохранение в объекте Bundle дополнительных данных.
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mCurrentStationFrom != null) {
            outState.putParcelable(SAVE_STATION_FROM, mCurrentStationFrom);
        }

        if (mCurrentStationTo != null) {
            outState.putParcelable(SAVE_STATION_TO, mCurrentStationTo);
        }
    }

    private void findAllViewById(View view) {
        fromSetButton = (Button) view.findViewById(R.id.start_button_from_set);
        toSetButton = (Button) view.findViewById(R.id.start_button_to_set);
        fromInfoButton = (Button) view.findViewById(R.id.start_button_from_info);
        toInfoButton = (Button) view.findViewById(R.id.start_button_to_info);
        datePickerButton = (Button) view.findViewById(R.id.start_button_date);
        fromStationTextView = (TextView) view.findViewById(R.id.start_text_from_station);
        fromCityTextView = (TextView) view.findViewById(R.id.start_text_from_city);
        toStationTextView = (TextView) view.findViewById(R.id.start_text_to_station);
        toCityTextView = (TextView) view.findViewById(R.id.start_text_to_city);
    }

    private void setAllClickListener() {
        fromSetButton.setOnClickListener(this);
        toSetButton.setOnClickListener(this);
        fromInfoButton.setOnClickListener(this);
        toInfoButton.setOnClickListener(this);
        datePickerButton.setOnClickListener(this);
    }
}
