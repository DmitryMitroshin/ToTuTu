package com.devgmail.mitroshin.totutu.hosts;

import android.support.v4.app.Fragment;

import com.devgmail.mitroshin.totutu.controllers.AboutFragment;
import com.devgmail.mitroshin.totutu.util.SingleFragmentActivity;

// Хост для фрагмента AboutFragment.java

public class AboutActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new AboutFragment();
    }
}
