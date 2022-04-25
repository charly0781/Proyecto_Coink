package com.project.coink.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.project.coink.MainHome;
import com.project.coink.R;
import com.project.coink.procesos.ApiRest;
import com.project.coink.procesos.ValidarVacio;
import com.project.coink.utilitarios.EnumErrores;
import com.project.coink.utilitarios.EnumTextos;
import com.project.coink.utilitarios.NegocioException;
import com.project.coink.utilitarios.Utilidades;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FragmentDatosCta extends Fragment implements View.OnClickListener {

    /**
     * proceso encargado de validar vacios
     */

    private final ValidarVacio validar = new ValidarVacio();

    private String numCelular = "";
    private Spinner sp_tipoDoc;
    private EditText et_numDoc;
    private EditText et_fechaDoc;
    private EditText et_fechaNac;
    private Spinner sp_genero;
    private EditText et_correo;
    private EditText et_correoconfirma;
    private EditText et_pin;
    private EditText et_pinconfirma;
    private int dia, mes, anio;
    private final Calendar c = Calendar.getInstance();
    private DatePickerDialog dpicker;
    private ApiRest api = new ApiRest();
    List<String> lDocIdentidad = new ArrayList<>();
    List<String> lgenero = new ArrayList<>();
    private RequestQueue requestQueue;
    private JsonArrayRequest jsonArrayRequest;

    ProgressDialog pd;

    private final String URL_CONSULTA_GENERO = "https://api.bancoink.biz/qa/signup/genders?apiKey=030106";
    private final String URL_CONSULTA_TIPO_DOCUMENTO = "https://api.bancoink.biz/qa/signup/documentTypes?apiKey=030106";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            numCelular = getArguments().getString(EnumTextos.CELULAR.getValue());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_datos_cta, container, false);

        pd = new ProgressDialog(getContext());
        pd.setMessage("Cargando Datos");
        pd.setCancelable(false);
        pd.show();

        ImageView iv_back = v.findViewById(R.id.iv_back);
        Button bt_siguiente = v.findViewById(R.id.bt_siguiente);
        sp_tipoDoc = v.findViewById(R.id.sp_tipoDoc);
        et_numDoc = v.findViewById(R.id.et_numDoc);
        et_fechaDoc = v.findViewById(R.id.et_fechaDoc);
        et_fechaNac = v.findViewById(R.id.et_fechaNac);
        sp_genero = v.findViewById(R.id.sp_genero);
        et_correo = v.findViewById(R.id.et_correo);
        et_correoconfirma = v.findViewById(R.id.et_correoconfirma);
        et_pin = v.findViewById(R.id.et_pin);
        et_pinconfirma = v.findViewById(R.id.et_pinconfirma);

        cargaSpinner2(URL_CONSULTA_TIPO_DOCUMENTO, sp_tipoDoc, jsonArrayRequest);
        cargaSpinner2(URL_CONSULTA_GENERO, sp_genero, jsonArrayRequest);

        bt_siguiente.setOnClickListener(this);
        et_fechaDoc.setOnClickListener(this);
        et_fechaNac.setOnClickListener(this);
        iv_back.setOnClickListener(this);

        return v;
    }

    private void cargaSpinner2(String url, Spinner spinner, JsonArrayRequest jRequest)
    {

        requestQueue = Volley.newRequestQueue(getContext());
        jRequest = new JsonArrayRequest (Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<String> rta = cargarArray(response.toString());
                        cargarVista(rta, spinner);
                        pd.dismiss();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Utilidades.logError("GET", error.getMessage().toString());
                    }
                });
        requestQueue.add(jRequest);
    }

    private void cargarSpinner(String urlTemp, Spinner spinner)
    {
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    StringBuilder sb = new StringBuilder();
                    String error = "error";
                    try {
                        URL url = new URL(urlTemp);
                        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                        try{
                            InputStream inputStream =  urlConnection.getInputStream();
                            BufferedInputStream buffer = new BufferedInputStream(inputStream);

                            InputStreamReader inputReader = new InputStreamReader(buffer);
                            BufferedReader bufferedReader = new BufferedReader(inputReader);

                            String inputLine = bufferedReader.readLine();
                            while (inputLine   != null) {
                                sb.append(inputLine + "\n");
                                inputLine = bufferedReader.readLine();
                            }
                            List<String> rta = cargarArray(sb.toString());
                            cargarVista(rta, spinner);
                            pd.dismiss();
                        }finally {
                            urlConnection.disconnect();
                        }

                    }
                    catch(Exception e){
                        e.printStackTrace();
                        Utilidades.logError("ERROR: ","error no se pudo realizar la peticion GET {}"+ e.getMessage());
                    }
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    private void cargarVista(List<String> lista, Spinner spinner)
    {
        spinner.setAdapter(new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, lista));

    }

    private List<String> cargarArray(String doc)  {
        List<String> rta = new ArrayList<>();
        try {
            if (!doc.equals("error"))
            {
                JSONArray json = new JSONArray(doc);
                for (int i =0; i<json.length(); i++)
                {
                    rta.add(json.getJSONObject(i).getString(EnumTextos.DESCRIPCION.getValue()));
                }
            }
        }catch (JSONException e)
        {
            Utilidades.mensageToast(getContext(), EnumErrores.ERROR_DATOS.getValue());
            Utilidades.logError("DATOS", EnumErrores.ERROR_DATOS.getValue());
        }
        return rta;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.bt_siguiente:
                try {
                    registrarDatos(validarVacios());
                } catch (NegocioException e) {
                    String TAG = "Datos_Cuenta";
                    Utilidades.logError(TAG, EnumErrores.ERROR_DATOS_CTA.getValue());
                }
                break;

            case R.id.et_fechaDoc:
                dia = c.get(Calendar.DAY_OF_MONTH);
                mes = c.get(Calendar.MONTH +1);
                anio = c.get(Calendar.YEAR);

                dpicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        et_fechaDoc.setText(String.valueOf(day + "/" + (month+1) + "/" + year));
                    }
                }, dia, mes, anio);
                dpicker.show();
                break;

            case R.id.et_fechaNac:
                dia = c.get(Calendar.DAY_OF_MONTH);
                mes = c.get(Calendar.MONTH+1);
                anio = c.get(Calendar.YEAR);

                dpicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        et_fechaNac.setText( String.valueOf(day + "/" + (month+1) + "/" + year));
                    }
                }, dia, mes, anio);
                dpicker.show();
                break;
            case R.id.iv_back:
               getActivity().onBackPressed();
                break;
        }

    }

    /**Metodo que obtiene los datos para ejecutar procesador
     * de validacion vacio
     * @return
     */

    private Boolean validarVacios() throws NegocioException
    {
        Map<String, Object> dato = new HashMap<>();
        dato.put(EnumTextos.CELULAR.getValue(), numCelular);
        dato.put(EnumTextos.TP_DOCUMENTO.getValue(), sp_tipoDoc.getSelectedItem().toString());
        dato.put(EnumTextos.FECHA_DOCUMENTO.getValue(), et_fechaDoc == null ? "" : et_fechaDoc.getText().toString());
        dato.put(EnumTextos.NUM_DOCUEMNTO.getValue(), et_numDoc.getText()  == null ? "" : et_numDoc.getText().toString());
        dato.put(EnumTextos.FECHA_NACIMIENTO.getValue(), et_fechaNac == null ? "" : et_fechaNac.getText().toString());
        dato.put(EnumTextos.GENERO.getValue(), sp_genero.getSelectedItem().toString());
        dato.put(EnumTextos.CORRERO.getValue(), et_correo == null ? "" : et_correo.getText().toString());
        dato.put(EnumTextos.CORREO_CONF.getValue(), et_correoconfirma == null ? "" : et_correoconfirma.getText().toString());
        dato.put(EnumTextos.PIN.getValue(), et_pin == null ? "" : et_pin.getText().toString());
        dato.put(EnumTextos.PIN_CONF.getValue(), et_pinconfirma == null ? "" :et_pinconfirma.getText().toString());

        Map<String, Object> rta =  validar.validarVacio(dato);

        if (rta.isEmpty()) {
            if (String.valueOf(et_pin.getText()).equals(String.valueOf(et_pinconfirma.getText())))
                return true;
            else {
                Utilidades.mensageToast(getContext(),
                        String.valueOf(rta.get(EnumTextos.ERROR_PIN.getValue())));
            }
        }
        else {
            Utilidades.mensageToast(getContext(),
                    String.valueOf(rta.get(EnumTextos.VACIOS.getValue())));
        }


        return false;
    }

    /**
     * Metodo encargado de registrar los datos y cargar pagina bienvenida
     * @param validarVacios
     */

    private void registrarDatos(Boolean validarVacios) {

        if (validarVacios)
        {
            Bundle args = new Bundle();

            args.putString(EnumTextos.CELULAR.getValue(), numCelular);
            args.putString(EnumTextos.TP_DOCUMENTO.getValue(), sp_tipoDoc.getSelectedItem().toString());
            args.putString(EnumTextos.FECHA_DOCUMENTO.getValue(), et_fechaDoc == null ? "" : et_fechaDoc.getText().toString());
            args.putString(EnumTextos.NUM_DOCUEMNTO.getValue(), et_numDoc.getText()  == null ? "" : et_numDoc.getText().toString());
            args.putString(EnumTextos.FECHA_NACIMIENTO.getValue(), et_fechaNac == null ? "" : et_fechaNac.getText().toString());
            args.putString(EnumTextos.GENERO.getValue(), sp_genero.getSelectedItem().toString());
            args.putString(EnumTextos.CORRERO.getValue(), et_correo == null ? "" : et_correo.getText().toString());
            args.putString(EnumTextos.CORREO_CONF.getValue(), et_correoconfirma == null ? "" : et_correoconfirma.getText().toString());
            args.putString(EnumTextos.PIN.getValue(), et_pin == null ? "" : et_pin.getText().toString());
            args.putString(EnumTextos.PIN_CONF.getValue(), et_pinconfirma == null ? "" :et_pinconfirma.getText().toString());

            Fragment fragment = new FragmentContrato();
            fragment.setArguments(args);

            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                    fragment).addToBackStack(null).commit();
        }
    }


}