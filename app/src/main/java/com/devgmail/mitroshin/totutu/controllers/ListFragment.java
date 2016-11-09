package com.devgmail.mitroshin.totutu.controllers;

import android.app.Activity;
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
import com.devgmail.mitroshin.totutu.model.Station;
import com.devgmail.mitroshin.totutu.util.DatabaseHelper;
import com.devgmail.mitroshin.totutu.util.StationCursorAdapter;

import static com.devgmail.mitroshin.totutu.hosts.ListActivity.EXTRA_DIRECTION_TYPE;

//Контроллер для представления fragment_list.xml

public class ListFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView mListView;
    private DatabaseHelper mDatabaseHelper;
    private Cursor mCursor;

//    Хранит направление текущего списка станций
    private String mDirectionType;

//     При клике на элемент списка, будет создаваться объект модели.

    private static Station mStation;
//     Перед записью в модель, данные нужно получить из базы.
    private Cursor mStationCursor;
    private Cursor mCityCursor;

//     При поиске Города из Станции нужно достать идентификатор родительского города.
    private Long mCityId;

//    Дополнение для объекта Станция
    public static final String EXTRA_REQUEST_STATION_OBJECT = "com.devgmail.mitroshin.totutu." +
        "extra_request_station_object";
//    Дополнение для типа направления
    public static final String EXTRA_REQUEST_DIRECTION_TYPE = "com.devgmail.mitroshin.totutu." +
        "extra_request_direction_type";

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

//        При отображении списка нам нужны не все данные из базы данных, а только соответствующие
//        выбранному пользователем направлению. Для этого с интентом передается информация о направлении
        mDirectionType = (String) getActivity().getIntent().
                getSerializableExtra(EXTRA_DIRECTION_TYPE);


//         Нужно запросить из базы необходимые для отображения в элементе списка заголовки и
//         идентификатор, который будет передаваться для отображения подробной информации о
//         станции в активность Info.
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

//         Данные после получения результатов запроса нужно адаптировать.
//         Есть несколько дефолтных адаптеров, в данном случае реализован отдельный класс.
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

//         После вызова этого метода mStation бедет ссылаться на объект станции,
//         в котором будет храниться ссылка на идентификатор родителя.
//         Родительский объект будет создаваться автоматически
        createModelByStationId(stationId);

//        Вернуться к стартовой активности можно:
//        * нажав на кнопку Back - RESULT_CANCELED
//        * нажав на элемент списка - RESULT_OK
//        Возвращая указатель на модель, мне нужно сообщить стартовой активности тип направления,
//        который был передан в эту активность в качестве дополнения к интенту.
        Intent data = new Intent();
        data.putExtra(EXTRA_REQUEST_STATION_OBJECT, mStation);
        data.putExtra(EXTRA_REQUEST_DIRECTION_TYPE, mDirectionType);
        getActivity().setResult(Activity.RESULT_OK, data);
        getActivity().finish();
    }

//    Информация о ключе к расширению является частью активности ListActivity или ее фрагмента.
//    Т.е. родительская активность при получении интента результата должна будет открыть дополнение
//    с помощью ключа, но делать его доступным для всех подряд не хорошо.
//    Поэтому родительская активность, получая результат передает его снова в дочернюю,
//    чтобы дочерняя его распаковала и вернула.
//    Для этого и созданы следующие два метода
    public static Station resultStationObject(Intent result) {
        return result.getParcelableExtra(EXTRA_REQUEST_STATION_OBJECT);
    }

    public static String resultDirectionType(Intent result) {
        return result.getStringExtra(EXTRA_REQUEST_DIRECTION_TYPE);
    }

//         Так как станция без города существовать не может, то ссылка на объект родительского
//         Класса будет передаваться вместе с ссылкой на объект дочернего.
//         Сначала нужно получить информацию о Станции, так как только класс потомок, знает о
//         существовании родителя. В классе City нет ссылок на дочерние элементы.
    public void createModelByStationId(Long stationId) {

        mStationCursor = mDatabaseHelper.database.rawQuery("SELECT * FROM " +
                mDatabaseHelper.STATIONS_TABLE + " WHERE " + mDatabaseHelper.STATION_ID +
                " = '" + stationId + "'", null);
        mStationCursor.moveToFirst();

        mCityId = mStationCursor.getLong(mStationCursor.
                getColumnIndexOrThrow(mDatabaseHelper.STATION_CITY_ID));

        mCityCursor = mDatabaseHelper.database.rawQuery("SELECT * FROM " +
                mDatabaseHelper.CITIES_TABLE + " WHERE " + mDatabaseHelper.CITY_CITY_ID +
                " = '" + mCityId + "'", null);
        mCityCursor.moveToFirst();

//        К этому моменту в курсорах City и Station хранится информация о выбранной
//        пользователем станции и о городе, в котором эта станция расположена
        mStation = new Station(mStationCursor, mCityCursor);
    }
}
