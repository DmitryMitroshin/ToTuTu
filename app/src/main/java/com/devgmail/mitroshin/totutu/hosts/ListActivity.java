package com.devgmail.mitroshin.totutu.hosts;

// Хост для фрагмента ListFragment.java

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.devgmail.mitroshin.totutu.controllers.ListFragment;
import com.devgmail.mitroshin.totutu.util.SingleFragmentActivity;

public class ListActivity extends SingleFragmentActivity {

    public static final String EXTRA_DIRECTION_TYPE = "com.devgmail.mitroshin.totutu.direction_type";

    @Override
    protected Fragment createFragment() {
        return new ListFragment();
    }

    public static Intent newIntent (Context packageContext, String directionType) {
        Intent intent = new Intent(packageContext, ListActivity.class);
        intent.putExtra(EXTRA_DIRECTION_TYPE, directionType);
        return intent;
    }
}
