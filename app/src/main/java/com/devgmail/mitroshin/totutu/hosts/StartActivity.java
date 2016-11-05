package com.devgmail.mitroshin.totutu.hosts;

// Хост для фрагмента StartFragment.java

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.devgmail.mitroshin.totutu.controllers.StartFragment;
import com.devgmail.mitroshin.totutu.util.DatabaseHelper;
import com.devgmail.mitroshin.totutu.util.SingleFragmentActivity;

public class StartActivity extends SingleFragmentActivity {

    private DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Создать базу данных если она еще не имеется.
        mDatabaseHelper = new DatabaseHelper(getApplicationContext());
        mDatabaseHelper.createDB();
    }

    @Override
    protected Fragment createFragment() {
        return new StartFragment();
    }
}
