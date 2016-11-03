package com.devgmail.mitroshin.totutu.util;

//Абстрактный класс.
//Наследники данного класса должны просто вернуть экземпляр соответствующего фрагмента,
//чтобы начать выполнять функцию хоста данного фрагмента.
//Наличие данного класса упрощает реализацию активностей-хостингов.

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.devgmail.mitroshin.totutu.R;

public abstract class SingleFragmentActivity extends FragmentActivity {

    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment fragment = fragmentManager.findFragmentById(R.id.container_frame_layout);
        // Вызов метода может произойти из-за изменения конфигурации,
        // в этом случае не нужно создавать новый экземпляр фрагмента
        if (fragment == null) {
            fragment = createFragment();
            fragmentManager.beginTransaction().add(R.id.container_frame_layout, fragment).commit();
        }
    }
}
