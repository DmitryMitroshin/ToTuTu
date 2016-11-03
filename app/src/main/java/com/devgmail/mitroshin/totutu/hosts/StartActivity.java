package com.devgmail.mitroshin.totutu.hosts;

// Хост для фрагмента StartFragment.java

import android.support.v4.app.Fragment;

import com.devgmail.mitroshin.totutu.controllers.StartFragment;
import com.devgmail.mitroshin.totutu.util.SingleFragmentActivity;

public class StartActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return new StartFragment();
    }
}
