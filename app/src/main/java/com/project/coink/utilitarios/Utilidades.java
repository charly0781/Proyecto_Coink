package com.project.coink.utilitarios;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class Utilidades {

    public static void mensageToast(Context contexto, String mensaje)
    {
        Toast.makeText(contexto, mensaje, Toast.LENGTH_SHORT).show();
    }

    public static void mensageSnack(View v, String mensaje)
    {
        Snackbar.make(v, mensaje, Snackbar.LENGTH_LONG).show();
    }

    public static void logError(String tag, String mensaje)
    {
        Log.e(tag, mensaje);
    }

    public static void logInfo(String tag, String mensaje)
    {
        Log.i(tag, mensaje);
    }

}
