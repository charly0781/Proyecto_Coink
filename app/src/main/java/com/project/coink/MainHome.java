package com.project.coink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.project.coink.fragment.FragmenLogo;

public class MainHome extends AppCompatActivity {

    public static final int MY_PERMISSIONS_INTERNET = 97;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /**
         * se puede controlar el tipo de inicio por medio de
         * fragmentos volviendolos dinamicos
         */
        Inicio();
    }

    private void Inicio() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fr_homeactivity,
                new FragmenLogo()).commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_INTERNET:
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}