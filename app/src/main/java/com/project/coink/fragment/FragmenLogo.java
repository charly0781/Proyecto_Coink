package com.project.coink.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.coink.MainRegistro;
import com.project.coink.R;
import android.os.Handler;


public class FragmenLogo extends Fragment {

    private final long WAIT_TIME = 1*3000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        new Handler().postDelayed(() -> {
            cargarRegistro();
        },WAIT_TIME);

        return inflater.inflate(R.layout.fragment_logo, container, false);
    }

    private void cargarRegistro() {

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_homeactivity,
                new FragmenRegistro()).addToBackStack(null).commit();
    }
}