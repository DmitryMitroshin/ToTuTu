package com.devgmail.mitroshin.totutu.hosts;

import android.support.v4.app.Fragment;

import com.devgmail.mitroshin.totutu.controllers.InfoFragment;
import com.devgmail.mitroshin.totutu.util.SingleFragmentActivity;

// Хост для фрагмента InfoFragment.java

public class InfoActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new InfoFragment();
    }
}
