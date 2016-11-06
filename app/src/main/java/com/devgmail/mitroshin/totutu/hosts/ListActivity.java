package com.devgmail.mitroshin.totutu.hosts;

// Хост для фрагмента ListFragment.java

import android.support.v4.app.Fragment;

import com.devgmail.mitroshin.totutu.controllers.ListFragment;
import com.devgmail.mitroshin.totutu.util.SingleFragmentActivity;

public class ListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new ListFragment();
    }
}
