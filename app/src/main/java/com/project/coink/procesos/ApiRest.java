package com.project.coink.procesos;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.project.coink.pojo.PojoDatos;
import com.project.coink.utilitarios.NegocioException;
import com.project.coink.utilitarios.Utilidades;

import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class ApiRest
{
    public String realizarGet(String urlGet) throws NegocioException
    {
        String urlLocal = urlGet;
        StringBuilder sb = new StringBuilder();
        String error = "error";
        try {
            URL url = new URL(urlLocal);
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
            }finally {
                urlConnection.disconnect();
            }

        }
        catch(Exception e){
            e.printStackTrace();
            Utilidades.logError("ERROR: ","error no se pudo realizar la peticion GET {}"+ e.getMessage());
            return error;
        }
        return sb.toString();
    }


}
