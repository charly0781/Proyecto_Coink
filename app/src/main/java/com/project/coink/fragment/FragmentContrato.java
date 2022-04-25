package com.project.coink.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.project.coink.MainActivity;
import com.project.coink.R;
import com.project.coink.procesos.ApiRest;
import com.project.coink.procesos.Contrato;
import com.project.coink.utilitarios.EnumErrores;
import com.project.coink.utilitarios.EnumTextos;
import com.project.coink.utilitarios.NegocioException;
import com.project.coink.utilitarios.Utilidades;

import java.util.HashMap;
import java.util.Map;


public class FragmentContrato extends Fragment implements Button.OnClickListener {

    private Map<String, Object> dato = new HashMap<>();

    private CheckBox cb_acepto;
    private View v;
    private Button bt_siguiente;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dato.put(EnumTextos.CELULAR.getValue(), getArguments().getString(EnumTextos.CELULAR.getValue()));
            dato.put(EnumTextos.TP_DOCUMENTO.getValue(), getArguments().getString(EnumTextos.TP_DOCUMENTO.getValue()));
            dato.put(EnumTextos.FECHA_DOCUMENTO.getValue(), getArguments().getString(EnumTextos.FECHA_DOCUMENTO.getValue()));
            dato.put(EnumTextos.NUM_DOCUEMNTO.getValue(), getArguments().getString(EnumTextos.NUM_DOCUEMNTO.getValue()));
            dato.put(EnumTextos.FECHA_NACIMIENTO.getValue(), getArguments().getString(EnumTextos.FECHA_NACIMIENTO.getValue()));
            dato.put(EnumTextos.GENERO.getValue(), getArguments().getString(EnumTextos.GENERO.getValue()));
            dato.put(EnumTextos.CORRERO.getValue(), getArguments().getString(EnumTextos.CORRERO.getValue()));
            dato.put(EnumTextos.CORREO_CONF.getValue(), getArguments().getString(EnumTextos.CORREO_CONF.getValue()));
            dato.put(EnumTextos.PIN.getValue(), getArguments().getString(EnumTextos.PIN.getValue()));
            dato.put(EnumTextos.PIN_CONF.getValue(), getArguments().getString(EnumTextos.PIN_CONF.getValue()));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         v= inflater.inflate(R.layout.fragment_contrato, container, false);

        cb_acepto = v.findViewById(R.id.cb_acepto);
        TextView tv_resumencontrato = v.findViewById(R.id.tv_resumencontrato);
        TextView tv_leecontrato = v.findViewById(R.id.tv_leecontrato);
        bt_siguiente = v.findViewById(R.id.bt_siguiente);
        ImageView iv_back = v.findViewById(R.id.iv_back);

        tv_leecontrato.setOnClickListener(this);
        bt_siguiente.setOnClickListener(this);
        cb_acepto.setOnClickListener(this);
        iv_back.setOnClickListener(this);

        tv_resumencontrato.setText(Contrato.getContrato());

        return v;
    }


    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.cb_acepto:;
                if (cb_acepto.isChecked())
                    bt_siguiente.setEnabled(true);
                else
                    bt_siguiente.setEnabled(false);
                break;
            case R.id.bt_siguiente:
                validarContrato();
                break;
            case R.id.tv_leecontrato:
               Utilidades.mensageToast(getContext(), EnumErrores.ERROR_OPCION.getValue());
                break;
            case R.id.iv_back:
                getActivity().onBackPressed();
                break;
        }

    }

    private void validarContrato() {
        if (cb_acepto.isChecked())
        {
            registrar();
            cagaPrincipal();
        }else
        {
            Utilidades.mensageSnack(v, EnumErrores.ERROR_CONTRATO.getValue());
        }
    }

    private void cagaPrincipal()
    {
        Intent homeIntent = new Intent(getContext(), MainActivity.class);
        homeIntent.putExtra(EnumTextos.MENSAJE.getValue(), EnumTextos.SI.getValue());
        getActivity().finish();
        startActivity(homeIntent);

    }

    private void registrar()
    {
        Log.i("REGISTRO", EnumErrores.REGISTRO_OK.getValue());
    }
}