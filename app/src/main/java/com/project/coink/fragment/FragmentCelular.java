package com.project.coink.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.project.coink.R;


public class FragmentCelular extends Fragment implements EditText.OnClickListener {

    protected EditText etnumero;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_celular, container, false);

        etnumero = view.findViewById(R.id.et_numeroCeluar);
        etnumero.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.et_numeroCeluar:

        }
    }
}