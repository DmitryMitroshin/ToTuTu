package com.devgmail.mitroshin.totutu.hosts;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.devgmail.mitroshin.totutu.controllers.InfoFragment;
import com.devgmail.mitroshin.totutu.model.Station;
import com.devgmail.mitroshin.totutu.util.SingleFragmentActivity;

// Хост для фрагмента InfoFragment.java

public class InfoActivity extends SingleFragmentActivity {

    public static final String EXTRA_STATION_OBJECT_TO_INFO =
            "com.devgmail.mitroshin.totutu.extra_station_object_to_info";

    @Override
    protected Fragment createFragment() {
        return new InfoFragment();
    }

    // Данный интент будет использоваться и для To и для From.
    public static Intent newIntent(Context packageContext, Station currentStation) {
        Intent intent = new Intent(packageContext, InfoActivity.class);
        intent.putExtra(EXTRA_STATION_OBJECT_TO_INFO, currentStation);
        return intent;
    }
}
