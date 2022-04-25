package com.project.coink.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.project.coink.MainRegistro;
import com.project.coink.R;
import com.project.coink.utilitarios.EnumTextos;
import com.project.coink.utilitarios.Utilidades;


public class FragmenRegistro extends Fragment implements Button.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registro, container, false);

        Button btRegistrar = view.findViewById(R.id.bt_registrar);
        Button btIngresar = view.findViewById(R.id.bt_ingresa);

        btIngresar.setOnClickListener(this);
        btRegistrar.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.bt_registrar:
                RegistrarUsuario();
                break;

            case R.id.bt_ingresa:
                Utilidades.mensageToast(getContext(), EnumTextos.UN_DESARROLLO.getValue());
                break;
        }

    }

    private void RegistrarUsuario() {
        final Intent homeIntent = new Intent(getContext(), MainRegistro.class);
        getActivity().finish();
        startActivity(homeIntent);


    }
}