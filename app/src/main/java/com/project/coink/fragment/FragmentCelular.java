package com.project.coink.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.project.coink.MainHome;
import com.project.coink.R;
import com.project.coink.procesos.ValidarVacio;
import com.project.coink.utilitarios.EnumErrores;
import com.project.coink.utilitarios.EnumTextos;
import com.project.coink.utilitarios.NegocioException;
import com.project.coink.utilitarios.Utilidades;

import java.util.HashMap;
import java.util.Map;


public class FragmentCelular extends Fragment implements View.OnClickListener  {

    private String TAG = "NumCelilar";

    private ValidarVacio validar = new ValidarVacio();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_celular, container, false);

        ImageView iv_back = view.findViewById(R.id.iv_back);

        iv_back.setOnClickListener(this);

        EditText etnumero = view.findViewById(R.id.et_numeroCeluar);

        etnumero.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent key) {
                try {
                    cargarDatosCuenta(validarCelular(keyCode, key,String.valueOf(etnumero.getText())),
                            String.valueOf(etnumero.getText()));

                } catch (NegocioException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

        return view;
    }

    private void cargarDatosCuenta(boolean validarCelular, String celular)
    {
        if (validarCelular) {
            Bundle args = new Bundle();
            args.putString("CELULAR", celular);

            Fragment fragment = new FragmentDatosCta();
            fragment.setArguments(args);

            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                     fragment).addToBackStack(null).commit();
        }
    }

    private boolean validarCelular(int keyCode, KeyEvent key, String numCelular) throws NegocioException {

        if (keyCode == KeyEvent.KEYCODE_ENTER)
        {
            if (String.valueOf(numCelular).length() == 10)
            {
                Map<String, Object> dato = new HashMap<>();
                dato.put(EnumTextos.CELULAR.getValue(), numCelular);

                Map<String, Object> rta = validar.validarVacio(dato);

                if (rta.isEmpty())
                    return true;
                else
                    Utilidades.mensageToast(getContext(),
                            String.valueOf(rta.get(EnumTextos.VACIOS.getValue())));

            }else
                Utilidades.mensageToast(getContext(), EnumErrores.ERROR_NUM_CELULAR.getValue());
        }

        return false;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.iv_back:
                final Intent homeIntent = new Intent(getContext(), MainHome.class);
                getActivity().finish();
                startActivity(homeIntent);
                break;
        }
    }
}