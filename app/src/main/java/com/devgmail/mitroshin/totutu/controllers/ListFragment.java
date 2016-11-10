package com.devgmail.mitroshin.totutu.controllers;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
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

//    Поле поиска
    private EditText mSearchEditText;

//    Кастомный адаптер
    private StationCursorAdapter stationsCursorAdapter;

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

        mSearchEditText = (EditText) view.findViewById(R.id.list_edit_search);

//        При отображении списка нам нужны не все данные из базы данных, а только соответствующие
//        выбранному пользователем направлению. Для этого с интентом передается информация о направлении
        mDirectionType = (String) getActivity().getIntent().
                getSerializableExtra(EXTRA_DIRECTION_TYPE);

//         Нужно запросить из базы необходимые для отображения в элементе списка заголовки и
//         идентификатор, который будет передаваться для отображения подробной информации о
//         станции в активность Info.
        mCursor = mDatabaseHelper.database.rawQuery(generateDefaultQuery(mDirectionType), null);

//         Данные после получения результатов запроса нужно адаптировать.
//         Есть несколько дефолтных адаптеров, в данном случае реализован отдельный класс.
        stationsCursorAdapter = new StationCursorAdapter(getActivity()
                .getApplicationContext(), mCursor);

        if (!mSearchEditText.getText().toString().isEmpty()) {
            stationsCursorAdapter.getFilter().filter(mSearchEditText.getText().toString());
        }

        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            // При изменении текста в поле ввода, будет выполняться фильтрация
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("    *** Text Change *** ");
                stationsCursorAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        stationsCursorAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence constraint) {

                System.out.println("    *** Run Query *** ");

                if (constraint == null || constraint.length() == 0) {

                    System.out.println("    *** Constraint == null || or length == 0 *** ");

                    return mDatabaseHelper.database.rawQuery(generateDefaultQuery(mDirectionType), null);
                } else {

                    System.out.println("    *** Constraint != null *** ");

                    return mDatabaseHelper.database.rawQuery(generateSearchQuery(mDirectionType),
                            new String[] {"%" + constraint.toString() + "%"});
                }
            }
        });

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

//    Запрос без поиска подстроки
    private String generateDefaultQuery (String directionType) {
        return firstPartOfQuery(directionType) + secondPartOfQuerty();
    }

//    Данный запрос дублирует дефолтный, но в нем установлена дополнительная проверка, на совпадение строки.
//    Так как эта проверка должна идти перед сортировкой - пришлось разделить строку для удобства
    private String generateSearchQuery (String directionType) {
        return firstPartOfQuery(directionType) + searchPartOfQuery() + secondPartOfQuerty();
    }

//    Первая часть запроса. Между первой и второй частью в последствии будет добавляться условие проверки
    private String firstPartOfQuery(String directionType) {
        return "SELECT " + mDatabaseHelper.COUNTRY_TITLE +
                ", " + mDatabaseHelper.CITY_TITLE + ", " + mDatabaseHelper.STATION_TITLE +
                ", " + mDatabaseHelper.STATIONS_TABLE + "." + mDatabaseHelper.STATION_ID + " FROM " +
                mDatabaseHelper.CITIES_TABLE + ", " + mDatabaseHelper.STATIONS_TABLE + " WHERE (" +
                mDatabaseHelper.CITY_DIRECTION + " LIKE '" + directionType + "' OR " +
                mDatabaseHelper.CITY_DIRECTION + " LIKE 'Both') AND (" +
                mDatabaseHelper.STATION_DIRECTION + " LIKE '" + directionType + "' OR " +
                mDatabaseHelper.STATION_DIRECTION + " LIKE 'Both') AND (" +
                mDatabaseHelper.CITIES_TABLE + "." + mDatabaseHelper.CITY_CITY_ID + " = " +
                mDatabaseHelper.STATION_CITY_ID + ")";
    }

//    Часть запроса, отвечающая за сортировку
    private String secondPartOfQuerty() {
        return " ORDER BY " + mDatabaseHelper.COUNTRY_TITLE + ", " + mDatabaseHelper.CITY_TITLE;
    }

//    Часть запроса, отвечающая за поиск подстроки
    private String searchPartOfQuery() {
        return " AND (" + mDatabaseHelper.STATION_TITLE + " LIKE ? )";
    }
}
